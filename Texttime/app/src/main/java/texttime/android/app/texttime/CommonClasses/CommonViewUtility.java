package texttime.android.app.texttime.CommonClasses;

/**
 * Created by Dinesh on 7/20/2016.
 */
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * This class handles the multiple screen sizes
 * Needs to call the getInstance method to initialise the class object
 * Set the screen size on the splash screen/First screen
 */
public class CommonViewUtility {

    public static CommonViewUtility cv;

    //--Current Screen dimesions-----
    public int width,height;

    //---Margin codes used to specify the margins----
    public static int LEFT=1;
    public static int TOP=2;
    public static int BOTTOM=3;
    public static int RIGHT=4;


    //-----Singleton Method for initializing the class object-----
    public static CommonViewUtility getInstance()
    {
        if(cv!=null)
            return  cv;
        else
        {
            cv=new CommonViewUtility();
            return cv;
        }
    }

    //--------Set screen size for the calculations------------
    public void setScreenDisplay(int width,int height)
    {
        this.height=height;
        this.width=width;
    }

    //----Calculate the updated width as per the screen dimensions-
    public int getWidth(int wid)
    {
        int w=0;
        w=((wid*width)/768);
        return w;
    }

    //----Calculate the updated height as per the screen dimensions-
    public int getHeight(int hei)
    {
        int h=0;
        h=((hei*height)/1080);
        return h;
    }


    //-----Following functions are used to adjust the view component dimesions that are placed
    //---- in the linear layout.
    public void adjustLinear(View v, int w, int h)
    {
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }

    public void adjustLinearHeight(View v, int h)
    {
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }

    public void adjustLinearWidth(View v, int w)
    {
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        v.setLayoutParams(lp);
    }

    public void adjustLinearSquare(View v, int w)
    {
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getWidth(w);
        v.setLayoutParams(lp);
    }



    public void adjustView(View v, int w, int h)
    {
        ViewGroup.LayoutParams lp= (ViewGroup.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }

    //-----Linear layout functions ends here----------------------------------------------------------


    //-----Following functions are used to adjust the view component dimesions that are placed
    //---- in the relative layout.

    public void adjustRelative(View v, int w, int h)
    {
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }




    public void adjustRelativeHeight(View v, int h)
    {
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }

    public void adjustRelativeWidth(View v, int w)
    {
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        v.setLayoutParams(lp);
    }

    public void adjustRelativeSquare(View v, int w)
    {
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getWidth(w);
        v.setLayoutParams(lp);
    }


    //-----Following functions are used to adjust the view component dimesions that are placed
    //---- in the frame layout.

    public void adjustFrame(View v, int w, int h)
    {
        FrameLayout.LayoutParams lp= (FrameLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }

    public void adjustFrameHeight(View v, int h)
    {
        FrameLayout.LayoutParams lp= (FrameLayout.LayoutParams) v.getLayoutParams();
        lp.height=getHeight(h);
        v.setLayoutParams(lp);
    }

    public void adjustFrameLayoutWidth(View v, int w)
    {
        FrameLayout.LayoutParams lp= (FrameLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        v.setLayoutParams(lp);
    }

    public void adjustFrameSquare(View v, int w)
    {
        FrameLayout.LayoutParams lp= (FrameLayout.LayoutParams) v.getLayoutParams();
        lp.width=getWidth(w);
        lp.height=getWidth(w);
        v.setLayoutParams(lp);
    }


    //-----Following functions are used to adjust the view component margins that are placed
    //---- in the frame layout.

    public void adjustFrameMargin(View v, int marginCode, int value)
    {
        FrameLayout.LayoutParams lp= (FrameLayout.LayoutParams) v.getLayoutParams();
        switch (marginCode)
        {
            case 1:
                lp.leftMargin=getWidth(value);
                break;

            case 4:
                lp.rightMargin=getWidth(value);
                break;

            case 2:
                lp.topMargin=getHeight(value);
                break;

            case 3:
                lp.bottomMargin=getHeight(value);
                break;
        }
        v.setLayoutParams(lp);
    }


    //-----Following functions are used to adjust the view component margins that are placed
    //---- in the linear layout.

    public void adjustLinearMargin(View v, int marginCode, int value)
    {
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
        switch (marginCode)
        {
            case 1:
                lp.leftMargin=getWidth(value);
                break;

            case 2:
                lp.topMargin=getHeight(value);
                break;

            case 3:
                lp.bottomMargin=getHeight(value);
                break;

            case 4:
                lp.rightMargin=getWidth(value);
                break;

            case 5:
                lp.setMargins(value, value, value, value);
                break;
        }
        v.setLayoutParams(lp);
    }

    //-----Following functions are used to adjust the view component margins that are placed
    //---- in the relative layout.
    public void adjustRelativeMargin(View v, int marginCode, int value)
    {
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
        switch (marginCode)
        {
            case 1:
                lp.leftMargin=getWidth(value);
                break;

            case 4:
                lp.rightMargin=getWidth(value);
                break;

            case 2:
                lp.topMargin=getHeight(value);
                break;

            case 3:
                lp.bottomMargin=getHeight(value);
                break;
            case 5:
                lp.setMargins(value, value, value, value);
                break;
        }
        v.setLayoutParams(lp);
    }

    public void showAlert(Context context, String message){
        AlertDialog alertDialog=new AlertDialog.Builder(context).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setTitle("").create();

        alertDialog.show();
    }

    public void showAlertDualAction(Context context, String message, String buttonText, String buttonText2, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener editListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(buttonText, okListener)
                .setNegativeButton(buttonText2, editListener)
                .create()
                .show();
    }


    public void showAlertSingleAction(Context context, String message, String buttonText, String buttonText2, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(buttonText, okListener)
                .setNegativeButton(buttonText2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void showAlertSingleAction(Context context, String message, String buttonText, DialogInterface.OnClickListener okListener, DialogInterface.OnDismissListener dismissLIstener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(buttonText, okListener)
                .setOnDismissListener(dismissLIstener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }


    public Bitmap fastblur(Bitmap sentBitmap, float scale, int radius) {

        int width = Math.round(sentBitmap.getWidth() * scale);
        int height = Math.round(sentBitmap.getHeight() * scale);
        sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }


    public Bitmap setText(String text, float textSize, int textColor){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
      return image;

    }
}