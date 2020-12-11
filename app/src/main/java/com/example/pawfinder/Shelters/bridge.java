package com.example.pawfinder.Shelters;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class bridge {
//    String mMessage;
//    String token;
//    String structure = null;
//
//
//    OkHttpClient client = new OkHttpClient();
//
//    bridge(){
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host("api.petfinder.com")
//                .addPathSegment("v2/oauth2/token")
//                .build();
//
//        RequestBody body = new FormBody.Builder()
//                .add("grant_type", "client_credentials")
//                .add("client_id", "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ")
//                .add("client_secret", "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://api.petfinder.com/v2/oauth2/token")
//                .post(body)
//                .build();
//        client.newCall(request).enqueue(callbackAfterRequest);
//    }
//
//    private Callback callbackAfterRequest = new Callback(){
//
//        @Override
//        public void onFailure(Call call, IOException e) {
//            String mMessage = e.getMessage().toString();
//            Log.w("failure Response", mMessage);
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            mMessage = response.body().string();
//            Log.i("RESPONSE-1", mMessage);
//            try{
//                JSONObject obj = new JSONObject(mMessage);
//
//                token = obj.getString("access_token");
//                structure = getShelters(token);
//
//            }catch (Exception e){
//
//            }
//            //Log.w("777" ,token );
//
//        }
//    };
}
