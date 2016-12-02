package com.example.tim.gentleresolve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

//Things to do:
//    1. Put each input into arraylist and send it to ManifestActivity.java
//    2. Create a drop-down button.
//    3. Put a listener on the button.
//    4. Send option data from button to ManifestActivity.java.


public class CreateActivity extends AppCompatActivity {
    @Bind(R.id.manifestButton) Button mManifestButton;
    @Bind(R.id.diamond) EditText mDiamond;
    private ArrayList<String> diamonds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        mManifestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diamond = mDiamond.getText().toString();
//                mDiamond.setText("");    (For ArrayList implementation)

                if ((diamond.length() < 3)) {
                    Toast.makeText(CreateActivity.this, "Our diamonds need to be more detailed. Good effort, but please try again.", Toast.LENGTH_LONG).show();
                    }else{
                    diamonds.add(diamond);
                    Intent intent = new Intent(CreateActivity.this, ManifestActivity.class);
                    intent.putStringArrayListExtra("diamonds", diamonds);
                    startActivity(intent); (For ArrayList implementation, delete the next three lines.)

                    Intent intent = new Intent(CreateActivity.this, ManifestActivity.class);
                    intent.putExtra("diamond", diamond);
                    startActivity(intent);
                    }

            }
        });
    }
}
