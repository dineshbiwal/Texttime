package texttime.android.app.texttime.DataModels;

/**
 * Created by Dinesh_Text on 2/17/2017.
 */

public class BroadcastHistoryModel {
    String profile_image_url;
    String user_display_name;
    String postedTimeAgo;
    String media_type;
    String media_text;
    String media_viedo;
    String media_audio;
    String media_image;
    String media_location;
    String location_address;
    boolean isLike;
    long likeCount;
    long commentCount;
    long shareCount;
    String share_user_profile_url;
    String share_user_display_name;
    String share_postedtime_ago;

    public String getShare_postedtime_ago() {
        return share_postedtime_ago;
    }

    public void setShare_postedtime_ago(String share_postedtime_ago) {
        this.share_postedtime_ago = share_postedtime_ago;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getUser_display_name() {
        return user_display_name;
    }

    public void setUser_display_name(String user_display_name) {
        this.user_display_name = user_display_name;
    }

    public String getPostedTimeAgo() {
        return postedTimeAgo;
    }

    public void setPostedTimeAgo(String postedTimeAgo) {
        this.postedTimeAgo = postedTimeAgo;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_text() {
        return media_text;
    }

    public void setMedia_text(String media_text) {
        this.media_text = media_text;
    }

    public String getMedia_viedo() {
        return media_viedo;
    }

    public void setMedia_viedo(String media_viedo) {
        this.media_viedo = media_viedo;
    }

    public String getMedia_audio() {
        return media_audio;
    }

    public void setMedia_audio(String media_audio) {
        this.media_audio = media_audio;
    }

    public String getMedia_image() {
        return media_image;
    }

    public void setMedia_image(String media_image) {
        this.media_image = media_image;
    }

    public String getMedia_location() {
        return media_location;
    }

    public void setMedia_location(String media_location) {
        this.media_location = media_location;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    public String getShare_user_profile_url() {
        return share_user_profile_url;
    }

    public void setShare_user_profile_url(String share_user_profile_url) {
        this.share_user_profile_url = share_user_profile_url;
    }

    public String getShare_user_display_name() {
        return share_user_display_name;
    }

    public void setShare_user_display_name(String share_user_display_name) {
        this.share_user_display_name = share_user_display_name;
    }
}
