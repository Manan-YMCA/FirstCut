package com.manan.dev.shineymca.AdminZone;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.Adapters.AutocompleteCoordinatorAdapter;
import com.manan.dev.shineymca.Adapters.CoordinatorAdapter;
import com.manan.dev.shineymca.Models.Coordinator;
import com.manan.dev.shineymca.Models.Event;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.Utility.Methods;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.Math.min;
import static java.util.Collections.sort;

public class AddEventActivity extends AppCompatActivity {

    ImageView mPoster, mEditPoster, mAddFaq;
    EditText mName, mDescription, mDate, mTime, mVenue, mSpecialNotes;
    AutoCompleteTextView mCoordinators;
    LinearLayout mCoordinatorView, mFaqView;
    Button mSubmit;
    DatabaseReference mCoordinatorDatabaseReference;
    ChildEventListener mCoordinatorChildEventListener;
    ArrayList<Coordinator> mCoordinatorsAll, mSelectedCorrdinators;
    AutocompleteCoordinatorAdapter coordinatorAdapter;
    final private int REQ_ID_POSTER = 101;
    Event mCurrEvent;
    Uri mPosterUri;
    long date, time;
    ArrayList<EditText> mFaqQuestion, mFaqAnswer;
    String mClubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initializeVariables();

        addOnClickListeners();

