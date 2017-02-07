package texttime.android.app.texttime.WebOperations;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import texttime.android.app.texttime.CommonClasses.CommonDataUtility;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.CommonClasses.CustomProgressDialog;
import texttime.android.app.texttime.CommonClasses.SaveDataPreferences;
import texttime.android.app.texttime.DataModels.AcceptDenyRequestsModel;
import texttime.android.app.texttime.DataModels.ActivitySendModel;
import texttime.android.app.texttime.DataModels.ContactModel;
import texttime.android.app.texttime.DataModels.FollowRequestsModel;
import texttime.android.app.texttime.DataModels.MeFollowingData;
import texttime.android.app.texttime.DataModels.MyProfileModel;
import texttime.android.app.texttime.DataModels.OtherUserProfileModel;
import texttime.android.app.texttime.DataModels.RecentActivitiesModel;
import texttime.android.app.texttime.DataModels.RegisterUser;
import texttime.android.app.texttime.DataModels.RequestErrorModel;
import texttime.android.app.texttime.DataModels.ResendVerifyCode;
import texttime.android.app.texttime.DataModels.SendContactJSON;
import texttime.android.app.texttime.DataModels.UserSignup;
import texttime.android.app.texttime.DataModels.ValidateUserName;
import texttime.android.app.texttime.DataModels.Verification;
import texttime.android.app.texttime.callbacks.ErrorUtils;


/**
 * Created by TextTime Android Dev on 8/2/2016.
 */
public class WebTask {

    int taskCode;
    WebTaskCallback callback;
    Object responseObject;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Map<String,String> paramsMap;
    CustomProgressDialog cpd;
    Context context;
    SendContactJSON contactJson;
    public WebTask(Context con, int taskCode, WebTaskCallback callback, Map<String, String> paramsMap) {
        this.taskCode = taskCode;
        this.callback = callback;
        this.paramsMap = paramsMap;
        this.context=con;
      /*  cpd=new CustomProgressDialog(con);
        cpd.show();*/
    }

    public WebTask(Context con, int taskCode, WebTaskCallback callback) {
        this.taskCode = taskCode;
        this.callback = callback;
        this.paramsMap = paramsMap;
        this.context=con;
      /*  cpd=new CustomProgressDialog(con);
        cpd.show();*/
    }

    public WebTask(Context con, int taskCode, WebTaskCallback callback, SendContactJSON json) {
        this.taskCode = taskCode;
        this.callback = callback;
        this.paramsMap = paramsMap;
        this.context=con;
        this.contactJson=json;
      /*  cpd=new CustomProgressDialog(con);
        cpd.show();*/
    }

    public void performTask(){
        if(new CommonDataUtility(context).isNetworkAvailable()) {
            switch (taskCode) {
                case TaskCode.SENDVERIFYCODE:
                    sendVerification();
                    break;

                case TaskCode.VERIFYCODE:
                    verifyCode();
                    break;

                case TaskCode.REVERIFYCODE:
                    reverifyCode();
                    break;

                case TaskCode.CHECKUSERNAME:
                    verifyUserName();
                    break;

                case TaskCode.RESERVEUSERNAME:
                    saveUserName();
                    break;

                case TaskCode.LOGINRETURNING:
                    loginReturningUser();
                    break;

                case TaskCode.LOGIN:
                    login();
                    break;

                case TaskCode.MYPROFILE:
                    getMyProfile();
                    break;

                case TaskCode.GETMEFOLLOWING:
                    getMeFollowing();
                    break;

                case TaskCode.GETFOLLOWING:
                    getFollowing();
                    break;

                case TaskCode.GETREQUESTS:
                    getRequest();
                    break;

                case TaskCode.ACCEPTREQUEST:
                    accpetRequest();
                    break;

                case TaskCode.REJECTREQUEST:
                    denyRequest();
                    break;

                case TaskCode.UNFOLLOWUSER:
                    unFollowUser();
                    break;

                case TaskCode.FOLLOWUSER:
                    followUser();
                    break;


                case TaskCode.GETOTHERPROFILE:
                    getOtherUserProfile();
                    break;

                case TaskCode.GETFOLLOWINGOTHER:
                    getFollowingOther();
                    break;

                case TaskCode.GETMEFOLLOWINGOTHER:
                    getMeFollowingOther();
                    break;

                case TaskCode.GETCONTACTSFROMSERVER:
                    getContactsFromServer();
                    break;

                case TaskCode.POSTCONTACTSTOSERVER:
                    sendContactsToServer();
                    break;

                case TaskCode.POSTACTIVITY:
                    sendActivity();
                    break;

                case TaskCode.GETRECENTACTIVITY:
                    getRecentPosts();
                    break;

                case TaskCode.GETRECENTACTIVITYUSER:
                    getUserRecentPosts();
                    break;


            }
        }

        else{
            CommonViewUtility.getInstance().showAlert(context,"Please connect to internet.");
        }
    }


