package texttime.android.app.texttime.CommonClasses;

import android.content.Context;

import org.json.JSONArray;

import texttime.android.app.texttime.WebOperations.ApiClient;
import texttime.android.app.texttime.callbacks.OTPRecievedCallback;

/**
 * Created by Dinesh_Text on 2/8/2017.
 */

public class AppDelegate {

    public static AppDelegate ad;
    OTPRecievedCallback otpCallback;
    String clickedImage;
    String croppedImage;
    String returningToken;
    boolean isReturningUser;
    boolean isVideoCaptured;

    public static AppDelegate getInstance() {
        if (ad != null)
            return ad;

        else {
            ad = new AppDelegate();
            return ad;
        }
    }

    public boolean isVideoCaptured() {
        return isVideoCaptured;
    }

    public void setVideoCaptured(boolean videoCaptured) {
        isVideoCaptured = videoCaptured;
    }
    public OTPRecievedCallback getOtpCallback() {
        return otpCallback;
    }

    public void setOtpCallback(OTPRecievedCallback otpCallback) {
        this.otpCallback = otpCallback;
    }

    public void getCountryDetails(String countryName,Context context){
        SaveDataPreferences sd=new SaveDataPreferences(context);
        try {
            JSONArray prior = new CountryJSON().getFirstPriorityCountry();
            for (int i = 0; i < prior.length(); i++) {
                if (prior.getJSONObject(i).getString("code").equalsIgnoreCase(countryName)) {
                    sd.setCountryName(prior.getJSONObject(i).getString("name"));
                    sd.setISOAlpha2(prior.getJSONObject(i).getString("code"));
                    sd.setDialCode(prior.getJSONObject(i).getString("dial_code"));

                    return;
                }
            }
            JSONArray countries = new CountryJSON().getAllCountryList();
            for (int i = 0; i < countries.length(); i++) {
                if (countries.getJSONObject(i).getString("code").equalsIgnoreCase(countryName)) {
                    sd.setCountryName(countryName);
                    sd.setISOAlpha2(countries.getJSONObject(i).getString("code"));
                    sd.setDialCode(countries.getJSONObject(i).getString("dial_code"));

                    return;
                }
            }
        }
        catch (Exception e){}
    }
    public String getClickedImage() {
        return clickedImage;
    }
    public void setClickedImage(String clickedImage) { this.clickedImage = clickedImage; }
    public String getCroppedImage() {
        return croppedImage;
    }
    public void setCroppedImage(String croppedImage) {
        this.croppedImage = croppedImage;
    }
    public void setReturningToken(String returningToken) {
        this.returningToken = returningToken;
    }
    public String getReturningToken() { return returningToken; }
    public void setReturningUser(boolean returningUser) {
        isReturningUser = returningUser;
    }
    public boolean isReturningUser() { return isReturningUser; }
    public String imageUrl(String id,SaveDataPreferences sd){
        String url = ApiClient.BASE_URL + "profile/images/original/" + sd.getUsername() + "/" + id;
        return url;
    }
    public static String getServerImageUrl(String media, String imageType, String username, String imageName){
        return ApiClient.BASE_URL + media+"/images/"+imageType+"/"+username+"/"+imageName;
    }
}
