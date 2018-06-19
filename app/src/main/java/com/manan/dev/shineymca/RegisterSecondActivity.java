package com.manan.dev.shineymca;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.Models.User;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

public class RegisterSecondActivity extends AppCompatActivity {

    private TextInputLayout mEmail, mPassword, mUsername, mName, mYear, mBranch;
    private Button mRegisterBtn;
    private FirebaseAuth mAuth;
    private LoginButton mFacebookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        mName = (TextInputLayout) findViewById(R.id.register2_name);
        mEmail = (TextInputLayout) findViewById(R.id.register2_email);
        mUsername = (TextInputLayout) findViewById(R.id.register2_username);
        mPassword = (TextInputLayout) findViewById(R.id.register2_password);
        mYear = (TextInputLayout)findViewById(R.id.register2_year);
        mBranch = (TextInputLayout)findViewById(R.id.register2_branch);
        mRegisterBtn = (Button) findViewById(R.id.register2_register_btn);
        mFacebookBtn = (LoginButton)findViewById(R.id.register2_facebook_login_button);

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "User: " + mAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
        if (mAuth.getCurrentUser() == null) {
            mRegisterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userName = mName.getEditText().getText().toString();
                    String userUsername = mUsername.getEditText().getText().toString();
                    String userYear = mYear.getEditText().getText().toString();
                    String userBranch = mBranch.getEditText().getText().toString();
                    String userEmail = mEmail.getEditText().getText().toString();
                    String password = mPassword.getEditText().getText().toString();
                    final HashMap<String, String> M = new HashMap<String, String>();
                    M.put("userName", userName);
                    M.put("userUsername", userUsername);
                    M.put("userEmail", userEmail);
                    M.put("userYear", userYear);
                    M.put("userBranch", userBranch);
                    M.put("userImage", "default");

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, password)
                            .addOnCompleteListener(RegisterSecondActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String uid = mAuth.getCurrentUser().getUid().toString();
                                        DatabaseReference mRegRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                        mRegRef.setValue(M).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegisterSecondActivity.this, "Deatils databased", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(RegisterSecondActivity.this, "Some error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegisterSecondActivity.this, "nhin ho rha ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            });
        } else {
            final FirebaseUser user = mAuth.getCurrentUser();
            final String uid = mAuth.getCurrentUser().getUid().toString();
            final String name = user.getDisplayName();
            mName.getEditText().setText(name);
            mEmail.setVisibility(View.GONE);
            mPassword.setVisibility(View.GONE);
            mFacebookBtn.setVisibility(View.GONE);
            mRegisterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userName = mName.getEditText().getText().toString();
                    String userUsername = mUsername.getEditText().getText().toString();
                    String userYear = mYear.getEditText().getText().toString();
                    String userBranch = mBranch.getEditText().getText().toString();
                    HashMap<String, String> M = new HashMap<String, String>();
                    M.put("userName", userName);
                    M.put("userUsername", userUsername);
                    M.put("userEmail", user.getEmail());
                    M.put("userYear", userYear);
                    M.put("userBranch", userBranch);
                    M.put("userImage", "default");
                    DatabaseReference mRegRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    mRegRef.setValue(M).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterSecondActivity.this, "Deatils databased", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterSecondActivity.this, BottomNavigator.class));
                                finish();
                            }else{
                                Toast.makeText(RegisterSecondActivity.this, "Some error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
