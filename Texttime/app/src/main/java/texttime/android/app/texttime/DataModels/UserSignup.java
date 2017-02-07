package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinesh on 7/23/2016.
 */
public class UserSignup {

    @SerializedName("response_code")
    private String response_code;
    @SerializedName("message")
    private String message;
    public UserMetaData data;

    public void setResponseCode(String code)
    {
        response_code = code;
    }
    public String getResponseCode(){
        return response_code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage()
    {
        return message;
    }
    public class UserMetaData{
        @SerializedName("token")
        private String token;
        @SerializedName("display_name")
        private String display_name;
        @SerializedName("seen_at")
        private String seen_at;
        @SerializedName("jid")
        private String jid;

        public String getJid() {
            return jid;
        }

        public void setJid(String jid) {
            this.jid = jid;
        }

        public void setToken(String token){
            this.token = token;
        }
        public String getToken(){
            return token;
        }
        public void setDisplayName(String name){
            display_name = name;
        }
        public String getDisplayName(){
            return display_name;
        }
        public void setSeenAt(String seenAt)
        {
            seen_at = seenAt;
        }
        public String getSeenAt()
        {
            return seen_at;
        }
    }

}
