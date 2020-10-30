package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    RelativeLayout pets_container;
    ImageButton filterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      pets_container = findViewById(R.id.pets_container);

//        final ImageView filter_icon = (ImageView)findViewById(R.id.filter_icon);
//        filter_icon.setClickable(true);
        filterButton = findViewById(R.id.filter);
        filterButton.setClickable(true);


        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_pets);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.nav_pets:
                        pets_container.setVisibility(View.VISIBLE);
                        selectedFragment = new PetsFragment();
                        break;
                    case R.id.nav_shelters:
                        pets_container.setVisibility(View.INVISIBLE);
                        selectedFragment = new SheltersFragment();
                        break;
                    case R.id.nav_lost:
                        pets_container.setVisibility(View.GONE);
                        selectedFragment = new LostFragment();
                        break;
                    case R.id.nav_profile:
                        pets_container.setVisibility(View.GONE);
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new PetsFragment()).addToBackStack(null).commit();


//        filterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pets_container.setVisibility(View.INVISIBLE);
//                Intent intent = new Intent(this , FilterFragment.class);
//                startActivity(intent);
//            }
//        });
    }

    public void openFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FilterFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openFilterFragment(View view) {
//        openFragment(FilterFragment.newInstance());
    }

}