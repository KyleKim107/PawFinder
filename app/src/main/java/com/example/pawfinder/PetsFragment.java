package com.example.pawfinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
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

    private static final String TAG = PetsFragment.class.getSimpleName();
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

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
        View root = inflater.inflate(R.layout.fragment_pets, container, false);

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
        init(root);
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
            catch(Exception e ){
                e.printStackTrace();
            }
            }
    };

    private void init(View root) {
        CardStackView cardStackView = root.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(getContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
                }

                // TODO: Why 10???
                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 10){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(createSpots());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> current = new ArrayList<>(createSpots());
        CardStackCallback callback = new CardStackCallback(old, current);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(current);
        result.dispatchUpdatesTo(adapter);

    }

    // Dummy data
    private List<ItemModel> createSpots() {
//        Log.i("GET", )
        List<ItemModel> items = new ArrayList<>();
        String pet1Url = "https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80";
        String pet2Url = "https://images.unsplash.com/photo-1570018143038-6f4c428f6e3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=698&q=80";
        String pet3Url = "https://images.unsplash.com/photo-1598739871560-29dfcd95b823?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80";
        String pet4Url = "https://images.unsplash.com/photo-1542296935124-75ae8a4e6329?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80";
        String pet5Url = "https://images.unsplash.com/photo-1570824105192-a7bb72b73141?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=767&q=80";
        String pet6Url = "https://images.unsplash.com/photo-1593991393705-552675fe6f6d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80";
        String pet7Url = "https://images.unsplash.com/photo-1605125731324-fe8ef7d199df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1418&q=80";
        items.add(new ItemModel(pet1Url, "Lucky", "3 years", "Male"));
        items.add(new ItemModel(pet2Url, "Pumpkin", "1 month", "Female"));
        items.add(new ItemModel(pet3Url, "Chase", "5 years", "Male"));
        items.add(new ItemModel(pet4Url, "Sweetie", "3 years", "Female"));
        items.add(new ItemModel(pet5Url, "Honey", "2 years", "Female"));
        items.add(new ItemModel(pet6Url, "Camper", "4 years", "Male"));
        items.add(new ItemModel(pet7Url, "Grover", "2 years", "Male"));
        return items;
    }



//    private ItemModel createSpot() {
//        return new ItemModel(R.drawable.pet1, "Daisy", "3 years", "Female");
//    }

}