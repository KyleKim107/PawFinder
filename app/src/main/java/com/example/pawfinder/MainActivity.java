package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawfinder.Login.LoginActivity;
import com.example.pawfinder.Lost.AllLostPetsFragment;
import com.example.pawfinder.Lost.LostFragment;
import com.example.pawfinder.Lost.MyLostPetsFragment;
import com.example.pawfinder.Lost.ViewLostPetFragment;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.PetfinderAPI.Entities.PetfinderResponse;
import com.example.pawfinder.PetfinderAPI.Network.ApiService;
import com.example.pawfinder.PetfinderAPI.Network.RetrofitBuilder;
import com.example.pawfinder.PetfinderAPI.TokenManager;
import com.example.pawfinder.Pets.FilterActivity;
import com.example.pawfinder.Pets.PetsFragment;
import com.example.pawfinder.Pets.ViewPetFragment;
import com.example.pawfinder.Profile.ProfileFragment;
import com.example.pawfinder.Profile.ViewFavoritePetFragment;
import com.example.pawfinder.Shelters.SheltersFragment;
import com.example.pawfinder.Utils.LostPetsListAdapter;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;



public class MainActivity extends AppCompatActivity implements AllLostPetsFragment.OnAllLostPetSelectedListener, MyLostPetsFragment.OnMyLostPetSelectedListener, LostPetsListAdapter.OnLoadMoreItemsListener {

    final static String TAG = "MainActivity";

//    ApiService service;
//    TokenManager tokenManager;
//    Call<PetfinderResponse> call;

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf=8");
//    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_container,
                new PetsFragment()).commit();

//        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
//
//        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
//
//        getAnimals();

//        String url = "https://api.petfinder.com/v2/oauth2/token";
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_pets:
                            selected = new PetsFragment();
                            break;
                        case R.id.navigation_shelters:
                            selected = new SheltersFragment();
                            break;
                        case R.id.navigation_lost:
                            selected = new LostFragment();
                            break;
                        case R.id.navigation_profile:
                            selected = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_container,
                            selected).commit();
                    return true;
                }
            };

//    public void getAnimals() {
//        call = service.animals();
//        call.enqueue(new Callback<PetfinderResponse>() {
//            @Override
//            public void onResponse(Call<PetfinderResponse> call, Response<PetfinderResponse> response) {
//                Log.w(TAG, "onResponse: " + response );
//
//                if(response.isSuccessful()){
//                    Log.e(TAG, "onResponse: " + response.body().getAnimals().get(0).getName());
//                }else {
//                    tokenManager.deleteToken();
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//            @Override
//            public void onFailure(Call<PetfinderResponse> call, Throwable t) {
//                Log.w(TAG, "onFailure: " + t.getMessage() );
//            }
//        });
//    }

    public void onFavoritePetSelected(Pet pet, String callingActivity) {
        Log.d(TAG, "onLostPetSelected: Selected a lost pet");

        ViewFavoritePetFragment fragment = new ViewFavoritePetFragment();
        Bundle args = new Bundle();
        args.putParcelable("LOSTPET", pet);
        args.putString("MAINACTIVITY", callingActivity);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_activity_container, fragment);
        transaction.addToBackStack("View Favorite Pet"); // TODO: or (null) ????
        transaction.commit();
    }

    public void onPetSelected(Pet pet, String callingActivity) {
        Log.d(TAG, "onPetSelected: Selected a pet");

        ViewPetFragment fragment = new ViewPetFragment();
        Bundle args = new Bundle();
        args.putParcelable("PET", pet);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_activity_container, fragment);
        transaction.addToBackStack("View Pet"); // TODO: or (null) ????
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
    }

    public void openFilterFragment(View view) {
        Intent filter = new Intent(MainActivity.this, FilterActivity.class);
        startActivity(filter);

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Toast.makeText(this, "Successfully logged out.",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAllLostPetSelected(LostPet lostPet) {
        Log.d(TAG, "onAllLostPetSelected: selected a lost pet listview: " + lostPet.toString());
        ViewLostPetFragment fragment = new ViewLostPetFragment();
        Bundle args = new Bundle();
        args.putParcelable("LOSTPET", lostPet);
        args.putBoolean("isMyLost", false);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_activity_container, fragment);
        transaction.addToBackStack(null); // TODO: or (null) ????
        transaction.commit();
    }

    @Override
    public void onMyLostPetSelected(LostPet lostPet) {
        Log.d(TAG, "onMyLostPetSelected: selected a lost pet listview: " + lostPet.toString());
        ViewLostPetFragment fragment = new ViewLostPetFragment();
        Bundle args = new Bundle();
        args.putParcelable("LOSTPET", lostPet);
        args.putBoolean("isMyLost", true);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_activity_container, fragment);
        transaction.addToBackStack(null); // TODO: or (null) ????
        transaction.commit();
    }

    @Override
    public void onLoadMoreItems() {
        // TODO: Not sure what to do here?
    }
}