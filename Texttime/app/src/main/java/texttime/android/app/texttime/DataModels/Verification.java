package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinesh on 7/21/2016.
 */
public class Verification {
    @SerializedName("response_code")
    private int response_code;
    @SerializedName("message")
    private String message;


    public RetruningUserData data;

    public void setResponseCode(int code){
        this.response_code = code;
    }
    public int getResponseCode(){
        return  response_code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    public class RetruningUserData{
        @SerializedName("is_returning")
        private boolean isReturning;
        @SerializedName("returning_token")
        private String returningToken;
        @SerializedName("username")
        private String username;
        @SerializedName("display_name")
        private String displayName;
        @SerializedName("seen_at")
        private String seenAt;

        @SerializedName("profile_image")
        private String profile_image;

      //  public ProfileImage images;

      public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public void setIsReturning(Boolean returning){
            this.isReturning = returning;
        }
        public  boolean getIsReturning(){
            return isReturning;
        }
        public void setReturningToken(String token)
        {
            this.returningToken = token;
        }
        public String getReturningToken(){
            return returningToken;
        }
        public void setUsername(String username){
            this.username = username;
        }
        public String getUsername(){
            return username;
        }
        public void setDisplayName(String displayName){
            this.displayName = displayName;
        }
        public String getDisplayName(){
            return displayName;
        }
        public void setSeenAt(String seenAt){
            this.seenAt = seenAt;
        }
        public String getSeenAt(){
            return seenAt;
        }
    }




}


