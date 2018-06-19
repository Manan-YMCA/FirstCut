package com.manan.dev.shineymca;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFirstActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private LoginButton loginButton;
    private Button mSignInBtn, mSignUpBtn;
    private TextInputLayout mSignInEmail, mSignInPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register_first);
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        loginButton = (LoginButton)findViewById(R.id.register1_facebook_login_button);
        mSignInBtn = (Button)findViewById(R.id.register1_sign_in_btn);
        mSignUpBtn = (Button) findViewById(R.id.register1_sign_up_btn);
        mSignInEmail = (TextInputLayout) findViewById(R.id.register1_sign_in_email);
        mSignInPassword = (TextInputLayout) findViewById(R.id.register1_sign_in_password);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mSignInEmail.getEditText().getText().toString();
                String password = mSignInPassword.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterFirstActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(RegisterFirstActivity.this, BottomNavigator.class));
                                } else {
                                    Toast.makeText(RegisterFirstActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterFirstActivity.this, RegisterSecondActivity.class));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        loginButton.setVisibility(View.GONE);
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        handleFacebookAccessToken(loginResult.getAccessToken());

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
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(RegisterFirstActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterFirstActivity.this, RegisterSecondActivity.class));
                            finish();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("userd", user.toString());
                            Log.d("emaild", user.getDisplayName());
                            Log.d("emaild", user.getEmail());
                            Log.d("emaild", user.getPhotoUrl().toString());
                            startActivity(new Intent(RegisterFirstActivity.this, RegisterSecondActivity.class));
                        } else {
                            Toast.makeText(RegisterFirstActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
