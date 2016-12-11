package com.example.tim.gentleresolve.api_ui;

import android.graphics.Movie;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.adapters.MeetupPagerAdapter;
import com.example.tim.gentleresolve.models.Meetup;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultsDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private MeetupPagerAdapter adapterViewPager;
    ArrayList<Meetup> mMeetups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_detail);
        ButterKnife.bind(this);

        mMeetups = Parcels.unwrap(getIntent().getParcelableExtra("meetups"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new MeetupPagerAdapter(getSupportFragmentManager(), mMeetups);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}