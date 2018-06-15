package com.manan.dev.shineymca;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.manan.dev.shineymca.Fragments.AboutFragment;
import com.manan.dev.shineymca.Fragments.CalenderFragment;
import com.manan.dev.shineymca.Fragments.HomeFragment;
import com.manan.dev.shineymca.Fragments.NotificationFragment;
import com.manan.dev.shineymca.Fragments.ProfileFragment;

public class BottomNavigator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigator);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                     android.support.v4.app.Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            //switch to selected fragment
                            case R.id.action_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_notifications:
                                selectedFragment = NotificationFragment.newInstance();
                                break;
                            case R.id.action_calender:
                                selectedFragment = CalenderFragment.newInstance();
                                break;
                            case R.id.action_profile:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                            case R.id.action_about:
                                selectedFragment = AboutFragment.newInstance();
                                break;
                        }
                       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        //Manually displaying the home fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

    }
}
