package texttime.android.app.texttime.Registration;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.HashMap;

import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
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

public class VerificationActivity extends BaseActivity implements View.OnClickListener, WebTaskCallback, OTPRecievedCallback{
    @BindView(R.id.code)
    CustomTextView code1;
    @BindView(R.id.phone)
    CustomTextView phone;
    @BindView(R.id.insert_code)
    EditText insertCode;
    @BindView(R.id.resendVerifyCode)
    LinearLayout resendVerifyCode;
    @BindView(R.id.sendcode)
    ImageView sendcode;
    @BindView(R.id.veriCode)
    LinearLayout veriCode;
    @BindView(R.id.digit)
    LinearLayout digit;

    int timerTime = 45;
    int resendActivationCount = 0;
    Handler handler;
    Runnable r;
    private String code = "";

    @BindView(R.id.verify_progress)
    ProgressBar verifyProgress;
    @BindView(R.id.timerText)
    TextView timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_code);
        ButterKnife.bind(this);
        init(this);
        setUpActionbar(getResources().getString(R.string.app), R.mipmap.ic_cancel,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View c) {
                        finish();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verifyCode();
                    }
                });
        adjustUIcontent();
        code1.setText(sd.getDialCode());
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(sd.getPhoneNumber(), sd.getISOAlpha2());
            phone.setText(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        } catch (NumberParseException e) {
            Log.e("Number Parse Error", e.toString());
        }
        initData();
    }

    private void initData(){
        startTimer();
        code = getIntent().getExtras().getString("verifycode");
        resendVerifyCode.setOnClickListener(this);
        Toast.makeText(context,"Code is >>"+code,Toast.LENGTH_LONG).show();
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(insertCode, InputMethodManager.SHOW_FORCED);
        if(pm.checkPermission(pm.FIRSTPAGEPERMISSIONS))
            AppDelegate.getInstance().setOtpCallback(this);
    }
    private void adjustUIcontent() {
        cv.adjustLinearSquare(sendcode, 60);
        cv.adjustLinearMargin(veriCode, 1, 24);
        cv.adjustLinearMargin(veriCode, 2, 120);
        cv.adjustLinearMargin(digit, 1, 24);
        cv.adjustLinearMargin(digit, 2, 60);
        cv.adjustLinearMargin(digit, 4, 24);
        cv.adjustLinearMargin(resendVerifyCode, 2, 74);
        cv.adjustLinearMargin(resendVerifyCode, 4, 44);
        cv.adjustLinearMargin(sendcode, 1, 7);
        cv.adjustLinearHeight(timerText,70);
        insertCode.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
    }

    //---Disable the resend button and the call button for 45 secs
    private void disableButtons() {
        resendVerifyCode.setOnClickListener(null);
        timerText.setOnClickListener(null);
        resendVerifyCode.setAlpha(0.5f);
        timerText.setAlpha(0.5f);
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
    private void startTimer(){
        if(handler!=null){
            handler.removeCallbacks(r);
            handler=null;
        }
        timerText.setTag(45);
        timerText.setText("00:45");
        handler=new Handler();
        startAnimation();
        //updateTimerText();
        disableButtons();
        r=new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTimerText();
                    }
                });

                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(r,1000);
    }

    //------Stop the timer after the 45 secs or if the correct code is entered.
    private void stopTimer(){
        handler.removeCallbacks(r);
        enableButtons();
    }

    //-------Animate the bar to reduce from the 45 secs to 0
    private void startAnimation(){
        verifyProgress.setMax(timerTime*100);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(verifyProgress, "progress", timerTime*100, 0);
        progressAnimator.setDuration(timerTime*1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    //--------Update the text of the timer every second---------
    private void updateTimerText(){
        int value= (int) timerText.getTag();
        if(value>0) {
            if (value == timerTime)
                timerText.setText("00:"+value+"");
            else {
                if (value < 10)
                    timerText.setText("00:0" + value+"");
                else
                    timerText.setText("00:" + value+"");
            }
            value=value-1;
            timerText.setTag(value);
        }
        else
        {
            timerText.setText("00:00");
            stopTimer();
        }
    }

    //-------make the web call after checking the code locally-------
    private void verifyCode() {
        String verifyCode = cd.etData(insertCode);
        if (TextUtils.equals(code, verifyCode)) {
            //setTopAnimation();
           // disableAllview();
            //verifyCodeServer();
            cv.showAlert(context, "Code verification successful");
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
        if(cd.isNetworkAvailable()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("token", sd.getToken());
            new WebTask(context, TaskCode.REVERIFYCODE, this, map).performTask();
        }

        else
            cv.showAlert(context,"Please connect to internet.");
    }

    @Override
    public void onClick(View view) {
        if (view == resendVerifyCode) {
            if(resendActivationCount<=2) {
               // setTopAnimation();
                //disableAllview();
                resendVerifyCodeServer();
            }
            else {
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
       // stopTopAnimation();
        //enableAllViews();
        if (taskCode == TaskCode.VERIFYCODE) {
            verifyCodeResultCheck(object);
        } else if (taskCode == TaskCode.REVERIFYCODE) {
            resendVerifyCodeResultCheck(object);
        }
    }

    @Override
    public void fail(int taskCode) {
        if (taskCode == TaskCode.VERIFYCODE) {
            showFailedMessage();
        } else if (taskCode == TaskCode.REVERIFYCODE) {
            showFailedResend();
        }
    }

    @Override
    public void failed(Object object, int taskCode) {
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

    public void clearSavedData() {
        AppDelegate.getInstance().setClickedImage(null);
        AppDelegate.getInstance().setCroppedImage(null);
        AppDelegate.getInstance().setReturningToken(null);
    }

    //---Callback from the OTPCallback----
    //---read the verification code-------
    @Override
    public void sendSMS(String code) {
        int start=code.indexOf(":");
        int end=code.indexOf(".");
        String c=code.substring(start+2,end);
        System.out.print("code is >>"+c);
        insertCode.setText("");
        insertCode.setText(c.toString());
    }


    //-------Checks response from the verification code response-----------
    private void verifyCodeResultCheck(Object object) {
        Verification verifyObject = (Verification) object;

        if (verifyObject.getResponseCode()== ResponseCodes.VERIFYCODESUCCESS) {
            startActivityTransition(ProfileUsernameActivity.class);
            AppDelegate.getInstance().setReturningUser(false);
            finish();
        }
        else if (verifyObject.getResponseCode()==ResponseCodes.VERIFYCODESUCCESSRETURNINGUSER) {
            sd.setUsername(verifyObject.data.getUsername());
            sd.setDisplayName(verifyObject.data.getDisplayName());

            //----To check-----
            AppDelegate.getInstance().setReturningToken(verifyObject.data.getReturningToken());
//            sd.setAccessToken(verifyObject.data.getReturningToken());

            AppDelegate.getInstance().setReturningUser(true);
            AppDelegate.getInstance().setCroppedImage(verifyObject.data.getProfile_image());

            startActivityTransition(ProfilePasswordActivity.class);
            finish();
        } else if (verifyObject.getResponseCode()==ResponseCodes.VERIFYCODEREPESTED) {

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
        if (verifyObject.getResponseCode()== ResponseCodes.RESENDVERIFICATIONCODE) {
            code = verifyObject.data.getVerificationCode();
            Log.d("Verify Code", code);
            Toast.makeText(context, code, Toast.LENGTH_LONG).show();
            disableButtons();
            startTimer();
            resendActivationCount += 1;

        } else if (verifyObject.getResponseCode()== ResponseCodes.UNABLERESENDVERIFICATIONCODE) {

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
}
