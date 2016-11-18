package com.example.tim.gentleresolve;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ManifestActivity extends AppCompatActivity {
    private TextView mPledgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);

        mPledgeView = (TextView) findViewById(R.id.pledgeView);
        Typeface champageFont = Typeface.createFromAsset(getAssets(), "fonts/cac_champagne.ttf");
        mPledgeView.setTypeface(champageFont);

        Intent intent = getIntent();
        String pledge = intent.getStringExtra("vision");
        mPledgeView.setText("I agree to take the first steps to" + pledge + ".");
    }
}
