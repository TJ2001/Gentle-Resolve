package com.example.tim.gentleresolve.main_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.SavedSearch;
import com.example.tim.gentleresolve.models.Vision;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateActivity extends AppCompatActivity {
    private DatabaseReference mVisionReference;
    private ValueEventListener mVisionReferenceReferenceListener;

    @Bind(R.id.manifestButton) Button mManifestButton;
    @Bind(R.id.vision) EditText mVision;
//    private ArrayList<String> visions = new ArrayList<>();

    public static final String TAG = CreateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mVisionReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_VISIONS);

        mVisionReferenceReferenceListener = mVisionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot visionSnapshot : dataSnapshot.getChildren()) {
                    String vision = visionSnapshot.getValue().toString();
                    Log.d("Interest updated", "vision: " + vision);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        mManifestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vision = mVision.getText().toString();
//                visions.add(vision);
                if ((vision.length() < 3)) {
                    Toast.makeText(CreateActivity.this, "Our visions need to be more detailed. Good effort, but please try again.", Toast.LENGTH_LONG).show();
                }else{
                    mVision.setText("");
                    Intent intent = new Intent(CreateActivity.this, ManifestActivity.class);
//                    intent.putStringArrayListExtra("visions", visions);
                    Log.v(TAG, "intent: " + intent);
                    saveToFirebase(vision);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVisionReference.removeEventListener(mVisionReferenceReferenceListener);
    }

    private void saveToFirebase(String vision) {
        Vision visionObject = new Vision(vision);
        mVisionReference.push().setValue(visionObject);
//        DatabaseReference mSetId = mVisionReference.push();
//        String generatedID = mSetId.getKey();
//        mSetId.setValue(vision);
    }
}