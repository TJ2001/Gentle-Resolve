package com.example.tim.gentleresolve;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.services.MeetupService;

import org.parceler.Parcels;

import java.io.IOException;
import java.security.acl.Group;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ResultsListActivity extends AppCompatActivity {
    @Bind(R.id.listView)
    ListView mListView;
    ArrayList<Meetup> mMeetups = new ArrayList<>();
    public static final String TAG = ResultsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String passion = intent.getStringExtra("passion");
        String zip = intent.getStringExtra("zip");
        String radius = intent.getStringExtra("radius");
        getMeetup(passion, zip, radius);
    }

    private void getMeetup(String passion, String zip, String radius) {
        final MeetupService MeetupFINDER = new MeetupService();

        MeetupFINDER.findSupport(passion, zip, radius, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMeetups = MeetupFINDER.processResults(response.body().string());

                ResultsListActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        String[] groupNames = new String[mMeetups.size()];
                        for (int i = 0; i < groupNames.length; i ++) {
                            groupNames[i] = mMeetups.get(i).getName();
                        }

                        ArrayAdapter adapter = new ArrayAdapter(ResultsListActivity.this,
                                android.R.layout.simple_list_item_1, groupNames);
                        mListView.setAdapter(adapter);

                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long id) {
                                Intent intent = new Intent(view.getContext(), ResultsDetailActivity.class);
                                intent.putExtra("position", positon);
                                intent.putExtra("movies", Parcels.wrap(mMeetups));
                                view.getContext().startActivity(intent);
                            }
                        });

                        for (Meetup movie : mMeetups) {
                            Log.d(TAG, "Title: " + movie.getTitle());
                            Log.d(TAG, "Overview: " + movie.getOverview());
                            Log.d(TAG, "Release Date: " + movie.getReleaseDate());
                            Log.d(TAG, "poster url: " + movie.getPoster_path());
                            Log.d(TAG, "voting average: " + movie.getVoteAverage());
                            Log.d(TAG, "Meetup ID: " + movie.getMeetupID());
                            Log.d(TAG, "Genres: " + movie.getGenreID().toString());
                        }
                    }
                });



            }
        });
    }
}