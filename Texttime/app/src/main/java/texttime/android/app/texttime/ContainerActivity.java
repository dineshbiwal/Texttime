package texttime.android.app.texttime;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ChatUI.fragments.ChatFragment;
import ChatUI.fragments.ChatOptionsInterface;
import CustomViews.CameraModule.CustomCameraView;
import CustomViews.CustomTextViewBold;
import CustomViews.CustomTextViewMedium;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CamUtils;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.PermissionCode;
import texttime.android.app.texttime.GeneralClasses.BaseActivityFull;
import texttime.android.app.texttime.History.BroadcastFragment;

/**
 * Created by Dinesh_Text on 2/16/2017.
 */

public class ContainerActivity extends BaseActivityFull implements ChatOptionsInterface{

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
    @BindView(R.id.icon4)
    ImageView icon4;
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

    @BindView(R.id.chatlabel)
    CustomTextViewMedium chatlabel;
    @BindView(R.id.settingsIcon)
    ImageView settingsIcon;
    @BindView(R.id.createNewChatIcon)
    ImageView createNewChatIcon;
    @BindView(R.id.onlinestatus)
    ImageView onlinestatus;

    @BindView(R.id.settingsIcon1)
    ImageView settingsIcon1;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    View topBar, topBarChat,topBarChatOptions;
    @BindView(R.id.slidinganimationMenu)
    RelativeLayout slidinganimationMenu;
    @BindView(R.id.blockBackClickImage)
    ImageView blockBackClickImage;
    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.muteButton)
    ImageView muteButton;
    @BindView(R.id.archiveButton)
    ImageView archiveButton;
    @BindView(R.id.deleteButton)
    ImageView deleteButton;
    @BindView(R.id.numberofSelection)
    TextView numberofSelection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);
        ButterKnife.bind(this);
        init(this);

        topBar = findViewById(R.id.topBar);
        topBarChat = findViewById(R.id.topBarChat);
        topBarChatOptions= findViewById(R.id.topBarChatOptions);
        currentTopBar=topBar;
        AppDelegate.getInstance().setChatOptionsInterface(this);
        setupBottomBarEnvironment();
        setupActionBarEnvironment();
        askPermission();
        openNewFragment(new BroadcastFragment(), "BroadcastListAdapter");
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        fab.setVisibility(View.GONE);
        setonclicklistener();
        resetTransition(broadcastDrawable);
    }


    int MENU_DELETE_CHAT=1;
    int height=0;
    private void openLayout(int code) {
        currentCode=code;
        if(code==MENU_DELETE_CHAT){
            height=977;
            fab.setVisibility(View.GONE);
            archiveButton.setAlpha(0.5f);
            muteButton.setAlpha(0.5f);
        }

        blockBackClickImage.setAlpha(0);
        blockBackClickImage.setVisibility(View.VISIBLE);

        ValueAnimator anim = ValueAnimator.ofInt(0, cv.getHeight(height));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = slidinganimationMenu.getLayoutParams();
                layoutParams.height = val;
                slidinganimationMenu.setLayoutParams(layoutParams);
            }
        });


        anim.setDuration(200);
        anim.start();

        ObjectAnimator animator=ObjectAnimator.ofFloat(blockBackClickImage,"alpha",0,1.0f);
        animator.setDuration(200);
        animator.start();

        blockBackClickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLayout();
            }
        });
    }


    int currentCode;
    private void closeLayout() {

        if(currentCode==MENU_DELETE_CHAT){
            fab.setVisibility(View.VISIBLE);
            archiveButton.setAlpha(1f);
            muteButton.setAlpha(1f);
        }

        ValueAnimator anim = ValueAnimator.ofInt(cv.getHeight(height), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = slidinganimationMenu.getLayoutParams();
                layoutParams.height = val;
                slidinganimationMenu.setLayoutParams(layoutParams);
               // blockBackClickImage.setAlpha(val / 1000);
                if (val == 0) {
                    blockBackClickImage.setVisibility(View.GONE);
                }
            }
        });
        anim.setDuration(200);
        anim.start();

        ObjectAnimator animator=ObjectAnimator.ofFloat(blockBackClickImage,"alpha",1.0f,0f);
        animator.setDuration(200);
        animator.start();

    }


    private void setonclicklistener() {
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTransition(chatDrawable);
                setUpActionbarChatList();
                changeTopBarUpdated(topBarChat);
                openNewFragment(new ChatFragment(), "Chat");
                cv.adjustRelativeMargin(fab, CommonViewUtility.BOTTOM, 169);
                cv.adjustRelativeMargin(fab, CommonViewUtility.RIGHT, 39);
                fab.setVisibility(View.VISIBLE);

               // openLayout();


            }
        });

        broadcastTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTransition(broadcastDrawable);
                setupActionBarEnvironment();
                changeTopBarUpdated(topBar);
                openNewFragment(new BroadcastFragment(), "BroadcastListAdapter");
                fab.setVisibility(View.GONE);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLayout(MENU_DELETE_CHAT);
            }
        });
        slidinganimationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLayout();
            }
        });
    }


    private void resetTransition(TransitionDrawable drawable) {
        chatDrawable.resetTransition();
        groupDrawable.resetTransition();
        profileDrawable.resetTransition();
        broadcastDrawable.resetTransition();
        drawable.startTransition(400);
    }


    View currentTopBar;
    private void changeTopBar(final View newView){


        newView.setAlpha(0);
        newView.setVisibility(View.VISIBLE);
        ValueAnimator anim=ValueAnimator.ofInt(0,10000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue=(Integer)valueAnimator.getAnimatedValue();
                int prevValue=10000-currentValue;
                float oldAlpha=(float) (prevValue/10000);
                float newAlpha=(float) (currentValue/10000);
                newView.setAlpha(newAlpha);
                currentTopBar.setAlpha(oldAlpha);
                if(prevValue==0){
                    currentTopBar.setVisibility(View.GONE);
                    currentTopBar=newView;
                }

            }
        });

        anim.setDuration(500);
        anim.start();
    }



    private void changeTopBarUpdated(final View newView){


        newView.setAlpha(0);
        newView.setVisibility(View.VISIBLE);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(currentTopBar, "alpha",  1f, 0);
        fadeOut.setDuration(200);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(newView, "alpha", 0, 1f);
        fadeIn.setDuration(200);


        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.play(fadeIn).after(fadeOut);

        mAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                currentTopBar.setVisibility(View.GONE);
                currentTopBar=newView;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                currentTopBar.setVisibility(View.GONE);
                currentTopBar=newView;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

       /* ValueAnimator anim=ValueAnimator.ofInt(0,10000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue=(Integer)valueAnimator.getAnimatedValue();
                int prevValue=10000-currentValue;
                float oldAlpha=(float) (prevValue/10000);
                float newAlpha=(float) (currentValue/10000);
                newView.setAlpha(newAlpha);
                currentTopBar.setAlpha(oldAlpha);
                if(prevValue==0){
                    currentTopBar.setVisibility(View.GONE);
                    currentTopBar=newView;
                }

            }
        });*/


        mAnimationSet.start();
    }


    @Override
    public void onBackPressed() {
        if(AppDelegate.getInstance().isChatSelection()){
            AppDelegate.getInstance().getUserSelectionCallback().deactivateSelectionMode();
            changeTopBarUpdated(topBarChat);
            closeLayout();
        }

        else
        super.onBackPressed();

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


    public void setUpActionbarChatListOptions() {

        cv.adjustRelative(backButton, 37, 64);
        cv.adjustRelative(deleteButton, 48, 56);
        cv.adjustRelative(archiveButton,64, 57);
        cv.adjustRelative(muteButton, 57, 64);
        cv.adjustRelativeSquare(numberofSelection, 62);
        cv.adjustRelativeMargin(backButton, CommonViewUtility.LEFT, 61);
        cv.adjustRelativeMargin(muteButton, CommonViewUtility.RIGHT, 57);
        cv.adjustRelativeMargin(archiveButton, CommonViewUtility.RIGHT, 64);
        cv.adjustRelativeMargin(deleteButton, CommonViewUtility.RIGHT, 64);
        cv.adjustRelativeMargin(numberofSelection, CommonViewUtility.RIGHT, 82);

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

    private void setupActionBarEnvironment() {

        cv.adjustRelativeMargin(searchIcon, CommonViewUtility.LEFT, 120);
        cv.adjustRelativeMargin(searchIcon, CommonViewUtility.RIGHT, 40);
        cv.adjustRelative(searchIcon, 51, 54);
        cv.adjustRelativeMargin(headingLable, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearMargin(icon1, CommonViewUtility.RIGHT, 60);
      //  cv.adjustLinearSquare(icon1, 67);
        cv.adjustLinearMargin(icon2, CommonViewUtility.RIGHT, 60);
      //  cv.adjustLinearSquare(icon2, 67);
        cv.adjustLinearMargin(icon3, CommonViewUtility.RIGHT, 60);
      //  cv.adjustLinear(icon3, 85, 72);
        cv.adjustLinearMargin(icon4, CommonViewUtility.RIGHT, 60);
      //  cv.adjustLinear(icon4, 57, 72);
        icon1.setVisibility(View.VISIBLE);
        icon2.setVisibility(View.VISIBLE);
        icon3.setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setupBottomBarEnvironment() {
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
            if (!pm.checkPermission(Manifest.permission.CAMERA))
                pm.getPermission(Manifest.permission.CAMERA, PermissionCode.PERMISSIONCAMERA);
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

    @Override
    public void selectionsMade() {
        if(topBarChatOptions.getVisibility()==View.GONE){
            setUpActionbarChatListOptions();
            changeTopBarUpdated(topBarChatOptions);
        }

        if(AppDelegate.getInstance().getSelectedUserList().size()>0) {
            numberofSelection.setVisibility(View.VISIBLE);
            numberofSelection.setText("" + AppDelegate.getInstance().getSelectedUserList().size());
        }

        else {
            numberofSelection.setVisibility(View.GONE);
        }
    }
}
