package texttime.android.app.texttime.History;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ChatUI.fragments.ChatAdapterClasses.ChatHistoryAdapter;
import ChatUI.fragments.ChatDataModels.ChattingUserList;
import CustomViews.VerticalItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.DataModels.BroadcastHistoryModel;
import texttime.android.app.texttime.GeneralClasses.BaseFragment;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/16/2017.
 */

public class BroadcastFragment extends BaseFragment {

    @BindView(R.id.historyList)
    RecyclerView historyList;

    List<BroadcastHistoryModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        init(getActivity());
        setBackPressed(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (savedInstanceState == null)
            view = inflater.inflate(R.layout.broadcast_fragment, null);
        ButterKnife.bind(this, view);
        fillData();
        historyList.setLayoutManager(new LinearLayoutManager(context));
        historyList.addItemDecoration(new VerticalItemDecoration(cv.getHeight(56), false));
        BroadcastListAdapter adapter = new BroadcastListAdapter(context, list);
        historyList.setAdapter(adapter);
        return view;
    }
    private void fillData(){
        BroadcastHistoryModel model = new BroadcastHistoryModel();
        model.setUser_display_name("Kenny Cuevas");
        model.setLocation_address("201 Mudit mansion Pal Road");
        model.setPostedTimeAgo("1 Day");
        model.setLike(true);
        model.setLikeCount(5210000);
        model.setMedia_text("Remember that building a connection with a woman is all about getting her to tell you things she views as personally significant. Her rationale for choosing a job she doesn't like over a job she does is pretty personally significant, and probably not something she talks about all that much with all that many people. The combination of its significance and its rarity as a conversational topic for her make it more impactful in building a stronger connection.");
        model.setCommentCount(1440);
        model.setProfile_image_url("https://pbs.twimg.com/profile_images/87325629/Sunglass.jpg");

        BroadcastHistoryModel model2 = new BroadcastHistoryModel();
        model2.setUser_display_name("John Tarerro");
        model2.setPostedTimeAgo("3 Days");
        model2.setLike(false);
        model2.setLikeCount(6200);
        model2.setMedia_text("Yes, that's right. So, since we last talked, I've got a new job and moved into a new apartment. What about you? What have you done since we last talked?\n" +
                "\n" +
                "Ah, that's interesting. I've been working hard on a project at work lately. What have you been doing lately?\n" +
                "\n" +
                "If I had more free time, I'd play some golf. What about you?");
        model2.setCommentCount(344);
        model2.setShare_user_profile_url("http://10ad.itocd.net/www/images/girl/1848801-1849000/2a10e542-628c-44b8-809b-0ed204d7a0a6.jpg");
        model2.setShare_user_display_name("Gabriel Paris");
        model2.setShare_postedtime_ago("5 Hours");
        model2.setShareCount(2390);
        model2.setProfile_image_url("https://lh6.googleusercontent.com/-eD5AKNbgT4g/AAAAAAAAAAI/AAAAAAAAADw/ksf-xKCNUjg/photo.jpg");

        BroadcastHistoryModel model3 = new BroadcastHistoryModel();
        model3.setUser_display_name("Dinesh Biwal");
        model3.setLocation_address("201 Mudit mansion Pal Road");
        model3.setPostedTimeAgo("7 Days");
        model3.setLike(true);
        model3.setLikeCount(9630);
        model3.setMedia_text("I got a new job, and everything changed. What did you do to change things?\n" +
                "\n" +
                "Oh well, I'm sure things will be different soon. I know things will change for the better when I get married! Will things change for you soon? If so, how will they change?\n" +
                "\n" +
                "Oh, look at the time!");
        model3.setCommentCount(4220);
        model3.setShare_user_display_name("Arun Sharma");
        model3.setShare_postedtime_ago("5 Days");
        model3.setShareCount(189);

        BroadcastHistoryModel model4 = new BroadcastHistoryModel();
        model4.setUser_display_name("Himanshu Rathore");
        model4.setLocation_address("201 Mudit mansion Pal Road");
        model4.setPostedTimeAgo("10 Days");
        model4.setLike(false);
        model4.setLikeCount(20);
        model4.setMedia_text("The most important tense for a summary is the simple present.");
        model4.setCommentCount(14);
        model4.setShare_user_profile_url("http://www.harishzone.com/wp-content/uploads/2014/09/Ankita-Sharma.jpg");
        model4.setShare_user_display_name("Dulce Baldera");
        model4.setShare_postedtime_ago("58 Mins");
        model4.setShareCount(8);
        model4.setProfile_image_url("https://s-media-cache-ak0.pinimg.com/736x/4c/8f/fe/4c8ffea329bde2a44132c72cea9de93c.jpg");

        BroadcastHistoryModel model5 = new BroadcastHistoryModel();
        model5.setUser_display_name("Rosemarie Cuevas");
        //model.setLocation_address("201 Mudit mansion Pal Road");
        model5.setPostedTimeAgo("19 Days");
        model5.setLike(false);
        model5.setLikeCount(8000);
        model5.setMedia_text("When you are first getting to know someone, text messaging can be a great way to break the ice and generate interest in getting to know each other better. To start a text conversation with a girl, send her a short open-ended question to get things going, then shift the conversation toward something timely or significant to her. Keep the conversation lighthearted, and take cues from her to determine whether to keep going or let it end.");
        model5.setCommentCount(140);
        model5.setProfile_image_url("http://hairstyles.thehairstyler.com/hairstyles/images/14816/icon/Rosemarie-DeWitt.jpg");

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
    }
}
