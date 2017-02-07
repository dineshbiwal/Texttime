package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by TextTime Android Dev on 10/6/2016.
 */
public class SendContactJSON {

    @SerializedName("contacts")
    private ArrayList<SendContactModel> contacts;

    public ArrayList<SendContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<SendContactModel> contacts) {
        this.contacts = contacts;
    }

    public SendContactJSON(ArrayList<SendContactModel> contacts) {
        this.contacts = contacts;
    }
}
