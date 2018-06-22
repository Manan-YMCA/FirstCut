package com.manan.dev.shineymca.AdminZone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.manan.dev.shineymca.BottomNavigator;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.Utility.Methods;

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
                startActivity(new Intent(AdminHomeActivity.this, AddCoordinator.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            FirebaseAuth.getInstance().signOut();
            Methods.callSharedPreference(getApplicationContext(), "default");
            startActivity(new Intent(AdminHomeActivity.this, BottomNavigator.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
