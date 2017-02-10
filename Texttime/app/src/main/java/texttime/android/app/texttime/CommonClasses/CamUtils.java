package texttime.android.app.texttime.CommonClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;

import java.util.List;



/**
 * Created by DELL on 10/21/2016.
 */

public class CamUtils {

    Camera camera;
    boolean isFront=true;
    boolean isVideo=false;
    Camera.Size previewSize;
    int camBackId,camFrontId;
    int currentOpenCamId=-1;
    Activity activity;
    int desiredPreviewWidth,desiredPreviewHeight;
    CommonViewUtility cv=CommonViewUtility.getInstance();
    Camera.Parameters parameters;

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public CamUtils(boolean isVideo, int desiredPreviewWidth, int desiredPreviewHeight, Activity activity) {
        this.isVideo = isVideo;
        this.desiredPreviewWidth = desiredPreviewWidth;
        this.desiredPreviewHeight = desiredPreviewHeight;
        this.activity=activity;
    }

    public Camera.Size getPreviewSize() {
        return previewSize;
    }

    public void setPreviewSize(Camera.Size previewSize) {
        this.previewSize = previewSize;
    }

    public int getCurrentOpenCamId() {
        return currentOpenCamId;
    }

    public void setCurrentOpenCamId(int currentOpenCamId) {
        this.currentOpenCamId = currentOpenCamId;
    }

    public int getCamBackId() {
        return camBackId;
    }

    public void setCamBackId(int camBackId) {
        this.camBackId = camBackId;
    }

    public int getCamFrontId() {
        return camFrontId;
    }

    public void setCamFrontId(int camFrontId) {
        this.camFrontId = camFrontId;
    }


