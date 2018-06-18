package com.manan.dev.shineymca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegisterFirstActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private LoginResult loginResul;
    private TextView textView;
    private LoginButton loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register_first);
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        textView = (TextView)findViewById(R.id.login_text);
        loginButton = (LoginButton)findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email","public_profile","user_friends");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        loginButton.setVisibility(View.GONE);
                        loginResul = loginResult;
                        textView.setText("Logged in!");
//                        Thread thread = new Thread(RegisterFirstActivity.this);
//                        thread.start();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(),"Login failed! Please try again.",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),"Login failed! Please try again.",Toast.LENGTH_SHORT).show();
                    }
                });
//        loginButton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        LoginManager.getInstance().logInWithReadPermissions(RegisterFirstActivity.this,
//                                Arrays.asList("email","public_profile","user_friends"));
//                    }
//                }
//        );
    }
//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Profile profile = Profile.getCurrentProfile();
//                            String fbid = profile.getId();
//                            mAuth = FirebaseAuth.getInstance();
//                            FirebaseUser currentUser = mAuth.getCurrentUser();
//                            //Store FirebaseUid k = new Store (FirebaseUid(currentUser.getUid()));
//                            databaseReference = FirebaseDatabase.getInstance().getReference();
//                            //databaseReference.child(fbid).setValue(k);
//                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
//                            handler.sendEmptyMessage(0);
//                            finish();
//                            startActivity(new Intent(RegisterFirstActivity.this,MainActivity.class));
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(getApplicationContext(),"Login failed! Please try again.",Toast.LENGTH_SHORT).show();
//                            finish();
//                            startActivity(new Intent(RegisterFirstActivity.this,MainActivity.class));
//                        }
//                    }
//                });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


//    @Override
//    public void run() {
//        handleFacebookAccessToken(loginResul.getAccessToken());
//
//    }
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//        }
//    };
}
