package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by TextTime Android Dev on 9/1/2016.
 */
public class FollowRequestsModel {
    @SerializedName("response_code")
    int response_code;
    @SerializedName("message")
    String message;

    ArrayList<FollowingRequestData> data;

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

    public ArrayList<FollowingRequestData> getData() {
        return data;
    }

    public void setData(ArrayList<FollowingRequestData> data) {
        this.data = data;
    }

    public class FollowingRequestData{
        @SerializedName("username")
        String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