    //----Get the cam Id values for the front and the back camera.
    //----Useful when the flash light is used to re initiate the camera.
    private void getCamIds() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        if(cameraCount>0) {
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    try {
                        camFrontId = camIdx;
                        if(currentOpenCamId==-1)
                        currentOpenCamId = camIdx;
                    } catch (RuntimeException e) {

                    }
                } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        camBackId = camIdx;
                    } catch (RuntimeException e) {

                    }
                }
            }

            if (currentOpenCamId <= -1) {
                currentOpenCamId = camBackId;
            }
        }
    }


    public boolean checkForCamera(){
       int cameraCount = Camera.getNumberOfCameras();
        if(cameraCount>0){
            return true;
        }

        else {
            return false;
        }
    }

    //------------- Optimal Preview size --------
    //---------Depending upon the parameters provided it will return the suitable preview size.
    //---------Params will be the for the smaller previews and the larger previews.
    //---------Also the params are for the video camera.

    public Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }


    public  Camera.Size getTheSuitablePreviewSize(boolean isVideo){
        if(isVideo){
            List<Camera.Size> mSupportedVideoSizes = parameters.getSupportedVideoSizes();
            previewSize=getOptimalPreviewSize(mSupportedVideoSizes,desiredPreviewWidth,desiredPreviewHeight);
        }

        else {
           /* if(desiredPreviewWidth > CommonViewUtility.getInstance().getWidth(450))
                previewSize=getOptimalPreviewSize(parameters.getSupportedPreviewSizes(),desiredPreviewWidth,desiredPreviewHeight);
            else*/
                previewSize=getOptimalPreviewSize(parameters.getSupportedPreviewSizes(),desiredPreviewWidth,desiredPreviewHeight);

            setPictureSizeImage();

        }

        parameters.setPreviewSize(previewSize.width,previewSize.height);
        return  previewSize;
    }


    private void setPictureSizeImage(){
        try {

            for(int i=0;i<parameters.getSupportedPictureSizes().size();i++){
                Camera.Size si=parameters.getSupportedPictureSizes().get(i);
                if(si.width>cv.getWidth(768) && si.height>cv.getWidth(1080) ) {
                    try {
                        parameters.setPictureSize(si.width, si.height);
                    }
                    catch (Exception e){
                        System.out.println("Error is >>>>>"+e);
                    }
                        break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Error is >>>>>"+e);

        }
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    public Camera initCamera(){
        getCamIds();
        if(camera!=null){
            try {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
            catch (Exception e){

            }
        }

        camera=Camera.open(currentOpenCamId);
        parameters=camera.getParameters();

        if(currentOpenCamId==camBackId)
            isFront=false;
        else
        isFront=true;

        getTheSuitablePreviewSize(isVideo);
        setUpCamera();
        camera.setParameters(parameters);

        Display display = activity.getWindowManager().getDefaultDisplay();

        if (display.getRotation() == Surface.ROTATION_0) {
            camera.setDisplayOrientation(90);
        }

        if (display.getRotation() == Surface.ROTATION_270) {
            camera.setDisplayOrientation(180);
        }

        return camera;
    }


    public Camera adjustBrightnessMin() {
        parameters.setExposureCompensation(parameters.getMinExposureCompensation());

        if (parameters.isAutoExposureLockSupported()) {
            parameters.setAutoExposureLock(false);
        }
        camera.setParameters(parameters);
        return camera;
    }

    public Camera adjustBrightnessMax() {
        parameters.setExposureCompensation(parameters.getMaxExposureCompensation());

        if (parameters.isAutoExposureLockSupported()) {
            parameters.setAutoExposureLock(false);
        }
        camera.setParameters(parameters);
        return camera;
    }


    public boolean isFlashSupported(){
        boolean isFlashSupported=false;
        if(currentOpenCamId==camFrontId)
            return false;
        else
            return true;
    }


    public void releaseCamera() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.setErrorCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            camera = null;
        }
    }


    public Camera setCameraDisplayOrientation(int currentRotation) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(currentOpenCamId, info);
        int degrees = currentRotation;


        Camera.Parameters params = camera.getParameters();
       /* if (degrees == 0) {
            if (currentOpenCamId == Camera.CameraInfo.CAMERA_FACING_FRONT)
                params.setRotation(270);

            else
                params.setRotation(90);
            System.out.println("Rotation 0 >>>>>>");

        } else if (degrees == 90) {
            System.out.println("Rotation 90 >>>>>>");
            params.setRotation(0);
        } else if (degrees == 270) {
            System.out.println("Rotation 270 >>>>>>");
            params.setRotation(180);
        } else if (degrees == 180) {
            System.out.println("Rotation 180 >>>>>>");
            params.setRotation(90);
        }*/

        params.setRotation(degrees);
        camera.setParameters(params);
        //camera.setDisplayOrientation(result);

        return camera;
    }


    public Camera setFlashOn(){
        if(isFlashSupported()){
            camera.stopPreview();
            camera.release();
            initCamera();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        }

        return camera;
    }

    public Camera setFlashOff(){
        if(isFlashSupported()){
            camera.stopPreview();
            camera.release();
            initCamera();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
        }

        return camera;
    }

    public Camera setFlashAutomatic(){
        if(isFlashSupported()){
            camera.stopPreview();
            camera.release();
            initCamera();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            camera.setParameters(parameters);
        }

        return camera;
    }

    public Camera flipCamera(){
        camera.stopPreview();
        camera.release();
        camera=null;

        if(currentOpenCamId==camBackId){
            currentOpenCamId=camFrontId;
        }
        else {
            currentOpenCamId=camBackId;
        }

        initCamera();

        return  camera;
    }


    private int rotation;

    @SuppressLint("InlinedApi")
    private void setUpCamera() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(currentOpenCamId, info);
        rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 270;
                break;

            default:
                break;
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // frontFacing
            rotation = (info.orientation + degree) % 360;
            rotation = (360 - rotation) % 360;
        } else {
            // Back-facing
            rotation = (info.orientation - degree + 360) % 360;
        }

        camera.setDisplayOrientation(rotation);
        if(parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO))
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

}
