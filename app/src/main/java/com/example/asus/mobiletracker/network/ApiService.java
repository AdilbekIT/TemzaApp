package com.example.asus.mobiletracker.network;

import com.example.asus.mobiletracker.entities.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register") //http://127.1.1.1:8888/a pi/register
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);


    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login (@Field("username") String username,@Field("password") String password);

    @POST("logout")
    @FormUrlEncoded
    Call<AccessToken> logout (@Field("access_token") String token);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh (@Field("refresh_token") String refreshToken);

//    @GET("organizations")
//    Call<FreeServicesResponse> freeServices();
//
//    @GET("organizations/{id}")
//    Call<SingleOrganization> getServices(@Path("id") int id);
}
