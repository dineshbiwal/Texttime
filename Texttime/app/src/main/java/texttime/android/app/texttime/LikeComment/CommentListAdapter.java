package texttime.android.app.texttime.LikeComment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.DataModels.CommentListModel;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/23/2017.
 */

public class CommentListAdapter extends RecyclerView.Adapter {

    Context context;
    List<CommentListModel> model;

    public CommentListAdapter(Context context, List<CommentListModel> model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder viewHolder = (CommentViewHolder) holder;
        setUIComponent(viewHolder);
        viewHolder.commentedUserName.setTextColor(Color.parseColor("#505f67"));
        viewHolder.commentedUserName.setText(model.get(position).getProfile_display_name());
        viewHolder.commentedTime.setText(model.get(position).getPostedTime());
        viewHolder.commentedUserProfile.setUrl(model.get(position).getProfile_image_url(),R.mipmap.placeholder);
        viewHolder.commentedText.setText(model.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    private void setUIComponent(CommentViewHolder holder){
        CommonViewUtility cv = new CommonViewUtility();
        cv.adjustLinearSquare(holder.commentedUserProfile, 173);
        cv.adjustLinearMargin(holder.commentedUserProfile, CommonViewUtility.LEFT, 14);
        cv.adjustLinearMargin(holder.commentedUserProfile, CommonViewUtility.RIGHT, 14);
        cv.adjustLinearMargin(holder.commentedUserName, CommonViewUtility.LEFT, 18);
        cv.adjustLinearMargin(holder.commentedUserName, CommonViewUtility.RIGHT, 35);
        cv.adjustLinearMargin(holder.commentedTime, CommonViewUtility.RIGHT, 35);
        cv.adjustLinearMargin(holder.commentedText, CommonViewUtility.LEFT, 35);
        cv.adjustLinearMargin(holder.commentedText, CommonViewUtility.RIGHT, 35);
    }
}
