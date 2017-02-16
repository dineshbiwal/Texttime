package texttime.android.app.texttime.Registration;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.HashMap;
import java.util.Map;

import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.IncomingSms;
import texttime.android.app.texttime.CommonClasses.PermissionCode;
import texttime.android.app.texttime.ContainerActivity;
import texttime.android.app.texttime.DataModels.RegisterUser;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.GeneralClasses.BaseActivityFull;
import texttime.android.app.texttime.R;
import texttime.android.app.texttime.WebOperations.ResponseCodes;
import texttime.android.app.texttime.WebOperations.TaskCode;
import texttime.android.app.texttime.WebOperations.WebTask;
import texttime.android.app.texttime.WebOperations.WebTaskCallback;

/**
 * Created by Dinesh_Text on 2/6/2017.
 */

public class MainActivity extends BaseActivity implements WebTaskCallback, View.OnClickListener, TextWatcher {
    @BindView(R.id.location)
    ImageButton location;
    @BindView(R.id.choose_country)
    LinearLayout chooseCountry;
    @BindView(R.id.insert_number)
    EditText insertNumber;
    @BindView(R.id.sendSms)
    ImageView sendSms;
    @BindView(R.id.your_country)
    CustomTextView yourCountry;
    @BindView(R.id.country_code)
    CustomTextView countryCode;
    @BindView(R.id.sendVerifyCode)
    LinearLayout sendVerifyCode;
    String phoneNumber = "";
    @BindView(R.id.numberinput)
    LinearLayout numberinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_number);
        ButterKnife.bind(this);
        init(this);
        checkIfLoggedIn();
        getCountryCode();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustUIcontent();
        chooseCountry.setOnClickListener(this);
        insertNumber.addTextChangedListener(this);
        sendVerifyCode.setOnClickListener(this);
        checkPermissions();
        insertNumber.clearFocus();
        countryCode.setVisibility(View.GONE);
        yourCountry.setText(sd.getCountryName() + "   ");
        countryCode.setText(sd.getDialCode());
        if (!TextUtils.isEmpty(sd.getDialCode())) {
            countryCode.setVisibility(View.VISIBLE);
            countryCode.setText(sd.getDialCode());
        }
        insertNumber.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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
        insertNumber.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                return true;
            }
        });
    }

    //----Get country code from the telephony manager class--------------------------------
    private void getCountryCode() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        AppDelegate.getInstance().getCountryDetails(countryCodeValue, this);
        insertNumber.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
    }

    private void adjustUIcontent() {
        cv.adjustLinearHeight(location, 62);
        cv.adjustLinearWidth(location, 44);
        cv.adjustLinearSquare(sendSms, 67);
       // cv.adjustLinearMargin(toolText, CommonViewUtility.TOP, 58);
        cv.adjustLinearMargin(chooseCountry, CommonViewUtility.TOP, 370);
        cv.adjustLinearMargin(chooseCountry, CommonViewUtility.LEFT, 62);
        cv.adjustLinearMargin(yourCountry, CommonViewUtility.LEFT, 30);
        cv.adjustLinearMargin(numberinput, CommonViewUtility.TOP, 74);
        cv.adjustLinearMargin(numberinput, CommonViewUtility.LEFT, 46);
        cv.adjustLinearMargin(numberinput, CommonViewUtility.RIGHT, 46);
        cv.adjustLinearMargin(countryCode, CommonViewUtility.RIGHT, 62);
        cv.adjustLinearMargin(sendVerifyCode, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearMargin(sendSms, CommonViewUtility.LEFT, 14);
        cv.adjustLinearMargin(sendVerifyCode, CommonViewUtility.TOP, 148);
    }

    private void failShowMessage() {
        cv.showAlert(context, getResources().getString(R.string.signup_error));
    }

    @Override
    public void onClick(View v) {
        if (v == chooseCountry) {
            startActivity(new Intent(this, CountryList.class));
        } else if (v == sendVerifyCode) {
            if (TextUtils.isEmpty(sd.getDialCode()) || TextUtils.isEmpty(cd.etData(insertNumber))) {
                cv.showAlert(context, getResources().getString(R.string.country_err));
            } else {
                if (cd.isNetworkAvailable()) {
                    //-----If the phone number is valid then make the web call--------------------------
                    //-----Phone number validation function is written in the datafunction class--------
                    if (dfunctions.isPhoneValid(phoneNumber, sd.getISOAlpha2())) {
                        String message = sd.getDialCode() + " " + cd.etData(insertNumber) + "\n" + getResources().getString(R.string.
                                number_correct);
                        hideKeyboard();
                        cv.showAlertSingleAction(context, message, "OK", "Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                validatePhoneNumber();
                            }
                        });
                    } else {
                        cv.showAlert(context, "Please enter valid phone number.");
                    }
                } else {
                    cv.showAlert(context, "Please connect to internet.");
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        phoneNumber = s.toString();
        if (dfunctions.isPhoneNumberValid(phoneNumber) && dfunctions.isPhoneValid(phoneNumber, sd.getISOAlpha2())) {
            try {
                final Phonenumber.PhoneNumber numberProto = phoneUtil.parse(s.toString(), sd.getISOAlpha2());
                insertNumber.setText(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
                insertNumber.setSelection(insertNumber.getText().length());
            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    //Check for the read sms permission necessary for the auto read verification code.
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!pm.checkPermission(pm.FIRSTPAGEPERMISSIONS)) {
                pm.getPermissionCollection(pm.FIRSTPAGEPERMISSIONS, PermissionCode.PERMISSIONREADSMS);
            }
        }
    }

    //----------Callback to get the result of the asked permission
    // Permission code class has static int defined to distinguish between the different permissions.

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PermissionCode.PERMISSIONREADSMS:
                //---Unregister the receiver when the permission is denied for the SMS read and receive.
                if (grantResults.length > 0 && (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    PackageManager pm = getPackageManager();
                    ComponentName compName =
                            new ComponentName(getApplicationContext(),
                                    IncomingSms.class);
                    pm.setComponentEnabledSetting(
                            compName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                }
                break;
        }
    }

    //as of now 1 is used as a place holder confirm with the web team to replace the suitable value.
    private void validatePhoneNumber() {
        Map<String, String> params = new HashMap<>();
        params.put("dial_code", sd.getDialCode());
        params.put("mobile_number", cd.etData(insertNumber));
        params.put("country_id", "1");
        cpd.show();
        hideKeyboard();
        new WebTask(context, TaskCode.SENDVERIFYCODE, this, params).performTask();
    }

    @Override
    public void success(Object object, int taskCode) {
        cpd.dismiss();
        if (taskCode == TaskCode.SENDVERIFYCODE) {
            RegisterUser user = (RegisterUser) object;
            if (user.getResponseCode() == ResponseCodes.PHONENUMBERVERIFY) {
                sd.setToken(user.data.getGuestToken());
                sd.setPhoneNumber(cd.etData(insertNumber));
                Bundle b = new Bundle();
                b.putString("verifycode", user.data.getVerificationCode());
                startActivityTransition(VerificationActivity.class, b);
                finish();
            } else {
                failShowMessage();
            }
        }
    }

    @Override
    public void fail(int taskCode) {
        cpd.dismiss();
        failShowMessage();
    }

    @Override
    public void failed(Object object, int taskCode) {
        cpd.dismiss();
        failShowMessage();
    }

    //-------If user has logged in redirect to the Home Activity-------------------------
    //else keep on this screen and continue OTP login process----------------------------
    private void checkIfLoggedIn() {
        if (!TextUtils.equals(sd.getAccessToken(), "")) {
            SAF(ContainerActivity.class);
        }
    }
}
