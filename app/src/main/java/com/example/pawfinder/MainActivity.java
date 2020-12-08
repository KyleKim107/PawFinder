package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pawfinder.Login.LoginActivity;
import com.example.pawfinder.Lost.AllLostPetsFragment;
import com.example.pawfinder.Lost.LostFragment;
import com.example.pawfinder.Lost.MyLostPetsFragment;
import com.example.pawfinder.Lost.ViewLostPetFragment;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.Models.PetfinderPet;
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
        getSupportFragmentManager().beginTransaction().add(R.id.main_activity_container,
                new PetsFragment(), "navigation_pets").addToBackStack("navigation_pets").commit();

//        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
//        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
//        getAnimals();

//        String url = "https://api.petfinder.com/v2/oauth2/token";
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = null;
                    String addToBS = "";
                    switch (item.getItemId()) {
                        case R.id.navigation_pets:
                            selected = new PetsFragment();
                            addToBS = "navigation_pets";
                            break;
                        case R.id.navigation_shelters:
                            selected = new SheltersFragment();
                            addToBS = "navigation_shelters";
                            break;
                        case R.id.navigation_lost:
                            selected = new LostFragment();
                            addToBS = "navigation_lost";
                            break;
                        case R.id.navigation_profile:
                            selected = new ProfileFragment();
                            addToBS = "navigation_profile";
                            break;
                    }
                    if(getSupportFragmentManager().findFragmentByTag(addToBS) != null) {
                        getSupportFragmentManager().popBackStackImmediate(addToBS, 0);
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.main_activity_container,
                                selected, addToBS).addToBackStack(addToBS).commit();
                    }
                    return true;
                }
            };

    public void onFavoritePetSelected(PetfinderPet pet) {
        Log.d(TAG, "onLostPetSelected: Selected a lost pet");

        ViewFavoritePetFragment fragment = new ViewFavoritePetFragment();
        Bundle args = new Bundle();
        args.putParcelable("LOSTPET", pet);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_activity_container, fragment);
        transaction.addToBackStack("View Favorite Pet"); // TODO: or (null) ????
        transaction.commit();
    }

    public void onPetSelected(PetfinderPet pet) {
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