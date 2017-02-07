package texttime.android.app.texttime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;


/**
 * Created by Dinesh_Text on 2/6/2017.
 */

public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        TextView tv=new TextView(this);
        tv.setText("git text");
        Log.d("Activity", "MainActivity");
    }
}
