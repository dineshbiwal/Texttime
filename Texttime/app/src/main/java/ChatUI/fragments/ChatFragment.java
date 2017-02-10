package ChatUI.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import ChatUI.fragments.ChatAdapterClasses.ChatHistoryAdapter;
import ChatUI.fragments.ChatDataModels.ChattingUserList;
import CustomViews.CustomTextView;
import CustomViews.VerticalItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.GeneralClasses.BaseFragment;
import texttime.android.app.texttime.R;


/**
 * Created by TextTime Android Dev on 8/24/2016.
 */
public class ChatFragment extends BaseFragment {


    @BindView(R.id.typeofchat)
    CustomTextView typeofchat;

    @BindView(R.id.chatList)
    RecyclerView chatList;

    ArrayList<ChattingUserList> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        init(getActivity());
        setBackPressed(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Back pressed from the Chat", Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState == null) {
            view = inflater.inflate(R.layout.chat_list_fragment, null);
        }

        ButterKnife.bind(this, view);

        fillData();
        chatList.setLayoutManager(new LinearLayoutManager(context));
        chatList.addItemDecoration(new VerticalItemDecoration(cv.getHeight(56),false));
        ChatHistoryAdapter adapter=new ChatHistoryAdapter(list,context);
        chatList.setAdapter(adapter);
        adjustUIComponent();

        return view;
    }



    private void adjustUIComponent(){
        cv.adjustRelativeMargin(typeofchat, CommonViewUtility.TOP,60);
        cv.adjustRelativeMargin(chatList, CommonViewUtility.TOP,162);
       // typeofchat.setTextSize(TypedValue.COMPLEX_UNIT_PX,25.35f);

    }
    private void fillData(){
        ChattingUserList user=new ChattingUserList();
        user.setLastMessage("Alpha bravo charlie delta echo foxtrot golf hotel india juliet kilo lima");
        user.setNewMessageCount(2);
        user.setDisplayName("Tyler Durden");
        user.setUserStatus(true);
        user.setUserAvatar(convertToByteArray(BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.image1)));

        ChattingUserList user1=new ChattingUserList();
        user1.setLastMessage("Alpha bravo charlie delta echo foxtrot golf hotel india juliet kilo lima");
        user1.setNewMessageCount(2);
        user1.setDisplayName("Tom Hanks");
        user.setUserStatus(false);
        user1.setUserAvatar(convertToByteArray(BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.image2)));

        ChattingUserList user2=new ChattingUserList();
        user2.setLastMessage("Image");
        user2.setNewMessageCount(1);
        user2.setDisplayName("Nathan Lyann");
        user.setUserStatus(true);
        user2.setUserAvatar(convertToByteArray(BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.image3)));
        user2.setBaseImage(convertToByteArray(BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.image1)));

        ChattingUserList user3=new ChattingUserList();
        user3.setLastMessage("Alpha bravo charlie delta echo foxtrot golf hotel india juliet kilo lima");
        user3.setNewMessageCount(1);
        user.setUserStatus(false);
        user3.setDisplayName("Brendon Smith");
        user3.setUserAvatar(convertToByteArray(BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.image4)));


        list.add(user);
        list.add(user1);
        list.add(user2);
        list.add(user3);
    }

    private byte[] convertToByteArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
