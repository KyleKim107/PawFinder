package com.example.pawfinder.Pets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pawfinder.PetfinderAPI.AccessToken;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.PetfinderAPI.PetfinderResponse;
import com.example.pawfinder.PetfinderAPI.ApiService;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.CardStackConfig;
import com.yuyakaido.android.cardstackview.CardStackView;


import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetsFragment extends Fragment {

    private static final String TAG = "PetsFragment";

    private final String grantType = "client_credentials";
    private final String clientId = "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ";
    private final String clientSecret = "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb";

    View root;
    private List<PetfinderPet> mPets;

    CardStackView mCardStackView;
    ImageView mFilter;

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pets, container, false);
        mCardStackView = root.findViewById(R.id.card_stack_view);
        mFilter = root.findViewById(R.id.filter_icon);

        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment fragment = new FilterFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_activity_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.petfinder.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiService service = retrofit.create(ApiService.class);
        Call<AccessToken> accessTokenCall = service.getAccessToken(
                grantType,
                clientId,
                clientSecret
        );

        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                final String tokenType = response.body().getTokenType();
                final String accessToken = response.body().getAccessToken();

                Retrofit retrofit  = new Retrofit.Builder()
                        .baseUrl("https://api.petfinder.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService client = retrofit.create(ApiService.class);
                Call<PetfinderResponse> getPetsCall = client.getPetfinderPets(tokenType + " " + accessToken);

                getPetsCall.enqueue(new Callback<PetfinderResponse>() {
                    @Override
                    public void onResponse(Call<PetfinderResponse> call, retrofit2.Response<PetfinderResponse> response) {
                        PetfinderResponse petfinderResponse = response.body();
                        mPets = petfinderResponse.getAnimals();
                        new CardStackConfig().setConfig(mCardStackView, getActivity(), mPets);
                    }
                    @Override
                    public void onFailure(Call<PetfinderResponse> call, Throwable t) {
                    }
                });
            }
            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
            }
        });

//
//        // Read favorites to know which pets can be swiped and which are already in favorites list
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        new FirebaseDatabaseHelper().readFavorites(new FirebaseDatabaseHelper.DataStatus() {
//            @Override
//            public void DataIsLoaded(ArrayList<Pet> favorites, ArrayList<String> keys) {
//            }
//            @Override
//            public void DataIsInserted() {
//            }
//            @Override
//            public void DataIsUpdated() {
//            }
//            @Override
//            public void DataIsDeleted() {
//            }
//        });
        return root;
    }

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