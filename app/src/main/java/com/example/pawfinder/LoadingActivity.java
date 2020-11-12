package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingActivity extends AppCompatActivity {

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    ImageView paw_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        paw_image = (ImageView) findViewById(R.id.paw_image_loading);

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
            }
        };
        handler.postDelayed(r, 1500);
    }
}