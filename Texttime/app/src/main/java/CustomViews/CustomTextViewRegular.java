package CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewRegular extends TextView {
	public CustomTextViewRegular(Context context) {
		super(context);
		setFont();
	}
	public CustomTextViewRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	public CustomTextViewRegular(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}

	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
		setTypeface(font, Typeface.NORMAL);
	}
}

