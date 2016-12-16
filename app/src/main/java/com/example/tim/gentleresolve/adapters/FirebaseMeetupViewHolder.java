package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.api_ui.ResultsDetailActivity;
import com.example.tim.gentleresolve.models.Meetup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseMeetupViewHolder extends RecyclerView.ViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    public ImageView mMeetupImageView;

    View mView;
    Context mContext;

    public FirebaseMeetupViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindMeetup(Meetup meetup) {
        mMeetupImageView = (ImageView) mView.findViewById(R.id.meetupImageView);
        ImageView meetupImageView = (ImageView) mView.findViewById(R.id.meetupImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.nameTextView);
        TextView cityTextView = (TextView) mView.findViewById(R.id.cityTextView);
        TextView organizerTextView = (TextView) mView.findViewById(R.id.organizerTextView);

        Picasso.with(mContext)
                .load(meetup.getPhotoLink())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(meetupImageView);

        nameTextView.setText(meetup.getName());
        cityTextView.setText("City: " + meetup.getCity());
        organizerTextView.setText("Organizer: " + meetup.getOrganizer());
    }

//    @Override
//    public void onClick(View view) {
//        final ArrayList<Meetup> meetups = new ArrayList<>();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String userId = user.getUid();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEETUPS).child(userId);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    meetups.add(snapshot.getValue(Meetup.class));
//                    Log.v("meetup", "snapshot: " + snapshot.getValue(Meetup.class));
//                }
//
//
//                int itemPosition = getLayoutPosition();
//                Log.v("position", "itemPosition: " + itemPosition);
//                Intent intent = new Intent(mContext, ResultsDetailActivity.class);
//                intent.putExtra("position", itemPosition);
//                intent.putExtra("meetups", Parcels.wrap(meetups));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
}
