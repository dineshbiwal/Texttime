package texttime.android.app.texttime.History;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import CustomViews.CustomImageView;
import CustomViews.CustomMediaImageView;
import CustomViews.CustomTextView;
import CustomViews.CustomTextViewLight;
import CustomViews.CustomTextViewMedium;
import CustomViews.CustomTextViewRegular;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/17/2017.
 */

public class HistoryViewholder extends RecyclerView.ViewHolder{
    public CustomImageView profileImage;
    public ImageView likeHistory;
    public CustomTextView likeCount;
    public CustomTextViewMedium userName;
    public ImageView showHistory;
    public RelativeLayout history;
    public ImageView locationIcon;
    public CustomTextViewLight locationAddress;
    public LinearLayout locationInfo;
    public CustomTextView postedTime;
    public CustomImageView sharePersonImg;
    public CustomTextViewLight sharePostedTime;
    public CustomTextViewMedium sharePersonName;
    public ImageView shareHistory;
    public CustomTextView shareCount;
    public LinearLayout sharedSection;
    public CustomTextViewRegular textMessage;
    public CustomTextView readMore;
    public RelativeLayout medialayout;
    public CustomTextView leaveComment;
    public ImageView commentHistory;
    public CustomTextView commentCount;
    public ImageView goCommentHistory;
    public LinearLayout features;
    public LinearLayout historyLayout;
    public LinearLayout mainBroadcast;
    public CustomMediaImageView mediaImage;
    public ImageView mediaPlay;
    public LinearLayout postView;

    public HistoryViewholder(View itemView) {
        super(itemView);
        profileImage = (CustomImageView) itemView.findViewById(R.id.profileImage);
        locationInfo = (LinearLayout) itemView.findViewById(R.id.locationInfo);
        sharePersonImg = (CustomImageView) itemView.findViewById(R.id.share_person_img);
        likeCount = (CustomTextView) itemView.findViewById(R.id.like_count);
        postedTime = (CustomTextView) itemView.findViewById(R.id.postedTime);
        sharePostedTime = (CustomTextViewLight) itemView.findViewById(R.id.sharePostedTime);
        userName = (CustomTextViewMedium) itemView.findViewById(R.id.userName);
        likeHistory = (ImageView) itemView.findViewById(R.id.like_history);
        showHistory = (ImageView) itemView.findViewById(R.id.show_history);
        locationIcon = (ImageView) itemView.findViewById(R.id.location_icon);
        shareHistory = (ImageView) itemView.findViewById(R.id.share_history);
        commentHistory = (ImageView) itemView.findViewById(R.id.comment_history);
        goCommentHistory = (ImageView) itemView.findViewById(R.id.go_comment_history);
        showHistory = (ImageView) itemView.findViewById(R.id.show_history);

        locationAddress = (CustomTextViewLight) itemView.findViewById(R.id.location_address);
        sharePersonName = (CustomTextViewMedium) itemView.findViewById(R.id.share_person_name);

        shareCount = (CustomTextView) itemView.findViewById(R.id.share_count);
        textMessage = (CustomTextViewRegular) itemView.findViewById(R.id.textMessage);
        leaveComment = (CustomTextView) itemView.findViewById(R.id.leave_comment);
        commentCount = (CustomTextView) itemView.findViewById(R.id.comment_count);
        readMore = (CustomTextView) itemView.findViewById(R.id.read_more);

        sharedSection = (LinearLayout) itemView.findViewById(R.id.shared_section);
        medialayout = (RelativeLayout) itemView.findViewById(R.id.medialayout);
        features = (LinearLayout) itemView.findViewById(R.id.features);
        historyLayout = (LinearLayout) itemView.findViewById(R.id.historyLayout);
        mediaPlay = (ImageView) itemView.findViewById(R.id.mediaPlay);
        history = (RelativeLayout) itemView.findViewById(R.id.history);
        mediaImage = (CustomMediaImageView) itemView.findViewById(R.id.media_image);
        mainBroadcast = (LinearLayout) itemView.findViewById(R.id.main_broadcast);
        postView = (LinearLayout) itemView.findViewById(R.id.post_view);
    }
}
