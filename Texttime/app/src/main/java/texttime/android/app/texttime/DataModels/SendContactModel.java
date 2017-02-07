package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TextTime Android Dev on 10/5/2016.
 */
public class SendContactModel {
    @SerializedName("country")
    public String country;
    @SerializedName("number")
    public String number;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public SendContactModel(String country, String number) {
        this.country = country;
        this.number = number;
    }
}
