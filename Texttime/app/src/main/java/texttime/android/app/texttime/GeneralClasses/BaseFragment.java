package texttime.android.app.texttime.GeneralClasses;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.Fragment;
import android.view.View;

import texttime.android.app.texttime.CommonClasses.CommonDataUtility;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.CustomProgressDialog;
import texttime.android.app.texttime.CommonClasses.DataFunctions;
import texttime.android.app.texttime.CommonClasses.PermissionManager;
import texttime.android.app.texttime.CommonClasses.SaveDataPreferences;
import texttime.android.app.texttime.WebOperations.ApiClient;
import texttime.android.app.texttime.WebOperations.ApiInterface;

/**
 * Created by TextTime Android Dev on 8/24/2016.
 */
public class BaseFragment extends Fragment {
    public CommonDataUtility cd;
    public CommonViewUtility cv;
    public SaveDataPreferences sd;
    public DataFunctions dfunctions;
    public Context context;
    public ApiInterface apiService;
    public CustomProgressDialog cpd;
    public PermissionManager pm;


    public void init(Context context){
        this.context=context;
        cd=new CommonDataUtility(context);
        cv=CommonViewUtility.getInstance();
        sd=new SaveDataPreferences(context);
        pm=new PermissionManager((Activity) context);
        dfunctions=new DataFunctions();
        cpd=new CustomProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    }


     public void openFragment(Fragment f, String tag){
        // ((ContainerActivity)getActivity()).openNewFragment(f,tag);
     }

    public void setBackPressed(View.OnClickListener listener){
        //((ContainerActivity)getActivity()).setBackClickListner(listener);
    }

    public TransitionDrawable createDrawable(int res1, int res2){
        TransitionDrawable drawable;
        Drawable cd[] = new Drawable[2];
        Resources res = getResources();
        cd[0] = res.getDrawable(res1);
        cd[1] = res.getDrawable(res2);

        drawable=new TransitionDrawable(cd);
        drawable.setCrossFadeEnabled(true);

        return drawable;
    }

}
