package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TextTime Android Dev on 8/17/2016.
 */
public class ValidateUserName {
    @SerializedName("response_code")
    int response_code;
    @SerializedName("message")
    String message;

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
}
