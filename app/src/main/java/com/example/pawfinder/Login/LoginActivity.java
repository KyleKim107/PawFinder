package com.example.pawfinder.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import static androidx.constraintlayout.widget.Constraints.TAG;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    ConstraintLayout registerCard;

    private EditText mEmail, mPassword;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private DatabaseReference reference;

    CallbackManager mCallbackManager;

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerCard = (ConstraintLayout) findViewById(R.id.registercard);
        registerCard.setVisibility(View.GONE);

        // Transition
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                registerCard.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(r, 400);

        // Firebase setup
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mCallbackManager = CallbackManager.Factory.create();

        Button mLogin = findViewById(R.id.btn_login);
        TextView mRegisterTxt = findViewById(R.id.registertxt_login);
        mEmail = findViewById(R.id.email_login);
        mPassword = findViewById(R.id.password_login);
        Button mGoogle = findViewById(R.id.google_login);
        Button mFacebook = findViewById(R.id.facebook_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    mEmail.setError("Email address required.");
                    mEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    mPassword.setError("Password required.");
                    mPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(TAG, "signInWithEmail:success");
                        }
                    }
                });
            }
        });

        mRegisterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        // GOOGLE AUTHENTICATION
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        // FACEBOOK AUTHENTICATION
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Sign in completed
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        //handling the token for Firebase Auth
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }
                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }
                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
            }
        });
    }

    /*
     GOOGLE AUTHENTICATION:
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("LoginActivity", "Google sign in failed", e);
                    // ...
                }
            } else {
                Log.w("LoginActivity", task.getException().toString());
            }
        } else {
            //If not request code is RC_SIGN_IN it must be facebook
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(final String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d("LoginActivity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String id = user.getUid();
                            Log.i(TAG, "onCompleted: Id: " + id);
                            String name = user.getDisplayName();
                            Log.i(TAG, "onCompleted: Name: " + name);
                            String email = user.getEmail();
                            Log.i(TAG, "onCompleted: Email: " + email);
                            addToDatabase(id,name, email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    /*
     FACEBOOK AUTHENTICATION:
     */
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "onComplete: login completed with user: " + user.getDisplayName());

                            String id = user.getUid();
                            Log.i(TAG, "onCompleted: Id: " + id);
                            String name = user.getDisplayName();
                            Log.i(TAG, "onCompleted: Name: " + name);
                            String email = user.getEmail();
                            Log.i(TAG, "onCompleted: Email: " + email);
                            addToDatabase(id,name, email);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // TODO: Add id to user database here --> consider moving method to FirebaseDatabaseHelper.java
    private void addToDatabase(String id, final String name, final String email) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    // user exists - do nothing?
                } else {
                    //user does not exist - add to database
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", name);
                    hashMap.put("email", email);
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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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