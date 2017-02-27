package ChatUI.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ChatUI.fragments.ChatDataModels.ChattingUserList;
import CustomViews.CustomImageView;
import CustomViews.CustomTextViewBold;
import CustomViews.CustomTextViewLight;
import CustomViews.CustomTextViewMedium;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.R;

/**
 * Created by TextTime Android Dev on 2/27/2017.
 */

public class ChatDeleteMenu {
    ArrayList<ChattingUserList> selectedUserList = new ArrayList<>();
    Context context;
    CommonViewUtility cv;


    LinearLayout selectedUsersImageLayout,optionsLayout;
    LinearLayout selectedUsersNameLayout;
    CustomTextViewBold cancelButton;
    private CustomTextViewLight deleteChatButton,archiveChatButton,clearChatButton;

    public ChatDeleteMenu(ArrayList<ChattingUserList> selectedUserList,Context context) {
        this.selectedUserList = selectedUserList;
        cv = CommonViewUtility.getInstance();
        this.context=context;
    }

    public RelativeLayout createDeleteMenu() {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.delete_chat_menu, null, false);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl.setLayoutParams(lp);
        selectedUsersImageLayout= (LinearLayout) rl.findViewById(R.id.selectedUsersImageLayout);
       // selectedUsersNameLayout= (LinearLayout) rl.findViewById(R.id.selectedUsersNameLayout);
        selectedUsersNameLayout= (LinearLayout) rl.findViewById(R.id.selectedUsersNameLayout);
        cancelButton= (CustomTextViewBold) rl.findViewById(R.id.cancelButton);
        deleteChatButton= (CustomTextViewLight) rl.findViewById(R.id.deleteChatButton);
        archiveChatButton= (CustomTextViewLight) rl.findViewById(R.id.archiveChatButton);
        clearChatButton= (CustomTextViewLight) rl.findViewById(R.id.clearChatButton);
        optionsLayout= (LinearLayout) rl.findViewById(R.id.optionsLayout);

        cv.adjustRelativeMargin(selectedUsersImageLayout,CommonViewUtility.TOP,30);
        cv.adjustRelativeMargin(selectedUsersNameLayout,CommonViewUtility.TOP,30);
        cv.adjustRelativeMargin(optionsLayout,CommonViewUtility.TOP,104);
       // cv.adjustRelativeMargin(optionsLayout,CommonViewUtility.BOTTOM,116);

        //cv.adjustLinearMargin(deleteChatButton,CommonViewUtility.TOP,104);
        /*cv.adjustLinearMargin(deleteChatButton,CommonViewUtility.TOP,104);
        cv.adjustLinearMargin(archiveChatButton,CommonViewUtility.TOP,114);
        cv.adjustLinearMargin(clearChatButton,CommonViewUtility.TOP,118);*/

        cv.adjustLinearMargin(deleteChatButton,CommonViewUtility.LEFT,152);
        cv.adjustLinearMargin(archiveChatButton,CommonViewUtility.LEFT,152);
        cv.adjustLinearMargin(clearChatButton,CommonViewUtility.LEFT,152);


        cv.adjustRelativeMargin(cancelButton,CommonViewUtility.BOTTOM,98);

        ButterKnife.bind(rl);
        selectedUsersImageLayout=(LinearLayout)rl.findViewById(R.id.selectedUsersImageLayout);
        for(int i=0;i<selectedUserList.size();i++){
            ChattingUserList model=selectedUserList.get(i);
            CustomImageView iv=new CustomImageView(context);
            LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(cv.getWidth(183),cv.getWidth(183));
            lp1.rightMargin=15;
            iv.setLayoutParams(lp1);
            iv.setUrl(model.getUserAvatar());
            selectedUsersImageLayout.addView(iv);
        }


        return rl;
    }
}
