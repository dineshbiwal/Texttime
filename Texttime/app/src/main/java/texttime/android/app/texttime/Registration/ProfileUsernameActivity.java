package texttime.android.app.texttime.Registration;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import CustomViews.CameraModule.CustomCameraView;
import CustomViews.CustomImageView;
import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CamUtils;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.PermissionCode;
import texttime.android.app.texttime.DataModels.ValidateUserName;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;
import texttime.android.app.texttime.WebOperations.ResponseCodes;
import texttime.android.app.texttime.WebOperations.TaskCode;
import texttime.android.app.texttime.WebOperations.WebTask;
import texttime.android.app.texttime.WebOperations.WebTaskCallback;

/**
 * Created by Dinesh_Text on 2/9/2017.
 */

public class ProfileUsernameActivity extends BaseActivity implements WebTaskCallback {
    @BindView(R.id.rightAction)
    CustomTextView rightAction;
    @BindView(R.id.customCameraView)
    CustomCameraView customCameraView;
    @BindView(R.id.selectedImage)
    CustomImageView selectedImage;
    @BindView(R.id.imageContainer)
    RelativeLayout imageContainer;
    @BindView(R.id.insert_username)
    EditText insertUsername;
    @BindView(R.id.alreadyProfile)
    CustomTextView alreadyProfile;

