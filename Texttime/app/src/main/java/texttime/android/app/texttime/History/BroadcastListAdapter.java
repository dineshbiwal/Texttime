package texttime.android.app.texttime.History;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import texttime.android.app.texttime.CommonClasses.CommonMethods;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.DataModels.BroadcastHistoryModel;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/16/2017.
 */

public class BroadcastListAdapter extends RecyclerView.Adapter {

    List<BroadcastHistoryModel> historyList;
    Context context;

    public BroadcastListAdapter(Context context, List<BroadcastHistoryModel> model) {
        this.context = context;
        historyList = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.broadcast_list_item, parent, false);
        return new HistoryViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoryViewholder history = (HistoryViewholder) holder;
        setUIComponent(history);
        history.profileImage.setUrl(historyList.get(position).getProfile_image_url(), R.mipmap.placeholder);
        history.userName.setText(historyList.get(position).getUser_display_name());
        history.postedTime.setText(historyList.get(position).getPostedTimeAgo());
        history.likeCount.setText(CommonMethods.convertIntoKillo(historyList.get(position).getLikeCount()));
        if(historyList.get(position).isLike())
            history.likeHistory.setImageResource(R.mipmap.liked_home_page);
        if(!TextUtils.isEmpty(historyList.get(position).getLocation_address())){
            history.locationInfo.setVisibility(View.VISIBLE);
            history.locationAddress.setText(historyList.get(position).getLocation_address());
        }
        if(!TextUtils.isEmpty(historyList.get(position).getShare_user_display_name())){
            history.sharedSection.setVisibility(View.VISIBLE);
            history.sharePersonName.setText(historyList.get(position).getShare_user_display_name());
            history.sharePersonImg.setUrl(historyList.get(position).getShare_user_profile_url(), R.mipmap.placeholder);
            history.sharePostedTime.setText(historyList.get(position).getShare_postedtime_ago());
            history.shareCount.setText(CommonMethods.convertIntoKillo(historyList.get(position).getShareCount()));
        }
        history.commentCount.setText(CommonMethods.convertIntoKillo(historyList.get(position).getCommentCount()) + " Comments");
        switch (historyList.get(position).getMedia_type()){
            case DOCUMENT:
            case PDF:
            case AUDIO:
            case CONTACT:
            case LOCATION:
            case TEXT: String message = historyList.get(position).getMedia();
                history.textMessage.setVisibility(View.VISIBLE);
                if(message.length() > 140)
                {
                    history.textMessage.setText(message.substring(0, 140));
                    history.readMore.setVisibility(View.VISIBLE);
                }
                else
                    history.textMessage.setText(message);
                break;
            case IMAGE:
                history.mediaImage.setVisibility(View.VISIBLE);
                history.mediaImage.setUrl(historyList.get(position).getMedia());
                break;
            case VIDEO:
                history.mediaPlay.setVisibility(View.VISIBLE);
                history.mediaImage.setVisibility(View.VISIBLE);
                history.mediaImage.setUrl(historyList.get(position).getMedia());
                break;
        }
        setonClickListener(history, position);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    private void setonClickListener(final HistoryViewholder holder, final int position){
        holder.readMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                holder.textMessage.setText(historyList.get(position).getMedia());
                holder.readMore.setVisibility(View.GONE);
            }
        });
    }
    private void setUIComponent(HistoryViewholder holder){
        CommonViewUtility cv=CommonViewUtility.getInstance();
        cv.adjustLinearSquare(holder.profileImage, 144);
        cv.adjustLinearMargin(holder.profileImage, CommonViewUtility.TOP, 35);
        cv.adjustLinearMargin(holder.profileImage, CommonViewUtility.LEFT, 19);
        cv.adjustLinearMargin(holder.profileImage, CommonViewUtility.RIGHT, 19);
        cv.adjustLinearMargin(holder.likeHistory, CommonViewUtility.TOP, 58);
        cv.adjustLinearSquare(holder.likeHistory, 72);
        cv.adjustLinearMargin(holder.likeCount, CommonViewUtility.TOP, 8);
        cv.adjustLinearMargin(holder.history, CommonViewUtility.TOP, 35);
        cv.adjustLinearMargin(holder.historyLayout, CommonViewUtility.LEFT, 10);
        cv.adjustRelativeMargin(holder.showHistory, CommonViewUtility.RIGHT, 46);
        cv.adjustRelative(holder.showHistory, 52, 33);
        cv.adjustRelativeMargin(holder.postedTime, CommonViewUtility.RIGHT, 20);
        cv.adjustRelativeMargin(holder.postedTime, CommonViewUtility.LEFT, 40);
        cv.adjustLinear(holder.locationIcon, 30, 43);
        cv.adjustLinearMargin(holder.locationAddress, CommonViewUtility.LEFT, 22);
        cv.adjustLinearMargin(holder.sharedSection, CommonViewUtility.TOP, 31);
        cv.adjustLinearSquare(holder.sharePersonImg, 87);
        cv.adjustLinearMargin(holder.sharePersonName, CommonViewUtility.LEFT, 21);
        cv.adjustLinearMargin(holder.sharePostedTime, CommonViewUtility.LEFT, 21);
        cv.adjustLinearMargin(holder.shareHistory, CommonViewUtility.LEFT, 21);
        cv.adjustLinear(holder.shareHistory, 52, 62);
        cv.adjustLinearMargin(holder.shareCount, CommonViewUtility.LEFT, 18);
        cv.adjustLinearMargin(holder.medialayout, CommonViewUtility.TOP, 31);
        cv.adjustLinearMargin(holder.medialayout, CommonViewUtility.RIGHT, 75);
        cv.adjustRelative(holder.medialayout, 808, 432);
        cv.adjustRelative(holder.mediaPlay, 51, 61);
        cv.adjustLinearMargin(holder.features, CommonViewUtility.TOP, 46);
        cv.adjustLinearMargin(holder.features, CommonViewUtility.RIGHT, 75);
        cv.adjustLinearMargin(holder.commentHistory, CommonViewUtility.LEFT, 60);
        cv.adjustLinear(holder.commentHistory, 56, 50);
        cv.adjustLinearMargin(holder.commentCount, CommonViewUtility.LEFT, 18);
        cv.adjustLinearMargin(holder.goCommentHistory, CommonViewUtility.LEFT, 6);
        cv.adjustLinear(holder.goCommentHistory, 18, 24);
        cv.adjustLinearMargin(holder.historyLayout, CommonViewUtility.BOTTOM, 40);
    }
}
