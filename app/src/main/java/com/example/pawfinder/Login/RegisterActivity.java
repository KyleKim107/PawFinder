package com.example.pawfinder.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;
import android.content.SharedPreferences;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstName, mLastName, mEmail, mPassword;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Button mRegister = findViewById(R.id.btn_register);
        TextView mLoginTxt = findViewById(R.id.logintxt_register);
        mFirstName = findViewById(R.id.firstName_register);
        mLastName = findViewById(R.id.lastName_register);
        mEmail = findViewById(R.id.email_register);
        mPassword = findViewById(R.id.password_register);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstName = mFirstName.getText().toString().trim();
                final String lastName = mLastName.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                if (firstName.isEmpty()) {
                    mFirstName.setError("First name required.");
                    mFirstName.requestFocus();
                    return;
                }
                if (lastName.isEmpty()) {
                    mLastName.setError("Last name required.");
                    mLastName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    mEmail.setError("Email address required.");
                    mEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Enter a valid email address.");
                    mEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    mPassword.setError("Password required.");
                    mPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password should be at least 6 characters long.");
                    mPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(TAG, "createUserWithEmail:success");
                            // Set display name
                            FirebaseUser user = mAuth.getCurrentUser();
                            String displayName = firstName + " " + lastName;
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName).build();
                            user.updateProfile(profile)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");

                                                FirebaseUser user = mAuth.getCurrentUser();
                                                String id = user.getUid();
                                                Log.i(TAG, "onCompleted: Id: " + id);
                                                // TODO: Add id to user database here (Check if already exists first)
                                                String name = user.getDisplayName();
                                                Log.i(TAG, "onCompleted: Name: " + name);
                                                // TODO: Add name to user database here (Check if already exists first)
                                                String email = user.getEmail();
                                                Log.i(TAG, "onCompleted: Email: " + email);
                                                // TODO: Add email to user database here (Check if already exists first)

                                                reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("name", name);
                                                hashMap.put("email", email);
                                                final ArrayList<Pet> favorites = new ArrayList<>();
                                                hashMap.put("favorites", favorites);
                                                hashMap.put("lastPet", "0");

                                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // updates database in real time
                                                            Log.d(TAG, "onSuccess: New user has been added to database.");
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });

        mLoginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}