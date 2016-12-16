package com.example.tim.gentleresolve.api_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.adapters.FirebaseMeetupViewHolder;
import com.example.tim.gentleresolve.models.Meetup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedMeetupActivity extends AppCompatActivity {
    private DatabaseReference mMeetupReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_meetup);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mMeetupReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEETUPS).child(uid);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Meetup, FirebaseMeetupViewHolder>
                (Meetup.class, R.layout.meetup_list_item_drag, FirebaseMeetupViewHolder.class, mMeetupReference) {

            @Override
            protected void populateViewHolder(FirebaseMeetupViewHolder viewHolder,
                                              Meetup model, int position) {
                viewHolder.bindMeetup(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

}
