package texttime.android.app.texttime;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ChatUI.fragments.ChatFragment;
import CustomViews.CustomTextViewBold;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;

/**
 * Created by TextTime Android Dev on 2/7/2017.
 */

public class ContainerActivity extends BaseActivity {
    @BindView(R.id.chatIcon)
    ImageView chatIcon;
    @BindView(R.id.chatText)
    CustomTextViewBold chatText;
    @BindView(R.id.chatTab)
    RelativeLayout chatTab;
    @BindView(R.id.groupIcon)
    ImageView groupIcon;
    @BindView(R.id.groupText)
    CustomTextViewBold groupText;
    @BindView(R.id.groupTab)
    RelativeLayout groupTab;
    @BindView(R.id.composeIcon)
    ImageView composeIcon;
    @BindView(R.id.composeTab)
    RelativeLayout composeTab;
    @BindView(R.id.broadCastIcon)
    ImageView broadCastIcon;
    @BindView(R.id.boadcastText)
    CustomTextViewBold boadcastText;
    @BindView(R.id.broadcastTab)
    RelativeLayout broadcastTab;
    @BindView(R.id.myProfileIcon)
    ImageView myProfileIcon;
    @BindView(R.id.requestText)
    CustomTextViewBold requestText;
    @BindView(R.id.myProfileTab)
    RelativeLayout myProfileTab;
    @BindView(R.id.bottomTabBar)
    LinearLayout bottomTabBar;
    @BindView(R.id.placeHolder)
    FrameLayout placeHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        init(this);
        //setUpActionbarChatSelection();
        // setUpActionbarChatListSearch();
         setUpActionbarChatList();
         openNewFragment(new ChatFragment(),"Chat");
         cv.adjustRelativeHeight(bottomTabBar,80);
    }

    Fragment currentFragment;
    public void openNewFragment(Fragment f, String tag)
    {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        Fragment fragment=getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment!=null){
            ft.show(fragment);
            ft.hide(currentFragment);
            currentFragment=fragment;
        }
        else
        {
            if(currentFragment!=null)
                ft.hide(currentFragment);

            ft.add(R.id.placeHolder,f,tag);
            currentFragment=f;
        }
        ft.commit();
    }

}
