package com.example.tim.gentleresolve.main_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.adapters.FirebaseVisionsListAdapter;
import com.example.tim.gentleresolve.adapters.FirebaseVisionsViewHolder;
import com.example.tim.gentleresolve.api_ui.FindSupportActivity;
import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.models.Vision;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManifestActivity extends AppCompatActivity{
    private FirebaseVisionsListAdapter mFirebaseAdapter;
    private DatabaseReference mVisionReference;

    @Bind(R.id.supportButton) Button mSupportButton;
    @Bind(R.id.visionRecyclerView) RecyclerView mVisionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mVisionReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_VISIONS)
                .child(uid);

        setUpFirebaseAdapter();

        mSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManifestActivity.this, FindSupportActivity.class));
            }
        });
    }


    private void setUpFirebaseAdapter() {
        new FirebaseRecyclerAdapter<Vision, FirebaseVisionsViewHolder>(Vision.class, R.layout.list_item, FirebaseVisionsViewHolder.class, mVisionReference) {

            @Override
            protected void populateViewHolder(FirebaseVisionsViewHolder viewHolder, Vision model, int position) {
                viewHolder.bindVision(model);
            }
        };

        mVisionRecyclerView.setHasFixedSize(true);
        mVisionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVisionRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




