package texttime.android.app.texttime.Registration;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.HashMap;

import CustomViews.CurrentFocusInterface;
import CustomViews.CustomEditText;
import CustomViews.CustomKeyboardLayout;
import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.DataModels.ResendVerifyCode;
import texttime.android.app.texttime.DataModels.Verification;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;
import texttime.android.app.texttime.WebOperations.ResponseCodes;
import texttime.android.app.texttime.WebOperations.TaskCode;
import texttime.android.app.texttime.WebOperations.WebTask;
import texttime.android.app.texttime.WebOperations.WebTaskCallback;
import texttime.android.app.texttime.callbacks.OTPRecievedCallback;

/**
 * Created by Dinesh_Text on 2/8/2017.
 */

public class VerificationActivity extends BaseActivity implements TextWatcher, View.OnClickListener, WebTaskCallback, OTPRecievedCallback {
    @BindView(R.id.code)
    CustomTextView code1;
    @BindView(R.id.phone)
    CustomTextView phone;
    @BindView(R.id.insert_code)
    CustomEditText insertCode;
    @BindView(R.id.resendVerifyCode)
    LinearLayout resendVerifyCode;
    @BindView(R.id.sendcode)
    ImageView sendcode;
    @BindView(R.id.veriCode)
    LinearLayout veriCode;
    @BindView(R.id.digit)
    LinearLayout digit;
    @BindView(R.id.customKeyboardLayout)
    LinearLayout customKeyboardLayout;
    @BindView(R.id.valid_code)
    LinearLayout validCode;
    @BindView(R.id.firstLine)
    View firstLine;
    @BindView(R.id.secondLine)
    View secondLine;
    @BindView(R.id.thirdLine)
    View thirdLine;
    @BindView(R.id.verify_progress)
    ProgressBar verifyProgress;
    @BindView(R.id.timerText)
    TextView timerText;
    @BindView(R.id.verify_main)
    RelativeLayout verifyMain;

