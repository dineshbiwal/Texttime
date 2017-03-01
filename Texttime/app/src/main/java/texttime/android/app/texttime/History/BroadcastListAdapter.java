package texttime.android.app.texttime.History;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.Objects;

import CustomViews.CustomTextView;
import texttime.android.app.texttime.CommonClasses.AppDelegate;
import texttime.android.app.texttime.CommonClasses.CommonMethods;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.DataFunctions;
import texttime.android.app.texttime.DataModels.BroadcastHistoryModel;
import texttime.android.app.texttime.LikeComment.CommentList;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/16/2017.
 */

public class BroadcastListAdapter extends RecyclerView.Adapter {

    List<BroadcastHistoryModel> historyList;
    Context context;
    boolean hidePost = true;
    CommonViewUtility cv=CommonViewUtility.getInstance();

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
       // setFontSize(history);
        history.profileImage.setUrl(historyList.get(position).getProfile_image_url(), R.mipmap.placeholder);
        history.userName.setText(historyList.get(position).getUser_display_name());
        history.postedTime.setText(historyList.get(position).getPostedTimeAgo());
        history.likeCount.setText(CommonMethods.convertIntoKillo(historyList.get(position).getLikeCount()));
        history.userName.setTextColor(Color.parseColor("#505f66"));
        history.sharePersonName.setTextColor(Color.parseColor("#506068"));
        history.sharingTotal.setText(CommonMethods.convertIntoKillo(historyList.get(position).getTotal_shared())+ " Share");
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
            case CONTACT:
            case TEXT: String message = historyList.get(position).getMedia();
                history.textMessage.setVisibility(View.VISIBLE);
                history.textMessage.setMaxLines(3);
                history.textMessage.setBackgroundResource(R.drawable.rounded_rectangle);
                if(message.length() > 120)
                {
                    history.textMessage.setText(message);
                    history.readMore.setVisibility(View.VISIBLE);
                }
                else
                    history.textMessage.setText(message);
                break;
            case LOCATION:
                cv.adjustRelative(history.mediaImage, 615, 432);
                history.mediaImage.setVisibility(View.VISIBLE);
                history.mediaImage.setUrl(historyList.get(position).getMedia());
                break;
            case AUDIO:
                history.audioLayout.setBackgroundResource(R.drawable.rounded_rectangle);
                history.audioLayout.setVisibility(View.VISIBLE);
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
        history.songProgressBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    private void setonClickListener(final HistoryViewholder holder, final int position){
        holder.readMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                holder.textMessage.setMaxLines(1000);
                holder.textMessage.setText(historyList.get(position).getMedia());
                holder.readMore.setVisibility(View.GONE);
            }
        });
        holder.showHistory.setOnClickListener(new View.OnClickListener() {
            // true => Hide the post
            // false => Show the post
            @Override
            public void onClick(View v) {
                if(hidePost) {
                    holder.postView.setVisibility(View.GONE);
                    holder.showHistory.setImageResource(R.mipmap.ic_up_post);
                    hidePost = false;
                }else{
                    holder.showHistory.setImageResource(R.mipmap.ic_back_down);
                    holder.postView.setVisibility(View.VISIBLE);
                    hidePost = true;
                }
            }
        });

        holder.commentSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDelegate.getInstance().setBroadcastModel(historyList.get(position));
                Intent intent = new Intent(context, CommentList.class);
                context.startActivity(intent);
            }
        });
    }
    private void setUIComponent(HistoryViewholder holder){

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
        //cv.adjustRelativeMargin(holder.showHistory, CommonViewUtility.TOP, 15);
        cv.adjustRelative(holder.showHistory, 35, 22);
        cv.adjustRelativeMargin(holder.postedTime, CommonViewUtility.RIGHT, 23);
        cv.adjustRelativeMargin(holder.postedTime, CommonViewUtility.LEFT, 40);
        cv.adjustLinearMargin(holder.locationInfo, CommonViewUtility.TOP, 15);
        cv.adjustLinear(holder.locationIcon, 21, 29);
        cv.adjustLinearMargin(holder.locationAddress, CommonViewUtility.LEFT, 26);
        cv.adjustLinearMargin(holder.sharedSection, CommonViewUtility.TOP, 31);
        cv.adjustLinearSquare(holder.sharePersonImg, 87);
        cv.adjustLinearMargin(holder.sharePersonName, CommonViewUtility.LEFT, 21);
        cv.adjustLinearMargin(holder.sharePostedTime, CommonViewUtility.LEFT, 21);
        cv.adjustLinearMargin(holder.shareHistory, CommonViewUtility.LEFT, 21);
        cv.adjustLinear(holder.shareHistory, 44, 36);
        cv.adjustLinearMargin(holder.shareCount, CommonViewUtility.LEFT, 18);
        cv.adjustLinearMargin(holder.medialayout, CommonViewUtility.TOP, 31);
        cv.adjustLinearMargin(holder.medialayout, CommonViewUtility.RIGHT, 75);
        cv.adjustRelative(holder.mediaImage, 792, 432);
        cv.adjustRelativeSquare(holder.mediaPlay, 144);
        cv.adjustLinearMargin(holder.features, CommonViewUtility.TOP, 46);
        cv.adjustLinearMargin(holder.features, CommonViewUtility.RIGHT, 75);
        cv.adjustLinearMargin(holder.commentSection, CommonViewUtility.LEFT, 69);
        //cv.adjustLinearWidth(holder.leaveComment, 307);
        cv.adjustLinear(holder.sharingsHistory, 44, 36);
        cv.adjustLinearMargin(holder.sharingsHistory, CommonViewUtility.RIGHT, 18);
        cv.adjustLinear(holder.commentHistory, 41, 36);
        cv.adjustLinearMargin(holder.commentCount, CommonViewUtility.LEFT, 18);
        //cv.adjustLinearMargin(holder.goCommentHistory, CommonViewUtility.LEFT, 6);
        //cv.adjustLinear(holder.goCommentHistory, 18, 24);
        cv.adjustLinearMargin(holder.historyLayout, CommonViewUtility.BOTTOM, 20);
        //cv.adjustRelativeMargin(holder.readMore, CommonViewUtility.TOP, 20);
        cv.adjustRelativeHeight(holder.audioLayout, 104);
        cv.adjustRelative(holder.audioPlay, 39, 47);
        cv.adjustRelativeWidth(holder.songProgressBar, 445);
       // cv.adjustRelativeMargin(holder.songProgressBar, CommonViewUtility.LEFT, 12);
       // cv.adjustRelativeMargin(holder.songProgressBar, CommonViewUtility.RIGHT, 12);
    }

    private void setFontSize(HistoryViewholder holder){
        holder.userName.setTextSize(DataFunctions.convertPixelsToDp(context,30));
        holder.postedTime.setTextSize(DataFunctions.convertPixelsToDp(context,18));
        holder.locationAddress.setTextSize(DataFunctions.convertPixelsToDp(context,18));
        holder.sharePersonName.setTextSize(DataFunctions.convertPixelsToDp(context,20));
        holder.sharePostedTime.setTextSize(DataFunctions.convertPixelsToDp(context,18));
        holder.shareCount.setTextSize(DataFunctions.convertPixelsToDp(context,20));
        holder.likeCount.setTextSize(DataFunctions.convertPixelsToDp(context,20));
        holder.readMore.setTextSize(DataFunctions.convertPixelsToDp(context,30));
        holder.commentCount.setTextSize(DataFunctions.convertPixelsToDp(context,20));
        holder.textMessage.setTextSize(DataFunctions.convertPixelsToDp(context,30));
        holder.audioTimer.setTextSize(DataFunctions.convertPixelsToDp(context,20));
    }
}
