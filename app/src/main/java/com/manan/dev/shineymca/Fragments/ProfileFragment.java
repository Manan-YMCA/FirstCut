package com.manan.dev.shineymca.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manan.dev.shineymca.BottomNavigator;
import com.manan.dev.shineymca.Methods;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.RegisterSecondActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by nisha on 6/15/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment{

    private Button mLogout;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        mLogout = (Button)mView.findViewById(R.id.profile_logout_btn);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Methods.callSharedPreference(getApplicationContext(), "default");
                startActivity(new Intent(getContext(), BottomNavigator.class));
            }
        });

        return mView;
    }
}
