package ChatUI.fragments.ChatAdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
        cv.adjustRelativeSquare(viewHolder.profileImage,150);
        cv.adjustRelativeSquare(viewHolder.unreadMessageLbl,50);
        cv.adjustRelativeSquare(viewHolder.messageStatusImage,20);

        if(model.getUserStatus()){
            viewHolder.messageStatusImage.setImageResource(R.drawable.circle_blue_solid);
        }

        else
            viewHolder.messageStatusImage.setImageResource(R.drawable.circle_blue_holo);


        viewHolder.messageText.setText(model.getLastMessage());
        viewHolder.profileImage.setUrl(model.getUserAvatar());
        viewHolder.userNameLabel.setText(model.getDisplayName());
        viewHolder.unreadMessageLbl.setText("" + model.getNewMessageCount());
        viewHolder.postedTimeLabel.setText("Today 11:17 AM");

        /*if(model.getLastMessageTime() != null)
            viewHolder.postedTimeLabel.setText(CommonMethods.calculateTimeDiffernce(Long.parseLong(model.getLastMessageTime())));
        if(model.getNewMessageCount()>0){
            viewHolder.unreadMessageLbl.setText("" + model.getNewMessageCount());
        }
        else {
            viewHolder.unreadMessageLbl.setVisibility(View.INVISIBLE);
        }*/

        /*if(!TextUtils.isEmpty(model.getMessageStatus())) {
            switch (model.getMessageStatus()) {
                case "not_send":
                    viewHolder.messageStatusImage.setImageResource(R.mipmap.unsend);
                    break;
                case "send":
                    viewHolder.messageStatusImage.setImageResource(R.mipmap.send);
                    break;
                case "deliver":
                    viewHolder.messageStatusImage.setImageResource(R.mipmap.send_unread);
                    break;
                case "read":
                    viewHolder.messageStatusImage.setImageResource(R.mipmap.send_read);
                    break;
            }
        }*/
/*        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyXMPP.getInstance(context).sendChatReqeuest(model.getDisplayName());
                Bundle b=new Bundle();
                b.putString("userName",model.getDisplayName());
                b.putString("displayName",model.getDisplayName());
                b.putString("jid", model.getJabberID());
                b.putString("state", "chat");
                Intent i=new Intent(context, ChatMainActivity.class);
                i.putExtras(b);
                context.startActivity(i);
            }
        });*/



    }

    @Override
    public int getItemCount() {
        return chatHistoryList.size();
    }
}
