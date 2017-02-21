package texttime.android.app.texttime.Registration;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import CustomViews.CameraModule.OrientationManager;
import CustomViews.CameraModule.PhotoHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CamUtils;
import texttime.android.app.texttime.GeneralClasses.BaseActivityFull;
import texttime.android.app.texttime.R;

/**
 * Created by DELL on 10/19/2016.
 */

public class CameraTestActivity extends BaseActivityFull implements TextureView.SurfaceTextureListener, OrientationManager.OrientationListener {

    @BindView(R.id.cameraPreviewDisplay)
    TextureView cameraPreviewDisplay;
    @BindView(R.id.donut_progress)
    DonutProgress donutProgress;

    boolean isRecording = false;
    long recordingms = 0;
    TimerTask detectRecordingTask;
    Timer detectRecordingTimer;

    //-----Run Progress bar task
    TimerTask recordingTask;
    Timer recordingTimer;
    int progress = 0;

    CamUtils camUtils;
    Camera camera;
    SurfaceTexture surfaceTexture;
    @BindView(R.id.switchCamera)
    ImageView switchCamera;
    @BindView(R.id.switchFlashMode)
    ImageView switchFlashMode;
    private OrientationManager orientationManager;
    int currentRotation;
    private MediaRecorder mMediaRecorder;

