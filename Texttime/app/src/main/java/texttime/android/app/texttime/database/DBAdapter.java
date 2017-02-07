package texttime.android.app.texttime.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import texttime.android.app.texttime.DataModels.ContactDataModel;

public class DBAdapter
{
		static final String DATABASE_NAME = "texttime.db";
		private static final int DATABASE_VERSION = 1;
		static final String TAB_CONTACT = "CONTACTS";
		static final String TAB_CHAT="CHAT";
		static final String TAB_ROSTER = "ROSTER";
		static final String TAB_MESSAGE_STANZA = "MESSAGE_STANZA";
		static final String TABLE_CHAT = "create table "+ TAB_CHAT +" (CHAT_ID text, SENDER text, MESSAGE text, DATETIME text, DATE text, TIME number, ISME text, RECEIVER text, READSTATUS text, MESSAGE_TYPE number, FILE_DATA text);";
    /**
     * Message Type
     *  1 => text
     *  2 => location
     *  3 => image
     *  4 => audio
     *  5 => video
     *  6 => document
	 *  7 => pdf
     *  8 => contact
	 *
     */

    static final String TABLE_CONTACT = "create table "+TAB_CONTACT+" ( ID integer primary key autoincrement, CONTACT_ID text, UID text, PROFILE_IMAGE text, IsMEMBER text, USERNAME text, CONTACT_NAME text, COUNTRY_CODE text," +
				"CONTACT_NUMBER text, CONTACT_JID text, ROW_INPUT text, INSERTED text);";
	static final String TABLE_ROSTER = "create table "+TAB_ROSTER+" ( ID integer primary key autoincrement, DISPLAY_NAME text, LAST_MESSAGE text, LAST_SEEN number, MESSAGE_STATUS text, JABBERID text, LAST_MESSAGE_TIME number," +
			"USER_STATUS text, USER_AVATAR blob);";
	static final String TABLE_MESSAGE_STANZA = "create table "+TAB_MESSAGE_STANZA+" (ID integer primary key autoincrement, CHAT_ID text, STANZA text, DATETIME number";
		public SQLiteDatabase db;
		
		@SuppressWarnings("unused")
		private final Context context;
		private DataBaseHelper dbHelper;
		public  DBAdapter(Context context)
		{
			this.context=context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public DBAdapter open() throws SQLException
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		public SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}

	//Insert Contact Information
		public void insertContactDetail(ArrayList<ContactDataModel> contacts){
			try {
				for(ContactDataModel data : contacts){
					ContentValues newValues = new ContentValues();
					newValues.put("CONTACT_ID","");
					newValues.put("UID", data.getUid());
					newValues.put("USERNAME", data.getUsername());
					newValues.put("CONTACT_NAME", data.getDisplay_name());
					newValues.put("PROFILE_IMAGE", data.getProfile_image());
					newValues.put("COUNTRY_CODE",data.getCountry());
					newValues.put("CONTACT_NUMBER",data.getMobile_number());
					newValues.put("ROW_INPUT", data.getCountry()+data.getMobile_number());
					newValues.put("CONTACT_JID", data.getJid());
					newValues.put("IsMEMBER", String.valueOf(data.is_member()));
					newValues.put("INSERTED", String.valueOf(data.isInserted()));
					db.insert(TAB_CONTACT, null, newValues);
				}
			}catch(Exception e){
				Log.e("error ", e.toString());
			}
		}

	// Update Contact info
		public void updateContactDetail(ContactDataModel data){
			try {
				ContentValues newValues = new ContentValues();
				newValues.put("CONTACT_ID","");
				newValues.put("UID", data.getUid());
				newValues.put("USERNAME", data.getUsername());
				newValues.put("CONTACT_NAME", data.getDisplay_name());
				newValues.put("PROFILE_IMAGE", data.getProfile_image());
				newValues.put("COUNTRY_CODE",data.getCountry());
				newValues.put("CONTACT_NUMBER",data.getMobile_number());
				newValues.put("ROW_INPUT", data.getCountry()+data.getMobile_number());
				newValues.put("CONTACT_JID", data.getJid());
				newValues.put("IsMEMBER", String.valueOf(data.is_member()));
				newValues.put("INSERTED", String.valueOf(data.isInserted()));
				String where="UID = ?";
				db.update(TAB_CONTACT, newValues, where, new String[]{String.valueOf(data.getUid())});
			}catch(Exception e){
				Log.e("error ", e.toString());
			}
		}

