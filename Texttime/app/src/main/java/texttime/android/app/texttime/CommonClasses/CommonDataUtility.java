package texttime.android.app.texttime.CommonClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;

/**
 * Created by Dinesh Android Dev on 8/2/2016.
 */
public class CommonDataUtility {

    Context context;

    public CommonDataUtility(Context context) {
        this.context = context;
    }

    public  boolean isNetworkAvailable(){
        boolean available = false;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isAvailable())
            available = true;
        return available;
    }

    public String etData(EditText editText){
        String s=editText.getText().toString();
        return s;
    }

    public boolean etMatchData(EditText editText, EditText editText1){
        String s=editText.getText().toString();
        String s1=editText1.getText().toString();
        if(s.equalsIgnoreCase(s1))
        return true;

        else
            return false;
    }
}
