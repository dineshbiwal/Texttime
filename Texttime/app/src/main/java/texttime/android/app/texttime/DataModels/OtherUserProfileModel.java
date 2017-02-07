package texttime.android.app.texttime.DataModels;

/**
 * Created by TextTime Android Dev on 9/3/2016.
 */
public class OtherUserProfileModel {
    int response_code;
    String message;
    OtherUserModel data;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OtherUserModel getData() {
        return data;
    }

    public void setData(OtherUserModel data) {
        this.data = data;
    }

    public class OtherUserModel{
        String username;
        String display_name;
        String profile_image;
        int follows;
        int followed_by;
        int activities;
        String mobile_number;
        String rid;
        String relation;
        UrlActivities urls;

        public UrlActivities getUrls() {
            return urls;
        }

        public void setUrls(UrlActivities urls) {
            this.urls = urls;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public int getFollows() {
            return follows;
        }

        public void setFollows(int follows) {
            this.follows = follows;
        }

        public int getFollowed_by() {
            return followed_by;
        }

        public void setFollowed_by(int followed_by) {
            this.followed_by = followed_by;
        }

        public int getActivities() {
            return activities;
        }

        public void setActivities(int activities) {
            this.activities = activities;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }

    public class UrlActivities{
        String activities;
        String follows;
        String followed_by;

        public String getActivities() {
            return activities;
        }

        public void setActivities(String activities) {
            this.activities = activities;
        }

        public String getFollows() {
            return follows;
        }

        public void setFollows(String follows) {
            this.follows = follows;
        }

        public String getFollowed_by() {
            return followed_by;
        }

        public void setFollowed_by(String followed_by) {
            this.followed_by = followed_by;
        }
    }
}
