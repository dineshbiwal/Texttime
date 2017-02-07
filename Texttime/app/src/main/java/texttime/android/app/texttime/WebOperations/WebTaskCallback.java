package texttime.android.app.texttime.WebOperations;

/**
 * Created by TextTime Android Dev on 8/2/2016.
 */
public interface WebTaskCallback {
    public void success(Object object, int taskCode);
    public void fail(int taskCode);
    public void failed(Object object, int taskCode);
}
