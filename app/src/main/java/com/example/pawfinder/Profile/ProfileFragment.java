package com.example.pawfinder.Profile;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pawfinder.Models.PetfinderIdResponse;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.Models.PetfinderResponse;
import com.example.pawfinder.PetfinderAPI.AccessToken;
import com.example.pawfinder.PetfinderAPI.ApiService;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.example.pawfinder.Utils.PetAdapter;
import com.example.pawfinder.Utils.RecyclerViewConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    View root;

    private static final String TAG = "ProfileFragment";

    private final String grantType = "client_credentials";
    private final String clientId = "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ";
    private final String clientSecret = "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb";
    private String tokenType;
    private String accessToken;

    PetfinderPet petfinderPet;

    //vars
    private ArrayList<PetfinderPet> mFavorites = new ArrayList<>();
    private ArrayList<String> mKeys = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private TextView name, noPetsText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Show the user's name in the profile page
        name = root.findViewById(R.id.userName);
        name.setText(user.getDisplayName());

        noPetsText = root.findViewById(R.id.noPetsText);
        noPetsText.setVisibility(View.GONE);

        mRecyclerView = root.findViewById(R.id.recyclerView);

        new FirebaseDatabaseHelper().readFavorites(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<PetfinderPet> favorites, ArrayList<String> keys) {
                for (int i = 0; i < favorites.size(); i++) {
                    checkPetStatus(favorites.get(i), keys.get(i));
                }
                root.findViewById(R.id.loadingFavorites).setVisibility(View.GONE);
                new RecyclerViewConfig().setConfig(mRecyclerView, getActivity(), favorites, keys);
                mFavorites = favorites;
                if (mFavorites.size() == 0) {
                    noPetsText.setVisibility(View.VISIBLE);
                } else {
                    noPetsText.setVisibility(View.GONE);
                }
                mKeys = keys;
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

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        return root;
    }

    private void checkPetStatus(final PetfinderPet pet, final String key) {
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
                tokenType = response.body().getTokenType();
                accessToken = response.body().getAccessToken();

                Retrofit retrofit  = new Retrofit.Builder()
                        .baseUrl("https://api.petfinder.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService client = retrofit.create(ApiService.class);

                Call<PetfinderIdResponse> getPetCall;
                getPetCall = client.checkPetStatus(tokenType + " " + accessToken, pet.getId());

                getPetCall.enqueue(new Callback<PetfinderIdResponse>() {
                    @Override
                    public void onResponse(Call<PetfinderIdResponse> call, retrofit2.Response<PetfinderIdResponse> response) {
                        PetfinderIdResponse petfinderIdResponse = response.body();
                        Boolean remove = false;
                        if (petfinderIdResponse != null) {
                            petfinderPet = petfinderIdResponse.getAnimal();
                            if (petfinderPet.getStatus().equals("adopted")) {
                                remove = true;
                            }
                        } else {
                            remove = true;
                        }
                        if (remove) {
                            mFavorites.remove(pet);
                            mKeys.remove(key);
                            new FirebaseDatabaseHelper().deleteFavorite(key, new FirebaseDatabaseHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(ArrayList<PetfinderPet> favorites, ArrayList<String> keys) { }
                                @Override
                                public void DataIsInserted() { }
                                @Override
                                public void DataIsUpdated() { }
                                @Override
                                public void DataIsDeleted() { }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<PetfinderIdResponse> call, Throwable t) {
                    }
                });
            }
            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
            }
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            PetfinderPet fav = mFavorites.get(viewHolder.getAdapterPosition());
            String key = mKeys.get(viewHolder.getAdapterPosition());
            mFavorites.remove(fav);
            mKeys.remove(key);

            new FirebaseDatabaseHelper().deleteFavorite(key, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(ArrayList<PetfinderPet> favorites, ArrayList<String> keys) {
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
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((RecyclerViewConfig.FavoriteItemView) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final View foregroundView = ((RecyclerViewConfig.FavoriteItemView) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(foregroundView);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((RecyclerViewConfig.FavoriteItemView) viewHolder).viewForeground;

            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }
    };


}