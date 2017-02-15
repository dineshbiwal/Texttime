package texttime.android.app.texttime.Registration;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

import CustomViews.CustomImageView;
import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.ContainerActivity;
import texttime.android.app.texttime.DataModels.UserSignup;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;
import texttime.android.app.texttime.WebOperations.TaskCode;
import texttime.android.app.texttime.WebOperations.WebTask;
import texttime.android.app.texttime.WebOperations.WebTaskCallback;

/**
 * Created by Dinesh_Text on 2/11/2017.
 */

public class LoginActivity extends BaseActivity implements WebTaskCallback{

    @BindView(R.id.go_back)
    ImageView goBack;
    @BindView(R.id.rightAction)
    CustomTextView rightAction;
    @BindView(R.id.userImage)
    CustomImageView userImage;
    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_view_password)
    ImageView loginViewPassword;
    @BindView(R.id.loginpass)
    RelativeLayout loginpass;

    boolean isView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        init(this);
        adjustUIComponent();
        initEnvironment();
        setoncliclistener();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void adjustUIComponent() {
       // cv.adjustRelativeMargin(goBack, CommonViewUtility.LEFT, 30);
        cv.adjustRelativeSquare(goBack, 72);
        cv.adjustRelativeMargin(rightAction, CommonViewUtility.RIGHT, 60);
        cv.adjustRelativeMargin(userImage, CommonViewUtility.TOP, 50);
        cv.adjustRelativeSquare(userImage, 396);
        cv.adjustLinearMargin(loginUsername, CommonViewUtility.TOP, 143);
        cv.adjustLinearMargin(loginUsername, CommonViewUtility.LEFT, 46);
        cv.adjustLinearMargin(loginUsername, CommonViewUtility.RIGHT, 46);
        cv.adjustLinearMargin(loginpass, CommonViewUtility.TOP, 70);
        cv.adjustLinearMargin(loginpass, CommonViewUtility.LEFT,46);
        cv.adjustLinearMargin(loginpass, CommonViewUtility.RIGHT, 47);
        cv.adjustRelativeMargin(loginViewPassword, CommonViewUtility.LEFT, 47);
        cv.adjustRelative(loginViewPassword, 77, 45);
    }

    private void initEnvironment(){
        loginUsername.setTypeface(dfunctions.getFontFamily(this), Typeface.NORMAL);
        loginPassword.setTypeface(dfunctions.getFontFamily(this), Typeface.NORMAL);
        userImage.setImageResource(R.mipmap.placeholder);
        goBack.setImageResource(R.mipmap.ic_back);
        rightAction.setVisibility(View.VISIBLE);
        goBack.setVisibility(View.VISIBLE);
        loginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i==0 && !charSequence.toString().equalsIgnoreCase("@")){

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = String.valueOf(charSequence);

                if(!TextUtils.isEmpty(str) && !str.startsWith("@")){

                    if(str.contains("@")){
                        int index=str.indexOf("@");
                        str=str.substring(index+1,str.length());
                    }
                    str="@"+str;
                }
                if (str.contains(" ")) {
                    str = str.replace(" ", "_");
                }

                if(str.length()>=2 && !TextUtils.equals(loginUsername.getText(),str)) {
                    SpannableString styledString
                            = new SpannableString(str);
                    //styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.button_blue)), 0, 1, 0);
                    styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
                    loginUsername.setText(styledString);
                    loginUsername.setSelection(styledString.length());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        loginUsername.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {}

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        loginUsername.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                return true;
            }
        });
        loginPassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {}

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        loginPassword.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                return true;
            }
        });
    }

    private void setoncliclistener(){

        rightAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginApplication();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isView) {
                    loginPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    loginPassword.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
                    loginViewPassword.setImageResource(R.mipmap.ic_eye);
                    loginPassword.setSelection(cd.etData(loginPassword).length());
                    isView = false;
                }
                else{
                    loginPassword.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    loginPassword.setBackgroundResource(R.drawable.edittext_bottom_line);
                    loginPassword.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
                    loginViewPassword.setImageResource(R.mipmap.ic_eye_choosen);
                    loginPassword.setSelection(cd.etData(loginPassword).length());
                    isView = true;
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    private void loginApplication() {
        if(!TextUtils.isEmpty(cd.etData(loginUsername))) {
            if (!TextUtils.isEmpty(cd.etData(loginPassword))) {
                    Map<String, String> map = new HashMap<>();
                    map.put("token", sd.getToken());
                    String username=cd.etData(loginUsername).substring(1);
                    map.put("username", username);
                    map.put("password", cd.etData(loginPassword));
                    cpd.show();
                    WebTask task = new WebTask(context, TaskCode.LOGIN, this, map);
                    task.performTask();
            } else
                cv.showAlert(context, "Please enter password.");
        }
       else
            cv.showAlert(context, "Please enter profile name.");
    }

    @Override
    public void success(Object object, int taskCode) {
        cpd.dismiss();
        UserSignup user= (UserSignup) object;
        if(user.getResponseCode().equalsIgnoreCase("2015")){
            sd.setToken("Bearer " + user.data.getToken());
            sd.setDisplayName(user.data.getDisplayName());
            sd.setAccessToken("Bearer " + user.data.getToken());
            sd.setUserJID(user.data.getJid());
            sd.setUserPassword(cd.etData(loginPassword));
            startActivityTransition(ContainerActivity.class);
            finish();
        }

        else
        {
            cpd.dismiss();
            cv.showAlert(context,user.getMessage());
        }
    }

    @Override
    public void fail(int taskCode) {
        cpd.dismiss();
        cv.showAlert(context,"Error, please try again.");
    }

    @Override
    public void failed(Object object, int taskCode) {
        cpd.dismiss();
        UserSignup user= (UserSignup) object;
        cv.showAlert(context,user.getMessage());
    }
}
