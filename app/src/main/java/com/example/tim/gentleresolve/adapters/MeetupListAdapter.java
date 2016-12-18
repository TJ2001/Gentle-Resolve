package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.api_ui.ResultsDetailActivity;
import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.util.ItemTouchHelperViewHolder;
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

public class MeetupListAdapter extends RecyclerView.ViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    public ImageView mMeetupImageView;

    View mView;
    Context mContext;

    public MeetupListAdapter(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();

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
//    public void onItemSelected() {
//        Log.d("Animation", "onItemSelected");
//        // we will add animations here
//    }
//
//    @Override
//    public void onItemClear() {
//        Log.d("Animation", "onItemClear");
//        // we will add animations here
//    }

}
