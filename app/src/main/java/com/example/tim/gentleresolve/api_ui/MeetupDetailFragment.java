package com.example.tim.gentleresolve.api_ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.Meetup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeetupDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 150;

    @Bind(R.id.meetupImageView) ImageView mPhotolink;
    @Bind(R.id.meetupNameTextView) TextView mName;
    @Bind(R.id.cityTextView) TextView mCity;
    @Bind(R.id.organizerTextView) TextView mOrganizer;
    @Bind(R.id.websiteTextView) TextView mLink;
    @Bind(R.id.descriptionTextView) TextView mDescription;
    @Bind(R.id.saveMeetupButton) TextView mSaveMeetupButton;

    private Meetup mMeetup;

    public MeetupDetailFragment(){
    };

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
        String mDescriptionString;
        View view = inflater.inflate(R.layout.fragment_meetup_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mMeetup.getPhotoLink()).resize(MAX_WIDTH, MAX_HEIGHT).centerCrop().into(mPhotolink);

        mName.setText(mMeetup.getName());
        mCity.setText(mMeetup.getCity());
        mOrganizer.setText("Organizer:   " + mMeetup.getOrganizer());

        mDescriptionString = stripHtml(mMeetup.getDescription());
        mDescription.setText(mDescriptionString);

        mLink.setOnClickListener(this);
        mSaveMeetupButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mLink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mMeetup.getLink()));
            startActivity(webIntent);
        } else if (view == mSaveMeetupButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();
            DatabaseReference meetupRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_MEETUPS)
                    .child(userId);

            DatabaseReference pushRef = meetupRef.push();
            String pushId = pushRef.getKey();
            mMeetup.setPushId(pushId);
            pushRef.setValue(mMeetup);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }
}
