package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by TextTime Android Dev on 8/30/2016.
 */
public class MeFollowingData {

    @SerializedName("response_code")
    int response_code;
    @SerializedName("message")
    String message;
    ArrayList<UserData> data;

    UserMetaData meta;
    UserlinkData links;

    public UserMetaData getMeta() {
        return meta;
    }

    public void setMeta(UserMetaData meta) {
        this.meta = meta;
    }

    public UserlinkData getLinks() {
        return links;
    }

    public void setLinks(UserlinkData links) {
        this.links = links;
    }

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

    public ArrayList<UserData> getData() {
        return data;
    }

    public void setData(ArrayList<UserData> data) {
        this.data = data;
    }

    public class UserData{
        @SerializedName("rid")
        String rid;
        @SerializedName("username")
        String username;
        @SerializedName("display_name")
        String display_name;
        @SerializedName("profile_image")
        String profile_image;

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
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
    }

    public class UserMetaData{
        @SerializedName("hasPrev")
        boolean hasPrev;
        @SerializedName("hasNext")
        boolean hasNext;

        public boolean isHasPrev() {
            return hasPrev;
        }

        public void setHasPrev(boolean hasPrev) {
            this.hasPrev = hasPrev;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }
    }


    public class UserlinkData{
        @SerializedName("self")
        String self;
        @SerializedName("next")
        String next;
        @SerializedName("previous")
        String previous;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }
    }
}
