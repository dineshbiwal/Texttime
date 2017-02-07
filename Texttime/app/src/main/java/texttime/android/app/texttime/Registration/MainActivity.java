package texttime.android.app.texttime.Registration;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/6/2017.
 */

public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_number);
        init(this );
        setUpActionbar("TextTime App.", 0, null);
    }
}