    private String code = "";
    CustomKeyboardLayout cutomKeyboard;
    CurrentFocusInterface interfaceCurrent;
    int timerTime = 45;
    int resendActivationCount = 0;
    Handler handler;
    Runnable r;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_code);
        ButterKnife.bind(this);
        init(this);
        adjustUIcontent();
        code1.setText(sd.getDialCode());
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(sd.getPhoneNumber(), sd.getISOAlpha2());
            phone.setText(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        } catch (NumberParseException e) {
            Log.e("Number Parse Error", e.toString());
        }
        verifyProgress.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
        initData();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (verifyMain != null)
                verifyMain.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setStatusBarTranslucent(true);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        View v = findViewById(R.id.toolbar);
        if (v != null) {
            int paddingTop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? android.R.attr.actionBarSize : 0;
            TypedValue tv = new TypedValue();
            getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
            paddingTop += TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            v.setPadding(0, makeTranslucent ? paddingTop : 0, 0, 0);
        }

        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initData() {
        startTimer();
        createCustomKeyboard();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        code = getIntent().getExtras().getString("verifycode");
        resendVerifyCode.setOnClickListener(this);
        insertCode.addTextChangedListener(this);
        Toast.makeText(context, "Code is >>" + code, Toast.LENGTH_LONG).show();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(insertCode, InputMethodManager.SHOW_FORCED);
        if (pm.checkPermission(pm.FIRSTPAGEPERMISSIONS))
            AppDelegate.getInstance().setOtpCallback(this);
        insertCode.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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
    }

    private void adjustUIcontent() {
        //cv.adjustLinearMargin(verifyTool, CommonViewUtility.TOP, 58);
        cv.adjustLinearMargin(veriCode, CommonViewUtility.LEFT, 46);
        cv.adjustLinearMargin(veriCode, CommonViewUtility.TOP, 205);
        cv.adjustLinearMargin(validCode, CommonViewUtility.TOP, 44);
        cv.adjustLinearMargin(digit, CommonViewUtility.LEFT, 46);
        cv.adjustLinearMargin(digit, CommonViewUtility.TOP, 116);
        cv.adjustLinearMargin(digit, CommonViewUtility.RIGHT, 46);
        cv.adjustLinearMargin(resendVerifyCode, CommonViewUtility.TOP, 148);
        cv.adjustLinearMargin(resendVerifyCode, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearSquare(sendcode, 67);
        cv.adjustLinearMargin(sendcode, CommonViewUtility.LEFT, 7);
        insertCode.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
    }

    //---Disable the resend button and the call button for 45 secs
    private void disableButtons() {
        resendVerifyCode.setOnClickListener(null);
        timerText.setOnClickListener(null);
        resendVerifyCode.setAlpha(0.5f);
    }
    //-------------------------------------------------------------

    //-----Enable the call button and the resend code button after 45 secs
    private void enableButtons() {
        resendVerifyCode.setOnClickListener(this);
        timerText.setOnClickListener(this);
        resendVerifyCode.setAlpha(1.0f);
        timerText.setAlpha(1.0f);
    }

    //-----Run the timer and the reducing status bar for 45 secs------------
    private void startTimer() {
        if (handler != null) {
            handler.removeCallbacks(r);
            handler = null;
        }
        timerText.setTag(45);
        timerText.setText("Call me 00:45");
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 1000);
        disableButtons();
        r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTimerText();
                    }
                });
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
    }

    //------Stop the timer after the 45 secs or if the correct code is entered.
    private void stopTimer() {
        handler.removeCallbacks(r);
        enableButtons();
    }

    //-------Animate the bar to reduce from the 45 secs to 0
    private void startAnimation() {
        verifyProgress.setMax(timerTime * 100);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(verifyProgress, "progress", 0, timerTime * 100);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue("progress");
                if (progress > (0 * 100) && progress <= (15 * 100)) {
                    verifyProgress.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
                }
                if (progress >= (15 * 100) && progress <= (30 * 100)) {
                    verifyProgress.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_blue));
                    firstLine.setVisibility(View.VISIBLE);
                } else if (progress >= (30 * 100)) {
                    verifyProgress.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_red));
                    secondLine.setVisibility(View.VISIBLE);
                }


            }
        });
        progressAnimator.setDuration(timerTime * 1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    //--------Update the text of the timer every second---------
    private void updateTimerText() {
        int value = (int) timerText.getTag();
        if (value > 0) {
            if (value == timerTime)
                timerText.setText("Call me 00:" + value + "");
            else {
                if (value < 10) {
                    timerText.setText("Call me 00:0" + value + "");
                } else
                    timerText.setText("Call me 00:" + value + "");
            }
            value = value - 1;
            timerText.setTag(value);
        } else {
            timerText.setText("Call me 00:00");
            stopTimer();
        }
    }

    //-------make the web call after checking the code locally-------
    private void verifyCode() {
        String verifyCode = cd.etData(insertCode);
        if (TextUtils.equals(code, verifyCode)) {
            cpd.show();
            verifyCodeServer();
        } else {
            cv.showAlert(context, "Incorrect verification code");
        }
    }

    //-----Make the web call to verify the code received--------------------------------
    private void verifyCodeServer() {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("token", sd.getToken());
        new WebTask(context, TaskCode.VERIFYCODE, this, map).performTask();
    }

    //-----make web call to the resend the verification code------------------------------
    private void resendVerifyCodeServer() {
        if (cd.isNetworkAvailable()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("token", sd.getToken());
            new WebTask(context, TaskCode.REVERIFYCODE, this, map).performTask();
        } else
            cv.showAlert(context, "Please connect to internet.");
    }

    @Override
    public void onClick(View view) {
        if (view == resendVerifyCode) {
            verifyProgress.setProgress(0);
            firstLine.setVisibility(View.INVISIBLE);
            secondLine.setVisibility(View.INVISIBLE);
            if (resendActivationCount <= 2) {
                resendVerifyCodeServer();
            } else {
                cv.showAlertSingleAction(context, "Resend activation code limit reached.", "Back", null, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        SAF(MainActivity.class);
                    }
                });
            }
        }
    }

    private void showFailedMessage() {
        cv.showAlertSingleAction(context, "Verification Failed.", "OK", null, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                insertCode.setText("");
            }
        });
    }

    private void showFailedResend() {
        cv.showAlertSingleAction(context, getResources().getString(R.string.unable_resend_code), "OK", null, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                enableButtons();
            }
        });
    }

    @Override
    public void success(Object object, int taskCode) {
        cpd.dismiss();
        if (taskCode == TaskCode.VERIFYCODE) {
            verifyCodeResultCheck(object);
        } else if (taskCode == TaskCode.REVERIFYCODE) {
            resendVerifyCodeResultCheck(object);
        }
    }

    @Override
    public void fail(int taskCode) {
        cpd.dismiss();
        if (taskCode == TaskCode.VERIFYCODE) {
            showFailedMessage();
        } else if (taskCode == TaskCode.REVERIFYCODE) {
            showFailedResend();
        }
    }

    @Override
    public void failed(Object object, int taskCode) {
        cpd.dismiss();
        if (taskCode == TaskCode.VERIFYCODE) {
            showFailedMessage();
        } else if (taskCode == TaskCode.REVERIFYCODE) {
            showFailedResend();
        }
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

    //---Callback from the OTPCallback----
    //---read the verification code-------
    @Override
    public void sendSMS(String code) {
        int start = code.indexOf(":");
        int end = code.indexOf(".");
        String c = code.substring(start + 2, end);
        System.out.print("code is >>" + c);
        insertCode.setText("");
        insertCode.setText(c.toString());
    }


    //-------Checks response from the verification code response-----------
    private void verifyCodeResultCheck(Object object) {
        Verification verifyObject = (Verification) object;

        if (verifyObject.getResponseCode() == ResponseCodes.VERIFYCODESUCCESS) {
            startActivityTransition(ProfileUsernameActivity.class);
            AppDelegate.getInstance().setReturningUser(false);
            finish();
        } else if (verifyObject.getResponseCode() == ResponseCodes.VERIFYCODESUCCESSRETURNINGUSER) {
            sd.setUsername(verifyObject.data.getUsername());
            sd.setDisplayName(verifyObject.data.getDisplayName());
            //----To check-----
            AppDelegate.getInstance().setReturningToken(verifyObject.data.getReturningToken());
            AppDelegate.getInstance().setReturningUser(true);
            AppDelegate.getInstance().setCroppedImage(verifyObject.data.getProfile_image());
            startActivityTransition(ProfilePasswordActivity.class);
            finish();
        } else if (verifyObject.getResponseCode() == ResponseCodes.VERIFYCODEREPESTED) {
            cv.showAlertSingleAction(context, getResources().getString(R.string.invalid_verify), "OK", null, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    insertCode.setText("");
                }
            });
        } else {
            showFailedMessage();
        }
    }

    //-------------------------------------------------------------------------------

    //-----Checks from the response from the callback response from the resend verification code
    private void resendVerifyCodeResultCheck(Object object) {
        ResendVerifyCode verifyObject = (ResendVerifyCode) object;
        if (verifyObject.getResponseCode() == ResponseCodes.RESENDVERIFICATIONCODE) {
            code = verifyObject.data.getVerificationCode();
            Log.d("Verify Code", code);
            Toast.makeText(context, code, Toast.LENGTH_LONG).show();
            disableButtons();
            startTimer();
            resendActivationCount += 1;
        } else if (verifyObject.getResponseCode() == ResponseCodes.UNABLERESENDVERIFICATIONCODE) {

            cv.showAlertSingleAction(context, getResources().getString(R.string.unable_resend_code), "OK", null, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    enableButtons();
                }
            });
        } else {
            cv.showAlertSingleAction(context, getResources().getString(R.string.reached_limit), "OK", null, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });
        }
    }

    //---Creates the custom keyboard to support the needs for the screen
    private void createCustomKeyboard() {
        cutomKeyboard = new CustomKeyboardLayout(customKeyboardLayout, this, insertCode);
        interfaceCurrent = cutomKeyboard.getInterface();
        cutomKeyboard.createCustomKeyBoard();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 4) {
            if (cd.isNetworkAvailable()) {
                verifyCode();
            } else
                cv.showAlert(context, "Please connect to internet.");
        }
    }
}
