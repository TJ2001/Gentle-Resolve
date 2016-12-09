package com.example.tim.gentleresolve.api_ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.Meetup;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeetupDetailFragment extends Fragment {
    @Bind(R.id.meetupImageView) ImageView mPhotolink;
    @Bind(R.id.meetupNameTextView) TextView mName;
    @Bind(R.id.interestTextView) TextView mCity;
    @Bind(R.id.organizerTextView) TextView mOrganizer;
    @Bind(R.id.websiteTextView) TextView mLink;
    @Bind(R.id.descriptionTextView) TextView mDescription;
    @Bind(R.id.saveMeetupButton) TextView mSaveMeetupButton;

    private Meetup mMeetup;

    public static MeetupDetailFragment newInstance(Meetup meetup) {
        MeetupDetailFragment meetupDetailFragment = new MeetupDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("meetup", Parcels.wrap(meetup));
        meetupDetailFragment.setArguments(args);
        return meetupDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetup = Parcels.unwrap(getArguments().getParcelable("meetup"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetup_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mMeetup.getPhotoLink()).into(mPhotolink);

        mName.setText(mMeetup.getName());
        mCity.setText(mMeetup.getCity());
        mOrganizer.setText("Organizer:   " + mMeetup.getOrganizer());
        mLink.setText(mMeetup.getLink());

        return view;
    }

}
