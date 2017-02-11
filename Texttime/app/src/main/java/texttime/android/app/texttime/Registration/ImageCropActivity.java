package texttime.android.app.texttime.Registration;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.togoto.imagezoomcrop.cropoverlay.CropOverlayView;
import io.togoto.imagezoomcrop.photoview.IGetImageBounds;
import io.togoto.imagezoomcrop.photoview.PhotoView;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.R;


/**
 * @author GT
 */
public class ImageCropActivity extends Activity {

    public static final String TAG = "ImageCropActivity";
    private static final int ANCHOR_CENTER_DELTA = 10;

    PhotoView mImageView;
    CropOverlayView mCropOverlayView;
    @BindView(R.id.done_drop_image)
    LinearLayout doneDropImage;
    private ContentResolver mContentResolver;
    private final int IMAGE_MAX_SIZE = 1024;
    private final Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
    //Temp file to save cropped image
    private String mImagePath;
    private Uri mSaveUri = null;
    private Uri mImageUri = null;
    //File for capturing camera images
    private File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_PICK_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROPPED_PICTURE = 0x3;
    public static final String ERROR_MSG = "error_msg";
    public static final String ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croppingimage);
        ButterKnife.bind(this);
        mContentResolver = getContentResolver();
        mImageView = (PhotoView) findViewById(R.id.originalImage);
        mCropOverlayView = (CropOverlayView) findViewById(R.id.imageMask);
        mCropOverlayView.moveToCenter(CommonViewUtility.getInstance().getWidth(768), CommonViewUtility.getInstance().getHeight(1080), CommonViewUtility.getInstance().getWidth(650), this);
        doneDropImage.setOnClickListener(btnDoneListerner);
        mImageView.setImageBoundsListener(new IGetImageBounds() {
            @Override
            public Rect getImageBounds() {
                return mCropOverlayView.getImageBounds();
            }
        });
        createTempFile();
        mImagePath = mFileTemp.getPath();
        mSaveUri = getImageUri(mImagePath);
        mImageUri = getImageUri(mImagePath);
        init();
    }

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        mImageUri = getImageUri(AppDelegate.getInstance().getClickedImage());
       // final Bitmap bitmap = getBitmap(mImageUri);
        Glide.with(this).load(mImageUri).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                if (resource.getWidth() >= CommonViewUtility.getInstance().getWidth(650) && resource.getHeight() >= CommonViewUtility.getInstance().getWidth(650)) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);

                    float minScale = mImageView.setMinimumScaleToFit(drawable);
                    mImageView.setMaximumScale(minScale * 3);
                    mImageView.setMediumScale(minScale * 2);
                    if(minScale>=1)
                    mImageView.setScale(minScale);
                    mImageView.setImageDrawable(drawable);
                }

                else {

                    AppDelegate.getInstance().setClickedImage(null);
                    Toast.makeText(ImageCropActivity.this,"unable to use this image.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }


        });
    }


    private View.OnClickListener btnDoneListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveUploadCroppedImage();
        }
    };

    private void saveUploadCroppedImage() {
        boolean saved = saveOutput();
        if (saved) {
            //USUALLY Upload image to server here
            AppDelegate.getInstance().setCroppedImage(mImagePath);
            finish();
        } else {
            Toast.makeText(this, "Unable to save Image into your device.", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private void createTempFile() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("restoreState", true);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        try {
            startActivityForResult(intent, REQUEST_CODE_PICK_GALLERY);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No image source available", Toast.LENGTH_SHORT).show();
        }
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }




    private Bitmap getBitmap(Uri uri) {
        InputStream in = null;
        Bitmap returnedBitmap = null;
        try {
            in = mContentResolver.openInputStream(uri);
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;

            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            //First check
            ExifInterface ei = new ExifInterface(uri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    returnedBitmap = rotateImage(bitmap, 90);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    returnedBitmap = rotateImage(bitmap, 180);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    returnedBitmap = rotateImage(bitmap, 270);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                default:
                    returnedBitmap = bitmap;
            }
            return returnedBitmap;
        } catch (FileNotFoundException e) {
            // L.e(e);
        } catch (IOException e) {
            // L.e(e);
        }
        return null;
    }



    private boolean saveOutput() {
        Bitmap croppedImage = mImageView.getCroppedImage();

        if (mSaveUri != null && croppedImage!=null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            } finally {
                closeSilently(outputStream);
            }
        } else {
           if(croppedImage==null)
            Toast.makeText(ImageCropActivity.this,"Unable to use this image", Toast.LENGTH_LONG).show();
            Log.e(TAG, "not defined image url");
            return false;
        }
        if(croppedImage!=null)
        croppedImage.recycle();

        return true;
    }


    public void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }


    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public void userCancelled() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void errored(String msg) {
        Intent intent = new Intent();
        intent.putExtra(ERROR, true);
        if (msg != null) {
            intent.putExtra(ERROR_MSG, msg);
        }
        finish();
    }


}
