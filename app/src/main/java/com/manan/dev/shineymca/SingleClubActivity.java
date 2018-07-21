package com.manan.dev.shineymca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.shineymca.Models.RoundStatus;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SingleClubActivity extends AppCompatActivity {

    private RecyclerView mRoundCircles;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private String clubName;
    private TextView mRound;
    private TextView mRname;
    private View mView;
    private Button mreg_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_club);
        mAuth = FirebaseAuth.getInstance();

        mRound=(TextView)mView.findViewById(R.id.round_number);
        mRname=(TextView)mView.findViewById(R.id.round_name);
        mRoundCircles = (RecyclerView)findViewById(R.id.club_round_circles);
        mRoundCircles.setHasFixedSize(true);
        mRoundCircles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        clubName = getIntent().getStringExtra("clubName");
        mreg_btn=(Button)mView.findViewById(R.id.reg);
        String uid = mAuth.getCurrentUser().getUid().toString();
        mRef=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        ValueEventListener valueEventListener = mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String round = dataSnapshot.child("number").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();

                mRound.setText(round);
                mRname.setText(name);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "NETWORK ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RoundStatus, RoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoundStatus, RoundViewHolder>(
                RoundStatus.class,
                R.layout.layout_round,
                RoundViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("Clubs").child(clubName).child("Rounds")

        ) {
            @Override
            protected void populateViewHolder(RoundViewHolder viewHolder, RoundStatus model, final int position) {


                Toast.makeText(SingleClubActivity.this, "STATUS: "+model.getStatus(), Toast.LENGTH_SHORT).show();
            }
        };

        mRoundCircles.setAdapter(firebaseRecyclerAdapter);

    }

    public void onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.activity_single_club, container, false);






    }

    public static class RoundViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public RoundViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}
