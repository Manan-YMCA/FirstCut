package com.manan.dev.shineymca.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manan.dev.shineymca.R;

/**
 * Created by nisha on 6/15/2018.
 */

public class CalenderFragment extends android.support.v4.app.Fragment{
    public static CalenderFragment newInstance() {
        CalenderFragment fragment = new CalenderFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }
}
