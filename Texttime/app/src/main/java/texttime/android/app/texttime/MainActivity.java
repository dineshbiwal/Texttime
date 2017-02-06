package texttime.android.app.texttime;

import android.app.Activity;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.TextView;
=======
import android.util.Log;
>>>>>>> 77662cfaa9c5d0bd28dd826208342217df92a4f0

/**
 * Created by Dinesh_Text on 2/6/2017.
 */

public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
<<<<<<< HEAD
        TextView tv=new TextView(this);
        tv.setText("git text");

=======
        Log.d("Activity", "MainActivity");
>>>>>>> 77662cfaa9c5d0bd28dd826208342217df92a4f0
    }
}
