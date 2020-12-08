package com.example.pawfinder.Pets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.CardStackConfig;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yuyakaido.android.cardstackview.CardStackView;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PetsFragment extends Fragment {

    private static final String TAG = "PetsFragment";
    View root;
    private ArrayList<Pet> mPets = new ArrayList<>();

    CardStackView mCardStackView;

    //private List<ItemModel> MyPets;

//    OkHttpClient client = new OkHttpClient();
//    String token;
//    ArrayList<String> list = new ArrayList<String>();

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pets, container, false);
        mCardStackView = (CardStackView) root.findViewById(R.id.card_stack_view);

        new FirebaseDatabaseHelper().readPets(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> pets, ArrayList<String> keys) {
                new CardStackConfig().setConfig(mCardStackView, getActivity(), pets, keys);
                mPets = pets;
            }
            @Override
            public void DataIsInserted() {
            }
            @Override
            public void DataIsUpdated() {
            }
            @Override
            public void DataIsDeleted() {
            }
        });

        // Read favorites to know which pets can be swiped and which are already in favorites list
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        new FirebaseDatabaseHelper().readFavorites(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> favorites, ArrayList<String> keys) {
            }
            @Override
            public void DataIsInserted() {
            }
            @Override
            public void DataIsUpdated() {
            }
            @Override
            public void DataIsDeleted() {
            }
        });

//        try {
//            HttpUrl httpUrl = new HttpUrl.Builder()
//                    .scheme("http")
//                    .host("api.petfinder.com")
//                    .addPathSegment("v2/oauth2/token")
//                    .build();
//
//            RequestBody body = new FormBody.Builder()
//                    .add("grant_type", "client_credentials")
//                    .add("client_id", "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ")
//                    .add("client_secret", "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb")
//                    .build();
//            Request request = new Request.Builder()
//                    .url("https://api.petfinder.com/v2/oauth2/token")
//                    .addHeader("Accept", "application/json")
//                    .post(body)
//                    .build();
//            client.newCall(request).enqueue(callbackAfterRequest);
//        }catch(Exception e){
//        }
        return root;
    }

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
//            try{
//                String mMessage = response.body().string();
//                JSONObject object =(JSONObject) new JSONObject(mMessage);
//               String value = object.getString("access_token");
//                Log.e("RESPONSE-1", value);
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//    };

//    private void paginate() {
//        List<ItemModel> old = adapter.getItems();
//        List<ItemModel> current = new ArrayList<>(createSpots());
//        CardStackCallback callback = new CardStackCallback(old, current);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//        adapter.setItems(current);
//        result.dispatchUpdatesTo(adapter);
//
//    }
}