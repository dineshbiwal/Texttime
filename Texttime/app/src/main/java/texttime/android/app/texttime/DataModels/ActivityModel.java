package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinesh on 10/28/16.
 */

public class ActivityModel {

    @SerializedName("u_id")
    private String u_id;
    @SerializedName("username")
    private String username;
    @SerializedName("display_name")
    private String display_name;
    @SerializedName("profile_image")
    private String profile_image;
    @SerializedName("a_id")
    private String a_id;
    @SerializedName("a_more_count")
    private int a_more_count;
    @SerializedName("a_hashtag_text")
    private String a_hashtag_text;
    @SerializedName("can_liked")
    private boolean can_liked;
    @SerializedName("can_comment")
    private boolean can_share;
    @SerializedName("can_share")
    private boolean can_comment;
    @SerializedName("is_liked")
    private boolean is_liked;
    @SerializedName("comment")
    private String comment;
    @SerializedName("m_type")
    private int m_type;
    @SerializedName("a_created_at")
    private long a_created_at;

    public ActivityCount a_counts;
    public ImageActivity m_image;
    public ActivityVideo m_video;

    public long getA_created_at() {
        return a_created_at;
    }

    public void setA_created_at(long a_created_at) {
        this.a_created_at = a_created_at;
    }

    public void setM_type(int m_type) {
        this.m_type = m_type;
    }

    public int getM_type() {
        return m_type;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public boolean isCan_share() {
        return can_share;
    }

    public void setCan_share(boolean can_share) {
        this.can_share = can_share;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean is_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public boolean isCan_comment() {
        return can_comment;
    }

    public void setCan_comment(boolean can_comment) {
        this.can_comment = can_comment;
    }

    public boolean isCan_liked() {
        return can_liked;
    }

    public void setCan_liked(boolean can_liked) {
        this.can_liked = can_liked;
    }

    public String getA_hashtag_text() {
        return a_hashtag_text;
    }

    public void setA_hashtag_text(String a_hashtag_text) {
        this.a_hashtag_text = a_hashtag_text;
    }

    public int getA_more_count() {
        return a_more_count;
    }

    public void setA_more_count(int a_more_count) {
        this.a_more_count = a_more_count;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public class ActivityCount{
        @SerializedName("likes")
        private int likes = 0;
        @SerializedName("share")
        private int share = 0;
        @SerializedName("comment")
        private int comment = 0;
        @SerializedName("views")
        private int views = 0;

        public int getLikes() {
            if(likes != 0)
                return likes;
            return 0;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public int getCommentCount() {
            if(comment != 0)
                return comment;
            return 0;
        }

        public void setCommentCount(int comment) {
            this.comment = comment;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }
    }

    public class ImageActivity{
        @SerializedName("filename")
        private String filename;
        @SerializedName("dimensions")
        private String dimensions;
        @SerializedName("thumbnail")
        private int thumbnail;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getDimensions() {
            return dimensions;
        }

        public void setDimensions(String dimensions) {
            this.dimensions = dimensions;
        }

        public int getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(int thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    public class ActivityVideo{
        @SerializedName("filename")
        private String filename;
        @SerializedName("length")
        private int length;
        @SerializedName("thumbnail")
        private int thumbnail;

        public int getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(int thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
