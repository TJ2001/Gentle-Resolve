package com.example.tim.gentleresolve.api_ui;

import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.adapters.FirebaseMeetupViewHolder;
import com.example.tim.gentleresolve.models.Meetup;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedMeetupActivity extends AppCompatActivity {
    private DatabaseReference mMeetupReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_meetup);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mMeetupReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEETUPS).child(uid);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Meetup, FirebaseMeetupViewHolder>
                (Meetup.class, R.layout.meetup_list_item, FirebaseMeetupViewHolder.class,
                        mMeetupReference) {

            @Override
            protected void populateViewHolder(FirebaseMeetupViewHolder viewHolder,
                                              Meetup model, int position) {
                viewHolder.bindMeetup(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final DatabaseReference mFilteredReference;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        ButterKnife.bind(this);

//        Firebase.setAndroidContext(this);
//
//        Firebase mRootRef = new Firebase("https://gentle-resolve.firebaseio.com/");

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


//            TODO add another property: topic with value inside object when user makes search query for meetups.

            @Override
            public boolean onQueryTextSubmit(String query) {
                mMeetupReference.addValueEventListener(new ValueEventListener(){
                   Map<String, String> map = dataSnapshot.getValue(Map.class);
                    String name = map.get("name");
                    String description = map.get("description");
                    Log.v("E_VALUE", name + " " + description);
                })

                mFilteredReference = mMeetupReference.child("name").getValue();


//            public Query equalTo (String value, String key){
//            }

                addToSharedPreferences(query);
                if(query.length() == 5){
                    getMeetup(mRecentPassion, query, "25");
                } else if (query.length() < 5 || query.length() > 5){
                    getMeetup(query, mRecentZip, "25");
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

}
