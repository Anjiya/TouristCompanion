package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SiteInformation extends AppCompatActivity {

    String[] siteReviews = {"Review 1", "Review 2", "Review 3", "Review 4", "Review 5", "Review 6", "Review 7"};

    ListView reviews;

    Button makevote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_information);
        makevote = (Button)findViewById(R.id.vote);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.label, siteReviews);

        reviews = (ListView)findViewById(R.id.siteReviews);
        reviews.setAdapter(adapter);

        makevote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SiteInformation.this, VotePlace.class);
                startActivity(i);
            }
        });


    }
}
