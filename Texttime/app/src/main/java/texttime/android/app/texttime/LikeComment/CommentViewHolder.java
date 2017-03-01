package texttime.android.app.texttime.LikeComment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import CustomViews.CustomImageView;
import CustomViews.CustomTextView;
import CustomViews.CustomTextViewLight;
import CustomViews.CustomTextViewMedium;
import butterknife.BindView;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/23/2017.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder{
    CustomImageView commentedUserProfile;
    CustomTextViewMedium commentedUserName;
    CustomTextViewLight commentedTime;
    CustomTextViewLight commentedText;
    LinearLayout commentPart;

    public CommentViewHolder(View itemView) {
        super(itemView);
        commentedUserProfile = (CustomImageView) itemView.findViewById(R.id.commented_user_profile);
        commentedUserName = (CustomTextViewMedium) itemView.findViewById(R.id.commented_user_name);
        commentedTime = (CustomTextViewLight) itemView.findViewById(R.id.commented_time);
        commentedText = (CustomTextViewLight) itemView.findViewById(R.id.commented_text);
        commentPart = (LinearLayout) itemView.findViewById(R.id.comment_part);
    }
}