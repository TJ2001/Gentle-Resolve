package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.Meetup;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseMeetupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseMeetupViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindMeetup(Meetup meeetup) {
        ImageView meeetupImageView = (ImageView) mView.findViewById(R.id.meetupImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.meetupNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.descriptionTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.organizerTextView);

//        TODO Refactor code

        Picasso.with(mContext)
                .load(meeetup.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(meeetupImageView);

        nameTextView.setText(meeetup.getName());
        categoryTextView.setText(meeetup.getCategories().get(0));
        ratingTextView.setText("Rating: " + meeetup.getRating() + "/5");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Meetup> meeetups = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEETUPS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    meeetups.add(snapshot.getValue(Meetup.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, MeetupDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("meeetups", Parcels.wrap(meeetups));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}