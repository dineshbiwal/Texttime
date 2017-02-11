package CustomViews.CameraModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.Registration.ImageCropActivity;


/**
 * Created by TextTime Android Dev on 8/9/2016.
 */
public class PhotoHandler implements Camera.PictureCallback {

    private final Context context;
    int cameraType;
    boolean isActivity;
    public PhotoHandler(Context context, boolean isActivity) {

        this.cameraType=cameraType;
        this.context = context;
        this.isActivity=isActivity;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

       // Appdelegate.getInstance().setData(data);
       File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            try {
                pictureFileDir.mkdirs();
            }
            catch (Exception e){
                System.out.println(">>>"+e);
            }
                Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_TextTime" + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;
        AppDelegate.getInstance().setClickedImage(filename);
        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(context, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();

            if(!isActivity)
            context.startActivity(new Intent(context,ImageCropActivity.class));

            else
              //  context.startActivity(new Intent(context,CustomizePostActivity.class));

            ((Activity)context).finish();
        } catch (Exception error) {
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }


    private File getDir() {
       // File sdDir= context.getExternalCacheDir();
        /*File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);*/
        return new File(context.getExternalCacheDir(), "TextTime");
    }
}
