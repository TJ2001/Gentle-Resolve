package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.tim.gentleresolve.api_ui.ResultsDetailActivity;
import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.util.ItemTouchHelperAdapter;
import com.example.tim.gentleresolve.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseMeetupListAdapter extends FirebaseRecyclerAdapter<Meetup, MeetupListAdapter> implements ItemTouchHelperAdapter{
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Meetup> mMeetups = new ArrayList<>();

    public FirebaseMeetupListAdapter(Class<Meetup> modelClass, int modelLayout,
                                     Class<MeetupListAdapter> viewHolderClass,
                                     Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mMeetups.add(dataSnapshot.getValue(Meetup.class));
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
    }

    @Override
    protected void populateViewHolder(final MeetupListAdapter viewHolder, Meetup model, int position) {
        viewHolder.bindMeetup(model);
        viewHolder.mMeetupImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultsDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("meetups", Parcels.wrap(mMeetups));
                mContext.startActivity(intent);
            }

        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mMeetups, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mMeetups.remove(position);
        getRef(position).removeValue();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }

    private void setIndexInFirebase(){
        for(Meetup meetup: mMeetups){
            int index = mMeetups.indexOf(meetup);
            DatabaseReference ref = getRef(index);
            meetup.setIndex(Integer.toString(index));
            ref.setValue(meetup);
        }
    }

}