    private void getUserRecentPosts(){
        String token=new SaveDataPreferences(context).getToken();
        Call<RecentActivitiesModel> call=apiService.getSevenPostOfUser(token,paramsMap.get("ac_id"),paramsMap.get("username"));
        call.enqueue(new Callback<RecentActivitiesModel>() {
            @Override
            public void onResponse(Call<RecentActivitiesModel> call, Response<RecentActivitiesModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){
                        System.out.print(">>>>"+e);
                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RecentActivitiesModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void getRecentPosts(){
        String token=new SaveDataPreferences(context).getToken();
        Call<RecentActivitiesModel> call=apiService.getAllPostActivities(token);
        call.enqueue(new Callback<RecentActivitiesModel>() {
            @Override
            public void onResponse(Call<RecentActivitiesModel> call, Response<RecentActivitiesModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){
                        System.out.print(">>>>"+e);
                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RecentActivitiesModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void sendContactsToServer(){
        String token=new SaveDataPreferences(context).getToken();
        Call<ContactModel> call=apiService.postContacts("application/json",token,contactJson);
        call.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){
                      System.out.print(">>>>"+e);
                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {

                callback.fail(taskCode);
            }
        });
    }

    private void getContactsFromServer(){
        String token=new SaveDataPreferences(context).getToken();
        Call<ContactModel> call=apiService.getContacts(token,paramsMap.get("page"));
        call.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){

                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {

                callback.fail(taskCode);
            }
        });
    }

    private void getOtherUserProfile(){
        String token=new SaveDataPreferences(context).getToken();
        String s=paramsMap.get("username");
        Call<OtherUserProfileModel> call=apiService.getOtherUserProfile(token,s);
        call.enqueue(new Callback<OtherUserProfileModel>() {
            @Override
            public void onResponse(Call<OtherUserProfileModel> call, Response<OtherUserProfileModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){

                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OtherUserProfileModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }



    private void sendActivity(){
        String token=new SaveDataPreferences(context).getToken();
        String s=paramsMap.get("media_id");
        String s1=paramsMap.get("hashtag_text");
        String s2=paramsMap.get("can_comment");
        String s3=paramsMap.get("can_liked");
        String s4=paramsMap.get("can_share");

        Call<ActivitySendModel> call=apiService.sendActivity(token,s,s1,s2,s3,s4);
        call.enqueue(new Callback<ActivitySendModel>() {
            @Override
            public void onResponse(Call<ActivitySendModel> call, Response<ActivitySendModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){

                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ActivitySendModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void unFollowUser(){
        String token=new SaveDataPreferences(context).getToken();
        String s=paramsMap.get("username");
        Call<AcceptDenyRequestsModel> call=apiService.unFollowUser(token,s);
        call.enqueue(new Callback<AcceptDenyRequestsModel>() {
            @Override
            public void onResponse(Call<AcceptDenyRequestsModel> call, Response<AcceptDenyRequestsModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){

                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AcceptDenyRequestsModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void followUser(){
        String token=new SaveDataPreferences(context).getToken();
        String s=paramsMap.get("username");
        Call<AcceptDenyRequestsModel> call=apiService.followUser(token,s);
        call.enqueue(new Callback<AcceptDenyRequestsModel>() {
            @Override
            public void onResponse(Call<AcceptDenyRequestsModel> call, Response<AcceptDenyRequestsModel> response) {
                if(response.body()!=null) {
                    try {
                        responseObject = response.body();
                        callback.success(responseObject, taskCode);
                    }
                    catch (Exception e){

                    }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AcceptDenyRequestsModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void accpetRequest(){
        String token=new SaveDataPreferences(context).getToken();
        String s=paramsMap.get("username");
        Call<AcceptDenyRequestsModel> call=apiService.approveRequest(token,s);
        call.enqueue(new Callback<AcceptDenyRequestsModel>() {
            @Override
            public void onResponse(Call<AcceptDenyRequestsModel> call, Response<AcceptDenyRequestsModel> response) {
                if(response.body()!=null) {
                   try {
                       responseObject = response.body();
                       callback.success(responseObject, taskCode);
                   }
                   catch (Exception e){

                   }
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AcceptDenyRequestsModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void denyRequest(){
        String token=new SaveDataPreferences(context).getToken();
        String s=paramsMap.get("username");
        Call<AcceptDenyRequestsModel> call=apiService.denyRequest(token,s);
        call.enqueue(new Callback<AcceptDenyRequestsModel>() {
            @Override
            public void onResponse(Call<AcceptDenyRequestsModel> call, Response<AcceptDenyRequestsModel> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            RequestErrorModel model= ErrorUtils.parseError(response);
                            responseObject = model;
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AcceptDenyRequestsModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }

    private void getRequest(){
        String token=new SaveDataPreferences(context).getToken();

        Call<FollowRequestsModel> call=apiService.getFollowRequest(token);

        call.enqueue(new Callback<FollowRequestsModel>() {
            @Override
            public void onResponse(Call<FollowRequestsModel> call, Response<FollowRequestsModel> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowRequestsModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }




    private void getMeFollowing(){
        String token=new SaveDataPreferences(context).getToken();
        Call<MeFollowingData> call=apiService.getMeFollowing(token);
        call.enqueue(new Callback<MeFollowingData>() {
            @Override
            public void onResponse(Call<MeFollowingData> call, Response<MeFollowingData> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MeFollowingData> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void getMeFollowingOther(){
        String token=new SaveDataPreferences(context).getToken();
        String username=paramsMap.get("username");
        Call<MeFollowingData> call=apiService.getMeFollowing(token,username);
        call.enqueue(new Callback<MeFollowingData>() {
            @Override
            public void onResponse(Call<MeFollowingData> call, Response<MeFollowingData> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MeFollowingData> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }





    private void getFollowing(){
        String token=new SaveDataPreferences(context).getToken();
        Call<MeFollowingData> call=apiService.getFollowing(token);
        call.enqueue(new Callback<MeFollowingData>() {
            @Override
            public void onResponse(Call<MeFollowingData> call, Response<MeFollowingData> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MeFollowingData> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void getFollowingOther(){
        String token=new SaveDataPreferences(context).getToken();
        String username=paramsMap.get("username");
        Call<MeFollowingData> call=apiService.getFollowing(token,username);
        call.enqueue(new Callback<MeFollowingData>() {
            @Override
            public void onResponse(Call<MeFollowingData> call, Response<MeFollowingData> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MeFollowingData> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }


    private void getMyProfile(){
     String token=new SaveDataPreferences(context).getToken();
        Call<MyProfileModel> call=apiService.getMyProfile(token);
        call.enqueue(new Callback<MyProfileModel>() {
            @Override
            public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyProfileModel> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }

    private void loginReturningUser(){
        Map<String,String> paramsMap1=new HashMap<>();
        paramsMap1.put("returning_token",paramsMap.get("returning_token"));
        paramsMap1.put("password",paramsMap.get("password"));

        Call<UserSignup> call = apiService.loginUser(paramsMap.get("token"),paramsMap1);
        call.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                //  cpd.dismiss();
                callback.fail(taskCode);
            }
        });
    }


    private void login(){
        Map<String,String> paramsMap1=new HashMap<>();
        paramsMap1.put("username",paramsMap.get("username"));
        paramsMap1.put("password",paramsMap.get("password"));

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), paramsMap.get("username"));
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), paramsMap.get("password"));


        Call<UserSignup> call = apiService.loginUserPost(paramsMap.get("token"),email,password);
        call.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        try {
                            responseObject = response.errorBody();
                            //  cpd.dismiss();
                            callback.failed(responseObject, taskCode);
                        }
                        catch (Exception e){
                            callback.fail(taskCode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                //  cpd.dismiss();
                callback.fail(taskCode);
            }
        });
    }

    private void sendVerification(){
        Call<RegisterUser> call = apiService.getRegisterUserDetail(paramsMap);
        call.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
//                    cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        responseObject=response.errorBody();
                      //  cpd.dismiss();
                        callback.failed(responseObject,taskCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
              //  cpd.dismiss();
                callback.fail(taskCode);
            }
        });
    }


    private void verifyCode(){
        Call<Verification> call = apiService.verifyCode(paramsMap.get("token"),paramsMap.get("code"));
        call.enqueue(new Callback<Verification>() {
            @Override
            public void onResponse(Call<Verification> call, Response<Verification> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
                    //cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        responseObject=response.errorBody();
                      //  cpd.dismiss();
                        callback.failed(responseObject,taskCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<Verification> call, Throwable t) {
                //cpd.dismiss();
                callback.fail(taskCode);
            }
        });
    }


    private void reverifyCode(){
        Call<ResendVerifyCode> call = apiService.resendTextCode(paramsMap.get("token"));
        call.enqueue(new Callback<ResendVerifyCode>() {
            @Override
            public void onResponse(Call<ResendVerifyCode> call, Response<ResendVerifyCode> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
                   // cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        responseObject=response.errorBody();
                     //   cpd.dismiss();
                        callback.failed(responseObject,taskCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResendVerifyCode> call, Throwable t) {
                //cpd.dismiss();
                callback.fail(taskCode);
            }
        });
    }


    private void verifyUserName(){
        Call<ValidateUserName> call = apiService.checkUserName(paramsMap.get("token"),paramsMap.get("username"));
        call.enqueue(new Callback<ValidateUserName>() {
            @Override
            public void onResponse(Call<ValidateUserName> call, Response<ValidateUserName> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
                    // cpd.dismiss();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        responseObject=response.raw();
                        //   cpd.dismiss();
                        callback.failed(responseObject,taskCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<ValidateUserName> call, Throwable t) {
                //cpd.dismiss();
                callback.fail(taskCode);
            }
        });
    }


    private void saveUserName(){
        Call<ValidateUserName> call = apiService.reserveUserName(paramsMap.get("token"),paramsMap.get("username"));
        call.enqueue(new Callback<ValidateUserName>() {
            @Override
            public void onResponse(Call<ValidateUserName> call, Response<ValidateUserName> response) {
                if(response.body()!=null) {
                    responseObject = response.body();
                    callback.success(responseObject,taskCode);
                }

                else
                {
                    if(response.errorBody()!=null){
                        responseObject=response.raw();
                        callback.failed(responseObject,taskCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<ValidateUserName> call, Throwable t) {
                callback.fail(taskCode);
            }
        });
    }
}
