package com.whistledevelopers.jojo;

import com.google.gson.JsonObject;
import com.whistledevelopers.jojo.model.Categories;
import com.whistledevelopers.jojo.model.Experts;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {
    //String BASE_URl="http://whistledevelopers.com/joju/api/";
    String BASE_URl = "http://app.joju-services.com/api/";

    @Headers("api_key: 14159265359")
    @GET("category")
    Call<Categories> getCategories(@Query("filter[parent_id]") String id);

    @Headers("api_key: 14159265359")
    @GET("category")
    Call<Categories> getSubCategory(@Query("filter[parent_id]") String name);

    @Headers("api_key: 14159265359")
    @GET("category/{id}")
    Call<ResponseBody> getOneCategory(@Path("id") String id);

    @Headers("api_key: 14159265359")
    @GET("experts")
    Call<Experts> getExperts(@Query("filter[categories]") String id);

    @Headers("api_key: 14159265359")
    @GET("experts/{id}.json")
    Call<ResponseBody> getOneExpert(@Path("id") String id);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> userLogin(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("phone") String phone,
                                 @Field("alt_phone") String alt_phone,
                                 @Field("password") String password);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("verify")
    Call<ResponseBody> otpverify(@Field("phone") String phone,
                                 @Field("status") String Status);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> userLogin(@Field("phone") String phone,
                                 @Field("password") String password);

    @Headers("api_key: 14159265359")
    @GET("user")
    Call<ResponseBody> getUserProfile(@Query("phone") String phone);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("call")
    Call<ResponseBody> callLog(@Field("user_phone") String userPhone,
                               @Field("expert_id") String expertId);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("update")
    Call<ResponseBody> updateUser(@Field("phone") String phone,
                                  @Field("name") String name,
                                  @Field("email") String email,
                                  @Field("alt_phone") String alt_phone);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("location")
    Call<ResponseBody> sendLocation(@Field("phone") String phone,
                                    @Field("address1") String address1,
                                    @Field("address2") String address2,
                                    @Field("city") String city,
                                    @Field("pincode") String pincode);

    @Headers("api_key: 14159265359")
    @GET("send_otp")
    Call<ResponseBody> sendOtp(@Query("user_phone") String phone);

    @Headers("api_key: 14159265359")
    @GET("verify_otp")
    Call<ResponseBody> verifyOtp(@Query("user_phone") String phone, @Query("otp") String otp);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("ch_pass")
    Call<ResponseBody> changePass(@Field("user_phone") String phone,
                                  @Field("password") String password);

    @Headers("api_key: 14159265359")
    @FormUrlEncoded
    @POST("book")
    Call<ResponseBody> bookExpert(@Field("user_phone") String user_phone,
                                  @Field("expert_id") String expertId,
                                  @Field("booking_phone") String booking_phone,
                                  @Field("date") String date);


        /*
@GET("send_otp")
@FormUrlEncoded
void sendOtp(@QueryMap Map<String, String> params, Callback<String> callback);
*/


}
