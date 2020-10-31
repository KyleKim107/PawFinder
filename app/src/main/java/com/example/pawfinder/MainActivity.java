package com.example.pawfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

//    String displayName, firstName, lastName, email, password, id;

    // Firebase
//    FirebaseAuth mAuth;
//    FirebaseUser currentUser;
//    DatabaseReference reference;

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openFilterFragment(View view) {
        openFragment(FilterFragment.newInstance());
    }

//    public void createUI(FirebaseUser user) {
//        if (displayName != null) {
//            // User has just signed up - update display name
//            if (user != null) {
//                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
//                        .setDisplayName(displayName)
//                        .build();
//            }
//        } else {
//            displayName = user.getDisplayName();
//        }
//        openFragment(PetsFragment.newInstance());
//        hideBottomBar(false);
//    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Successfully logged out.",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}