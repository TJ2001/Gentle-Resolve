package com.example.tim.gentleresolve.main_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.api_ui.FindSupportActivity;
import com.example.tim.gentleresolve.models.Achievement;
import com.example.tim.gentleresolve.models.Vision;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManifestActivity extends AppCompatActivity {
    private FirebaseListAdapter<Vision> mVision;
    private DatabaseReference mVisionReference;
    private FirebaseListAdapter<Vision> mVisionFirebaseListAdapter;
    private ValueEventListener mVisionReferenceListener;
    private ChildEventListener mChildEventListener;
    private ArrayList<Vision> mVisions = new ArrayList<>();
    private ValueEventListener mAchievementReferenceReferenceListener;
    private DatabaseReference mAchievementReference;

    @Bind(R.id.supportButton)
    Button mSupportButton;
    @Bind(R.id.visionListView)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);

        setUpFirebaseAdapter();
    }


    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mVisionReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_VISIONS)
                .child(uid);

        mChildEventListener = mVisionReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mVisions.add(dataSnapshot.getValue(Vision.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mVisionFirebaseListAdapter = new FirebaseListAdapter<Vision>(this, Vision.class, R.layout.list_item, mVisionReference) {

            @Override
            protected void populateView(View v, Vision model, int position) {
                ((TextView) v.findViewById(R.id.itemTextView)).setText(model.getName());
                model.setPushId(getRef(position).getKey());
            }
        };

        mListView.setAdapter(mVisionFirebaseListAdapter);


//       setting listener for storing achievements into firebase

        mAchievementReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_VISIONS);

        mAchievementReferenceReferenceListener = mAchievementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot achievementSnapshot : dataSnapshot.getChildren()) {
                    String achievement = achievementSnapshot.getValue().toString();
                    Log.d("Interest updated", "achievement: " + achievement);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Vision vision = mVisions.get(position);
                saveToFirebaseAchievements(vision.getName());
                Log.v("LogV", "postion " + position);
                mVisionReference.child(vision.getPushId()).removeValue();
            }
        });

        mSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManifestActivity.this, FindSupportActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVisionReference.removeEventListener(mChildEventListener);

    }

    private void saveToFirebaseAchievements(String achievement) {
        Achievement mAchievement = new Achievement(achievement);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference mRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_ACHIEVEMENTS)
                .child(uid);

        DatabaseReference pushRef = mRef.push();
        String pushId = pushRef.getKey();
        mAchievement.setPushId(pushId);
        pushRef.setValue(mAchievement);

        mAchievementReference.push().setValue(mAchievement);
    }
}
