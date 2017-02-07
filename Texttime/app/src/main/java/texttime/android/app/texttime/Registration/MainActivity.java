package texttime.android.app.texttime.Registration;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/6/2017.
 */

public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_number);
        TextView tv=new TextView(this);
        tv.setText("git text");
        Log.d("Activity", "MainActivity");
    }
}
