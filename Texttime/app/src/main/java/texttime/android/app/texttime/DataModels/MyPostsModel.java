package texttime.android.app.texttime.DataModels;

/**
 * Created by TextTime Android Dev on 8/27/2016.
 */
public class MyPostsModel {
    String url;
    boolean isVideo;
    String comments;

    public MyPostsModel(String url, boolean isVideo, String comments) {
        this.url = url;
        this.isVideo = isVideo;
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