    @Override
    protected void onPause() {
        super.onPause();
        if(camUtils!=null){
            camUtils.releaseCamera();
        }
        if(isRecording){
            releaseMediaRecorder();
            isRecording=false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCams(false);
    }

    private void initCams(boolean isVideo) {

        if(camera!=null)
            camUtils.releaseCamera();
        if(camUtils!=null)
          camUtils.setVideo(isVideo);
        else
        camUtils = new CamUtils(isVideo, cv.getWidth(1080), cv.getHeight(1920), this);

        try {
            camera = camUtils.initCamera();
        } catch (Exception e) {
           System.out.print(">>>"+e);
        }
        try {
            camera.setPreviewTexture(surfaceTexture);
            camera.startPreview();
            camera.autoFocus(null);
        } catch (Exception e) {}
    }


    //-----------Recording and image capture switch------------
    //-----------This code supports the animation to be played and detect if the intention is
    //-----------to capture the image or to record a video-----------------------------------
    //---Initialise the detect recording task-----
    Camera.Size size=null;
    private TimerTask reinitTimerTask() {
        TimerTask detectRecordingTask = new TimerTask() {
            @Override
            public void run() {
                recordingms += 100;
                if (recordingms > 1000) {
                    isRecording = true;
                    runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Recording started", Toast.LENGTH_LONG).show();
                            size= camUtils.getTheSuitablePreviewSize(true);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new MediaPrepareTask().execute();
                                }
                            },100);
                        }
                    });

                    if (detectRecordingTimer != null) {
                        detectRecordingTimer.cancel();
                        detectRecordingTimer = null;
                    }
                    //---Write code to start recording
                    //---Write code to start the recording animation.
                }
            }
        };
        return detectRecordingTask;
    }


    //---Initialise the recording task-----
    private TimerTask reinitRecordingTimerTask() {
        TimerTask detectRecordingTask = new TimerTask() {
            @Override
            public void run() {
                progress += 1;
                if (progress <= 2000) {
                    // isRecording=false;
                    runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            donutProgress.setProgress(progress);
                        }
                    });
                } else {
                    this.cancel();
                    recordingTask.cancel();
                    recordingTask = null;
                    progress = 0;
                }
            }
        };

        return detectRecordingTask;
    }

    private void startRecordingTimerBar() {
        if (recordingTimer != null) {
            recordingTimer.cancel();
            recordingTimer = null;
            if (recordingTask != null) {
                recordingTask.cancel();
            }
        }

        recordingTask = reinitRecordingTimerTask();
        recordingTimer = new Timer();
        recordingTimer.scheduleAtFixedRate(recordingTask, 0, 10);
    }

    //------Function to detect the recording---
    private void detectIsRecording() {

        recordingms = 0;
        if (detectRecordingTimer != null) {
            detectRecordingTimer.cancel();
            detectRecordingTimer = null;

            if (detectRecordingTask != null)
                detectRecordingTask.cancel();
        }
        detectRecordingTimer = new Timer();
        detectRecordingTask = reinitTimerTask();
        detectRecordingTimer.scheduleAtFixedRate(detectRecordingTask, 0, 100);
    }

    private void captureButtonAction() {
        donutProgress.setTextColor(Color.TRANSPARENT);
        donutProgress.setProgress(0);
        donutProgress.setMax(2000);
        donutProgress.setRotation(270);

        if(isActivity) {
            donutProgress.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int eventAction = motionEvent.getAction();
                    if (eventAction == MotionEvent.ACTION_DOWN) {
                        if (!isRecording)
                            detectIsRecording();
                        return true;
                    } else if (eventAction == MotionEvent.ACTION_UP || eventAction == MotionEvent.ACTION_CANCEL) {

                        if (detectRecordingTimer != null) {
                            detectRecordingTimer.cancel();
                            detectRecordingTimer = null;
                        }

                        if (isRecording) {
                            //--Write code to stop media recorder and
                            //--Stop the animation for the recording.
                            Toast.makeText(context, "Recording stopped", Toast.LENGTH_LONG).show();
                            releaseMediaRecorder();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initCams(false);
                                }
                            }, 200);

                            isRecording = false;


                            if (recordingTimer != null) {
                                recordingTimer.cancel();
                                recordingTimer = null;
                                recordingTask.cancel();
                                progress = 0;
                                donutProgress.setProgress(0);
                            }

                        } else {
                            Toast.makeText(context, "Image captured", Toast.LENGTH_LONG).show();
                            TakePhotoButtonTapped();
                        }
                    }
                    return false;
                }
            });
        }

        else {
            donutProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TakePhotoButtonTapped();
                }
            });
        }
    }

    //--------------------------------------------------------------


    boolean isActivity=true;
    private void TakePhotoButtonTapped() {
       // camUtils.setCameraDisplayOrientation(currentRotation);
        if(camUtils.isFront())
            camUtils.setCameraDisplayOrientation(270);
        else
            camUtils.setCameraDisplayOrientation(90);

        try {
            AppDelegate.getInstance().setVideoCaptured(false);
            camera.takePicture(null, null, new PhotoHandler(context, isActivity));
        } catch (Exception e) {

        }

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout_updated);
        ButterKnife.bind(this);
        init(this);
        try {
            isActivity = getIntent().getExtras().getBoolean("isActivity");
        }
        catch (Exception e){

        }
        captureButtonAction();
        initEnvironment();
        adjustUIComponents();
        setonclicklistener();
    }

    private void adjustUIComponents() {
        cv.adjustRelativeSquare(switchCamera, 80);
        cv.adjustRelativeSquare(switchFlashMode, 80);
    }

    private void setonclicklistener() {
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera = camUtils.flipCamera();
                camera.startPreview();
                try {
                    camera.setPreviewTexture(surfaceTexture);
                    if(!camUtils.isFlashSupported()){
                        setFlashMode("off");
                    }
                } catch (Exception e) {}
            }
        });

        switchFlashMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flashMode;

                if(camUtils.isFlashSupported()) {
                    if (view.getTag() != null) {
                        flashMode = (String) view.getTag();
                        if (flashMode.equalsIgnoreCase("off")) {
                            flashMode = "on";
                        } else if (flashMode.equalsIgnoreCase("on")) {
                            flashMode = "auto";
                        } else if (flashMode.equalsIgnoreCase("auto")) {
                            flashMode = "off";
                        }
                    } else {
                        flashMode = "on";
                    }

                    setFlashMode(flashMode);
                }
            }
        });
    }


    private void setFlashMode(String flashMode){
        switchFlashMode.setTag(flashMode);
        if(flashMode.equalsIgnoreCase("off")){
            camera=camUtils.setFlashOff();
            switchFlashMode.setBackgroundResource(R.mipmap.flash_off);
        }

        else if(flashMode.equalsIgnoreCase("on")){
            camera=camUtils.setFlashOn();
            switchFlashMode.setBackgroundResource(R.mipmap.flash);
        }

        else if(flashMode.equalsIgnoreCase("auto")){
            camera=camUtils.setFlashAutomatic();
            switchFlashMode.setBackgroundResource(R.mipmap.flash_auto);
        }

        if(camera!=null){
            camera.startPreview();
            try {
                camera.setPreviewTexture(surfaceTexture);
            }
            catch (Exception e){
                System.out.println(">>>>"+e);
            }
        }
    }
    private void initEnvironment() {
        cameraPreviewDisplay.setSurfaceTextureListener(this);
        orientationManager = new OrientationManager(context, SensorManager.SENSOR_DELAY_NORMAL, this);
        orientationManager.enable();

    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        this.surfaceTexture = surfaceTexture;
        initCams(false);
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

    @Override
    public void onOrientationChange(OrientationManager.ScreenOrientation screenOrientation) {
        switch (screenOrientation) {
            case PORTRAIT:
                currentRotation = 0;
                break;
            case REVERSED_PORTRAIT:
                currentRotation = 180;
                break;
            case REVERSED_LANDSCAPE:
                currentRotation = 270;
                break;
            case LANDSCAPE:
                currentRotation = 90;
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean prepareVideoRecorder(){
        // BEGIN_INCLUDE (configure_preview)
       // mCamera = CameraHelper.getDefaultCameraInstance();
        // We need to make sure that our preview and recording video size are supported by the
        // camera. Query camera to find all the sizes and choose the optimal size given the
        // dimensions of our preview surface.
      //  Camera.Parameters parameters = mCamera.getParameters();
       // List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
      //  Camera.Size optimalSize = CameraHelper.getOptimalPreviewSize(mSupportedPreviewSizes,
       //         mPreview.getWidth(), mPreview.getHeight());
        // Use the same size for recording profile.
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        if(size!=null) {
            profile.videoFrameWidth = size.width;
            profile.videoFrameHeight = size.height;
        }
        // likewise for the camera object itself.
      //  parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);
       // mCamera.setParameters(parameters);

        // END_INCLUDE (configure_preview)
        // BEGIN_INCLUDE (configure_media_recorder)
        mMediaRecorder = new MediaRecorder();
        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        mMediaRecorder.setCamera(camera);
        if(camUtils.isFront())
        mMediaRecorder.setOrientationHint(270);
        else
            mMediaRecorder.setOrientationHint(90);
        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT );
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(profile);
        // Step 4: Set output file
        mMediaRecorder.setOutputFile(getPath());
        mMediaRecorder.setMaxDuration(20000);
        // END_INCLUDE (configure_media_recorder)
        // Step 5: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (Exception e) {
          //  Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private String getPath(){
        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return "";

        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Video_TextTime" + ".mp4";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;
        File pictureFile = new File(filename);
        AppDelegate.getInstance().setClickedImage(filename);
        return filename;
    }
    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        return new File(sdDir, "TextTime");
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            // clear recorder configuration
            mMediaRecorder.reset();
            // release the recorder object
            mMediaRecorder.release();
            mMediaRecorder = null;
            // Lock camera for later use i.e taking it back from MediaRecorder.
            // MediaRecorder doesn't need it anymore and we will release it if the activity pauses.
            camera.lock();
            AppDelegate.getInstance().setVideoCaptured(true);
           // startActivityTransition(CustomizePostActivity.class);
        }
    }
    /**
     * Asynchronous task for preparing the {@link android.media.MediaRecorder} since it's a long blocking
     * operation.
     */
    class MediaPrepareTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();
                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
              finish();
            }
            startRecordingTimerBar();
        }
    }

}
