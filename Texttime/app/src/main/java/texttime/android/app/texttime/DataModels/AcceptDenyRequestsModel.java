package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TextTime Android Dev on 9/1/2016.
 */
public class AcceptDenyRequestsModel {
    @SerializedName("response_code")
    int response_code;
    @SerializedName("message")
    String message;

    StatusData data;

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

    public StatusData getData() {
        return data;
    }

    public void setData(StatusData data) {
        this.data = data;
    }

    public class StatusData{
        @SerializedName("in")
        String in;

        @SerializedName("out")
        String out;

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getIn() {
            return in;
        }

        public void setIn(String in) {
            this.in = in;
        }
    }
}
