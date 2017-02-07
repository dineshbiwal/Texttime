package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Dinesh on 9/7/2016.
 */

public class RecentActivitiesModel {

    @SerializedName("response_code")
    private int response_code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<ActivityModel> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public ArrayList<ActivityModel> getData() {
        return data;
    }

    public void setData(ArrayList<ActivityModel> data) {
        this.data = data;
    }
}
