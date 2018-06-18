package com.manan.dev.shineymca.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.SignupAct;
import com.manan.dev.shineymca.SingleClubActivity;

/**
 * Created by nisha on 6/15/2018.
 */

public class CalenderFragment extends android.support.v4.app.Fragment{

    private TextView textView, textView2;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_calender, container, false);
        textView = (TextView)mView.findViewById(R.id.calendar_club);
        textView2 = (TextView)mView.findViewById(R.id.calendar_register_second);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SingleClubActivity.class));
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), RegisterSecondActivity.class));
                startActivity(new Intent(getContext(), SignupAct.class));
            }
        });
        return mView;
    }
}
