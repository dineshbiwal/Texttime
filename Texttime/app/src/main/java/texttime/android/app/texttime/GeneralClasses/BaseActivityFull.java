package texttime.android.app.texttime.GeneralClasses;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import texttime.android.app.texttime.CommonClasses.CommonDataUtility;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.CustomProgressDialog;
import texttime.android.app.texttime.CommonClasses.DataFunctions;
import texttime.android.app.texttime.CommonClasses.PermissionManager;
import texttime.android.app.texttime.CommonClasses.SaveDataPreferences;
import texttime.android.app.texttime.R;
import texttime.android.app.texttime.WebOperations.ApiClient;
import texttime.android.app.texttime.WebOperations.ApiInterface;


public class BaseActivityFull extends AppCompatActivity {
    public CommonDataUtility cd;
    public CommonViewUtility cv;
    public SaveDataPreferences sd;
    public DataFunctions dfunctions;
    public Context context;
    public ApiInterface apiService;
    public CustomProgressDialog cpd;
    public View currentFocus;
    public PermissionManager pm;
    SwipeRefreshLayout swipeContainer;

    public SwipeRefreshLayout getSwipeContainer() {
        return swipeContainer;
    }

    public void setSwipeContainer(SwipeRefreshLayout swipeContainer) {
        this.swipeContainer = swipeContainer;
    }

    public void init(Context context){
      this.context=context;
        cd=new CommonDataUtility(context);
        cv=CommonViewUtility.getInstance();
        sd=new SaveDataPreferences(context);
        pm=new PermissionManager(this);
        dfunctions=new DataFunctions();
        cpd=new CustomProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        setDisplay();
    }

    public void SA(Class classFile){
        startActivity(new Intent(context,classFile));
    }

    public void SAF(Class classFile){
        startActivity(new Intent(context,classFile));
        finish();
    }

    public void startActivityTransition(Class classFile){
        startActivity(new Intent(context,classFile));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
    }

    public void startActivityTransition(Class classFile,Bundle extra){
        Intent intent=new Intent(context,classFile);
        intent.putExtras(extra);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
    }

    public void setDisplay(){
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        CommonViewUtility.getInstance().setScreenDisplay(dm.widthPixels,dm.heightPixels);
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*public void clearSavedData(){
        Appdelegate.getInstance().setClickedImage(null);
        Appdelegate.getInstance().setCroppedImage(null);
    }


    public  void setTopLoadingBar(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeContainer.setOnRefreshListener(listener);
        swipeContainer.setEnabled(false);
        swipeContainer.setColorSchemeResources(R.color.yellow, R.color.blue, R.color.red, R.color.button_blue);
    }

    public void setTopAnimation() {
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(true);
            }
        });
    }

    public void stopTopAnimation() {
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
            }
        });
    }*/

    /*public void setUpActionbar(String title, int iconResource, View.OnClickListener listener) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View view = getSupportActionBar().getCustomView();
        CustomTextViewBold titleText = (CustomTextViewBold) view.findViewById(R.id.toolBarText);
        ImageView toolbarIcon = (ImageView) view.findViewById(R.id.tool_barIcon);
        titleText.setText(title);
        if (iconResource == 0) {
            toolbarIcon.setVisibility(View.GONE);
        }

        if (iconResource != 0)
            toolbarIcon.setImageResource(iconResource);

      //  toolbarIcon.setImageResource(iconResource);
        toolbarIcon.setOnClickListener(listener);
    }*/


    public TransitionDrawable createDrawable(int res1,int res2){
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