        setUpAutoCompleteTextView();

    }

    private void initializeVariables() {
        mPoster = (ImageView) findViewById(R.id.iv_main_poster);
        mEditPoster = (ImageView) findViewById(R.id.iv_edit);
        mAddFaq = (ImageView) findViewById(R.id.iv_add_faq);

        mName = (EditText) findViewById(R.id.et_event_name);
        mDescription = (EditText) findViewById(R.id.et_description);
        mDate = (EditText) findViewById(R.id.et_date);
        mTime = (EditText) findViewById(R.id.et_time);
        mVenue = (EditText) findViewById(R.id.et_venue);
        mSpecialNotes = (EditText) findViewById(R.id.et_special_notes);

        mCoordinators = (AutoCompleteTextView) findViewById(R.id.et_coordinators);

        mCoordinatorView = (LinearLayout) findViewById(R.id.ll_coordinators);
        mFaqView = (LinearLayout) findViewById(R.id.ll_faq);

        mSubmit = (Button) findViewById(R.id.bt_submit);

        mClubName = Methods.getEmailSharedPref(AddEventActivity.this);

        mCurrEvent = new Event();
        mFaqQuestion = new ArrayList<>();
        mFaqAnswer = new ArrayList<>();
        mCoordinatorsAll = new ArrayList<>();
        mSelectedCorrdinators = new ArrayList<>();
        mCoordinatorDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Coordinators");
    }


    private void addOnClickListeners() {
        //Select poster
        mEditPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Poster"), REQ_ID_POSTER);
            }
        });

        //select date
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.util.Calendar mcurrentDate = java.util.Calendar.getInstance();
                final int mYear = mcurrentDate.get(java.util.Calendar.YEAR);
                final int mMonth = mcurrentDate.get(java.util.Calendar.MONTH);
                final int mDay = mcurrentDate.get(java.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        java.util.Calendar myCalendar = java.util.Calendar.getInstance();
                        myCalendar.setTimeInMillis(0);
                        myCalendar.set(java.util.Calendar.YEAR, selectedyear);
                        myCalendar.set(java.util.Calendar.MONTH, selectedmonth);
                        myCalendar.set(java.util.Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                        mDate.setText(sdf.format(myCalendar.getTime()));

                        AddEventActivity.this.date = (myCalendar.getTimeInMillis());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.getDatePicker().setCalendarViewShown(true);
                mDatePicker.show();
            }
        });

        //select time
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar mcurrentDate = java.util.Calendar.getInstance();
                final int mHour = mcurrentDate.get(java.util.Calendar.HOUR_OF_DAY);
                final int mMinute = mcurrentDate.get(java.util.Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String displayTime = String.format(Locale.ENGLISH, "%02d:%02d", hourOfDay, minute);

                        mTime.setText(displayTime);
                        AddEventActivity.this.time = 1000L * (hourOfDay * 60 * 60 + minute * 60) - TimeZone.getDefault().getRawOffset();
                    }
                }, mHour, mMinute, true);

                mTimePicker.setTitle("Select date");
                mTimePicker.show();
            }
        });

        //add faq
        mAddFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFaqList();
            }
        });

        //click to add event to db
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                //check if no field is empty (make new function)
                //then upload poster whose uri is stored in mPosterUri (make new function)
                //then get uri from firebase storage//
                //save all the values to mCurrEvent variable (make new function)
                //upload value to database based on schema as follows
                //events -> clubname -> {push_id} -> mCurrEvent
                //clubname is stored in variable mCLubName

            }
        });
    }

    private void addFaqList() {
        @SuppressLint("InflateParams") LinearLayout view = (LinearLayout) LayoutInflater.from(AddEventActivity.this).inflate(R.layout.layout_faq, null, false);
        EditText mQuestion = (EditText) view.findViewById(R.id.et_faq_question);
        EditText mAnswer = (EditText) view.findViewById(R.id.et_faq_answer);

        mFaqQuestion.add(mQuestion);
        mFaqAnswer.add(mAnswer);
        mFaqView.addView(view);
    }

    //adding listeners to Autocomplete text view for adding coordinators.
    private void setUpAutoCompleteTextView() {

        coordinatorAdapter = new AutocompleteCoordinatorAdapter(AddEventActivity.this,
                R.layout.coordinator_item_view,
                R.id.coordinator_item_name,
                mCoordinatorsAll);

        mCoordinators.setAdapter(coordinatorAdapter);
        mCoordinators.setThreshold(1);
        mCoordinators.setInputType(InputType.TYPE_CLASS_TEXT);

        mCoordinators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCoordinators.showDropDown();
            }
        });

        mCoordinators.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Coordinator mCoordinator = mCoordinatorsAll.get(i);
                mCoordinators.setText("", false);

                for(int j = 0; j < mSelectedCorrdinators.size(); j++){
                    if(mCoordinator.getCoordPhone().equals(mSelectedCorrdinators.get(j).getCoordPhone())){
                        return;
                    }
                }

                mSelectedCorrdinators.add(mCoordinator);
                addCoordinatorToLayout(mCoordinator);
            }
        });
    }


    private void addCoordinatorToLayout(final Coordinator mCoordinator) {
        @SuppressLint("InflateParams") final LinearLayout view = (LinearLayout) LayoutInflater.from(AddEventActivity.this).inflate(R.layout.layout_coordinators, null, false);
        ((TextView) view.findViewById(R.id.tvUserName)).setText(mCoordinator.getCoordName());
        ((TextView) view.findViewById(R.id.tvUserId)).setText(mCoordinator.getCoordPhone());

        view.findViewById(R.id.removeCoordinator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                mCoordinatorView.removeView(view);

                for(int k = 0; k < mSelectedCorrdinators.size(); k++){
                    if(mCoordinator.getCoordPhone().equals(mSelectedCorrdinators.get(k).getCoordPhone())){
                        mSelectedCorrdinators.remove(k);
                        break;
                    }
                }

            }
        });

        mCoordinatorView.addView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCoordinatorsAll.clear();
        removeDatabaseListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addDatabaseListeners();
    }

    // remove listener when activity is paused.
    private void removeDatabaseListeners() {
        if(mCoordinatorChildEventListener != null){
            mCoordinatorDatabaseReference.removeEventListener(mCoordinatorChildEventListener);
            mCoordinatorChildEventListener = null;
        }
    }

    // add listener on databse when activity is resumed.
    private void addDatabaseListeners() {
        if(mCoordinatorChildEventListener == null){
            mCoordinatorChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    try {
                        if (dataSnapshot.getKey().equals(Methods.getEmailSharedPref(getApplicationContext()))) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("yatin", snapshot.getValue().toString());
                                Coordinator coordinator = snapshot.getValue(Coordinator.class);
                                coordinator.setCoordId(snapshot.getKey());
                                mCoordinatorsAll.add(coordinator);
                            }
                        }
                        updateList();
                    } catch (Exception e){
                        Log.d("yd", e.getMessage());
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    try {
                        if (dataSnapshot.getKey().equals(Methods.getEmailSharedPref(getApplicationContext()))) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("yatin", snapshot.getValue().toString());
                                Coordinator coordinator = snapshot.getValue(Coordinator.class);
                                coordinator.setCoordId(snapshot.getKey());
                                mCoordinatorsAll.add(coordinator);
                            }
                        }
                        updateList();
                    } catch (Exception e){
                        Log.d("yd", e.getMessage());
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.getKey().equals(Methods.getEmailSharedPref(getApplicationContext()))) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("removed", snapshot.getValue().toString());
                                Coordinator coordinator = snapshot.getValue(Coordinator.class);
                                coordinator.setCoordId(snapshot.getKey());
                                mCoordinatorsAll.remove(coordinator);
                            }
                        }
                        updateList();
                    } catch (Exception e){
                        Log.d("yd", e.getMessage());
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mCoordinatorDatabaseReference.addChildEventListener(mCoordinatorChildEventListener);
        }
    }

    private void updateList() {
        sort(mCoordinatorsAll);
        coordinatorAdapter = new AutocompleteCoordinatorAdapter(
                AddEventActivity.this,
                R.layout.coordinator_item_view,
                R.id.coordinator_item_name,
                mCoordinatorsAll
        );
        mCoordinators.setAdapter(coordinatorAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode) {
            case REQ_ID_POSTER:
                if (data != null && data.getData() != null) {
                    mPosterUri = data.getData();
                    try {
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mPosterUri);
                        float finalWidth = min(100, bitmap.getWidth());
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) finalWidth, (int) (finalWidth / bitmap.getWidth() * bitmap.getHeight()),
                                true);
                        mPoster.setImageBitmap(bitmap);
                        mPoster .setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }
}
