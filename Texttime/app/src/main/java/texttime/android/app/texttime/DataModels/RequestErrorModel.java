package texttime.android.app.texttime.DataModels;

/**
 * Created by TextTime Android Dev on 9/2/2016.
 */
public class RequestErrorModel {
    int statusCode;
    String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
