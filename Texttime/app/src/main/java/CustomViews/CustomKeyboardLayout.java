package CustomViews;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import texttime.android.app.texttime.CommonClasses.CommonDataUtility;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.R;

/**
 * Created by TextTime Android Dev on 8/6/2016.
 */
public class CustomKeyboardLayout implements CurrentFocusInterface{

    LinearLayout customKeyboardLayout;
    Context context;
    View currentFocus;
    CommonDataUtility cd;
    CommonViewUtility cv;
    String key ="";
    public CustomKeyboardLayout(LinearLayout customKeyboardLayout, Context context, View current) {
        this.customKeyboardLayout = customKeyboardLayout;
        this.context=context;
        key = "";
        this.currentFocus=current;
        cd=new CommonDataUtility(context);
        cv=CommonViewUtility.getInstance();
    }

    public CurrentFocusInterface getInterface(){
      return  this;
    }

    public LinearLayout createCustomKeyBoard(){
        customKeyboardLayout.setBackgroundColor(Color.parseColor("#BEC2C5"));
        cv.adjustLinearHeight(customKeyboardLayout,590);

        for(int i=0;i<4;i++){
            createVerticalRow();
        }
        for(int i=1;i<10;i++){
            createHorizontalRow(i);
        }
        createHorizontalRow(6,0,"",-1);
        createHorizontalRow(6,1,"0",-1);
        createHorizontalRow(6,2,"", R.mipmap.deletekey);
        return customKeyboardLayout;
    }

    private void createHorizontalRow(int pos){
        int lCount=pos/3;

        if(pos%3==0){
            lCount=pos/3;

            if(lCount%2!=0){
                if(lCount>1)
                    lCount+=1;
                else
                    lCount=lCount-1;
            }
        }

        else {
            if (lCount > 0) {
                lCount = lCount + ((int) (pos / 3));
            }
        }
        System.out.println("Position is >>>"+pos+" lcount is >>>"+lCount);

        LinearLayout addLayout= (LinearLayout) customKeyboardLayout.getChildAt(lCount);

        TextView textView=new TextView(context);
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);

        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        textView.setLayoutParams(lp1);
        textView.setText(""+pos);
        textView.setBackgroundResource(R.drawable.btn_click_key);
        textView.setGravity(Gravity.CENTER);
        addLayout.addView(textView);

        View lineSep2=new View(context);
        lineSep2.setBackgroundColor(Color.parseColor("#BEC2C5"));
        lineSep2.setLayoutParams(lp2);

        addLayout.addView(lineSep2);

        textView.setTag(pos);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p= (int) view.getTag();
                View v=currentFocus;
                if(v instanceof EditText){
                     ((EditText) v).setText(key+""+p);
                     key = ((EditText) v).getText().toString();
                    ((EditText) v).setSelection(((EditText) v).getText().length());
                }
            }
        });
    }

    private void createHorizontalRow(int pos, int col, String text, int resource){

        LinearLayout addLayout= (LinearLayout) customKeyboardLayout.getChildAt(pos);
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);

        if(resource > 0){
            ImageView iv=new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.CENTER);
            iv.setImageResource(resource);
            iv.setLayoutParams(lp1);
            iv.setBackgroundResource(R.drawable.btn_click_key);
            addLayout.addView(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v=currentFocus;
                    if(v instanceof EditText){
                        EditText e=((EditText) v);
                        if(TextUtils.isEmpty(cd.etData(e))){
                            if(e.getTag()!=null) {
                                EditText f = (EditText) e.getTag();
                               // e.clearFocus();
                               // f.requestFocus();
                                currentFocus=e;
                                e.setSelection(e.getText().length());
                            }
                        }
                        else {
                            ((EditText) v).setText(key.substring(0, key.length()-1));
                            key = key.substring(0, key.length()-1);
                            ((EditText) v).setSelection(((EditText) v).getText().length());
                        }
                    }
                }
            });
        }
        else {
            TextView textView=new TextView(context);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18);
            textView.setLayoutParams(lp1);
            textView.setText(""+text);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.btn_click_key);
            addLayout.addView(textView);

            if(!TextUtils.isEmpty(text)) {

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  int p = (int) view.getTag();
                        View v = currentFocus;
                        if (v instanceof EditText) {
                            ((EditText) v).setText(key+0);
                            key = key+0;
                            ((EditText) v).setSelection(((EditText) v).getText().length());
                        }
                    }
                });
            }
        }

        View lineSep2=new View(context);
        lineSep2.setBackgroundColor(Color.parseColor("#BEC2C5"));
        lineSep2.setLayoutParams(lp2);

        addLayout.addView(lineSep2);

    }
    private void createVerticalRow(){
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1.0f);
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);

        LinearLayout r1=new LinearLayout(context);
        r1.setOrientation(LinearLayout.HORIZONTAL);
        r1.setBackgroundColor(Color.WHITE);

        View lineSep2=new View(context);
        lineSep2.setBackgroundColor(Color.parseColor("#BEC2C5"));

        r1.setLayoutParams(lp1);
        lineSep2.setLayoutParams(lp2);

        customKeyboardLayout.addView(r1);
        customKeyboardLayout.addView(lineSep2);
    }

    @Override
    public void setCurrentFocus(View focus) {
        currentFocus=focus;
    }
}
