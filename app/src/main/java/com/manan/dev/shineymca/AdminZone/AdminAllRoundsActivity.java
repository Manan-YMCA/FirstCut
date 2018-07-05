package com.manan.dev.shineymca.AdminZone;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.Fragments.HomeFragment;
import com.manan.dev.shineymca.Models.Club;
import com.manan.dev.shineymca.Models.Round;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.SingleClubActivity;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class AdminAllRoundsActivity extends AppCompatActivity {

    private ImageView mAddRound;
    private RecyclerView mRoundList;
    private String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rounds_all);

        mRoundList = (RecyclerView)findViewById(R.id.admin_rounds_all_rounds_list);
        mRoundList.setHasFixedSize(true);
        mRoundList.setLayoutManager(new LinearLayoutManager(this));

        mAddRound = (ImageView)findViewById(R.id.admin_rounds_all_add_round);
        mAddRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminAllRoundsActivity.this, AddRoundActivity.class)
                        .putExtra("roundNumber", 0));
            }
        });

        clubName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Round, RoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Round, RoundViewHolder>(
                Round.class,
                R.layout.admin_single_round_in_admin_rounds_all_card,
                RoundViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("Clubs").child(clubName).child("Rounds")
        ) {
            @Override
            protected void populateViewHolder(RoundViewHolder viewHolder, final Round model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setNumber(model.getNumber());
                viewHolder.mView.findViewById(R.id.rounds_all_specific_round_intent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(AdminAllRoundsActivity.this, AddRoundActivity.class)
                                .putExtra("roundNumber", model.getNumber()));
                    }
                });
            }
        };
        mRoundList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RoundViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public RoundViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView textView = mView.findViewById(R.id.rounds_all_round_name);
            textView.setText(name);
        }

        public void setNumber(long number){
            TextView textView = mView.findViewById(R.id.rounds_all_round_number);
            textView.setText("Round " + String.valueOf(number));
        }
    }
}
