package texttime.android.app.texttime;


import android.Manifest;
import android.annotation.TargetApi;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ChatUI.fragments.ChatFragment;
import CustomViews.CameraModule.CustomCameraView;
import CustomViews.CameraModule.CustomTextViewMedium;
import CustomViews.CustomTextViewBold;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.CamUtils;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.PermissionCode;
import texttime.android.app.texttime.GeneralClasses.BaseActivityFull;

/**
 * Created by TextTime Android Dev on 2/7/2017.
 */

public class ContainerActivity extends BaseActivityFull {
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
    @BindView(R.id.placeHolder)
    FrameLayout placeHolder;

    TransitionDrawable chatDrawable;
    TransitionDrawable groupDrawable;
    TransitionDrawable broadcastDrawable;
    TransitionDrawable profileDrawable;
    @BindView(R.id.customCameraView)
    CustomCameraView customCameraView;
    @BindView(R.id.cameraPreview)
    RelativeLayout cameraPreview;
    @BindView(R.id.settingsIcon)
    ImageView settingsIcon;
    @BindView(R.id.searchIcon)
    ImageView searchIcon;
    @BindView(R.id.chatlabel)
    CustomTextViewMedium chatlabel;
    @BindView(R.id.createNewChatIcon)
    ImageView createNewChatIcon;
    @BindView(R.id.onlinestatus)
    ImageView onlinestatus;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.settingsIcon1)
    ImageView settingsIcon1;
   /* @BindView(R.id.toolbar)
    Toolbar toolbar;*/


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void createIcons() {
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


    public void setUpActionbarChatList() {

        cv.adjustRelative(settingsIcon, 30, 30);
        cv.adjustRelative(settingsIcon1, 30, 30);
        cv.adjustRelativeSquare(onlinestatus, 12);
        cv.adjustRelative(searchIcon, 49, 52);
        cv.adjustRelativeSquare(createNewChatIcon, 54);
        cv.adjustRelativeMargin(settingsIcon1, CommonViewUtility.LEFT, 30);
        cv.adjustRelativeMargin(searchIcon, CommonViewUtility.LEFT, 78);
        cv.adjustRelativeMargin(settingsIcon, CommonViewUtility.RIGHT, 43);
        cv.adjustRelativeMargin(chatlabel, CommonViewUtility.LEFT, 46);
        cv.adjustRelativeMargin(onlinestatus, CommonViewUtility.LEFT, 28);

    }

    Camera camera;
    CamUtils cUtils;

    //-------Initiate the camera-------------------------------------------
    private void initCam() {

        cUtils = new CamUtils(false, cv.getWidth(93), cv.getWidth(93), this);

        try {
            if (cUtils.checkForCamera()) {
                camera = cUtils.initCamera();
            }
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

        if (reqh == newW) {
            cv.adjustRelativeSquare(customCameraView, newW);
        } else
            cv.adjustRelative(customCameraView, newW, reqh);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_updated);
        ButterKnife.bind(this);
        init(this);
        //setUpActionbarChatSelection();
        // setUpActionbarChatListSearch();
        setUpActionbarChatList();
        openNewFragment(new ChatFragment(), "Chat");
        cv.adjustRelativeHeight(bottomTabBar, 133);
        cv.adjustRelativeMargin(fab, CommonViewUtility.BOTTOM, 169);
        cv.adjustRelativeMargin(fab, CommonViewUtility.RIGHT, 39);
        //cv.adjustRelativeSquare(fab,116);
        createIcons();
        askPermission();
    }


    //--------Ask for the camera permission in the marshmallow devices-----------
    private void askPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // Marshmallow+
            if (!pm.checkPermission(Manifest.permission.CAMERA)) {
                pm.getPermission(Manifest.permission.CAMERA, PermissionCode.PERMISSIONCAMERA);
            } else {
                initCam();
            }
        } else {
            //below Marshmallow
            initCam();
        }


    }


    //-------Check if camera is enabled on the custom cameraview----
    private boolean isCameraEnabled() {
        String s = (String) customCameraView.getTag();
        if (s != null) {
            if (s.equalsIgnoreCase("gallery")) {
                return false;
            } else {
                return true;
            }
        } else
            return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pm.checkPermission(Manifest.permission.CAMERA)) {
            if (isCameraEnabled()) {
                if (camera == null)
                    initCam();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pm.checkPermission(Manifest.permission.CAMERA)) {
            if (isCameraEnabled()) {
                if (camera != null) {
                    try {
                        cUtils.releaseCamera();
                        camera = null;
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    Fragment currentFragment;

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

}
