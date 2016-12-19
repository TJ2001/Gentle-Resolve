package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.Vision;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VisionListAdapter extends RecyclerView.Adapter<VisionListAdapter.FirebaseVisionsViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Vision> mVisions = new ArrayList<>();
    private Context mContext;
    private OnVisionSelectedListener mOnVisionSelectedListener;

    public VisionListAdapter(Context context, ArrayList<Vision> visions, OnVisionSelectedListener visionSelectedListener) {
        mContext = context;
        mVisions = visions;
        mOnVisionSelectedListener = visionSelectedListener;
    }

    @Override
    public VisionListAdapter.VisionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        VisionViewHolder viewHolder = new VisionViewHolder(view, mVisions, mOnVisionSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VisionListAdapter.VisionViewHolder holder, int position) {
        holder.bindVision(mVisions.get(position));
    }

    @Override
    public int getItemCount() {
        return mVisions.size();
    }



    public class VisionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.restaurantImageView)
        ImageView mVisionImageView;
        @Bind(R.id.restaurantNameTextView)
        TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Vision> mVisions = new ArrayList<>();
        private OnVisionSelectedListener mVisionSelectedListener;


        public VisionViewHolder(View itemView, ArrayList<Vision> restaurants, OnVisionSelectedListener restaurantSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mVisions = restaurants;
            mVisionSelectedListener = restaurantSelectedListener;

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);
        }

        public void bindVision(Vision restaurant) {

            mNameTextView.setText(restaurant.getName());
        }

        private void createDetailFragment(int position) {
            VisionDetailFragment detailFragment = VisionDetailFragment.newInstance(mVisions, position, Constants.SOURCE_FIND);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.restaurantDetailContainer, detailFragment);
            ft.commit();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();

            mVisionSelectedListener.onVisionSelected(itemPosition, mVisions, Constants.SOURCE_FIND);

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, VisionDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mVisions));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }
    }
}
