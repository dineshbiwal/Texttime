package texttime.android.app.texttime.callbacks;

/**
 * Created by TextTime Android Dev on 9/2/2016.
 */
public interface AcceptRejectRequestCallback {
    public void acceptRequest(String username);
    public void rejectRequest(String username);
}
