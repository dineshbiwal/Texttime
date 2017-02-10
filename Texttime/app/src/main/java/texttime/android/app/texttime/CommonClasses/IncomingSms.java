package texttime.android.app.texttime.CommonClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by TextTime Android Dev on 9/24/2016.
 */
public class IncomingSms extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();
                    try
                    {
                        if (phoneNumber .contains("+13344685484"))
                        {
                            if(AppDelegate.getInstance().getOtpCallback()!=null){
                                AppDelegate.getInstance().getOtpCallback().sendSMS(message);
                            }
                        }
                    }
                    catch(Exception e){}
                }
            }
        } catch (Exception e)
        {

        }
    }

}
