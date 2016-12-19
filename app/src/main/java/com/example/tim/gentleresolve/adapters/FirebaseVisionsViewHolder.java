package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.main_ui.AchievementsActivity;
import com.example.tim.gentleresolve.models.Vision;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by TJ2001 on 12/18/2016.
 */
public class FirebaseVisionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;


    public FirebaseVisionsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindVision(Vision vision) {
        TextView nameTextView = (TextView) mView.findViewById(R.id.visionTextView);

        nameTextView.setText(vision.getName());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Vision> visions = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_VISIONS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    visions.add(snapshot.getValue(Vision.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, AchievementsActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("visions", Parcels.wrap(visions));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

