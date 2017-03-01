package CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import texttime.android.app.texttime.R;


/**
 * Created by TextTime Android Dev on 7/28/2016.
 */
public class CustomImageView extends ImageView{

    int width,height;
    Context context;
    DiskCache cache;
    String url;

    public CustomImageView(Context context) {
        super(context);
        this.context=context;
        cache=new InternalCacheDiskCacheFactory(context).build();

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUrl(String url){
        this.url=url;
        cache=new InternalCacheDiskCacheFactory(context).build();
        if(cache.get(new StringSignature(url))!=null){
            Glide.with(context).load(cache.get(new StringSignature(url))).into(this);
        }
        else
        {
            this.setImageResource(R.mipmap.placeholder);
            if (url.contains(".jpg") || url.contains(".png") || url.contains(".jpeg")
                    || url.contains(".JPG") || url.contains(".PNG") || url.contains(".JPEG")
                    ) {
                Glide
                        .with(context)
                        .load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(new SimpleTarget<Bitmap>(width, height) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                if(resource!=null)
                                setDimesions(resource,true);
                            }
                        });
            } else {
                new LoadImageTask(url).execute();
            }
        }
    }

    public void setUrl(String url, int placeholder){
        this.url=url;
        if(!TextUtils.isEmpty(url))
        {
            if (url.contains(".jpg") || url.contains(".png") || url.contains(".jpeg")
                    || url.contains(".JPG") || url.contains(".PNG") || url.contains(".JPEG")
                    ) {
                Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>(width, height) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                setDimesions(resource, true);
                            }
                        });
            } else {
                new LoadImageTask(url).execute();
            }
        }else{
            this.setImageResource(placeholder);
        }
    }

    public void setUrl(byte[] bm){
        if(bm != null) {
            Glide.with(context)
                    .load(bm)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(width, height) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            setDimesions(resource, true);
                        }
                    });
        }
        else
            this.setImageResource(R.mipmap.placeholder);
    }

    public void setUrl(Uri image){
        this.url="";
        Glide
                .with(context)
                .load(image)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        if(resource!=null)
                        setDimesions(resource,false);
                    }
                });
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        cache=new InternalCacheDiskCacheFactory(context).build();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        cache=new InternalCacheDiskCacheFactory(context).build();
    }


    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        width=params.width;
        height=params.height;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setDimesions(Bitmap bitmap,boolean store){

       // Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        bitmap=Bitmap.createScaledBitmap(bitmap, width, height,false);
        Bitmap mask=createMaskBitmap(width,height);
        //Bitmap mask1=createMaskBitmap(width+20,height+20);

        final Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);


        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(bitmap, 0, 0, null);
       // mCanvas.drawBitmap(m1, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        mask.recycle();
        bitmap.recycle();
        mask=null;
        bitmap=null;

        BitmapDrawable db=new BitmapDrawable(getResources(),result);
        this.setBackground(db);
       // this.setImageBitmap(result);
        //Appdelegate.getInstance().getImageList().put(url,result);
        this.setScaleType(ScaleType.CENTER_INSIDE);
        //this.setBackgroundResource(R.drawable.image_new_mask1);
        //new TransformImageTask(bitmap).execute();

        if(store && !TextUtils.isEmpty(url)) {
            cache=new InternalCacheDiskCacheFactory(context).build();
            cache.put(new StringSignature(url),new DiskCache.Writer() {
                @Override
                public boolean write(File file) {
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        result.compress(Bitmap.CompressFormat.PNG, 100, out);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    } finally {
                        if(out != null){
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    }
                    return true;
                }
            });
        }
    }



    public Bitmap transformImages(Bitmap bitmap){

        // Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        bitmap=Bitmap.createScaledBitmap(bitmap,width,height,false);
        Bitmap mask=createMaskBitmap(width,height);
        Bitmap mask1=createMaskBitmap(width+20,height+20);

        final Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);


        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(bitmap, 0, 0, null);
        // mCanvas.drawBitmap(m1, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        mask.recycle();
        bitmap.recycle();
        mask=null;
        bitmap=null;
       // Appdelegate.getInstance().getImageList().put(url,result);
        DiskCache cache=new InternalCacheDiskCacheFactory(context).build();
        cache.put(new StringSignature(url), new DiskCache.Writer() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean write(File file) {
                try (OutputStream out = new FileOutputStream(file)) {
                    // mimic default behavior you can also use Bitmap.compress
                    BitmapResource resource = BitmapResource.obtain(result, null);
                    new BitmapEncoder().encode(resource, out);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
        return result;


        //this.setBackgroundResource(R.drawable.image_new_mask1);
    }


    /*class TransformImageTask extends AsyncTask{
        Bitmap result;
        Bitmap input;

        public TransformImageTask(Bitmap input) {
            this.input = input;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            BitmapDrawable db=new BitmapDrawable(getResources(),result);
            setBackground(db);
            //this.setImageBitmap(result);
            setScaleType(ScaleType.CENTER_INSIDE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            result=transformImages(input);
            return null;
        }
    }*/

    private void createBackGround(){

    }


    @Override
    public void setImageResource(int resId) {
        Bitmap bm=BitmapFactory.decodeResource(getResources(),resId);
        if(bm!=null)
        setDimesions(bm,false);
    }

    public void setDimesions(Drawable d){
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        Bitmap mask=createMaskBitmap(width,height);
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(bitmap, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        mask.recycle();
        bitmap.recycle();
        mask=null;
        bitmap=null;

        this.setImageBitmap(result);
        this.setScaleType(ScaleType.CENTER);
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Bitmap createMaskBitmap(int w, int h){
        Drawable d=getResources().getDrawable(R.mipmap.mask_photo,null);
        //Drawable d=getResources().getDrawable(R.mipmap.mask_photo,null);
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas c=new Canvas(bitmap);
        d.setBounds(0,0,w,h);

        d.draw(c);

        return bitmap;
    }



    private class LoadImageTask extends AsyncTask<Void,Void,Void>{

        String url="";
        Bitmap bm;
        public LoadImageTask(String url) {
            this.url = url;
        }

        public Bitmap getBitmapFromURL() {
            try {
                java.net.URL url = new java.net.URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected Void doInBackground(Void... voids) {
            bm=getBitmapFromURL();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(bm!=null)
            setDimesions(bm,true);
        }
    }

}
