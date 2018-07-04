package com.manan.dev.shineymca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RoundActivity extends AppCompatActivity {

    private TextView mClubName, mRoundNumber;
    private String clubName, round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        mClubName = (TextView)findViewById(R.id.round_club_name);
        mRoundNumber = (TextView)findViewById(R.id.round_club_round_number);
        clubName = getIntent().getStringExtra("clubName");
        round = getIntent().getStringExtra("round");
        Toast.makeText(this, "ROUND NUMBER: " + round, Toast.LENGTH_SHORT).show();
        mClubName.setText(clubName);
        mRoundNumber.setText(round);

    }
}
