package com.example.tim.gentleresolve;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

//THings to work on: push:
//1. Change TextView to GridView and pop each visions arraylist element as a seperate clickable item.

public class ManifestActivity extends AppCompatActivity {
    private TextView mPledgeView;
    @Bind(R.id.listView) ListView mListView;
//    private GridView<String> Visions = new ArrayList<>();

    private String[] diamonds = new String[] {"Create a beautiful project", "The Adapter provides access to the data items. The Adapter         is also responsible for making a View for each item in the data set.", "Java is cool"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, diamonds);
        mListView.setAdapter(adapter);

//        mPledgeView = (GridView) GridViewfindViewById(R.id.pledgeView);
        mPledgeView = (TextView) findViewById(R.id.pledgeView);
        Typeface champageFont = Typeface.createFromAsset(getAssets(), "fonts/cac_champagne.ttf");
        mPledgeView.setTypeface(champageFont);


//        visions = intent.getStringArrayListExtra("words");
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, words);
//        mGridView.setAdapter(adapter); (For List implementation, delete the next three lines.)

        Intent intent = getIntent();
        String pledge = intent.getStringExtra("vision");
        mPledgeView.setText(getString(R.string.agreeTo) + pledge + ".");
    }
}
