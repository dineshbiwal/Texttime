package texttime.android.app.texttime.Registration;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/8/2017.
 */

public class VerificationActivity extends BaseActivity {
    @BindView(R.id.code)
    CustomTextView code;
    @BindView(R.id.phone)
    CustomTextView phone;
    @BindView(R.id.insert_code)
    EditText insertCode;
    @BindView(R.id.resendVerifyCode)
    LinearLayout resendVerifyCode;
    @BindView(R.id.sendcode)
    ImageView sendcode;

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

                    }
                });
        adjustUIcontent();
        code.setText(sd.getDialCode());
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(sd.getPhoneNumber(), sd.getISOAlpha2());
            phone.setText(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        } catch (NumberParseException e) {
            Log.e("Number Parse Error", e.toString());
        }
    }

    private void adjustUIcontent() {
        cv.adjustLinearSquare(sendcode, 60);
        insertCode.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
    }
}
