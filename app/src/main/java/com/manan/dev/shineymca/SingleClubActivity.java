package com.manan.dev.shineymca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.Models.RoundStatus;

public class SingleClubActivity extends AppCompatActivity {

    private RecyclerView mRoundCircles;
    private String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_club);
        mRoundCircles = (RecyclerView)findViewById(R.id.club_round_circles);
        mRoundCircles.setHasFixedSize(true);
        mRoundCircles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        clubName = getIntent().getStringExtra("clubName");
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
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SingleClubActivity.this, RoundActivity.class)
                                .putExtra("clubName", clubName).putExtra("round", String.valueOf(position+1)));
                    }
                });
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
    }
}
