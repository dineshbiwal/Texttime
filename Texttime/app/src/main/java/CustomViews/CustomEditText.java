package CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by TextTime Android Dev on 8/4/2016.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
        setFont();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return false;
    }
}
