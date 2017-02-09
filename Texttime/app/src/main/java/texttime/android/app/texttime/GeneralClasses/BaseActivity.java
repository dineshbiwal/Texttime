package texttime.android.app.texttime.GeneralClasses;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import CustomViews.CustomTextView;

import CustomViews.CustomTextViewBold;
import CustomViews.CustomTextViewRegular;
import texttime.android.app.texttime.CommonClasses.CommonDataUtility;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;

import texttime.android.app.texttime.CommonClasses.DataFunctions;
import texttime.android.app.texttime.CommonClasses.PermissionManager;
import texttime.android.app.texttime.CommonClasses.CustomProgressDialog;
import texttime.android.app.texttime.CommonClasses.DataFunctions;
import texttime.android.app.texttime.CommonClasses.PermissionManager;
import texttime.android.app.texttime.CommonClasses.SaveDataPreferences;
import texttime.android.app.texttime.R;


public class BaseActivity extends AppCompatActivity {
    public CommonDataUtility cd;
    public CommonViewUtility cv;
    public SaveDataPreferences sd;
    public DataFunctions dfunctions;
    public Context context;
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

    public void init(Context context) {
        this.context = context;
        cd = new CommonDataUtility(context);
        cv = CommonViewUtility.getInstance();
        sd = new SaveDataPreferences(context);
        pm = new PermissionManager(this);
        dfunctions = new DataFunctions();
        cpd = new CustomProgressDialog(context);
        setDisplay();
    }

    public void SA(Class classFile) {
        startActivity(new Intent(context, classFile));
    }

    public void SAF(Class classFile) {
        startActivity(new Intent(context, classFile));
        finish();
    }

    public void startActivityTransition(Class classFile) {
        startActivity(new Intent(context, classFile));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
    }

    public void startActivityTransition(Class classFile, Bundle extra) {
        Intent intent = new Intent(context, classFile);
        intent.putExtras(extra);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
    }

    public void setDisplay() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        CommonViewUtility.getInstance().setScreenDisplay(dm.widthPixels, dm.heightPixels);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

   /* public void clearSavedData() {
        Appdelegate.getInstance().setClickedImage(null);
        Appdelegate.getInstance().setCroppedImage(null);
        Appdelegate.getInstance().setReturningToken(null);
    }*/

   /* public void setTopLoadingBar(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeContainer.setOnRefreshListener(listener);
        swipeContainer.setEnabled(false);
        swipeContainer.setColorSchemeResources(R.color.yellow, R.color.blue, R.color.red, R.color.button_blue);
    }*/

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
    }

    public void setUpActionbar(String title, int iconResource, View.OnClickListener listener) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View view = getSupportActionBar().getCustomView();
        CustomTextView titleText = (CustomTextView) view.findViewById(R.id.toolBarText);
        ImageView toolbarIcon = (ImageView) view.findViewById(R.id.tool_barIcon);
        titleText.setText(title);
        if (iconResource == 0) {
            toolbarIcon.setVisibility(View.GONE);
        }
        if (iconResource != 0)
            toolbarIcon.setImageResource(iconResource);
        toolbarIcon.setOnClickListener(listener);
    }


    public void setUpActionbar(String title, int iconResource, View.OnClickListener leftIconListener, View.OnClickListener rightIconListener) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View view = getSupportActionBar().getCustomView();
        CustomTextView titleText = (CustomTextView) view.findViewById(R.id.toolBarText);
        ImageView toolbarIcon = (ImageView) view.findViewById(R.id.tool_barIcon);
        //CustomTextView rightIcon = (CustomTextView) view.findViewById(R.id.rightAction);
        titleText.setText(title);
        if (iconResource == 0) {
            toolbarIcon.setVisibility(View.GONE);
        }
        if (iconResource != 0)
            toolbarIcon.setImageResource(iconResource);
        toolbarIcon.setOnClickListener(leftIconListener);
    }
       // rightIcon.setVisibility(View.VISIBLE);
       // rightIcon.setOnClickListener(rightIconListener);


    public void setUpActionbarChatList() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);

        getSupportActionBar().setCustomView(R.layout.toolbar_chat_list);
        View view = getSupportActionBar().getCustomView();
        CustomTextViewBold chatlabel = (CustomTextViewBold) view.findViewById(R.id.chatlabel);
        chatlabel.setAlpha(0.5f);
        ImageView settingsIcon = (ImageView) view.findViewById(R.id.settingsIcon);
        ImageView searchIcon = (ImageView) view.findViewById(R.id.searchIcon);
        ImageView createNewChatIcon = (ImageView) view.findViewById(R.id.createNewChatIcon);
        ImageView onlinestatus = (ImageView) view.findViewById(R.id.onlinestatus);
        CommonViewUtility cv = CommonViewUtility.getInstance();

        cv.adjustRelative(settingsIcon,30,30);
        cv.adjustRelativeSquare(onlinestatus,12);
        cv.adjustRelative(searchIcon,49,52);
        cv.adjustRelativeSquare(createNewChatIcon,54);
        cv.adjustRelativeMargin(settingsIcon,CommonViewUtility.LEFT,30);
        cv.adjustRelativeMargin(searchIcon,CommonViewUtility.LEFT,78);
        cv.adjustRelativeMargin(createNewChatIcon,CommonViewUtility.RIGHT,24);
        cv.adjustRelativeMargin(chatlabel,CommonViewUtility.LEFT,46);
        cv.adjustRelativeMargin(onlinestatus,CommonViewUtility.LEFT,28);

    }


    public void setUpActionbarChatListSearch() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setCustomView(R.layout.toolbar_search);
        View view = getSupportActionBar().getCustomView();
        CustomTextView chatlabel = (CustomTextView) view.findViewById(R.id.cancelText);
        SearchView searchView=(SearchView)view.findViewById(R.id.searchView);

    }


    public void setUpActionbarChatSelection() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setCustomView(R.layout.toolbar_chat_options);
        View view = getSupportActionBar().getCustomView();
        ImageView backButton = (ImageView) view.findViewById(R.id.backButton);
        ImageView muteButton = (ImageView) view.findViewById(R.id.muteButton);
        ImageView archiveButton = (ImageView) view.findViewById(R.id.archiveButton);
        ImageView deleteButton = (ImageView) view.findViewById(R.id.deleteButton);
        TextView numberofSelection = (TextView) view.findViewById(R.id.numberofSelection);
        CommonViewUtility cv = CommonViewUtility.getInstance();
        cv.adjustRelativeSquare(deleteButton,80);
        cv.adjustRelativeSquare(archiveButton,70);
        cv.adjustRelativeSquare(muteButton,70);
        cv.adjustRelativeSquare(numberofSelection,55);
