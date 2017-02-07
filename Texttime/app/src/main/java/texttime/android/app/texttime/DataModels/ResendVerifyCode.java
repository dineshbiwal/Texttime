package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinesh on 7/26/2016.
 */
public class ResendVerifyCode {
    @SerializedName("response_code")
    private int response_code;
    @SerializedName("message")
    private String message;
    public ResendData data;
    public void setResponseCode(int code){
        this.response_code = code;
    }
    public int getResponseCode(){
        return response_code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public class ResendData{
        @SerializedName("resend_limit")
        private int resend_limit;
        @SerializedName("resend_reached")
        private int resend_reached;
        @SerializedName("verification_code")
        private String verification_code;

        public void setResendLimit(int limit){
            this.resend_limit = limit;
        }
        public int getResendLimit(){
            return resend_limit;
        }
        public void setResendReached(int reached){
            this.resend_reached = reached;
        }
        public int getResendReached() {
            return resend_reached;
        }
        public void setVerificationCode(String code){
            this.verification_code = code;
        }
        public String getVerificationCode(){
            return this.verification_code;
        }
    }
}
