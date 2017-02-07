package texttime.android.app.texttime.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import CustomViews.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/6/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.location)
    ImageButton location;
    @BindView(R.id.choose_country)
    LinearLayout chooseCountry;
    @BindView(R.id.insert_number)
    EditText insertNumber;
    @BindView(R.id.aTIL)
    TextInputLayout aTIL;
    @BindView(R.id.sendSms)
    ImageView sendSms;
    @BindView(R.id.your_country)
    CustomTextView yourCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_number);
        ButterKnife.bind(this);
        init(this);
        setUpActionbar("TextTime App.", 0, null);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        adjustUIcontent();
        chooseCountry.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        insertNumber.clearFocus();
        //yourCountry.setText(sd.getCountryName() + " " + sd.getDialCode());
    }

    private void adjustUIcontent(){
        cv.adjustLinearSquare(location, 50);
        cv.adjustLinearSquare(sendSms, 60);
    }

    @Override
    public void onClick(View v) {
        if(v == chooseCountry){
            startActivity(new Intent(this, CountryList.class));
        }
    }
}
