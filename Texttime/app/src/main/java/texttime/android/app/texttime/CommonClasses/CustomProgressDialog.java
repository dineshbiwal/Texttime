package texttime.android.app.texttime.CommonClasses;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import texttime.android.app.texttime.R;


/**
 * Created by TextTime Android Dev on 8/2/2016.
 */
public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_progress_bar);
        this.setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
