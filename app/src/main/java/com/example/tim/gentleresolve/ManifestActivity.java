package com.example.tim.gentleresolve;

import android.content.Intent;
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
        Intent intent = getIntent();
        String pledge = intent.getStringExtra("vision");
        mPledgeView.setText("I agree to take the first steps to" + pledge + ".");
    }
}
