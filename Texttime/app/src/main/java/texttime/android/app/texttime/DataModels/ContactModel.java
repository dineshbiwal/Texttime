package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by TextTime Android Dev on 10/5/2016.
 */
public class ContactModel {

    @SerializedName("response_code")
    int response_code;
    @SerializedName("message")
    String message;

    ArrayList<ContactDataModel> data;

  //  MetaData meta;

   /* public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }*/

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ContactDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<ContactDataModel> data) {
        this.data = data;
    }

}
