package com.manan.dev.shineymca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.manan.dev.shineymca.AdminZone.AdminLoginActivity;
import com.manan.dev.shineymca.Utility.Methods;

public class RegisterFirstActivity extends AppCompatActivity{

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private LoginButton loginButton;
    private ProgressDialog mProgress;
    private TextView mAdminZone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register_first);
        Context context = this;
        callSharedPreference(this);
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        loginButton = (LoginButton)findViewById(R.id.register1_facebook_login_button);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading");
        mProgress.setMessage("Please wait to enter further details");
        mProgress.setCanceledOnTouchOutside(false);
        mAdminZone = (TextView) findViewById(R.id.tv_admin_zone);
        mAdminZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(RegisterFirstActivity.this, AdminLoginActivity.class), 100);
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

    private void callSharedPreference(Context context) {
        Methods.callSharedPreference(context, "default");
    }

    private void handleFacebookAccessToken(AccessToken token) {
        mProgress.show();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(RegisterFirstActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgress.dismiss();
                            if(task.getResult().getAdditionalUserInfo().isNewUser()) {
                                startActivity(new Intent(RegisterFirstActivity.this, RegisterSecondActivity.class));
                                finish();
                            } else {
                                Methods.callSharedPreference(getApplicationContext(), task.getResult().getUser().getEmail());
                                startActivity(new Intent(RegisterFirstActivity.this, BottomNavigator.class));
                                finish();
                            }
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
        if(requestCode == 100){
            if(resultCode == 101){
                finish();
            }
        }
    }

}