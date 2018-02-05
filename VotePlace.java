package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class VotePlace extends AppCompatActivity {

    ImageButton thumbsUp, thumbsDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_place);
        thumbsUp = (ImageButton)findViewById(R.id.tup);
        thumbsDown = (ImageButton)findViewById(R.id.tdown);

        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VotePlace.this, "You voted for this place", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VotePlace.this, MapsActivity1.class);
                startActivity(i);
            }
        });

        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VotePlace.this, "You voted against this place", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VotePlace.this, MapsActivity1.class);
                startActivity(i);
            }
        });
    }
}
