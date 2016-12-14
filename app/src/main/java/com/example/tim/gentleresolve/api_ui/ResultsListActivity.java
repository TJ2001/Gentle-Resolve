package com.example.tim.gentleresolve.api_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.models.Meetup;
import com.example.tim.gentleresolve.services.MeetupServices;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ResultsListActivity extends AppCompatActivity {
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private String mRecentZip, mRecentRadius;
    public static final String TAG = ResultsListActivity.class.getSimpleName();

    @Bind(R.id.listView)
    ListView mListView;
    ArrayList<Meetup> mMeetups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String interest = intent.getStringExtra("interest");
        String zip = intent.getStringExtra("zip");
        String radius = intent.getStringExtra("radius");

        getMeetup(zip, interest, radius);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentZip = mSharedPreferences.getString(Constants.PREFERENCES_ZIP_KEY, null);
        mRecentRadius = mSharedPreferences.getString(Constants.PREFERENCES_RADIUS_KEY, null);
        Log.d("Shared Pref Zip", "LogD RecentZip: " + mRecentZip);
        Log.d("Shared Pref Radius", "RecentRadius: " + mRecentRadius);
        if (mRecentZip != null && mRecentRadius != null){
            getMeetup(mRecentZip, interest, mRecentRadius);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
//                addToSharedPreferences(query);
                getMeetup(mRecentZip, query, mRecentRadius);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getMeetup(String zip, String passion, String radius) {
        final MeetupServices MeetupFINDER = new MeetupServices();

        MeetupFINDER.findSupport(zip, passion, radius, new Callback() {
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
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(view.getContext(), ResultsDetailActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("meetups", Parcels.wrap(mMeetups));
                                view.getContext().startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

//    private void addToSharedPreferences(String zip) {
//        mEditor.putString(Constants.PREFERENCES_ZIP_KEY, zip).apply();
//    }
}