//        cv.adjustRelativeSquare(numberofSelection,70);
        numberofSelection.setAlpha(0.5f);


    }

  /*  public void setUpActionbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_updated);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        ImageView searchIcon = (ImageView) view.findViewById(R.id.searchIcon);
        ImageView settingIcon = (ImageView) view.findViewById(R.id.settingIcon);
        CommonViewUtility cv = CommonViewUtility.getInstance();
        cv.adjustRelativeSquare(settingIcon, 60);
        cv.adjustRelativeSquare(searchIcon, 60);
    }

    public void setUpActionbarChat(String username) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_chat);
        View view = getSupportActionBar().getCustomView();
        ImageView backButton = (ImageView) view.findViewById(R.id.backButton);
        ImageView moreOptionsIcon = (ImageView) view.findViewById(R.id.moreOptionsIcon);
        ImageView timerIcon = (ImageView) view.findViewById(R.id.timerIcon);
        CustomTextViewRegular chatNameText = (CustomTextViewRegular) view.findViewById(R.id.chatNameText);
        chatNameText.setText(username);
        CommonViewUtility cv = CommonViewUtility.getInstance();
        cv.adjustRelative(backButton, 46,88);
        cv.adjustRelative(moreOptionsIcon, 128,28);
        cv.adjustRelativeSquare(timerIcon, 60);
        getSupportActionBar().setCustomView(view);
    }*/

    public TransitionDrawable createDrawable(int res1, int res2) {
        TransitionDrawable drawable;
        Drawable cd[] = new Drawable[2];
        Resources res = getResources();
        cd[0] = res.getDrawable(res1);
        cd[1] = res.getDrawable(res2);
        drawable = new TransitionDrawable(cd);
        drawable.setCrossFadeEnabled(true);
        return drawable;
    }



    float alpha=0;
    int mAnimDuration = 600/* milliseconds */;

    ValueAnimator mVaActionBar;

    public void hideActionBar() {
        // initialize `mToolbarHeight`
        if (alpha == 0) {
            alpha = getSupportActionBar().getCustomView().getAlpha();
        }

        if (mVaActionBar != null && mVaActionBar.isRunning()) {
            // we are already animating a transition - block here
            return;
        }

        // animate `Toolbar's` height to zero.
        mVaActionBar = ValueAnimator.ofFloat(alpha , 0);
        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // update LayoutParams
                getSupportActionBar().getCustomView().setAlpha((Float) animation.getAnimatedValue());
                getSupportActionBar().getCustomView().requestLayout();
            }
        });

        mVaActionBar.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (getSupportActionBar() != null) { // sanity check
                   // getSupportActionBar().hide();
                    setUpActionbar("2nd Action Bar",android.R.drawable.ic_dialog_dialer,null);
                }
            }
        });

        mVaActionBar.setDuration(mAnimDuration);
        mVaActionBar.start();
    }


}