	//Delete Contact info
		public void deleteContactDetail(){
            ContentValues cv = new ContentValues();
            cv.put("FLAG", "DELETE");
            String where ="FLAG = ?";
            db.update(TAB_CONTACT, cv, where, new String[]{"ADD"});
			//where="FLAG=?";
			//int numberOFEntriesDeleted= db.delete(TAB_CONTACT, where, new String[]{"DELETE"}) ;
		}

	// Get Al contact detail
		public List<ContactDataModel> getAllContacts(){
			List<ContactDataModel> contactarr = new ArrayList<>();
			//Cursor cursor=db.query(TAB_CONTACT, null, null, null, null , null, null);
			Cursor cursor=db.query(TAB_CONTACT, null, " IsMEMBER = 'true' ", null, null , null, null);
			if(cursor.getCount()<1)
			{
				cursor.close();
				return null;
			}
			try {
				while (cursor.moveToNext()) {
					ContactDataModel data = new ContactDataModel();
					data.setUid(Long.parseLong(cursor.getString(cursor.getColumnIndex("UID"))));
					data.setDisplay_name(cursor.getString(cursor.getColumnIndex("CONTACT_NAME")));
					data.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
					data.setProfile_image(cursor.getString(cursor.getColumnIndex("PROFILE_IMAGE")));
					data.setCountry(cursor.getString(cursor.getColumnIndex("COUNTRY_CODE")));
					data.setMobile_number(cursor.getString(cursor.getColumnIndex("CONTACT_NUMBER")));
					//data.setR("raw_input", cursor.getString(cursor.getColumnIndex("ROW_INPUT")));
					data.setJid(cursor.getString(cursor.getColumnIndex("CONTACT_JID")));
					contactarr.add(data);
				}
			}catch(Exception e){
				Log.e("Error ", e.toString());
			}
			cursor.close();
			return contactarr;
		}

	//Get All Contact with row_input number for check contact inserted or not

		public boolean checkNumberExist(String code, String number){
			Cursor cursor=db.query(TAB_CONTACT, null, " COUNTRY_CODE = '"+code+"' AND CONTACT_NUMBER = '"+number+"' ", null, null , null, null);
			if(cursor.getCount()>0)
			{
				cursor.close();
				return false;
			}
			cursor.close();
			return true;
		}

	// Get Contact jabber id if user is texttime user

