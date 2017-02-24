package texttime.android.app.texttime.CommonClasses;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataFunctions {

	public static float convertPixelsToDp(int px){
		/*DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return Math.round(dp);*/
		return Math.round(px/(Resources.getSystem().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
	}

	 public boolean isEmailValid(String email) {
	        boolean isValid = false;
	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        CharSequence inputStr = email;
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(inputStr);
	        if (matcher.matches()) {
	               isValid = true;
	        }
	        return isValid;
	    }
	 public boolean isPhoneNumberValid(String phonenumber)
	 {
		 boolean isValid = false;
		 String expression = "^[1-9][0-9]{9}$" ;
		 CharSequence phone = phonenumber;
		 Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern.matcher(phone);
		 if(matcher.matches())
			 isValid = true;
		 return isValid;
	 }

	public boolean nameValidation(String username) {
		boolean isValid = false;
		String exp = "^[\\p{L} .'-]+$";
		Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(username);
		if (matcher.matches())
			isValid = true;
		return isValid;
	}

	 public boolean isUsernameValid(String username)
	 {
		 boolean isValid = false;
		 String exp = "^[a-z0-9_]{3,30}$";
		 Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern.matcher(username);
		 if(matcher.matches())
			 isValid = true;
		 return isValid;
	 }

	public boolean isPhoneValid(String phone, String region)
	{
		boolean isValid = false;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance().getInstance();
		Phonenumber.PhoneNumber phNumber =null;
		try{
			phNumber = phoneUtil.parse(phone, region);
		}
		catch(NumberParseException e)
		{
			Log.d("Error :", e.toString());
		}
		isValid = phoneUtil.isValidNumber(phNumber);
		Log.d("Valid", phoneUtil.format(phNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
		return isValid;
	}

	public Typeface getFontFamily(Context ctx)
	{
		return Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");
	}

	public Uri resourceToUri(Context context, int resID) {
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
				context.getResources().getResourcePackageName(resID) + '/' +
				context.getResources().getResourceTypeName(resID) + '/' +
				context.getResources().getResourceEntryName(resID) );
	}
}
