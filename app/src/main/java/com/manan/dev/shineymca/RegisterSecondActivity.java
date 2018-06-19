package com.manan.dev.shineymca;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterSecondActivity extends AppCompatActivity {

    private TextInputLayout mEmail, mPassword, mUsername;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        mEmail = (TextInputLayout) findViewById(R.id.register_email);
        mUsername = (TextInputLayout) findViewById(R.id.register_username);
        mPassword = (TextInputLayout) findViewById(R.id.register_password);
        mRegister = (Button) findViewById(R.id.register_register);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                final String username = mUsername.getEditText().getText().toString();

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterSecondActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    HashMap<String, String> M = new HashMap<>();
                                    M.put("email", mEmail.getEditText().getText().toString());
                                    M.put("username", mUsername.getEditText().getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).setValue(M)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        FirebaseDatabase.getInstance().getReference().child("Usernames")
                                                                .child(username).setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(RegisterSecondActivity.this, "Logout now", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }else{
                                                        FirebaseAuth.getInstance().getCurrentUser().delete();
                                                        Toast.makeText(RegisterSecondActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                }
                                else {
                                    Toast.makeText(RegisterSecondActivity.this, "nhin ho rha ", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

    }
}
