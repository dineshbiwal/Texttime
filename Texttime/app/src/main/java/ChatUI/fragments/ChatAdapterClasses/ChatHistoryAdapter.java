package ChatUI.fragments.ChatAdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ChatUI.fragments.ChatDataModels.ChattingUserList;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.R;


/**
 * Created by DELL on 11/28/2016.
 */

public class ChatHistoryAdapter extends RecyclerView.Adapter{

    ArrayList<ChattingUserList> chatHistoryList;
    Context context;


    public ChatHistoryAdapter(ArrayList<ChattingUserList> chatHistoryList, Context context) {
        this.chatHistoryList = chatHistoryList;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_chatlist, parent, false);
        return new ChatListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ChattingUserList model=chatHistoryList.get(position);
        final ChatListViewHolder viewHolder= (ChatListViewHolder) holder;
        CommonViewUtility cv=CommonViewUtility.getInstance();
        cv.adjustRelativeSquare(viewHolder.profileImage,180);
        cv.adjustRelativeSquare(viewHolder.unreadMessageLbl,55);
        cv.adjustRelativeSquare(viewHolder.messageStatusImage,23);
        cv.adjustRelativeMargin(viewHolder.profileImage,CommonViewUtility.LEFT,15);
        cv.adjustRelativeMargin(viewHolder.messageText,CommonViewUtility.LEFT,15);
        cv.adjustRelativeMargin(viewHolder.receivedImageThumbnail,CommonViewUtility.LEFT,15);
        cv.adjustRelativeMargin(viewHolder.chatMessageLayout,CommonViewUtility.TOP,35);
        cv.adjustRelativeMargin(viewHolder.chatMessageLayout,CommonViewUtility.LEFT,30);
        cv.adjustRelativeMargin(viewHolder.postedTimeLabel,CommonViewUtility.LEFT,28);
        cv.adjustLinearMargin(viewHolder.messageReceivedLayout,CommonViewUtility.TOP,20);
        cv.adjustRelativeSquare(viewHolder.receivedImageThumbnail,116);
        viewHolder.userNameLabel.setTextColor(Color.parseColor("#505f67"));

        if(model.getUserStatus())
            viewHolder.messageStatusImage.setImageResource(R.drawable.circle_blue_solid);
        else
            viewHolder.messageStatusImage.setImageResource(R.drawable.circle_blue_holo);

        viewHolder.messageText.setText(model.getLastMessage());
        viewHolder.profileImage.setUrl(model.getUserAvatar());
        viewHolder.userNameLabel.setText(model.getDisplayName());
        viewHolder.unreadMessageLbl.setText("" + model.getNewMessageCount());
        viewHolder.postedTimeLabel.setText("Today 11:17 AM");
        if(model.getLastMessage().contains("Image")){
            viewHolder.messageText.setVisibility(View.GONE);
            viewHolder.receivedImageThumbnail.setVisibility(View.VISIBLE);
            viewHolder.receivedImageThumbnail.setUrl(model.getBaseImage());
        }

        else {
            viewHolder.messageText.setVisibility(View.VISIBLE);
            viewHolder.receivedImageThumbnail.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatHistoryList.size();
    }
}
