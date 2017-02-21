package ChatUI.fragments.ChatAdapterClasses;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import CustomViews.CameraModule.CustomReceivedImageView;
import CustomViews.CustomTextViewMedium;
import CustomViews.CustomImageView;
import CustomViews.CustomTextViewBold;
import CustomViews.CustomTextViewLight;
import texttime.android.app.texttime.R;


/**
 * Created by DELL on 11/28/2016.
 */

public class ChatListViewHolder extends RecyclerView.ViewHolder{

    public CustomImageView profileImage;
    public CustomTextViewMedium userNameLabel;
    public LinearLayout chatMessageLayout;
    public RelativeLayout messageReceivedLayout;
    public CustomTextViewBold unreadMessageLbl;
    public CustomTextViewLight messageText,postedTimeLabel;
    public ImageView messageStatusImage;
    public CustomReceivedImageView receivedImageThumbnail;
    public RelativeLayout chatContainerRowLayout;

    public ChatListViewHolder(View itemView) {
        super(itemView);
        chatContainerRowLayout= (RelativeLayout) itemView.findViewById(R.id.chatContainerRowLayout);
        messageReceivedLayout = (RelativeLayout) itemView.findViewById(R.id.messageReceivedLayout);
        chatMessageLayout = (LinearLayout) itemView.findViewById(R.id.chatMessageLayout);
        profileImage = (CustomImageView) itemView.findViewById(R.id.profileImage);
        userNameLabel = (CustomTextViewMedium) itemView.findViewById(R.id.userNameLabel);
        postedTimeLabel = (CustomTextViewLight) itemView.findViewById(R.id.postedTimeLabel);
        unreadMessageLbl = (CustomTextViewBold) itemView.findViewById(R.id.unreadMessageLbl);
        messageText = (CustomTextViewLight) itemView.findViewById(R.id.messageText);
        messageStatusImage = (ImageView) itemView.findViewById(R.id.messageStatusImage);
        receivedImageThumbnail = (CustomReceivedImageView) itemView.findViewById(R.id.receivedImageThumbnail);
    }
}
