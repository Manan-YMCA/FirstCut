package com.manan.dev.shineymca.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.shineymca.BottomNavigator;
import com.manan.dev.shineymca.Utility.Methods;
import com.manan.dev.shineymca.R;
import com.squareup.picasso.Picasso;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by nisha on 6/15/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment{

    private Button mLogout;
    private View mView;
    private ImageView mProfilePic;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        mLogout = (Button)mView.findViewById(R.id.profile_logout_btn);
        mProfilePic = (ImageView)mView.findViewById(R.id.profile_picture);
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid().toString();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.child("userImage").getValue().toString();
                Toast.makeText(getApplicationContext(), "image" + s, Toast.LENGTH_SHORT).show();
                Picasso.get().load(s).into(mProfilePic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Methods.callSharedPreference(getApplicationContext(), "default");
                finishActivity activity = (finishActivity) mContext;
                activity.finisher(true);
                startActivity(new Intent(getContext(), BottomNavigator.class));
            }
        });

        return mView;
    }

    public interface finishActivity {
        void finisher(boolean value);
    }
}
