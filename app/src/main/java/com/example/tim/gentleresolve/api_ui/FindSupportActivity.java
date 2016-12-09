package com.example.tim.gentleresolve.api_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tim.gentleresolve.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindSupportActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.passionInput) EditText mInterest;
    @Bind(R.id.zipInput) EditText mZip;
    @Bind(R.id.radiusInput) EditText mRadius;
    @Bind(R.id.findMeetupsButton) Button mFindMeetupsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_support);
        ButterKnife.bind(this);

        mFindMeetupsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(FindSupportActivity.this, ResultsListActivity.class);
            myIntent.putExtra("interest", mInterest.getText().toString());
            myIntent.putExtra("zip", mZip.getText().toString());
            myIntent.putExtra("radius", mRadius.getText().toString());
            Log.i("FindSupportActivity", "Clicked!");
            startActivity(myIntent);
    }
}