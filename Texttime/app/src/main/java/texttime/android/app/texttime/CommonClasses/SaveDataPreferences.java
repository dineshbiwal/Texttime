package texttime.android.app.texttime.CommonClasses;

/**
 * Created by Dinesh on 7/26/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveDataPreferences {

   SharedPreferences prefs;
   SharedPreferences.Editor editor;
   Context context;

   public SaveDataPreferences(Context context) {
       this.context = context;
       prefs = PreferenceManager.getDefaultSharedPreferences(context);
       editor = prefs.edit();
   }
   public void setDialCode(String dialCode)
   {
       editor.putString("dialCode", dialCode);
       editor.commit();
   }
   public String getDialCode(){
       return prefs.getString("dialCode", "+91");
   }
   public void setCountryName(String countryname){
       editor.putString("countryName", countryname);
       editor.commit();
   }
   public String getCountryName(){
       return  prefs.getString("countryName","India");
   }
   public void setISOAlpha2(String alpha2){
       editor.putString("ISOAlpha2",alpha2);
       editor.commit();
   }
   public String getISOAlpha2()
   {
       return prefs.getString("ISOAlpha2","IN");
   }
   public void setToken(String token){
       editor.putString("token", token);
       editor.commit();
   }
   public String getToken()
   {
       return prefs.getString("token","");
   }
   public void setUsername(String username){
       editor.putString("username", username);
       editor.commit();
   }

    public void setLastSyncCOntact(String contact){
        editor.putString("contact", contact);
        editor.commit();
    }
   public String getUsername(){
       return prefs.getString("username", "");
   }
   public void setDisplayName(String displayName){
       editor.putString("displayName", displayName);
       editor.commit();
   }
   public String getDisplayName(){
       return prefs.getString("displayName", "");
   }

    public void setPhoneNumber(String phoneNumber){
        editor.putString("phoneNumber", phoneNumber);
        editor.commit();
    }
    public String getPhoneNumber(){
        return prefs.getString("phoneNumber","");
    }
    public String getLastSyncContact(){
        return prefs.getString("contact","");
    }
    public void setProfileImagePath(String profileImage){
        editor.putString("profileImage", profileImage);
        editor.commit();
    }
    public String getProfileImagePath(){
        return prefs.getString("profileImage", "");
    }
    public void setAccessToken(String accessToken){
        editor.putString("accessToken", accessToken);
        editor.commit();
    }
    public String getAccessToken(){
        return prefs.getString("accessToken","");
    }

    public void setUserJID(String jid){
        editor.putString("jid", jid);
        editor.commit();
    }
    public String getUserJID(){
        return prefs.getString("jid","");
    }

    public void setUserPassword(String password){
        editor.putString("password", password);
        editor.commit();
    }
    public String getUserPassword(){
        return prefs.getString("password","");
    }


}
