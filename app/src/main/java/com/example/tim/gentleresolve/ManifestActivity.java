package com.example.tim.gentleresolve;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//THings to work on: push:
//1. Change TextView to GridView and pop each visions arraylist element as a seperate clickable item.

public class ManifestActivity extends AppCompatActivity {
    private TextView mPledgeView;
//    private GridView<String> Visions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);

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
