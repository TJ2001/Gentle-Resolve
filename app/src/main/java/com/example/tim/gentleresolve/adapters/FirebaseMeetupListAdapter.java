package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.util.ItemTouchHelperAdapter;
import com.example.tim.gentleresolve.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FirebaseMeetupListAdapter extends FirebaseRecyclerAdapter<Meetup, FirebaseMeetupViewHolder> implements ItemTouchHelperAdapter{
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseMeetupListAdapter(Class<Meetup> modelClass, int modelLayout,
                                     Class<FirebaseMeetupViewHolder> viewHolderClass,
                                     Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
}
    @Override
    protected void populateViewHolder(FirebaseMeetupViewHolder viewHolder, Meetup model, int position) {
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
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
