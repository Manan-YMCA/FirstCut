package com.manan.dev.shineymca.AdminZone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.manan.dev.shineymca.R;

public class AdminHomeActivity extends AppCompatActivity {

    TextView mAddDescription, mAddNewEvent, mViewAttendees, mResult, mCoordinators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        initializeVariables();

        setListeners();

    }

    //giving reference to all the variables
    private void initializeVariables() {
        mAddDescription = (TextView) findViewById(R.id.tv_add_description);
        mAddNewEvent = (TextView) findViewById(R.id.tv_add_event);
        mViewAttendees = (TextView) findViewById(R.id.tv_view_registered);
        mResult = (TextView) findViewById(R.id.tv_result);
        mCoordinators = (TextView) findViewById(R.id.tv_add_coordinators);
    }

    //adding listeners to all the TextViews
    private void setListeners() {

        //open a dialog box to add/ update the description of a club
        mAddDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //open activity to add new event
        mAddNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, AddEventActivity.class));
            }
        });

        //open activity to add view coordinators
        //in new activity add coordinators using dialog fragment
        mCoordinators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //open activity to view th list of attendees
        mViewAttendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //open activity to perform all activities related to results
        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
