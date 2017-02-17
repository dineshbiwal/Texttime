package texttime.android.app.texttime;


import android.annotation.TargetApi;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import CustomViews.CameraModule.CustomCameraView;
import CustomViews.CustomTextViewMedium;
import CustomViews.CustomTextViewBold;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.CamUtils;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.PermissionCode;
import texttime.android.app.texttime.GeneralClasses.BaseActivityFull;
import texttime.android.app.texttime.History.BroadcastFragment;

/**
 * Created by Dinesh_Text on 2/16/2017.
 */

public class ContainerActivity extends BaseActivityFull {

    @BindView(R.id.searchIcon)
    ImageView searchIcon;
    @BindView(R.id.heading_lable)
    CustomTextViewMedium headingLable;
    @BindView(R.id.icon1)
    ImageView icon1;
    @BindView(R.id.icon2)
    ImageView icon2;
    @BindView(R.id.icon3)
    ImageView icon3;
    @BindView(R.id.placeHolder)
    FrameLayout placeHolder;
    @BindView(R.id.chatIcon)
    ImageView chatIcon;
    @BindView(R.id.chatText)
    CustomTextViewBold chatText;
    @BindView(R.id.chatTab)
    RelativeLayout chatTab;
    @BindView(R.id.groupIcon)
    ImageView groupIcon;
    @BindView(R.id.groupText)
    CustomTextViewBold groupText;
    @BindView(R.id.groupTab)
    RelativeLayout groupTab;
    @BindView(R.id.customCameraView)
    CustomCameraView customCameraView;
    @BindView(R.id.cameraPreview)
    RelativeLayout cameraPreview;
    @BindView(R.id.composeTab)
    RelativeLayout composeTab;
    @BindView(R.id.broadCastIcon)
    ImageView broadCastIcon;
    @BindView(R.id.boadcastText)
    CustomTextViewBold boadcastText;
    @BindView(R.id.broadcastTab)
    RelativeLayout broadcastTab;
    @BindView(R.id.myProfileIcon)
    ImageView myProfileIcon;
    @BindView(R.id.requestText)
    CustomTextViewBold requestText;
    @BindView(R.id.myProfileTab)
    RelativeLayout myProfileTab;
    @BindView(R.id.bottomTabBar)
    LinearLayout bottomTabBar;

    Fragment currentFragment;
    TransitionDrawable chatDrawable;
    TransitionDrawable groupDrawable;
    TransitionDrawable broadcastDrawable;
    TransitionDrawable profileDrawable;
    Camera camera;
    CamUtils cUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);
        ButterKnife.bind(this);
        init(this);
        setupBottomBarEnvironment();
        setupActionBarEnvironment();
        askPermission();
        openNewFragment(new BroadcastFragment(), "BroadcastListAdapter");
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pm.checkPermission(android.Manifest.permission.CAMERA)) {
            if (isCameraEnabled()) {
                if (camera == null)
                    initCam();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pm.checkPermission(android.Manifest.permission.CAMERA)) {
            if (isCameraEnabled()) {
                if (camera != null) {
                    try {
                        cUtils.releaseCamera();
                        camera = null;
                    } catch (Exception e) {
                        Log.e("Error", "Getting error in camera releasing");
                    }
                }
            }
        }
    }

    public void openNewFragment(Fragment f, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            ft.show(fragment);
            ft.hide(currentFragment);
            currentFragment = fragment;
        } else {
            if (currentFragment != null)
                ft.hide(currentFragment);
            ft.add(R.id.placeHolder, f, tag);
            currentFragment = f;
        }
        ft.commit();
    }

    private void setupActionBarEnvironment(){
        cv.adjustRelativeMargin(searchIcon, CommonViewUtility.LEFT,120);
        cv.adjustRelativeMargin(searchIcon, CommonViewUtility.RIGHT, 40);
        cv.adjustRelativeMargin(headingLable, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearMargin(icon1, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearSquare(icon1, 67);
        cv.adjustLinearMargin(icon2, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearHeight(icon2, 72);
        cv.adjustLinearWidth(icon2, 85);
        cv.adjustLinearMargin(icon3, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearHeight(icon3, 72);
        cv.adjustLinearWidth(icon3, 57);
        icon1.setVisibility(View.VISIBLE);
        icon2.setVisibility(View.VISIBLE);
        icon3.setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setupBottomBarEnvironment(){
        cv.adjustRelativeHeight(bottomTabBar, 133);

        chatDrawable = createDrawable(R.mipmap.ic_tabbar_chat, R.mipmap.ic_tabbar_chat_choosen);
        groupDrawable = createDrawable(R.mipmap.ic_tabbar_groups, R.mipmap.ic_tabbar_groups_choosen);
        broadcastDrawable = createDrawable(R.mipmap.ic_tabbar_history, R.mipmap.ic_tabbar_history_choosen);
        profileDrawable = createDrawable(R.mipmap.ic_tabbar_me, R.mipmap.ic_tabbar_me_choosen);

        chatIcon.setBackground(chatDrawable);
        groupIcon.setBackground(groupDrawable);
        broadCastIcon.setBackground(broadcastDrawable);
        myProfileIcon.setBackground(profileDrawable);

        cv.adjustRelativeSquare(chatIcon, 94);
        cv.adjustRelativeSquare(groupIcon, 94);
        cv.adjustRelativeSquare(broadCastIcon, 94);
        cv.adjustRelativeSquare(myProfileIcon, 94);
        cv.adjustRelativeMargin(chatText, CommonViewUtility.TOP, 14);
        cv.adjustRelativeMargin(groupText, CommonViewUtility.TOP, 14);
        cv.adjustRelativeMargin(boadcastText, CommonViewUtility.TOP, 14);
        cv.adjustRelativeMargin(requestText, CommonViewUtility.TOP, 14);
    }

    //--------Ask for the camera permission in the marshmallow devices-----------
    private void askPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!pm.checkPermission(android.Manifest.permission.CAMERA))
                pm.getPermission(android.Manifest.permission.CAMERA, PermissionCode.PERMISSIONCAMERA);
            else
                initCam();
        } else
            initCam();
    }

    //-------Initiate the camera-------------------------------------------
    private void initCam() {
        cUtils = new CamUtils(false, cv.getWidth(93), cv.getWidth(93), this);
        try {
            if (cUtils.checkForCamera())
                camera = cUtils.initCamera();
        } catch (Exception e) {
            System.out.print("Error is >>>" + e);
        }
        if (camera != null) {
            customCameraView.setPreviewUpdated(camera, (93));
            Camera.Size s = camera.getParameters().getPreviewSize();
            maintainAspect(s.width, s.height);
        }
    }

    private void maintainAspect(int width, int height) {
        float ratio = width / height;
        int reqh = (93);
        int newW = (int) (ratio * reqh);
        if (reqh == newW)
            cv.adjustRelativeSquare(customCameraView, newW);
        else
            cv.adjustRelative(customCameraView, newW, reqh);
    }

    //-------Check if camera is enabled on the custom cameraview----
    private boolean isCameraEnabled() {
        String s = (String) customCameraView.getTag();
        if (s != null) {
            if (s.equalsIgnoreCase("gallery"))
                return false;
            else
                return true;
        } else
            return false;
    }
}
