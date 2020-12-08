package com.example.pawfinder.PetfinderAPI;

import com.example.pawfinder.BuildConfig;
import com.example.pawfinder.PetfinderAPI.AccessToken;
import com.example.pawfinder.PetfinderAPI.PetfinderResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("animals?location=53715&distance=10&limit=100&sort=random")
    Call<PetfinderResponse> getPetfinderPets(
            @Header("Authorization") String token
    );

//    @Headers("Authorization: Bearer " + PetsFragment.token)
//    @GET("animals?location=53715&distance=10")
//    Call<List<PetfinderPet>> getPetfinderPets();

}