	public String checkNumberExist(String number){
		Cursor cursor=db.query(TAB_CONTACT, null, " CONTACT_NUMBER = '"+number+"' ", null, null , null, null);
		if(cursor.getCount() <= 0)
		{
			cursor.close();
			return "";
		}
		cursor.close();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex("CONTACT_JID"));
	}
	//Get Contact where flag = "UPDATE"
		public JSONArray getupdatedContact(){
			JSONArray contactarr = new JSONArray();
			Cursor cursor=db.query(TAB_CONTACT, null, " FLAG = 'UPDATE' ", null, null , null, null);
			if(cursor.getCount()<1)
			{
				cursor.close();
				return null;
			}
			try {
				while (cursor.moveToNext()) {
					JSONObject data = new JSONObject();
						data.put("contact_id", cursor.getString(cursor.getColumnIndex("CONTACT_ID")));
						data.put("contact_name", cursor.getString(cursor.getColumnIndex("CONTACT_NAME")));
						data.put("country_code", cursor.getString(cursor.getColumnIndex("COUNTRY_CODE")));
						data.put("contact_number", cursor.getString(cursor.getColumnIndex("CONTACT_NUMBER")));
						data.put("raw_input", cursor.getString(cursor.getColumnIndex("ROW_INPUT")));
						data.put("contact_email", cursor.getString(cursor.getColumnIndex("CONTACT_EMAIL")));
						data.put("flag", cursor.getString(cursor.getColumnIndex("FLAG")));
					contactarr.put(data);
				}
			}catch(Exception e){
				Log.e("Error ", e.toString());
			}
			cursor.close();
			return contactarr;
		}

	// Check Contact Availability
	public String checkContactStatus(String contact_id, String name, String phonenumber){

        Cursor cursor=db.query(TAB_CONTACT, null, " CONTACT_ID = '"+contact_id+"' ", null, null , null, null);
        if(cursor.getCount()<1)
        {
            cursor.close();
            return "ADD";
        }
        else{
            cursor.moveToFirst();
                    if(TextUtils.equals(cursor.getString(cursor.getColumnIndex("CONTACT_NAME")), name) &&
                            TextUtils.equals(cursor.getString(cursor.getColumnIndex("ROW_INPUT")), phonenumber)){
                        cursor.close();
                        return "UPDATE";
                    }
                    else
                    {
                        cursor.close();
                        return "UPDATE";
                    }
                }
	}

    // Update flag status of contacts

    public  void updateFlagStatus(String contact_id)
    {
        ContentValues cv = new ContentValues();
        cv.put("FLAG", "EXIST");
        String where ="CONTACT_ID = ?";
        db.update(TAB_CONTACT, cv, where, new String[]{contact_id});
    }

	public void updateFlagStatus(){
		ContentValues cv = new ContentValues();
		cv.put("FLAG", "EXIST");
		String where ="FLAG = ?";
		db.update(TAB_CONTACT, cv, where, new String[]{"UPDATE"});
	}

	/**
	 *
	 * 	status =  read
	 * 	status =  deliver
	 * 	status =  not read
	 * 	status =  not deliver
     */
	/*public void insertChat(ChatMessage message)
	{
		ContentValues newValues = new ContentValues();
		newValues.put("CHAT_ID", message.getId());
		newValues.put("READSTATUS", message.getReadStatus());
		newValues.put("SENDER",message.getSender());
		newValues.put("MESSAGE", message.getMessage());
		newValues.put("DATETIME", String.valueOf(message.getDateTime()));
		newValues.put("DATE",message.getDate());
		newValues.put("TIME", message.getTime());
		newValues.put("ISME", String.valueOf(message.getIsme()));
		newValues.put("RECEIVER", message.getReceiver());
		newValues.put("MESSAGE_TYPE", message.getMessageType());
        newValues.put("FILE_DATA", message.getFileData());
		db.insert(TAB_CHAT, null, newValues);
	}

	public List<ChatMessage> getChatDetail(String user, String me) {
		List<ChatMessage> chat = new ArrayList<ChatMessage>();
		Cursor cursor = db.query(TAB_CHAT, null, " (SENDER = '" + user + "' AND RECEIVER = '"+ me +"') OR (RECEIVER = '"+ user +"' AND SENDER = '"+ me +"') ", null, null, null, "DATETIME ASC");
		Log.d("Cursor Length :", String.valueOf(cursor.getCount()));
		if (cursor.getCount() < 1) {
			cursor.close();
			return null;
		}
		cursor.moveToFirst();
		do{
			ChatMessage msg = new ChatMessage();
			msg.setId(cursor.getString(cursor.getColumnIndex("CHAT_ID")));
			msg.setMessage(cursor.getString(cursor.getColumnIndex("MESSAGE")));
			msg.setDateTime(cursor.getLong(cursor.getColumnIndex("DATETIME")));
			msg.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
			msg.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
			if(TextUtils.equals(cursor.getString(cursor.getColumnIndex("ISME")),"true"))
                msg.setMe(true);
			else msg.setMe(false);
            if(cursor.getString(cursor.getColumnIndex("FILE_DATA")) != null)
                msg.setFileData(cursor.getString(cursor.getColumnIndex("FILE_DATA")));
			msg.setReadStatus(cursor.getString(cursor.getColumnIndex("READSTATUS")));
            msg.setMessageType(cursor.getInt(cursor.getColumnIndex("MESSAGE_TYPE")));
			chat.add(msg);
		}while(cursor.moveToNext());
		cursor.close();
		return chat;
	}

	public List<ChatMessage> getGroupChat(String me) {
		List<ChatMessage> chat = new ArrayList<ChatMessage>();
		Cursor cursor = db.query(TAB_CHAT, null, " (SENDER = '" + me + "' AND RECEIVER = '"+ me +"') ", null, null, null, null);
		Log.d("Cursor Length :", String.valueOf(cursor.getCount()));
		if (cursor.getCount() < 1) {
			cursor.close();
			return null;
		}
		cursor.moveToFirst();
		do{
			ChatMessage msg = new ChatMessage();
			msg.setId(cursor.getString(0));
			msg.setMessage(cursor.getString(2));
			msg.setDate(cursor.getString(3));
			if(cursor.getString(4).equalsIgnoreCase("true")) msg.setMe(true);
			else msg.setMe(false);
			msg.setReadStatus(cursor.getString(6));
            msg.setMessageType(cursor.getInt(7));
			chat.add(msg);
			Log.d("Get Chat :", Long.parseLong(cursor.getString(0))+ ", "+cursor.getString(1)+", "+cursor.getString(2)+ ", "+cursor.getString(3)+", "+cursor.getString(4));
		}while(cursor.moveToNext());
		cursor.close();
		return chat;
	}

	public String[] getLastMessage(String user, String me){
		Cursor cursor = db.query(TAB_CHAT, null, " (SENDER = '" + user + "' AND RECEIVER = '"+ me +"') OR (RECEIVER = '"+ user +"' AND SENDER = '"+ me +"') ", null, null, null, null);
		Log.d("Cursor Length :", String.valueOf(cursor.getCount()));
		if (cursor.getCount() < 1) {
			cursor.close();
			return null;
		}
		Cursor cur = db.query(TAB_CHAT, null, " (SENDER = '" + user + "' AND RECEIVER = '"+ me +"' AND READSTATUS = 'deliver')", null, null, null, null);
		Log.d("Cursor Length :", String.valueOf(cur.getCount()));
		if(cursor.moveToLast()){
			String str[] = new String[6];
            str[0] = cursor.getString(cursor.getColumnIndex("MESSAGE"));
            str[1] = cursor.getString(cursor.getColumnIndex("DATETIME"));
			str[2] = cur.getCount()+"";
            str[3] = cursor.getInt(cursor.getColumnIndex("MESSAGE_TYPE"))+"";
			str[4] = cursor.getString(cursor.getColumnIndex("READSTATUS"));
			str[5] = cursor.getString(cursor.getColumnIndex("ISME"));
			Log.e("Error", str.toString());
			return str;
		}else{
			return null;
		}
	}

	public void updateMessageReadStatus(String id, String status)
	{
		Cursor cursor = db.query(TAB_CHAT, null, " (CHAT_ID = '" + id + "') ", null, null, null, null);
		Log.d("Cursor Length :", String.valueOf(cursor.getCount()));
		if (cursor.getCount() == 1) {
			ContentValues cv = new ContentValues();
			cv.put("READSTATUS", status);
			String where ="CHAT_ID = ?";
			db.update(TAB_CHAT, cv, where, new String[]{id});
			cursor.close();
		}
	}
    public void updateMessageStatus(String user, String me)
    {
        Cursor cursor = db.query(TAB_CHAT, null, " (SENDER = '" + user + "' AND RECEIVER = '"+ me +"') AND (ISME = 'false') AND (READSTATUS = 'deliver')", null, null, null, null);
        Log.d("Cursor Length :", String.valueOf(cursor.getCount()));
        if (cursor.getCount() < 1) {
            cursor.close();
            return;
        }
        cursor.moveToFirst();
        do{
            ContentValues cv = new ContentValues();
            cv.put("READSTATUS", "read");
            String where =" CHAT_ID = '"+cursor.getString(cursor.getColumnIndex("CHAT_ID"))+"'";
            db.update(TAB_CHAT, cv, where, null);
            try {
                Message msg = new Message(user + "@" + MyXMPP.DOMAIN, Message.Type.chat);
                ReadReceipt read = new ReadReceipt(cursor.getString(cursor.getColumnIndex("CHAT_ID")));
                msg.addExtension(read);
                MyXMPP.getConnectionFor().sendStanza(msg);
            }catch(Exception e){
                Log.e("Error", e.toString());
            }
        }while(cursor.moveToNext());
        cursor.close();
    }
	public void insertRosterEntry(List<ChattingUserList> userLists){
		Cursor cursor = db.query(TAB_ROSTER, null, null, null, null, null, null);
		if(cursor.getCount() != userLists.size()) {
			db.delete(TAB_ROSTER, null, null);
			for (ChattingUserList user : userLists) {
				ContentValues newValues = new ContentValues();
				newValues.put("DISPLAY_NAME", user.getDisplayName());
				newValues.put("LAST_MESSAGE", user.getLastMessage());
				newValues.put("LAST_SEEN", user.getLastMessage());
				newValues.put("MESSAGE_STATUS", user.getMessageStatus());
				newValues.put("JABBERID", user.getJabberID());
				newValues.put("LAST_MESSAGE_TIME", user.getLastMessageTime());
				newValues.put("USER_STATUS", user.getUserStatus());
				newValues.put("USER_AVATAR", user.getUserAvatar());
				Log.e("Insert id :", String.valueOf(db.insert(TAB_ROSTER, null, newValues)));
			}
		}
	}

	public List<ChattingUserList> getRosterDetail(){
		List<ChattingUserList> userList = new ArrayList<>();
		try {
            Cursor cursor = db.query(TAB_ROSTER, null, null, null, null, null, "LAST_MESSAGE_TIME DESC");
            if (cursor.getCount() < 1) {
                cursor.close();
                return null;
            }
            cursor.moveToFirst();
            do {
                ChattingUserList user = new ChattingUserList();
				user.setUserid(cursor.getString(cursor.getColumnIndex("ID")));
                user.setDisplayName(cursor.getString(cursor.getColumnIndex("DISPLAY_NAME")));
                user.setLastMessageTime(cursor.getLong(cursor.getColumnIndex("LAST_MESSAGE_TIME")));
                user.setLastSeen(String.valueOf(cursor.getString(cursor.getColumnIndex("LAST_SEEN"))));
                user.setJabberID(cursor.getString(cursor.getColumnIndex("JABBERID")));
                if (TextUtils.equals(cursor.getString(cursor.getColumnIndex("USER_STATUS")), "true"))
                    user.setUserStatus(true);
                else
                    user.setUserStatus(false);
                if (cursor.getBlob(cursor.getColumnIndex("USER_AVATAR")) != null)
                    user.setUserAvatar(cursor.getBlob(cursor.getColumnIndex("USER_AVATAR")));
                String[] uslist = getLastMessage(user.getDisplayName(), new SaveDataPreferences(context).getUserJID());
                if (uslist != null) {
                    user.setLastMessage(uslist[0]);
                    int msgType = Integer.parseInt(uslist[3]);
                    if (msgType == 2)
                        user.setLastMessage("Share Location");
                    else if (msgType >= 3)
                        user.setLastMessage("Share File");
                    user.setLastMessageTime(Long.parseLong(uslist[1]));
                    user.setNewMessageCount(Integer.parseInt(uslist[2]));
                    user.setMessageStatus(uslist[4]);
                    if (TextUtils.equals(uslist[5], "true"))
                        user.setMe(true);
                    else user.setMe(false);
                }
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
            Collections.sort(userList, new Comparator<ChattingUserList>() {
                @Override
                public int compare(ChattingUserList lhs, ChattingUserList rhs) {
                    return String.valueOf(rhs.getLastMessageTime()).compareTo(String.valueOf(lhs.getLastMessageTime()));   //or whatever your sorting algorithm
                }
            });

        }catch(Exception e){
            Log.e("Roster Error", e.toString());
        }
        return userList;
	}

    public static String selectTrigger(){
        String selectTrigger;
        try{
            selectTrigger = "CREATE TRIGGER if not exists select_roster AFTER UPDATE " +
                    "ON["+TAB_CHAT+"] for each row BEGIN SELECT * FROM " +TAB_CHAT+" ORDER BY LAST_MESSAGE_TIME DESC"+
                    " END;";
            Log.e("Trigger", selectTrigger);
            return selectTrigger;
        }catch(Exception e){
            return "";
        }

    }

    private String checkRosterData(){
        if(getRosterDetail() != null)
           return getRosterDetail().toString();
        else
           return "";
    }*/
	public void close()
		{
			db.close();
		}
}