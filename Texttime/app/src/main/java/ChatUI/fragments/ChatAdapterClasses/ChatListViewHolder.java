package ChatUI.fragments.ChatAdapterClasses;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import CustomViews.CustomImageView;
import CustomViews.CustomTextViewBold;
import CustomViews.CustomTextViewLight;
import CustomViews.CustomTextViewRegular;
import texttime.android.app.texttime.R;


/**
 * Created by DELL on 11/28/2016.
 */

public class ChatListViewHolder extends RecyclerView.ViewHolder{

    public CustomImageView profileImage;
    public CustomTextViewBold userNameLabel;
    public CustomTextViewRegular unreadMessageLbl;
    public CustomTextViewLight messageText,postedTimeLabel;
    public ImageView messageStatusImage,receivedImageThumbnail;

    public ChatListViewHolder(View itemView) {
        super(itemView);
        profileImage= (CustomImageView) itemView.findViewById(R.id.profileImage);
        userNameLabel= (CustomTextViewBold) itemView.findViewById(R.id.userNameLabel);
        postedTimeLabel= (CustomTextViewLight) itemView.findViewById(R.id.postedTimeLabel);
        unreadMessageLbl= (CustomTextViewRegular) itemView.findViewById(R.id.unreadMessageLbl);
        messageText= (CustomTextViewLight) itemView.findViewById(R.id.messageText);
        messageStatusImage= (ImageView) itemView.findViewById(R.id.messageStatusImage);
        receivedImageThumbnail= (ImageView) itemView.findViewById(R.id.receivedImageThumbnail);

    }
}
