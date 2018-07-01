package com.manan.dev.shineymca.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.Models.Club;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.SingleClubActivity;

/**
 * Created by nisha on 6/15/2018.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private RecyclerView mClubList;
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mClubList = (RecyclerView)mView.findViewById(R.id.home_club_list);
        mClubList.setHasFixedSize(true);
        mClubList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Club, ClubViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Club, ClubViewHolder>(
                Club.class,
                R.layout.single_club_layout,
                ClubViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("Clubs")
        ) {
            @Override
            protected void populateViewHolder(ClubViewHolder viewHolder, Club model, int position) {
                Toast.makeText(getContext(), "Club: "+ model.getClubName(), Toast.LENGTH_SHORT).show();
                viewHolder.setName(model.getClubName());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), SingleClubActivity.class));
                    }
                });
            }

        };
        mClubList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ClubViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        public void setName(String name){
            Toast.makeText(mView.getContext(), "Entered", Toast.LENGTH_SHORT).show();
            TextView textView = mView.findViewById(R.id.single_club_name);
            textView.setText(name);
        }
    }
}
