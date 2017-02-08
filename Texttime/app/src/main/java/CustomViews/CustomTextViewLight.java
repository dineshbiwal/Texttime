package CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewLight extends TextView {
	public CustomTextViewLight(Context context) {
		super(context);
		setFont();
	}
	public CustomTextViewLight(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	public CustomTextViewLight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}

	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin.ttf");
		setTypeface(font, Typeface.NORMAL);
	}
}