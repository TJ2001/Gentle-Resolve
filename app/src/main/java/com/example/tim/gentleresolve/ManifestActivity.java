package com.example.tim.gentleresolve;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//THings to work on: push:
//1. Change TextView to GridView and pop each visions arraylist element as a seperate clickable item.

public class ManifestActivity extends AppCompatActivity {
    @Bind(R.id.listView) ListView mListView;
//    private GridView<String> Visions = new ArrayList<>();

    private List<String> diamonds = new ArrayList<>();

    public static final String TAG = CreateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);
        Intent intent = getIntent();



        diamonds = intent.getStringArrayListExtra("diamonds");
        Log.v(TAG, "intent: " + diamonds);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, diamonds);
        mListView.setAdapter(adapter);

//        Typeface champageFont = Typeface.createFromAsset(getAssets(), "fonts/cac_champagne.ttf");
//        mListView.setTypeface(champageFont);



//        visions = intent.getStringArrayListExtra("words");
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, words);
//        mGridView.setAdapter(adapter); (For List implementation, delete the next three lines.)


//        String pledge = intent.getStringExtra("vision");
//        mPledgeView.setText(getString(R.string.agreeTo) + pledge + ".");
    }
}
