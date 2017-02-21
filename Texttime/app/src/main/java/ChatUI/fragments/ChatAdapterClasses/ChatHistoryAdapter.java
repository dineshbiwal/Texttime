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
import ChatUI.fragments.SelectionInterface;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.R;


/**
 * Created by DELL on 11/28/2016.
 */

public class ChatHistoryAdapter extends RecyclerView.Adapter implements SelectionInterface{

    ArrayList<ChattingUserList> chatHistoryList;
    Context context;

    SelectionInterface selectionInterface;
    boolean isSelection=false;


    public ChatHistoryAdapter(ArrayList<ChattingUserList> chatHistoryList, Context context) {
        this.chatHistoryList = chatHistoryList;
        this.context = context;
        this.selectionInterface=this;
        AppDelegate.getInstance().setUserSelectionCallback(this);

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

        viewHolder.chatContainerRowLayout.setTag(model);
        viewHolder.chatContainerRowLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!isSelection) {
                    selectionInterface.activateSelectionMode();
                    ChattingUserList modelClicked= (ChattingUserList) view.getTag();
                    AppDelegate.getInstance().getSelectedUserList().add(modelClicked);
                    view.setBackgroundColor(Color.parseColor("#1A000000"));
                    return true;
                }
                return false;
            }
        });

        viewHolder.chatContainerRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelection){
                    ChattingUserList modelClicked= (ChattingUserList) view.getTag();
                    if(AppDelegate.getInstance().getSelectedUserList().contains(modelClicked)){
                        AppDelegate.getInstance().getSelectedUserList().remove(modelClicked);
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        view.setAlpha(1);
                    }

                    else {
                        AppDelegate.getInstance().getSelectedUserList().add(modelClicked);
                        view.setBackgroundColor(Color.parseColor("#1A000000"));
                        //view.setAlpha(0.1f);
                    }
                }
            }
        });

        viewHolder.chatContainerRowLayout.setPadding(cv.getWidth(15),cv.getHeight(28),cv.getWidth(15),cv.getHeight(28));

    }

    @Override
    public int getItemCount() {
        return chatHistoryList.size();
    }

    @Override
    public void activateSelectionMode() {
        isSelection=true;
        AppDelegate.getInstance().setChatSelection(true);
        AppDelegate.getInstance().setSelectedUserList(new ArrayList<ChattingUserList>());
    }

    @Override
    public void deactivateSelectionMode() {
        AppDelegate.getInstance().setSelectedUserList(new ArrayList<ChattingUserList>());
        isSelection=false;
        notifyDataSetChanged();
    }
}
