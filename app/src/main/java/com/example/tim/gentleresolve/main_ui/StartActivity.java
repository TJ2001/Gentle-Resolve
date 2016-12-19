package com.example.tim.gentleresolve.main_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.api_ui.FindSupportActivity;
import com.example.tim.gentleresolve.api_ui.SavedMeetupActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.achieveButton) Button mAchieveButton;
    @Bind(R.id.startButton) Button mStartButton;
    @Bind(R.id.savedSuppportButton) Button mSavedSuppportButton;
    @Bind(R.id.findSupportButton) Button mFindSupportButton;
    @Bind(R.id.inMotionButton) Button mInMotionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        mAchieveButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mSavedSuppportButton.setOnClickListener(this);
        mFindSupportButton.setOnClickListener(this);
        mInMotionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mAchieveButton) {
            startActivity(new Intent(StartActivity.this, AchievementsActivity.class));
        } else if (v == mStartButton){
            startActivity(new Intent(StartActivity.this, CreateActivity.class));
        } else if (v == mSavedSuppportButton){
            Intent intent = new Intent(StartActivity.this, SavedMeetupActivity.class);
            startActivity(intent);
        } else if (v == mFindSupportButton) {Intent intent = new Intent(StartActivity.this, FindSupportActivity.class);
            startActivity(intent);
        } else if (v == mInMotionButton) {
            Intent intent = new Intent(StartActivity.this, ManifestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}