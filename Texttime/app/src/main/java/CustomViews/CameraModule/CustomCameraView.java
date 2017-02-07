package CustomViews.CameraModule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.R;


/**
 * Created by TextTime Android Dev on 8/6/2016.
 */
public class CustomCameraView extends FrameLayout implements TextureView.SurfaceTextureListener{

    int width,height;
    Context context;
    TextureView textureView;
    ImageView imageView;
    ImageView imageViewTrans;
    Camera camera;
    public CustomCameraView(Context context, int width) {
        super(context);
        this.context=context;

    }

    public CustomCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

    }

    public CustomCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomCameraView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
    }

    public void releaseCam(){
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera=null;
        }
    }

    public void setPreviewUpdated(Camera camera,int width){
        this.setTag("camera");
        this.removeAllViews();
        this.camera=camera;
        RelativeLayout relativeLayout;
        if(width>100)
        relativeLayout= (RelativeLayout) ((Activity)context).getLayoutInflater().inflate(R.layout.custom_cam_holder,null);
        else
            relativeLayout= (RelativeLayout) ((Activity)context).getLayoutInflater().inflate(R.layout.custom_cam_holder_small,null);

        ImageView imageTrans= (ImageView) relativeLayout.findViewById(R.id.imageTrans);
        ImageView imageMask= (ImageView) relativeLayout.findViewById(R.id.imageMask);
        TextureView camDisplay= (TextureView) relativeLayout.findViewById(R.id.camDisplay);

        CommonViewUtility.getInstance().adjustRelativeSquare(imageTrans,width);
        CommonViewUtility.getInstance().adjustRelativeSquare(imageMask,width);

        this.addView(relativeLayout);

        camDisplay.setSurfaceTextureListener(this);
    }



    public void setPreviewWOCamera(int width){
        this.setTag("gallery");
        this.removeAllViews();
        imageView=new ImageView(context);
        imageViewTrans=new ImageView(context);

        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(width,width);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);


        this.addView(imageViewTrans,lp);
        this.addView(imageView,lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewTrans.setScaleType(ImageView.ScaleType.FIT_XY);


        CommonViewUtility.getInstance().adjustFrameSquare(imageView,width);
        CommonViewUtility.getInstance().adjustFrameSquare(imageViewTrans,width);

        imageView.setImageResource(R.mipmap.profile_capture);
        imageViewTrans.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.trans_patch1));
    }

    public void setPreview(Camera camera,int width){
        this.setTag("camera");
        this.removeAllViews();

        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(width,width);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        this.camera=camera;
        textureView=new TextureView(context);
        this.addView(textureView,lp);

        imageView=new ImageView(context);
        imageViewTrans=new ImageView(context);




        this.addView(imageViewTrans,lp);
        this.addView(imageView,lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


      //  CommonViewUtility.getInstance().adjustFrameSquare(textureView,width);
        CommonViewUtility.getInstance().adjustFrameSquare(imageView,width);
        CommonViewUtility.getInstance().adjustFrameSquare(imageViewTrans,width);

        imageView.setImageResource(R.mipmap.profile_capture);
        imageViewTrans.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.trans_patch1));
        CommonViewUtility.getInstance().adjustRelativeSquare(this,width);
        textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, int i, int i1) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    camera.setPreviewTexture(surfaceTexture);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },1000);

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
