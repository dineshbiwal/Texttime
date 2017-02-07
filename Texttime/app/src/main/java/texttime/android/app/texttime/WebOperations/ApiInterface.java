package texttime.android.app.texttime.WebOperations;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import texttime.android.app.texttime.DataModels.AcceptDenyRequestsModel;
import texttime.android.app.texttime.DataModels.ActivitySendModel;
import texttime.android.app.texttime.DataModels.ContactModel;
import texttime.android.app.texttime.DataModels.FollowRequestsModel;
import texttime.android.app.texttime.DataModels.MeFollowingData;
import texttime.android.app.texttime.DataModels.MyProfileModel;
import texttime.android.app.texttime.DataModels.OtherUserProfileModel;
import texttime.android.app.texttime.DataModels.RecentActivitiesModel;
import texttime.android.app.texttime.DataModels.RegisterUser;
import texttime.android.app.texttime.DataModels.ResendVerifyCode;
import texttime.android.app.texttime.DataModels.SendContactJSON;
import texttime.android.app.texttime.DataModels.UserSignup;
import texttime.android.app.texttime.DataModels.ValidateUserName;
import texttime.android.app.texttime.DataModels.Verification;


public interface ApiInterface {

    String currentVersion="/v1";
   /* @FormUrlEncoded
    @POST("/register")
    Call<RegisterUser> getRegisterUserDetail(@Field("dial_code") String dial_code, @Field("mobile_number") String mobile_number, @Field("country_id") String country_id);*/

    @FormUrlEncoded
    @POST(currentVersion+"/register")
    Call<RegisterUser> getRegisterUserDetail(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(currentVersion+"/register/verify/{guestToken}")
    Call<Verification> verifyCode(@Path("guestToken") String guestToken, @Field("verification_code") String verification_code);

    @FormUrlEncoded
    @POST(currentVersion+"/register/username/available/{guestToken}")
    Call<ValidateUserName> checkUserName(@Path("guestToken") String guestToken, @Field("username") String username);

    @FormUrlEncoded
    @POST(currentVersion+"/register/username/reserve/{guestToken}")
    Call<ValidateUserName> reserveUserName(@Path("guestToken") String guestToken, @Field("username") String username);


    @Multipart
    @POST(currentVersion+"/register/create-profile/{guestToken}")
    Call<UserSignup> userSignup(@Path("guestToken") String guestToken, @Part("username") RequestBody username, @Part("password") RequestBody password, @Part MultipartBody.Part file);

    @GET(currentVersion+"/register/verify/resend/{guestToken}")
    Call<ResendVerifyCode> resendTextCode(@Path("guestToken") String guestToken);

    /*@Multipart
    @POST("/login/{guestToken}")
    Call<UserSignup> loginUser(@Path("guestToken") String guestToken, @Part("username") RequestBody username, @Part("password") RequestBody password);*/

    @FormUrlEncoded
    @PUT(currentVersion+"/login/{guestToken}")
    Call<UserSignup> loginUser(@Path("guestToken") String guestToken, @FieldMap Map<String, String> fields);

    @Multipart
    @POST(currentVersion+"/login/{guestToken}")
    Call<UserSignup> loginUserPost(@Path("guestToken") String guestToken, @Part("username") RequestBody username, @Part("password") RequestBody password);


    @FormUrlEncoded
    @PUT(currentVersion+"/login/{guestToken}")
    Call<UserSignup> returnningUser(@Path("guestToken") String guestToken, @Field("returning_token") String returning_user, @Field("password") String password);

    @GET(currentVersion+"/users/self")
    Call<MyProfileModel> getMyProfile(@Header("Authorization") String authToken);

    @GET(currentVersion+"/users/self/follows")
    Call<MeFollowingData> getMeFollowing(@Header("Authorization") String authToken);

    @GET(currentVersion+"/users/self/followed-by")
    Call<MeFollowingData> getFollowing(@Header("Authorization") String authToken);

    @GET(currentVersion+"/users/self/requests")
    Call<FollowRequestsModel> getFollowRequest(@Header("Authorization") String authToken);

    @PUT(currentVersion+"/users/{username}/requests/accept")
    Call<AcceptDenyRequestsModel> approveRequest(@Header("Authorization") String authToken, @Path("username") String username);

    @PUT(currentVersion+"/users/{username}/requests/deny")
    Call<AcceptDenyRequestsModel> denyRequest(@Header("Authorization") String authToken, @Path("username") String username);

    @DELETE(currentVersion+"/users/{username}/follow")
    Call<AcceptDenyRequestsModel> unFollowUser(@Header("Authorization") String authToken, @Path("username") String username);

    @POST(currentVersion+"/users/{username}/follow")
    Call<AcceptDenyRequestsModel> followUser(@Header("Authorization") String authToken, @Path("username") String username);


    @GET(currentVersion+"/users/{username}")
    Call<OtherUserProfileModel> getOtherUserProfile(@Header("Authorization") String authToken, @Path("username") String username);

    @GET(currentVersion+"/users/{username}/follows")
    Call<MeFollowingData> getMeFollowing(@Header("Authorization") String authToken, @Path("username") String username);

    @GET(currentVersion+"/users/{username}/followed-by")
    Call<MeFollowingData> getFollowing(@Header("Authorization") String authToken, @Path("username") String username);

    @GET(currentVersion+"/contacts")
    Call<ContactModel> getContacts(@Header("Authorization") String authToken, @Query("page") String params);

    @POST(currentVersion+"/contacts")
    Call<ContactModel> postContacts(@Header("Content-Type") String con, @Header("Authorization") String authToken, @Body SendContactJSON contacts);

    @FormUrlEncoded
    @POST(currentVersion+"/activities")
    Call<ActivitySendModel> sendActivity(@Header("Authorization") String authToken, @Field("media_id") String media_id
            , @Field("hashtag_text") String hashtag_text
            , @Field("can_comment") String can_comment
            , @Field("can_liked") String can_liked
            , @Field("can_share") String can_share
    );

    @GET(currentVersion+"/feeds")
    Call<RecentActivitiesModel> getAllPostActivities(@Header("Authorization") String Authorization);

    @GET(currentVersion+"/feeds/{username}/{id}/more")
    Call<RecentActivitiesModel> getSevenPostOfUser(@Header("Authorization") String Authorization, @Path("id") String id, @Path("username") String username);


}
