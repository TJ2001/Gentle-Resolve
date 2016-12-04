package com.example.tim.gentleresolve;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//THings to work on: push:
//1. Change TextView to GridView and pop each visions arraylist element as a seperate clickable item.

public class ManifestActivity extends AppCompatActivity {
    @Bind(R.id.visionListView) ListView mListView;
//    private GridView<String> Visions = new ArrayList<>();

    private List<String> visions = new ArrayList<>();

    public static final String TAG = CreateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);
        Intent intent = getIntent();


        visions = intent.getStringArrayListExtra("visions");
        Log.v(TAG, "intent: " + visions);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, visions);
//        mListView.setAdapter(adapter);
        mListView.setAdapter(new MyListAdapter(this, R.layout.list_item, visions));
        visions.add("First Item");
        visions.add("secoond Item");
//        Typeface champageFont = Typeface.createFromAsset(getAssets(), "fonts/cac_champagne.ttf");
//        mListView.setTypeface(champageFont);



//        visions = intent.getStringArrayListExtra("words");
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, words);
//        mGridView.setAdapter(adapter); (For List implementation, delete the next three lines.)


//        String pledge = intent.getStringExtra("vision");
//        mPledgeView.setText(getString(R.string.agreeTo) + pledge + ".");
    }


    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.itemTextView = (TextView) convertView.findViewById(R.id.itemTextView);
                viewHolder.doneButton = (Button) convertView.findViewById(R.id.doneButton);
                viewHolder.shareButton = (Button) convertView.findViewById(R.id.shareButton);

                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            mainViewholder.doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vision = visions.get(position).toString();
                    Intent intent = new Intent(ManifestActivity.this, AchievementsActivity.class);
                    intent.putExtra("vision", vision);
                    Log.v(TAG, "intent from Done Button: " + intent);
                    startActivity(intent);
                }
            });

            mainViewholder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Share button was clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });

            mainViewholder.itemTextView.setText(getItem(position));

            return convertView;
        }
    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView itemTextView;
        Button doneButton;
        Button shareButton;
    }
}