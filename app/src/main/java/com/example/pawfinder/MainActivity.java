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
import android.widget.Toast;

import com.example.pawfinder.Login.LoginActivity;
import com.example.pawfinder.Lost.LostFragment;
import com.example.pawfinder.Lost.ViewLostPetFragment;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.Pets.FilterActivity;
import com.example.pawfinder.Pets.PetsFragment;
import com.example.pawfinder.Profile.ProfileFragment;
import com.example.pawfinder.Profile.ViewFavoritePetFragment;
import com.example.pawfinder.Shelters.SheltersFragment;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf=8");
    OkHttpClient client = new OkHttpClient();

    FrameLayout mFrameLayout;
    View mFragment;

    String post(String url, String json) throws IOException{


        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_container,
                new PetsFragment()).commit();

        mFrameLayout = findViewById(R.id.main_activity_container);
//        mFragment = findViewById(R.id.nav_host_fragment);

        String url = "https://api.petfinder.com/v2/oauth2/token";




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

    public void onLostPetSelected(LostPet lostPet, String callingActivity) {
        Log.d(TAG, "onLostPetSelected: Selected a lost pet");

        ViewLostPetFragment fragment = new ViewLostPetFragment();
        Bundle args = new Bundle();
        args.putParcelable("LOSTPET", lostPet);
        args.putString("MAINACTIVITY", callingActivity);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_container, fragment);
        transaction.addToBackStack("View Lost Pet"); // TODO: or (null) ????
        transaction.commit();
    }

    public void onFavoritePetSelected(Pet pet, String callingActivity) {
        Log.d(TAG, "onLostPetSelected: Selected a lost pet");

        ViewFavoritePetFragment fragment = new ViewFavoritePetFragment();
        Bundle args = new Bundle();
        args.putParcelable("LOSTPET", pet);
        args.putString("MAINACTIVITY", callingActivity);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_container, fragment);
        transaction.addToBackStack("View Favorite Pet"); // TODO: or (null) ????
        transaction.commit();
    }

    public void hideLayout() {
        Log.d(TAG, "hideLayout: Hiding Layout");
//        mFragment.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    public void showLayout() {
        Log.d(TAG, "showLayout: Showing Layout");
//        mFragment.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFrameLayout.getVisibility() == View.VISIBLE) {
            showLayout();
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
}