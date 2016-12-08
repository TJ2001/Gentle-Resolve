package com.example.tim.gentleresolve.api_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tim.gentleresolve.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindSupportActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.passionInput) EditText mPassion;
    @Bind(R.id.zipInput) EditText mZip;
    @Bind(R.id.radiusInput) EditText mRadius;
    @Bind(R.id.findMeetupsButton) Button mFindMeetupsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_support);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v){
        if (v == mFindMeetupsButton){
            Intent myIntent = new Intent(FindSupportActivity.this, ResultsListActivity.class);
            myIntent.putExtra("passion", mPassion.getText().toString());
            myIntent.putExtra("zip", mZip.getText().toString());
            myIntent.putExtra("radius", mRadius.getText().toString());
            startActivity(myIntent);
        }
    };
}
