package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinesh on 7/20/2016.
 */
public class RegisterUser {

    @SerializedName("response_code")
    private int response_code;
    @SerializedName("message")
    private String message;
    public MetaData data;
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

    public class MetaData{
        @SerializedName("guest_token")
        private String guest_token;
        @SerializedName("verification_code")
        private String verification_code;
        public void setGuestToken(String token){
            this.guest_token = token;
        }
        public String getGuestToken()
        {
            return guest_token;
        }
        public void setVerificationCode(String code){
            this.verification_code = code;
        }
        public String getVerificationCode(){
            return verification_code;
        }
    }
}


