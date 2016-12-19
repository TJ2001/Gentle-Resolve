package com.example.tim.gentleresolve.main_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.Achievement;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AchievementsActivity extends AppCompatActivity {
    private DatabaseReference mAchievementReference;
    private FirebaseListAdapter<Achievement> mAchievementFirebaseAdapter;
    private ValueEventListener mAchievementReferenceListener;

    @Bind(R.id.achievementListView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mAchievementReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_ACHIEVEMENTS)
                .child(uid);

        mAchievementReferenceListener = mAchievementReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mAchievementFirebaseAdapter = new FirebaseListAdapter<Achievement>(this, Achievement.class, R.layout.list_item, mAchievementReference) {

            @Override
            protected void populateView(View v, Achievement model, int position) {
                ((TextView)v.findViewById(R.id.itemTextView)).setText(model.getName());
                model.setPushId(getRef(position).getKey());
            }
        };

        Log.v("mAchievement", "LogZZZ" + mAchievementFirebaseAdapter);

        mListView.setAdapter(mAchievementFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAchievementReference.removeEventListener(mAchievementReferenceListener);
    }


}
