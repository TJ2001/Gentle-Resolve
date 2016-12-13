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
    private FirebaseListAdapter mFirebaseAdapter;
    private ValueEventListener mVisionReferenceListener;

    @Bind(R.id.visionListView) ListView mListView;
    @Bind(R.id.supportButton) Button mSupportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);

        mVisionReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_VISIONS);

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

//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder mainViewholder = null;
//        if(convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(layout, parent, false);
//            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.itemTextView = (TextView) convertView.findViewById(R.id.itemTextView);
//            viewHolder.doneButton = (Button) convertView.findViewById(R.id.doneButton);
//            viewHolder.supportButton = (Button) convertView.findViewById(R.id.supportButton);
//
//            convertView.setTag(viewHolder);
//        }
//        mainViewholder = (ViewHolder) convertView.getTag();
//
//        mainViewholder.doneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO get position for list item, push the item into firebase via createActivity.class method.. make list.. output it same way as ManifestActivty... Delete the item of Firbase Visions... create Object in Achievements
//
////                    String vision = visions.get(position).toString();
//                Intent intent = new Intent(ManifestActivity.this, AchievementsActivity.class);
////                    intent.putExtra("vision", vision);
//                Log.v(TAG, "intent from Done Button: " + intent);
//                startActivity(intent);
//            }
//        });
//
//
//        mainViewholder.itemTextView.setText(getItem(position));
//
//        return convertView;
//    }
//
//    public class ViewHolder {
//        ImageView thumbnail;
//        TextView itemTextView;
//        Button doneButton;
//        Button supportButton;
//    }
}



