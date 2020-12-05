package com.example.pawfinder.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingActivity extends AppCompatActivity {

    private FirebaseUser user;
    ImageView paw_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        paw_image = findViewById(R.id.paw_image_loading);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if (user != null) {
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(LoadingActivity.this, paw_image, ViewCompat.getTransitionName(paw_image));
                    Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent,activityOptionsCompat.toBundle());
                }
                finish();
            }
        };
        handler.postDelayed(r, 1500);
    }
}