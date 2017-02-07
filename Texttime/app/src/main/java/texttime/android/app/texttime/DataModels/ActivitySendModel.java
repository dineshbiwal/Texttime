package texttime.android.app.texttime.DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 11/12/2016.
 */

public class ActivitySendModel {
    @SerializedName("message")
    String message;
    @SerializedName("response_code")
    int response_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public DataHolder getData() {
        return data;
    }

    public void setData(DataHolder data) {
        this.data = data;
    }

    DataHolder data;
    public class DataHolder{
        @SerializedName("filename")
        String filename;
        @SerializedName("id")
        String id;
        @SerializedName("thumbnail")
        String thumbnail;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
