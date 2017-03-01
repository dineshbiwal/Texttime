package texttime.android.app.texttime.LikeComment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import CustomViews.CustomImageView;
import CustomViews.CustomTextViewLight;
import CustomViews.CustomTextViewMedium;
import CustomViews.CustomTextViewRegular;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonMethods;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.DataModels.BroadcastHistoryModel;
import texttime.android.app.texttime.DataModels.CommentListModel;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.History.BroadcastListAdapter;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/23/2017.
 */

public class CommentList extends BaseActivity {

    @BindView(R.id.likes)
    CustomTextViewMedium likes;
    @BindView(R.id.click_like)
    LinearLayout clickLike;
    @BindView(R.id.comments)
    CustomTextViewMedium comments;
    @BindView(R.id.click_comment)
    LinearLayout clickComment;
    @BindView(R.id.options_selection)
    LinearLayout optionsSelection;
    @BindView(R.id.about_comment)
    CustomTextViewLight aboutComment;
    @BindView(R.id.comment_user_profile)
    CustomImageView commentUserProfile;
    @BindView(R.id.commentList)
    RecyclerView commentList;
    @BindView(R.id.comment_area)
    LinearLayout commentArea;
    @BindView(R.id.comment_done)
    ImageView commentDone;
    @BindView(R.id.comment_user_name)
    CustomTextViewMedium commentUserName;
    @BindView(R.id.comment_text)
    CustomTextViewLight commentText;
    @BindView(R.id.user_comment)
    LinearLayout userComment;
    @BindView(R.id.send_comment)
    EditText sendComment;

    List<CommentListModel> list = new ArrayList<>();

    @BindView(R.id.go_back)
    ImageView goBack;
    @BindView(R.id.toolTitle)
    CustomTextViewMedium toolTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_screen);
        ButterKnife.bind(this);
        init(this);
        adjustUIContent();
        gettingData();
        getCommentList();
    }

    @Override
    public void onResume(){
        super.onResume();
        commentList.setLayoutManager(new LinearLayoutManager(this));
        CommentListAdapter adapter = new CommentListAdapter(this, list);
        commentList.setAdapter(adapter);
    }
    private void gettingData() {
        BroadcastHistoryModel model = AppDelegate.getInstance().getBroadcastModel();
        likes.setText(CommonMethods.convertIntoKillo(model.getLikeCount()));
        comments.setText(CommonMethods.convertIntoKillo(model.getCommentCount()));
        commentUserName.setText(model.getUser_display_name());
        commentText.setText(model.getMedia());
        commentUserProfile.setUrl(model.getProfile_image_url(), R.mipmap.placeholder);
    }

    private void adjustUIContent() {
        cv.adjustLinearMargin(optionsSelection, CommonViewUtility.TOP, 69);
        cv.adjustLinearMargin(optionsSelection, CommonViewUtility.LEFT, 27);
        cv.adjustLinearMargin(optionsSelection, CommonViewUtility.RIGHT, 27);
        cv.adjustLinearMargin(likes, CommonViewUtility.TOP, 24);
        cv.adjustLinearMargin(likes, CommonViewUtility.BOTTOM, 24);
        cv.adjustLinearMargin(likes, CommonViewUtility.LEFT, 44);
        cv.adjustLinearMargin(comments, CommonViewUtility.TOP, 24);
        cv.adjustLinearMargin(comments, CommonViewUtility.BOTTOM, 24);
        cv.adjustLinearMargin(comments, CommonViewUtility.LEFT, 44);
        cv.adjustLinearMargin(aboutComment, CommonViewUtility.TOP, 72);
        cv.adjustLinearMargin(aboutComment, CommonViewUtility.LEFT, 140);
        cv.adjustLinearMargin(userComment, CommonViewUtility.TOP, 60);
        cv.adjustLinearMargin(userComment, CommonViewUtility.RIGHT, 60);
        cv.adjustLinearSquare(commentUserProfile, 107);
        cv.adjustLinearMargin(commentUserProfile, CommonViewUtility.LEFT, 27);
        cv.adjustLinearMargin(commentUserProfile, CommonViewUtility.RIGHT, 27);
        cv.adjustLinearMargin(commentText, CommonViewUtility.LEFT, 12);
        cv.adjustLinearMargin(commentText, CommonViewUtility.TOP, 16);
        cv.adjustLinearMargin(commentList, CommonViewUtility.TOP, 53);
        cv.adjustFrameHeight(commentArea, 130);
        cv.adjustLinearSquare(commentDone, 72);
        cv.adjustLinearMargin(sendComment, CommonViewUtility.LEFT, 81);
        cv.adjustLinearMargin(sendComment, CommonViewUtility.RIGHT, 33);
        cv.adjustLinearMargin(goBack, CommonViewUtility.LEFT, 32);
        cv.adjustLinear(goBack, 36, 64);
        cv.adjustLinearMargin(toolTitle, CommonViewUtility.LEFT, 99);
        commentUserName.setTextColor(Color.parseColor("#505f67"));
        sendComment.setTypeface(dfunctions.getFontFamily(context), Typeface.NORMAL);
    }

    private void getCommentList() {
        CommentListModel model = new CommentListModel();
        model.setProfile_image_url("http://www.kpopmusic.com/wp-content/uploads/2015/01/LeeJoonProfile.png");
        model.setComment("Good idea ,I can set tag line about first aid in my Blog. Thanks for sharing");
        model.setPostedTime("Today 11:27 AM");
        model.setProfile_display_name("Denial");

        CommentListModel model2 = new CommentListModel();
        model2.setProfile_image_url("https://upload.wikimedia.org/wikipedia/commons/0/03/Ashish_Profile_picture.jpg");
        model2.setComment("This is exact, I was searching for customize message in blog post. Appreciate work dude!");
        model2.setPostedTime("Yesterday");
        model2.setProfile_display_name("Aashish");

        CommentListModel model3 = new CommentListModel();
        model3.setProfile_image_url("http://img01.ibnlive.in/ibnlive/uploads/2016/04/12931171_1079163228794337_8732305975871635522_n.jpg");
        model3.setComment("Once you have read this article, you might have a blog or probably planning to have one. I advise that you must appreciate your readers because they are your fuel in order to go farther in blogging.");
        model3.setPostedTime("20/02/2017");
        model3.setProfile_display_name("Martina Zulunga");

        CommentListModel model4 = new CommentListModel();
        model4.setProfile_image_url("http://slideplayer.com/10/2920788/big_thumb.jpg");
        model4.setComment("Very nice, thanks");
        model4.setPostedTime("18/02/2017");
        model4.setProfile_display_name("Isita Saxena");

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);
    }
}
