package texttime.android.app.texttime.CommonClasses;

import texttime.android.app.texttime.callbacks.OTPRecievedCallback;

/**
 * Created by Dinesh_Text on 2/8/2017.
 */

public class AppDelegate {

    public static AppDelegate ad;
    OTPRecievedCallback otpCallback;

    public static AppDelegate getInstance() {
        if (ad != null)
            return ad;

        else {
            ad = new AppDelegate();
            return ad;
        }
    }

    public OTPRecievedCallback getOtpCallback() {
        return otpCallback;
    }

    public void setOtpCallback(OTPRecievedCallback otpCallback) {
        this.otpCallback = otpCallback;
    }

}
