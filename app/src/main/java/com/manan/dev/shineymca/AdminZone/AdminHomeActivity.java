package com.manan.dev.shineymca.AdminZone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.BottomNavigator;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.Utility.Methods;

public class AdminHomeActivity extends AppCompatActivity {

    TextView mAddDescription, mAddNewEvent, mViewAttendees, mResult, mCoordinators;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String clubName;
    EditText input1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        mAuth = FirebaseAuth.getInstance();
        clubName = mAuth.getCurrentUser().getDisplayName();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Clubs").child(clubName);
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
                input1 = new EditText(AdminHomeActivity.this);

                //flag = false;
                final AlertDialog.Builder alert = new AlertDialog.Builder(AdminHomeActivity.this);
                final LinearLayout layout = new LinearLayout(AdminHomeActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.removeAllViews();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(40, 0, 40, 0);
                final TextView status = new TextView(AdminHomeActivity.this);
                status.setLayoutParams(layoutParams);


                status.setText("Status");
                input1.setLayoutParams(layoutParams);


                input1.setText(mAddDescription.getText());
                layout.addView(status);

                layout.addView(input1);
                alert.setTitle("Add Description").setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                /*
                                 * User clicked ok
                                 */

                                boolean checker = checkDetails();
                                if (checker) {
                                    status.setText("");
                                    mUserDatabase.child("Description").setValue(input1.getText().toString());
                                    mAddDescription.setText(input1.getText().toString());
                                    layout.removeAllViews();
                                } else {
                                    Toast.makeText(AdminHomeActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                 /*
                 * User clicked cancel
                 */
                                layout.removeAllViews();
                            }
                        });

                alert.show();


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

    private boolean checkDetails() {

        if (!isNetworkAvailable()) {
            Toast.makeText(AdminHomeActivity.this, "No Internet Access", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (input1.getText().toString().equals("")) {
            input1.setError("Enter a Name");
            return false;
        }
        return true;
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
