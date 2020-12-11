package com.example.pawfinder.Pets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pawfinder.PetfinderAPI.AccessToken;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.Models.PetfinderResponse;
import com.example.pawfinder.PetfinderAPI.ApiService;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.CardStackConfig;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.example.pawfinder.Utils.PetAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
    private String tokenType;
    private String accessToken;

    private int totalPages;
    private int currentPage = 1;

    private View root;
    private ImageView mFilter;
    private ProgressBar mLoadingPets;
    private TextView mNoPets;
    private TextView mReachedEnd;
    private Button mViewPetsAgain;

    private ArrayList<PetfinderPet> mPets;

    private CardStackView mCardStackView;
    private CardStackLayoutManager manager;
    private PetAdapter mAdapter;

    private HashMap<String, String> params;
    private HashMap<String, Boolean> filtersApplied;
    private Boolean allFiltersApplied;

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pets, container, false);

        mCardStackView = root.findViewById(R.id.card_stack_view);
        mLoadingPets = root.findViewById(R.id.loadingPets);
        mNoPets = root.findViewById(R.id.textNoPets);
        mReachedEnd = root.findViewById(R.id.textReachedEnd);
        mViewPetsAgain = root.findViewById(R.id.buttonViewPetsAgain);

        mNoPets.setVisibility(View.GONE);
        mReachedEnd.setVisibility(View.GONE);
        mViewPetsAgain.setVisibility(View.GONE);
        mViewPetsAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpRetrofit();
            }
        });

        mFilter = root.findViewById(R.id.filter_icon);
        mFilter.setVisibility(View.GONE);
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment fragment = new FilterFragment();
                Bundle args = new Bundle();
                args.putSerializable("params", params);
                args.putSerializable("filtersApplied", filtersApplied);
                fragment.setArguments(args);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_activity_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        allFiltersApplied = false;
        params = null;

        try {
            params = getParamsFromBundle();
            allFiltersAppliedCheck();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: params was null from bundle " + e.getMessage());
        }

        if (filtersApplied == null) {
            filtersApplied = new HashMap<>();
            addDefaultFiltersValues();
        }

        setUpRetrofit();

        return root;
    }

    private void allFiltersAppliedCheck() {
        for(Map.Entry<String, Boolean> entry : filtersApplied.entrySet()) {
            if (!entry.getValue()) {
                allFiltersApplied = false;
                break;
            }
            allFiltersApplied = true;
        }
    }

    private void addDefaultFiltersValues() {
        filtersApplied.put("dog", false);
        filtersApplied.put("cat", false);
        filtersApplied.put("rabbit", false);
        filtersApplied.put("small-furry", false);
        filtersApplied.put("horse", false);
        filtersApplied.put("bird", false);
        filtersApplied.put("scales-fins-other", false);
        filtersApplied.put("barnyard", false);
        filtersApplied.put("small", false);
        filtersApplied.put("medium", false);
        filtersApplied.put("large", false);
        filtersApplied.put("xlarge", false);
        filtersApplied.put("male", false);
        filtersApplied.put("female", false);
        filtersApplied.put("baby", false);
        filtersApplied.put("young", false);
        filtersApplied.put("adult", false);
        filtersApplied.put("senior", false);
    }

    private void setUpRetrofit() {
        // Hide any messages
        mNoPets.setVisibility(View.GONE);
        mReachedEnd.setVisibility(View.GONE);
        mViewPetsAgain.setVisibility(View.GONE);

        // Show loading
        mLoadingPets.setVisibility(View.VISIBLE);

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

                Call<PetfinderResponse> getPetsCall;
                if (params == null || allFiltersApplied) {
                    getPetsCall = client.getPetfinderPets(tokenType + " " + accessToken, currentPage + "");
                } else {
                    getPetsCall = client.getFilteredPetfinderPets(tokenType + " " + accessToken, currentPage + "", params);
                }

                getPetsCall.enqueue(new Callback<PetfinderResponse>() {
                    @Override
                    public void onResponse(Call<PetfinderResponse> call, retrofit2.Response<PetfinderResponse> response) {
                        PetfinderResponse petfinderResponse = response.body();
                        mPets = petfinderResponse.getAnimals();
                        String pagesStr = petfinderResponse.getPagination().getTotal_pages();
                        totalPages = Integer.parseInt(pagesStr);
                        mLoadingPets.setVisibility(View.GONE);
                        cardStackConfig();
                        for (int i = 1; i < totalPages; i++) {
                            callNextPage();
                        }
                        mPets = PetAdapter.mPets;
                        mFilter.setVisibility(View.VISIBLE);
                        if (mPets.size() == 0) {
                            mNoPets.setVisibility(View.VISIBLE);
                        }
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
    }

    private void cardStackConfig() {
        final View[] root = new View[1];
        mAdapter = new PetAdapter(mPets, getActivity());
        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (manager.getTopPosition() == mAdapter.getItemCount()){
                    mReachedEnd.setVisibility(View.VISIBLE);
                    mViewPetsAgain.setVisibility(View.VISIBLE);
                }
                if (direction == Direction.Right) {
                    TextView id_tv = root[0].findViewById(R.id.item_id);
                    String id = id_tv.getText().toString();

                    PetfinderPet favorite = mPets.get(0);

                    for (PetfinderPet pet : mPets) {
                        if (pet.getId().equals(id)) {
                            favorite = pet;
                            break;
                        }
                    }

                    new FirebaseDatabaseHelper().addFavorite(favorite, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(ArrayList<PetfinderPet> favorites, ArrayList<String> keys) { }
                        @Override
                        public void DataIsInserted() { Log.d(TAG, "Pet added to favorites."); }
                        @Override
                        public void DataIsUpdated() { }
                        @Override
                        public void DataIsDeleted() { }
                    });
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
                root[0] = view;
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
        mCardStackView.setLayoutManager(manager);
        mCardStackView.setAdapter(mAdapter);
        mCardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void callNextPage() {
        currentPage++;
        if (currentPage <= totalPages) {
            Retrofit retrofit  = new Retrofit.Builder()
                    .baseUrl("https://api.petfinder.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService client = retrofit.create(ApiService.class);
            Call<PetfinderResponse> getRemainingPetsCall = client.getPetfinderPets(tokenType + " " + accessToken, currentPage + "");

            getRemainingPetsCall.enqueue(new Callback<PetfinderResponse>() {
                @Override
                public void onResponse(Call<PetfinderResponse> call, retrofit2.Response<PetfinderResponse> response) {
                    PetfinderResponse petfinderResponse = response.body();
                    ArrayList<PetfinderPet> more = petfinderResponse.getAnimals();
                    PetAdapter.mPets.addAll(more);
                }
                @Override
                public void onFailure(Call<PetfinderResponse> call, Throwable t) {
                }
            });

        }
    }

    private HashMap<String, String> getParamsFromBundle() {
        Log.d(TAG, "getParamsFromBundle: arguments " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            filtersApplied = (HashMap<String, Boolean>) bundle.getSerializable("filtersApplied");
            return (HashMap<String, String>) bundle.getSerializable("params");
        } else {
            return null;
        }
    }
}