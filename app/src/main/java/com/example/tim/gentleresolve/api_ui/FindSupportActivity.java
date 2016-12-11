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
import com.example.tim.gentleresolve.models.SavedSearch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindSupportActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mSearchedMeetupReference;
    private ValueEventListener mSearchedMeetupReferenceListener;

    @Bind(R.id.passionInput) EditText mInterest;
    @Bind(R.id.zipInput) EditText mZip;
    @Bind(R.id.radiusInput) EditText mRadius;
    @Bind(R.id.findMeetupsButton) Button mFindMeetupsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedMeetupReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_MEETUPS);

        mSearchedMeetupReferenceListener = mSearchedMeetupReference.addValueEventListener(new ValueEventListener() {

//            possible bug in the future with passion, maybe switch name of string later

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot meetupSnapshot : dataSnapshot.getChildren()) {
                    String passion = meetupSnapshot.getValue().toString();
                    Log.d("Interest updated", "passion: " + passion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_support);
        ButterKnife.bind(this);

        mFindMeetupsButton.setOnClickListener(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedMeetupReference.removeEventListener(mSearchedMeetupReferenceListener);
    }

    private void saveParamsToFirebase(String passion, String zip, String radius) {
        DatabaseReference mSetId = mSearchedMeetupReference.push();
        String generatedID = mSetId.getKey();
        mSetId.setValue(new SavedSearch(passion, zip, radius, generatedID));
    }

}