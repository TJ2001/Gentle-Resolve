package com.example.tim.gentleresolve.main_ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.adapters.FirebaseMeetupViewHolder;
import com.example.tim.gentleresolve.api_ui.FindSupportActivity;
import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.models.Vision;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManifestActivity extends AppCompatActivity{
    private DatabaseReference mVisionReference;
    private FirebaseListAdapter<Vision> mVision;
    private ValueEventListener mVisionReferenceListener;

    @Bind(R.id.visionListView) ListView mListView;
    @Bind(R.id.supportButton) Button mSupportButton;

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

        mVisionReferenceListener = mVisionReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mVision = new FirebaseListAdapter<Vision>(this, Vision.class, R.layout.list_item, mVisionReference) {

            @Override
            protected void populateView(View v, Vision model, int position) {
                ((TextView)v.findViewById(R.id.itemTextView)).setText(model.getName());

                model.setPushId(getRef(position).getKey());
            }
        };

        mListView.setAdapter(mVision);

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
        mVisionReference.removeEventListener(mVisionReferenceListener);
    }
}



