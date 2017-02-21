package texttime.android.app.texttime.CommonClasses;

/**
 * Created by dinesh on 11/21/16.
 */

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonMethods {
    private static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    private static DateFormat timeFormat = new SimpleDateFormat("K:mma");

    public static String getCurrentTime() {

        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }

    public static String getCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static String calculateTimeDiffernce(long createTime){
        Date date=new Date(createTime);

        Calendar c= Calendar.getInstance();
        c.setTime(date);
        long ctime=c.getTimeInMillis();
        long currentime= Calendar.getInstance().getTimeInMillis();
        long diff=currentime-ctime;
        diff=diff/1000;

      //  long sec = diff % 60;
       // long minutes = diff % 3600 / 60;
        long hours = diff % 86400 / 3600;
        long days = diff / 86400;
        Date day = c.getTime();
        String[] week_days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday"};
        String display="";
        if(days>7){
            display = dateFormat.format(day)+" "+timeFormat.format(day);
        }
        else if(days > date.getDate()){
            display = week_days[date.getDay()]+" "+timeFormat.format(day);
        }
        else if(date.getHours() < hours){
            //---Show days--
            display="Yesterday "+timeFormat.format(day);
        }
        else {
            /*if(hours>0){
                //--Show hours and minutes---
                display=hours+"hrs "+minutes+" mins"+" ago";
            }
            else {
                //---Show minutes------
                if(minutes>0)
                    display=minutes+" mins"+" ago";

                else {
                    display=sec+" sec"+" ago";
                }
            }*/
            display = timeFormat.format(day);
        }
        return display;
    }

    public static String convertIntoKillo(long count) {
        String value = "0";
        double cnt = 0;
        if(count > 999999 && count < 100000000 )
            return new DecimalFormat("##.##").format((double) count/1000000) + "M";
        else if(count > 999 && count < 1000000)
            cnt = (double) count/1000;
        else
            return String.valueOf(count);
        if(cnt < 99)
            value =  new DecimalFormat("##.##").format(cnt) + "K";
        else
            value = new DecimalFormat("###.##").format(cnt) + "K";
        return value;
    }
}

