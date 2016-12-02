package com.example.tim.gentleresolve;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tim.gentleresolve.db.TaskContract;
import com.example.tim.gentleresolve.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//THings to work on: push:
//1. Change TextView to GridView and pop each visions arraylist element as a seperate clickable item.

public class ManifestActivity extends AppCompatActivity {
//    @Bind(R.id.textView) ListView mListView;
    @Bind(R.id.diamond_title) TextView mDiamondView;
    private TaskDbHelper mHelper;
//    private GridView<String> Visions = new ArrayList<>();

    private List<String> diamonds = new ArrayList<>();

    public static final String TAG = CreateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);
        mHelper = new TaskDbHelper(this);
        Intent intent = getIntent();

        SQLiteDatabase dbRead = mHelper.getReadableDatabase();
        Cursor cursor = dbRead.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        dbRead.close();

//        diamonds = intent.getStringArrayListExtra("diamonds");
//        Log.v(TAG, "intent: " + diamonds);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, diamonds);
//        mListView.setAdapter(adapter);
//        diamonds.add("First Item");
//        diamonds.add("secoond Item");

        Typeface champageFont = Typeface.createFromAsset(getAssets(), "fonts/cac_champagne.ttf");
        mDiamondView.setTypeface(champageFont);
        String diamond = intent.getStringExtra("diamond");
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, diamond);
        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        mDiamondView.setText(getString(R.string.agreeTo) + diamond + ".");





//        visions = intent.getStringArrayListExtra("words");
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, words);
//        mGridView.setAdapter(adapter); (For List implementation, delete the next three lines.)


//        String pledge = intent.getStringExtra("vision");
//        mPledgeView.setText(getString(R.string.agreeTo) + pledge + ".");
    }
}
