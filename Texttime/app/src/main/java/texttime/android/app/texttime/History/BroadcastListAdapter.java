package texttime.android.app.texttime.History;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

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
        history.likeCount.setText(historyList.get(position).getLikeCount()+"");
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
            history.shareCount.setText(historyList.get(position).getShareCount()+"");
        }
        history.textMessage.setText(historyList.get(position).getMedia_text());
        history.commentCount.setText(historyList.get(position).getCommentCount() + " Comments");
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    private void setUIComponent(HistoryViewholder holder){
        CommonViewUtility cv=CommonViewUtility.getInstance();
        cv.adjustLinearSquare(holder.profileImage, 144);
        cv.adjustLinearMargin(holder.profileImage, CommonViewUtility.LEFT, 19);
        cv.adjustLinearMargin(holder.profileImage, CommonViewUtility.RIGHT, 19);
        cv.adjustLinearMargin(holder.likeHistory, CommonViewUtility.TOP, 58);
        cv.adjustLinearSquare(holder.likeHistory, 72);
        cv.adjustLinearMargin(holder.likeCount, CommonViewUtility.TOP, 8);
        cv.adjustLinearMargin(holder.history, CommonViewUtility.TOP, 53);
        cv.adjustLinearMargin(holder.historyLayout, CommonViewUtility.LEFT, 10);
        cv.adjustRelativeMargin(holder.showHistory, CommonViewUtility.RIGHT, 46);
        cv.adjustRelativeMargin(holder.showHistory, CommonViewUtility.LEFT, 46);
        cv.adjustLinearHeight(holder.locationIcon, 43);
        cv.adjustLinearWidth(holder.locationIcon, 30);
        cv.adjustLinearMargin(holder.locationAddress, CommonViewUtility.LEFT, 27);
        cv.adjustLinearMargin(holder.sharedSection, CommonViewUtility.TOP, 16);
        cv.adjustLinearSquare(holder.sharePersonImg, 87);
        cv.adjustLinearMargin(holder.sharePersonName, CommonViewUtility.LEFT, 21);
        cv.adjustLinearMargin(holder.sharePostedTime, CommonViewUtility.LEFT, 21);
        cv.adjustLinearMargin(holder.shareHistory, CommonViewUtility.LEFT, 15);
        cv.adjustLinearHeight(holder.shareHistory, 50);
        cv.adjustLinearWidth(holder.shareHistory, 60);
        cv.adjustLinearMargin(holder.shareCount, CommonViewUtility.LEFT, 13);
        cv.adjustLinearMargin(holder.medialayout, CommonViewUtility.TOP, 16);
        cv.adjustLinearMargin(holder.medialayout, CommonViewUtility.RIGHT, 75);
        cv.adjustLinearMargin(holder.features, CommonViewUtility.TOP, 46);
        cv.adjustLinearMargin(holder.commentHistory, CommonViewUtility.LEFT, 90);
        cv.adjustLinearHeight(holder.commentHistory, 50);
        cv.adjustLinearWidth(holder.commentHistory, 56);
        cv.adjustLinearMargin(holder.commentCount, CommonViewUtility.LEFT, 18);
        cv.adjustLinearMargin(holder.goCommentHistory, CommonViewUtility.LEFT, 6);
        cv.adjustLinearHeight(holder.goCommentHistory, 35);
        cv.adjustLinearWidth(holder.goCommentHistory, 22);
        cv.adjustLinearMargin(holder.goCommentHistory, CommonViewUtility.RIGHT, 75);

    }
}
