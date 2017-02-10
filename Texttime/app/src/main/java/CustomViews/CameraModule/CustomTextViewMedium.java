package CustomViews.CameraModule;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewMedium extends TextView {
	public CustomTextViewMedium(Context context) {
		super(context);
		setFont();
	}
	public CustomTextViewMedium(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	public CustomTextViewMedium(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}

	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
		setTypeface(font, Typeface.NORMAL);
	}
}