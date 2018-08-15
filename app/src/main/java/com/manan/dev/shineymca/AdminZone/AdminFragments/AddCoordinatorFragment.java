package com.manan.dev.shineymca.AdminZone.AdminFragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.shineymca.AdminZone.AddCoordinator;
import com.manan.dev.shineymca.Models.Coordinator;
import com.manan.dev.shineymca.R;
import com.manan.dev.shineymca.Utility.Methods;

import java.util.regex.Pattern;

public class AddCoordinatorFragment extends DialogFragment{

    Context mContext;
    EditText mCoordName, mCoordPhone;
    TextView mSubmit;
    ProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_coordinator_fragment, container, false);

        mCoordName = (EditText) rootView.findViewById(R.id.et_coord_name);
        mCoordPhone = (EditText) rootView.findViewById(R.id.et_coord_phone);

        mSubmit = (TextView) rootView.findViewById(R.id.tv_add);
        dialog=new ProgressDialog(rootView.getContext());


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Loading");
                dialog.setMessage("Sabr rakhe");
                dialog.show();
                if(checker()){
                    Coordinator mCoordinator = new Coordinator(mCoordName.getText().toString(), mCoordPhone.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Coordinators").child(Methods.getEmailSharedPref(mContext))
                            .push().setValue(mCoordinator).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                getDialog().dismiss();

                                Toast.makeText(mContext, "Coordinator added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Unable to add Coordinator. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return rootView;
    }

    private boolean checker() {
        if(mCoordName.getText().toString().equals("") || mCoordName.getText().toString().trim().equals("")){
            mCoordName.setError("Name cannot be empty");
            return false;
        }
        if(mCoordPhone.getText().toString().equals("") || mCoordPhone.getText().toString().trim().equals("")){
            mCoordPhone.setError("Phone cannot be empty");
            return false;
        }
        if(!Patterns.PHONE.matcher(mCoordPhone.getText().toString()).matches()){
            mCoordPhone.setError("Invalid Phone Number");
            return false;
        }
        if(mCoordPhone.getText().toString().length() != 10){
            mCoordPhone.setError("Invalid Phone Number");
            return false;
        }
        return true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
