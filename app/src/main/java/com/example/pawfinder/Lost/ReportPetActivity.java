package com.example.pawfinder.Lost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawfinder.R;

import static android.content.ContentValues.TAG;

public class ReportPetActivity extends AppCompatActivity {

    private static final String TAG = "ReportPetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_lost_pet);
        Log.d(TAG, "onCreate: Started.");

        // Set up back arrow for navigating back to Lost Fragment
        ImageView backArrow_reportPet = findViewById(R.id.backArrow_reportPet);
        backArrow_reportPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to Lost Fragment");
                finish();
            }
        });

        // Set up Report Missing Pet Button
        Button reportMissingPetButton = findViewById(R.id.reportMissingPetButton);
        reportMissingPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating to Report Missing Pet Fragment");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = ReportMissingPetFragment.newInstance();
                transaction.replace(R.id.relLayout1,fragment).commit();
                transaction.addToBackStack(null);
            }
        });

        // Set up Report Found Pet Button
        Button reportFoundPetButton = findViewById(R.id.reportFoundPetButton);
        reportFoundPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating to Report Found Pet Fragment");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = ReportFoundPetFragment.newInstance();
                transaction.replace(R.id.relLayout1,fragment).commit();
                transaction.addToBackStack(null);
            }
        });
    }
}