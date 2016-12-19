package com.example.tim.gentleresolve.main_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

public class ManifestActivity extends AppCompatActivity{
    private FirebaseVisionsListAdapter mFirebaseAdapter;

//    private DatabaseReference mVisionReference;
//    private FirebaseListAdapter<Vision> mVisionFirebaseListAdapter;
//    private ValueEventListener mVisionReferenceListener;
//    private ChildEventListener mChildEventListener;
//    private ArrayList<Vision> mVisions = new ArrayList<>();

    @Bind(R.id.visionListView) ListView mListView;
    @Bind(R.id.supportButton) Button mSupportButton;
    @Bind(R.id.visionRecyclerView) RecyclerView mVisionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);

        setUpFirebaseAdapter();
    }


    private void setmFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mVisionReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_VISIONS)
                .child(uid);
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseVisionsListAdapter(Vision.class,
                R.layout.list_item, FirebaseVisionsViewHolder.class,
                query, this, this);

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

//        mVisionReferenceListener = mVisionReference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {}
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });




        mVisionFirebaseListAdapter = new FirebaseListAdapter<Vision>(this, Vision.class, R.layout.list_item, mVisionReference) {

            @Override
            protected void populateView(View v, Vision model, int position) {
                ((TextView)v.findViewById(R.id.itemTextView)).setText(model.getName());

                model.setPushId(getRef(position).getKey());
            }
        };

        mListView.setAdapter(mVisionFirebaseListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                mListView.getRef(position).removeValue();
                Log.v("LogV", "postion " + position);
                Intent intent = new Intent(view.getContext(), AchievementsActivity.class);;

                String postKey = mVision.get(position).getKey();
                mVisions.remove(position);
                postKey.removeValue();
//                Log.v("LogV", "mVisionFirebaseListAdapter " + mVisionFirebaseListAdapter);
//                mVisionFirebaseListAdapter.remove(position);

//                mVisionReference.removeValue();

//                intent.putExtra("Achievements", Parcels.wrap(mVisionFirebaseListAdapter));
                view.getContext().startActivity(intent);
            }
        });

        mSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManifestActivity.this, FindSupportActivity.class));
            }
        });


    }






//    private class MyListAdapter extends ArrayAdapter<String> {
//        private int layout;
//        public MyListAdapter(Context context, int resource, List<String> objects) {
//            super(context, resource, objects);
//            layout = resource;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            ViewHolder mainViewholder = null;
//            if(convertView == null) {
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                convertView = inflater.inflate(layout, parent, false);
//                ViewHolder viewHolder = new ViewHolder();
//                viewHolder.itemTextView = (TextView) convertView.findViewById(R.id.itemTextView);
//                viewHolder.doneButton = (Button) convertView.findViewById(R.id.doneButton);
//                viewHolder.supportButton = (Button) convertView.findViewById(R.id.supportButton);
//
//                convertView.setTag(viewHolder);
//            }
//            mainViewholder = (ViewHolder) convertView.getTag();
//
//            mainViewholder.doneButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(ManifestActivity.this, AchievementsActivity.class);
//                    Log.v("LogV", "intent from Done Button: ");
//                    startActivity(intent);
//                }
//            });
//
//
//            mainViewholder.itemTextView.setText(getItem(position));
//
//            return convertView;
//        }
//    }
//
//    public class ViewHolder {
//
//        ImageView thumbnail;
//        TextView itemTextView;
//        Button doneButton;
//        Button supportButton;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVisionReference.removeEventListener(mChildEventListener);
//        mVisionReference.removeEventListener(mVisionReferenceListener);
    }
}




