package com.manan.dev.shineymca.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.RegisterSecondActivity;

/**
 * Created by nisha on 6/15/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment{

    private Button mLogout;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        mLogout = (Button)mView.findViewById(R.id.profile_logout_btn);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        mView.findViewById(R.id.profile_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterSecondActivity.class));
            }
        });

        return mView;
    }
}
