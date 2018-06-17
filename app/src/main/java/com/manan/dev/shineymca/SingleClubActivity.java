package com.manan.dev.shineymca;

import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleClubActivity extends AppCompatActivity {

    private RecyclerView mRoundCircles;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_club);
        mRoundCircles = (RecyclerView)findViewById(R.id.club_round_circles);
        mRoundCircles.setHasFixedSize(true);
        mRoundCircles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        db = FirebaseDatabase.getInstance().getReference().child("Clubs").child("Rounds").child("Round1");
        Log.d("hello,", db.toString());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(SingleClubActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Log.d("ghab",dataSnapshot.child("status").getValue().toString());
                Toast.makeText(SingleClubActivity.this, "hello" + dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SingleClubActivity.this, "Not Working", Toast.LENGTH_SHORT).show();
                Log.d("akki", databaseError.toString());

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RoundStatus, RoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoundStatus, RoundViewHolder>(
                RoundStatus.class,
                R.layout.round_circle_layout,
                RoundViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("Clubs").child("Rounds")
        ) {
            @Override
            protected void populateViewHolder(RoundViewHolder viewHolder, RoundStatus model, int position) {
//                viewHolder.setCircle(model.getStatus());
                Toast.makeText(SingleClubActivity.this, "STATUS: "+model.getStatus(), Toast.LENGTH_SHORT).show();
            }
        };

        mRoundCircles.setAdapter(firebaseRecyclerAdapter);

    }

    public static class RoundViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public RoundViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
//
//        ImageView i = (ImageView)mView.findViewById(R.id.round_circle_image);
//
//        public void setCircle(String status){
//            switch (status){
//                case "Selected":
//                    Picasso.get().load(uri).into(i);
//            }
//        }

    }
}
