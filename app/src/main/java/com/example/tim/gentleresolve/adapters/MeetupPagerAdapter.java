package com.example.tim.gentleresolve.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tim.gentleresolve.api_ui.MeetupDetailFragment;
import com.example.tim.gentleresolve.models.Meetup;

import java.util.ArrayList;

public class MeetupPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Meetup> mMeetups;

    public MeetupPagerAdapter(FragmentManager fm, ArrayList<Meetup> meetups) {
        super(fm);
        mMeetups = meetups;
    }

    @Override
    public Fragment getItem(int position) {
        return MeetupDetailFragment.newInstance(mMeetups.get(position));
    }

    @Override
    public int getCount() {
        return mMeetups.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMeetups.get(position).getName();
    }
}
