package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TextTime Android Dev on 8/29/2016.
 */
public class MyProfileModel {
    @SerializedName("response_code")
    int response_code;
    @SerializedName("message")
    String message;

    MyProfileData data;

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

    public MyProfileData getData() {
        return data;
    }

    public void setData(MyProfileData data) {
        this.data = data;
    }

    public class MyProfileData{
        @SerializedName("username")
        String username;
        @SerializedName("display_name")
        String display_name;
        @SerializedName("profile_image")
        String profile_image;
        @SerializedName("follows")
        int follows;
        @SerializedName("followed_by")
        int followed_by;
        @SerializedName("activities")
        int activities;
        @SerializedName("mobile_number")
        String mobile_number;
        @SerializedName("rid")
        String rid;
        MetaData urls;



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

        public MetaData getUrls() {
            return urls;
        }

        public void setUrls(MetaData urls) {
            this.urls = urls;
        }
    }

    public class MetaData{
        @SerializedName("activities")
        String activities;
        @SerializedName("follows")
        String follows;
        @SerializedName("followed_by")
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
