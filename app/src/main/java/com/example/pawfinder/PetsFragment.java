package com.example.pawfinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.pawfinder.MainActivity.JSON;

public class PetsFragment extends Fragment {

    private static final String TAG = "PetsFragment";
    View root;
    private ArrayList<Pet> mPets = new ArrayList<>();

    CardStackView mCardStackView;

    //private List<ItemModel> MyPets;

    OkHttpClient client = new OkHttpClient();
    String token;
    ArrayList<String> list = new ArrayList<String>();

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pets, container, false);

        mCardStackView = (CardStackView) root.findViewById(R.id.card_stack_view);

        new FirebaseDatabaseHelper("pets", "").readPets(new FirebaseDatabaseHelper.DataStatus() {
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

        try {
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host("api.petfinder.com")
                    .addPathSegment("v2/oauth2/token")
                    .build();

            RequestBody body = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .add("client_id", "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ")
                    .add("client_secret", "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb")
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.petfinder.com/v2/oauth2/token")
                    .addHeader("Accept", "application/json")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callbackAfterRequest);
        }catch(Exception e){
        }
        return root;
    }

    private Callback callbackAfterRequest = new Callback(){

        @Override
        public void onFailure(Call call, IOException e) {
            String mMessage = e.getMessage().toString();
            Log.w("failure Response", mMessage);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try{
                String mMessage = response.body().string();
                JSONObject object =(JSONObject) new JSONObject(mMessage);
               String value = object.getString("access_token");
                Log.e("RESPONSE-1", value);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    };
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