    Camera camera;
    CamUtils cUtils;
    @BindView(R.id.userMain)
    LinearLayout userMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_username);
        ButterKnife.bind(this);
        init(this);
        AppDelegate.getInstance().setClickedImage("");
        AppDelegate.getInstance().setCroppedImage("");
        adjustUIComponent();
        initEnvironment();
        setonclicklistener();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (userMain != null)
                userMain.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        askPermission();
    }

    @Override
    public void onBackPressed() {
        cv.showAlertSingleAction(context, "Do you want to exit ?", "Exit", "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearSavedData();
                finish();
            }
        });
    }

    //--Receive the response from the check username api
    //--if username is valid reserve it by calling reserveUserName()

    private void CheckUserNameResponse(ValidateUserName userName) {
        if (userName.getResponse_code() == ResponseCodes.VALIDATEUSERNAME) {
            reserveUserName();
        } else {
            cv.showAlert(context, userName.getMessage());
        }
    }

    //--reserver the username, useful as the user signup api isn't called
    //--till next screen.
    private void reserveUserName() {
        Map<String, String> params = new HashMap<>();
        params.put("token", sd.getToken());
        String username = cd.etData(insertUsername).substring(1);
        params.put("username", username);
        WebTask wTask = new WebTask(context, TaskCode.RESERVEUSERNAME, this, params);
        wTask.performTask();
    }

    //---Adjust the size of UI elements-----------
    private void adjustUIComponent() {
        //cv.adjustLinearMargin(profileTool, CommonViewUtility.TOP, 58);
        cv.adjustRelativeMargin(rightAction, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearMargin(imageContainer, CommonViewUtility.TOP, 48);
        cv.adjustRelativeSquare(selectedImage, 396);
        cv.adjustLinearMargin(insertUsername, CommonViewUtility.TOP, 266);
        cv.adjustLinearMargin(insertUsername, CommonViewUtility.LEFT, 45);
        cv.adjustLinearMargin(alreadyProfile, CommonViewUtility.TOP, 236);
    }

    //----Initialize the class data and setup the required callbacks---
    private void initEnvironment() {
        insertUsername.setTypeface(dfunctions.getFontFamily(this), Typeface.NORMAL);
        rightAction.setVisibility(View.VISIBLE);
        insertUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = String.valueOf(charSequence);
                if (!TextUtils.isEmpty(str) && !str.startsWith("@")) {
                    if (str.contains("@")) {
                        int index = str.indexOf("@");
                        str = str.substring(index + 1, str.length());
                    }
                    str = "@" + str;
                }
                if (str.contains(" ")) {
                    str = str.replace(" ", "_");
                }
                if (str.length() >= 2 && !TextUtils.equals(insertUsername.getText(), str)) {
                    SpannableString styledString = new SpannableString(str);
                    // styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.button_blue)), 0, 1, 0);
                    styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
                    insertUsername.setText(styledString);
                    insertUsername.setSelection(styledString.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        insertUsername.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        insertUsername.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    //---When app is paused release the camera if it's in use
    @Override
    protected void onPause() {
        super.onPause();
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


    @Override
    protected void onResume() {
        super.onResume();
        //---If image has been clicked and cropped
        //--Hide camera view and display the clicked and cropped image.
        if (!TextUtils.isEmpty(AppDelegate.getInstance().getCroppedImage())) {
            customCameraView.setVisibility(View.GONE);
            selectedImage.setVisibility(View.VISIBLE);
            selectedImage.setUrl(Uri.fromFile(new File(AppDelegate.getInstance().getCroppedImage())));
        }

        //---Else if camera permission is granted reinitialise the camera
        else {
            if (isCameraEnabled() && pm.checkPermission(Manifest.permission.CAMERA)) {
                if (camera == null)
                    initCam();
            } else {
                if (!pm.checkPermission(Manifest.permission.CAMERA)) {
                    customCameraView.setPreviewWOCamera(cv.getWidth(396));
                }
            }
        }
    }

    //--Ask for the camera permission----
    private void askPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!pm.checkPermission(pm.SECONDPAGEPERMISSIONS)) {
                pm.getPermissionCollection(pm.SECONDPAGEPERMISSIONS, PermissionCode.PERMISSIONCAMERA);
            } else
                initCam();
        } else
            initCam();
    }

    //--Register click listeners------------------------------
    private void setonclicklistener() {
        alreadyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---User already has a profile redirect to login page.
                startActivityTransition(LoginActivity.class);
            }
        });

        customCameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCameraEnabled()) {
                    askWritePermission();
                }
            }
        });

        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCameraEnabled()) {
                    askWritePermission();
                }
            }
        });
        rightAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(cd.etData(insertUsername))) {
                    cpd.show();
                    checkUserName();
                } else {
                    cv.showAlert(context, "Please enter profile name.");
                }
            }
        });
    }

    //--Webcall to check if username hasn't been taken already or if it isn't valid.
    private void checkUserName() {
        Map<String, String> params = new HashMap<>();
        params.put("token", sd.getToken());
        String username = cd.etData(insertUsername).substring(1);
        params.put("username", username);
        // setTopAnimation();
        WebTask wTask = new WebTask(context, TaskCode.CHECKUSERNAME, this, params);
        wTask.performTask();
    }

    //--If camera permission is granted then the
    //--custom camera tag should be the "camera"
    //--else it is gallery so user can pick the image from gallery.
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

    //----Check for the write external storage permission----
    private void askWritePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                pm.getPermissionCollection(pm.SECONDPAGEPERMISSIONS, PermissionCode.PERMISSIONCAMERA);
            } else {
                Bundle b = new Bundle();
                b.putBoolean("isActivity", false);
                startActivityTransition(CameraTestActivity.class, b);
            }
        } else {
            Bundle b = new Bundle();
            b.putBoolean("isActivity", false);
            startActivityTransition(CameraTestActivity.class, b);
        }
    }

    //---Initialise the camera view---------------
    private void initCam() {
        cUtils = new CamUtils(false, cv.getWidth(396), cv.getWidth(396), this);
        try {
            if (cUtils.checkForCamera())
                camera = cUtils.initCamera();
        } catch (Exception e) {
            System.out.print("Error is >>>" + e);
        }
        if (camera != null) {
            customCameraView.setPreviewUpdated(camera, (396));
            Camera.Size s = camera.getParameters().getPreviewSize();
            maintainAspect(s.width, s.height);
        } else
            customCameraView.setPreviewWOCamera(cv.getWidth(396));
    }

    //----Keep the camera view in the aspect ratio--------
    //----From Marshmallow onward devices have square camera resolution
    //----Any device before that will have slighly destorted preview.
    private void maintainAspect(int width, int height) {
        float ratio = (float) width / height;
        int reqh = (396);
        int newW = (int) (ratio * reqh);
        if (reqh == newW) {
            cv.adjustRelativeSquare(customCameraView, newW);
        } else {
            cv.adjustRelativeSquare(customCameraView, reqh);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            //---If camera permission is granted display the camera else create a view without camera.
            case PermissionCode.PERMISSIONCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCam();
                } else {
                    customCameraView.setPreviewWOCamera(cv.getWidth(396));
                }
                break;
        }
    }

    //--WebTask Success function.
    //--CheckUsername successfull --->> perform reserveUsername
    //--Reserve username successfull --->> Move to next screen.

    @Override
    public void success(Object object, int taskCode) {
        cpd.dismiss();
        if (taskCode == TaskCode.CHECKUSERNAME) {
            ValidateUserName userName = (ValidateUserName) object;
            CheckUserNameResponse(userName);
        } else if (taskCode == TaskCode.RESERVEUSERNAME) {
            //  stopTopAnimation();
            ValidateUserName userName = (ValidateUserName) object;
            if (userName.getResponse_code() == ResponseCodes.RESERVEUSERNAME) {
                String username = cd.etData(insertUsername).substring(1);
                sd.setUsername(username);
                startActivityTransition(ProfilePasswordActivity.class);
                finish();
            } else {
                cpd.dismiss();
                cv.showAlert(context, userName.getMessage());
                //stopTopAnimation();
            }
        }
    }


    //-------WebTask callback failed function------------------
    @Override
    public void fail(int taskCode) {
        // stopTopAnimation();
        cpd.dismiss();
        cv.showAlert(context, "Unable to check username availability.");
    }

    @Override
    public void failed(Object object, int taskCode) {
        // stopTopAnimation();
        cpd.dismiss();
        try {
            ValidateUserName userName = (ValidateUserName) object;
            cv.showAlert(context, userName.getMessage());
        } catch (Exception e) {
            cv.showAlert(context, "Username not valid! try with other");
        }
    }
}
