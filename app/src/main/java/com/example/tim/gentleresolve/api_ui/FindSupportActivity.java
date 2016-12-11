package com.example.tim.gentleresolve.api_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindSupportActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.passionInput) EditText mInterest;
    @Bind(R.id.zipInput) EditText mZip;
    @Bind(R.id.radiusInput) EditText mRadius;
    @Bind(R.id.findMeetupsButton) Button mFindMeetupsButton;

    private DatabaseReference mSearchedMeetupReference;
    private DatabaseReference mSearchedZip;
    private DatabaseReference mSearchedRadius;
    private DatabaseReference mSearchedMeetup;

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedMeetup = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_MEETUPS);

        mSearchedMeetupReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_MEETUPS)
                .child(Constants.FIREBASE_CHILD_PASSION);
//
//        mSearchedZip = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_MEETUPS)
//                .child(Constants.FIREBASE_CHILD_ZIP);
//
//        mSearchedRadius = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_MEETUPS)
//                .child(Constants.FIREBASE_CHILD_RADIUS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_support);
        ButterKnife.bind(this);

        mFindMeetupsButton.setOnClickListener(this);

        mRef = new Firebase ("https://gentle-resolve.firebaseio.com/");
    }

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(FindSupportActivity.this, ResultsListActivity.class);
            String passion = mInterest.getText().toString();
            myIntent.putExtra("interest", passion);
            String zip = mZip.getText().toString();
            myIntent.putExtra("zip", zip);
            String radius = mRadius.getText().toString();
            myIntent.putExtra("radius", radius);
            saveParamsToFirebase(passion, zip, radius);
            startActivity(myIntent);
    }

    private void saveParamsToFirebase(String passion, String zip, String radius) {
        mSearchedMeetupReference.push().setValue(passion);
//        mSearchedZip.push().setValue(zip);
//        mSearchedRadius.push().setValue(radius);
    }


}