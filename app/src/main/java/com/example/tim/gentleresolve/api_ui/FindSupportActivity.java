package com.example.tim.gentleresolve.api_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Bind(R.id.passionInput) EditText mInterest;
    @Bind(R.id.zipInput) EditText mZip;
    @Bind(R.id.radiusInput) EditText mRadius;
    @Bind(R.id.findMeetupsButton) Button mFindMeetupsButton;
    @Bind(R.id.savedMeetupsButton) Button mSavedMeetupsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedMeetupReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_MEETUPS);

        mSearchedMeetupReferenceListener = mSearchedMeetupReference.addValueEventListener(new ValueEventListener() {

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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();


        mFindMeetupsButton.setOnClickListener(this);
        mSavedMeetupsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v == mFindMeetupsButton){
            Intent myIntent = new Intent(FindSupportActivity.this, ResultsListActivity.class);

            String passion = mInterest.getText().toString();
            String zip = mZip.getText().toString();
            String radius = mRadius.getText().toString();
            myIntent.putExtra("interest", passion);
            myIntent.putExtra("zip", zip);
            myIntent.putExtra("radius", radius);
            addToSharedPreferencesZip(zip);;
            saveParamsToFirebase(passion, zip, radius);
            if(!(zip).equals("")) {
                addToSharedPreferencesZip(zip);
            }
            startActivity(myIntent);
        } else if (v == mSavedMeetupsButton){
            Intent intent = new Intent(FindSupportActivity.this, SavedMeetupActivity.class);
            startActivity(intent);
        }
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


    private void addToSharedPreferencesZip(String zip) {
        mEditor.putString(Constants.PREFERENCES_ZIP_KEY, zip).apply();

    }

}