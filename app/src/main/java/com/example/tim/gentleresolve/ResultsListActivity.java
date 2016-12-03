package com.example.tim.gentleresolve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


import java.security.acl.Group;
import java.util.ArrayList;

import butterknife.Bind;

public class ResultsListActivity extends AppCompatActivity {

    public ArrayList<Group> mGroup = new ArrayList<>();

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ResultListAdapter mAdapter;

    public static final String TAG = ResultsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
    }
}
