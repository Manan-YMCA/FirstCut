package com.manan.dev.shineymca;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class RegisterSecondActivity extends AppCompatActivity {

    private TextInputLayout username;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        username = (TextInputLayout)findViewById(R.id.register2_username);
        mSubmit = (Button)findViewById(R.id.register2_submit);

        String s = username.getEditText().getText().toString();
//        FirebaseDatabase.getInstance().getReference().child("Usernames").

    }
}
