package com.example.tim.gentleresolve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.achieveButton) Button mAchieveButton;
    @Bind(R.id.startButton) Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        mAchieveButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mAchieveButton){
            Toast.makeText(StartActivity.this, "This button is a work in progress.", Toast.LENGTH_LONG).show();
        } else if (v == mStartButton){
            startActivity(new Intent(StartActivity.this, CreateActivity.class));
        }
    }
}