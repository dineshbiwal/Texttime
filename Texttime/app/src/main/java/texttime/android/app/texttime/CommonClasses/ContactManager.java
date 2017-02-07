package texttime.android.app.texttime.CommonClasses;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.util.ArrayList;
import texttime.android.app.texttime.DataModels.ContactDataModel;
import texttime.android.app.texttime.DataModels.SendContactModel;
import texttime.android.app.texttime.database.DBAdapter;

/**
 * Created by TextTime Android Dev on 10/5/2016.
 */
public class ContactManager {

    Context context;
    DBAdapter db;
    public ContactManager(Context context) {
        this.context = context;
        db = new DBAdapter(context);
    }


    //-----Gets the phone number and country code from the contacts in the phonebook
    //-----Send contact model can be directly converted into the JSON to export to the server.
    //-----If contact list returns with size 0 then no contact is valid.
    public ArrayList<SendContactModel> fetchContactList(){

        ArrayList<SendContactModel> contactList=new ArrayList<>();
         try {
             Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
             while(phones.moveToNext()){
                 String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                 SendContactModel model=extractCountryCode(phoneNumber);
                 if(model!=null) {
                     if (!TextUtils.isEmpty(model.country) && !TextUtils.isEmpty(model.number)) {
                         contactList.add(model);
                     }
                 }
             }
         }
         catch (Exception e) {
         }
        return  contactList;
    }

    //---Extract the country code and returns with the object containing
    //---Phone number and country code in the seperate params
    //---Object will be later serialized into JSON and sent to server.

    private SendContactModel extractCountryCode(String phoneNumber){
        SendContactModel model=null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        db.open();
        int countryCode = 0;
        long nationalNumber = 0;
            try {
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
                if (phoneUtil.isValidNumber(numberProto)) {
                    countryCode = numberProto.getCountryCode();
                    nationalNumber = numberProto.getNationalNumber();
                    if(db.checkNumberExist(String.valueOf(countryCode), String.valueOf(nationalNumber))) {
                        model = new SendContactModel("" + countryCode, "" + nationalNumber);
                    }
                }

            } catch (NumberParseException e) {
                //System.out.println("Exception is >>>>>" + e);
            }
        db.close();
        return model;
    }


    //---Checkes the updated phonelist from server against the updated phone contact list
    //---any missing contact from the phone book on the server will be added to the missingcontact list
    //---this object of missing contact list can then be synced with the server.
    public ArrayList<SendContactModel> getMissingSyncContacts(ArrayList<ContactDataModel> serverContactList){
        ArrayList<SendContactModel> phoneBookList=fetchContactList();
        ArrayList<SendContactModel> missingContactList=new ArrayList<>();

        for(int i=0;i<phoneBookList.size();i++){
            SendContactModel phoneContact=phoneBookList.get(i);
            boolean isFound=false;
            for(int j=0;j<serverContactList.size();j++){
                ContactDataModel serverContact=serverContactList.get(j);
                if(phoneContact.number.equalsIgnoreCase(serverContact.getMobile_number())){
                    isFound=true;
                }
            }
            if(!isFound)
                missingContactList.add(phoneContact);
        }

        return missingContactList;
    }



    //---Get the list of contacts that are Texttime users.
    //---This details should be cached to display the chat users.

    public ArrayList<ContactDataModel> getMembersList(ArrayList<ContactDataModel> serverContactList){
        ArrayList<ContactDataModel> memberContactList=new ArrayList<>();
        for(int i=0;i<serverContactList.size();i++){
            ContactDataModel model=serverContactList.get(i);
            if(model.is_member()){
                memberContactList.add(model);
            }
        }

        return memberContactList;
    }
}
