package com.example.pawfinder.PetfinderAPI.Network;

import com.example.pawfinder.BuildConfig;
import com.example.pawfinder.PetfinderAPI.Entities.AccessToken;
import com.example.pawfinder.PetfinderAPI.Entities.PetfinderResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Accept: application/json" + BuildConfig.APPLICATION_ID)
    @POST("oauth2/token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @GET("animals?location=53715&distance=10")
    Call<PetfinderResponse> animals();

}