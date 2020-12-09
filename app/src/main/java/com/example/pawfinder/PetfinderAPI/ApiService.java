package com.example.pawfinder.PetfinderAPI;

import com.example.pawfinder.BuildConfig;
import com.example.pawfinder.Models.PetfinderResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @Headers("Accept: application/json" + BuildConfig.APPLICATION_ID)
    @POST("oauth2/token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("animals?location=53715&distance=10&sort=random")
    Call<PetfinderResponse> getPetfinderPets(
            @Header("Authorization") String token,
            @Query("page") String page
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("animals?location=53715&distance=10&sort=random")
    Call<PetfinderResponse> getFilterdPetfinderPets(
            @Header("Authorization") String token,
            @Query("page") String page,
            @QueryMap HashMap<String, String> params);

}