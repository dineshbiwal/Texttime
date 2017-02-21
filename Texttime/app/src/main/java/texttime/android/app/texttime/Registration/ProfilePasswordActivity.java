package texttime.android.app.texttime.Registration;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import CustomViews.CustomImageView;
import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.ContainerActivity;
import texttime.android.app.texttime.DataModels.UserSignup;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;
import texttime.android.app.texttime.WebOperations.ApiClient;
import texttime.android.app.texttime.WebOperations.MultipartUtility;
import texttime.android.app.texttime.WebOperations.TaskCode;
import texttime.android.app.texttime.WebOperations.WebTask;
import texttime.android.app.texttime.WebOperations.WebTaskCallback;

/**
 * Created by Dinesh_Text on 2/9/2017.
 */

public class ProfilePasswordActivity extends BaseActivity implements WebTaskCallback {

    @BindView(R.id.profileImage)
    CustomImageView profileImage;
    @BindView(R.id.username)
    CustomTextView username;
    @BindView(R.id.insert_password)
    EditText insertPassword;
    @BindView(R.id.view_password)
    ImageView viewPassword;
    @BindView(R.id.not_user)
    CustomTextView notUser;
    @BindView(R.id.yourpass)
    RelativeLayout yourpass;

    boolean isView = false;
    @BindView(R.id.right_done)
    ImageView rightDone;
    @BindView(R.id.passMain)
    LinearLayout passMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_password);
        ButterKnife.bind(this);
        init(this);
        adjustUIContent();
        initData();
        setonclickListener();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (passMain != null)
                passMain.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void adjustUIContent() {
        // cv.adjustLinearMargin(profileTool, CommonViewUtility.TOP, 58);
        cv.adjustRelativeMargin(rightDone, CommonViewUtility.RIGHT, 62);
        cv.adjustRelativeSquare(rightDone, 72);
        cv.adjustRelativeSquare(profileImage, 396);
        cv.adjustRelativeMargin(profileImage, CommonViewUtility.TOP, 48);
        cv.adjustLinearMargin(yourpass, CommonViewUtility.TOP, 266);
        cv.adjustLinearMargin(yourpass, CommonViewUtility.LEFT, 45);
        cv.adjustLinearMargin(yourpass, CommonViewUtility.RIGHT, 47);
        cv.adjustRelativeMargin(viewPassword, CommonViewUtility.LEFT, 47);
        cv.adjustRelative(viewPassword, 77, 45);
        cv.adjustRelativeMargin(username, CommonViewUtility.TOP, 46);
        cv.adjustLinearMargin(notUser, CommonViewUtility.TOP, 250);
    }

    private void setonclickListener() {
        rightDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppDelegate.getInstance().isReturningUser()) {
                    if (!TextUtils.isEmpty(cd.etData(insertPassword))) {
                        loginApplication();
                    }
                } else if (applyPasswordValidation()) {
                    createProfile();
                }
            }
        });

        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isView) {
                    insertPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    insertPassword.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
                    viewPassword.setImageResource(R.mipmap.ic_eye);
                    insertPassword.setSelection(cd.etData(insertPassword).length());
                    isView = false;
                } else {
                    insertPassword.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    insertPassword.setBackgroundResource(R.drawable.edittext_bottom_line);
                    insertPassword.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
                    viewPassword.setImageResource(R.mipmap.ic_eye_choosen);
                    insertPassword.setSelection(cd.etData(insertPassword).length());
                    isView = true;
                }
            }
        });

        notUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDelegate.getInstance().setReturningUser(false);
                SAF(ProfileUsernameActivity.class);
            }
        });
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

    //---Function determines whether it's for the returning user or for the new user
    //---For the returning user the data such as the image and the user name are prepopulated.
    //---For the new user the above mentioned data are taken from the cache new password is asked
    //---For existing user it's validated.

    private void initData() {
        insertPassword.setTypeface(dfunctions.getFontFamily(this), Typeface.NORMAL);
        rightDone.setImageResource(R.mipmap.ic_right);
        rightDone.setVisibility(View.VISIBLE);
        if (AppDelegate.getInstance().isReturningUser()) {
            if (!TextUtils.isEmpty(AppDelegate.getInstance().getCroppedImage())) {
                String url = AppDelegate.getInstance().imageUrl(AppDelegate.getInstance().getCroppedImage(), sd);
                profileImage.setUrl(url);
            } else {
                profileImage.setImageResource(R.mipmap.placeholder);
            }
            insertPassword.setHint(R.string.password);
            notUser.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            username.setText("@" + sd.getUsername());
            notUser.setText("Not " + sd.getUsername() + "?");
        } else {
            insertPassword.setHint(R.string.your_pass);
            if (TextUtils.isEmpty(AppDelegate.getInstance().getCroppedImage())) {
                profileImage.setImageResource(R.mipmap.placeholder);
            } else
                profileImage.setUrl(Uri.fromFile(new File(AppDelegate.getInstance().getCroppedImage())));
            notUser.setText("");
        }
        insertPassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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
        insertPassword.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    //----Perform login for the returning user------------
    private void loginApplication() {
        if (!TextUtils.isEmpty(cd.etData(insertPassword))) {
            if (applyPasswordValidation()) {
                Map<String, String> map = new HashMap<>();
                map.put("token", sd.getToken());
                map.put("returning_token", AppDelegate.getInstance().getReturningToken());
                map.put("password", cd.etData(insertPassword));
                map.put("username", sd.getUsername());
                WebTask task = new WebTask(context, TaskCode.LOGIN, this, map);
                task.performTask();
            }
        } else {
            cv.showAlert(context, "Please enter password.");
        }
    }

    //----Password validation apply----
    //---Must contain a capital letter
    //---Must contain a numeric digit
    //---Must contain a special character
    private boolean applyPasswordValidation() {

        String passwordString = cd.etData(insertPassword);
        if (passwordString.length() >= 8 && passwordString.length() <= 24) {
            boolean hasUppercase = !passwordString.equals(passwordString.toLowerCase());
            boolean hasSpecial = !passwordString.matches("[A-Za-z0-9 ]*");
            boolean hasNumber;
            if (passwordString.matches(".*\\d.*"))
                hasNumber = true;
            else
                hasNumber = false;

            if (hasNumber && hasSpecial && hasUppercase) {
                return true;
            } else {
                if (!hasNumber) {
                    cv.showAlert(context, "Password must contain at least 1 numeric character.");
                    return false;
                } else if (!hasSpecial) {
                    cv.showAlert(context, "Password must contain at least 1 special character.");
                    return false;
                } else if (!hasUppercase) {
                    cv.showAlert(context, "Password must contain at least 1 uppercase character.");
                    return false;
                }
            }
        } else {
            cv.showAlert(context, "Password must be at least 6 characters and at most 24 characters long.");
            return false;
        }

        return false;
    }

    //----Create profile by uploading image-------------
    //----Retrofit image upload causing issue hence using the multipart image upload--
    CreateProfileTask task;

    private void createProfile() {
        if (!TextUtils.isEmpty(cd.etData(insertPassword))) {
            if (cd.etData(insertPassword).length() >= 8 && cd.etData(insertPassword).length() <= 24) {
                if (task == null || !task.isRunning) {
                    task = new CreateProfileTask(ApiClient.BASE_URL + "v1/register/create-profile/" + sd.getToken(), false);
                    task.execute();
                }
            } else {
                cv.showAlert(context, "Password must be at least 8 characters and at most 24 characters long.");
            }
        } else {
            cv.showAlert(context, "Please enter password.");
        }
    }

    @Override
    public void success(Object object, int taskCode) {
        UserSignup user = (UserSignup) object;
        if (user.getResponseCode().equalsIgnoreCase("2015")) {
            sd.setToken("Bearer " + user.data.getToken());
            sd.setDisplayName(user.data.getDisplayName());
            sd.setAccessToken("Bearer " + user.data.getToken());
            sd.setUserJID(user.data.getJid());
            sd.setUserPassword(cd.etData(insertPassword));
            startActivityTransition(ContainerActivity.class);
            finish();
        } else {
            cpd.dismiss();
            cv.showAlert(context, user.getMessage());
        }
    }

    @Override
    public void fail(int taskCode) {
        cv.showAlert(context, "Error, please try again.");
    }

    @Override
    public void failed(Object object, int taskCode) {
        UserSignup user = (UserSignup) object;
        cv.showAlert(context, user.getMessage());
    }

    //----Create profile AsyncTasK----------------------
    private class CreateProfileTask extends AsyncTask<Void, Void, Void> {

        public boolean isRunning = false;
        String response = "";
        String url = "";
        boolean isLogin;

        public CreateProfileTask(String url, boolean isLogin) {
            this.url = url;
            this.isLogin = isLogin;
        }

        private void makeWebCall() {
            try {
                MultipartUtility mu = new MultipartUtility(url, "UTF-8");
                mu.addFormField("username", sd.getUsername());
                mu.addFormField("password", cd.etData(insertPassword));
                if (!isLogin) {
                    if (!TextUtils.isEmpty(AppDelegate.getInstance().getCroppedImage())) {
                        File file = new File(AppDelegate.getInstance().getCroppedImage());
                        if (file.exists())
                            mu.addFilePart("profile_image", file);
                    }
                }
                response = mu.finish();
            } catch (Exception e) {
                response = "";
            }
        }

        //-----parse the create profile task response-----
        private void parseResponse() {
            if (TextUtils.isEmpty(response)) {
                cv.showAlert(context, "Error, please try again.");
            } else {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject data = null;
                    if (TextUtils.equals(json.getString("response_code"), "2014")) {
                        data = json.getJSONObject("data");
                        sd.setToken("Bearer " + data.getString("token"));
                        sd.setDisplayName(data.getString("display_name"));
                        sd.setAccessToken("Bearer " + data.getString("token"));
                        sd.setUserJID(data.getString("jid"));
                        sd.setUserPassword(cd.etData(insertPassword));
                        startActivityTransition(ContainerActivity.class);
                        finish();
                    } else {
                        cv.showAlert(context, data.getString("message"));
                    }
                } catch (Exception e) {
                }
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cpd.show();
            isRunning = true;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cpd.dismiss();
            isRunning = false;
            parseResponse();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            makeWebCall();
            return null;
        }
    }
}
