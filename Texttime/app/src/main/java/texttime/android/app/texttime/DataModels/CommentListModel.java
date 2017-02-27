package texttime.android.app.texttime.DataModels;

/**
 * Created by Dinesh_Text on 2/23/2017.
 */

public class CommentListModel {

    String profile_image_url;
    String profile_display_name;
    String profile_user_name;
    String comment;
    String postedTime;

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_display_name() {
        return profile_display_name;
    }

    public void setProfile_display_name(String profile_display_name) {
        this.profile_display_name = profile_display_name;
    }

    public String getProfile_user_name() {
        return profile_user_name;
    }

    public void setProfile_user_name(String profile_user_name) {
        this.profile_user_name = profile_user_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
