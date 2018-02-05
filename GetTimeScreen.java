package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.touristcompanion.MainActivity.MyPreferences;
import static com.example.lenovo.touristcompanion.MainActivity.fName;
import static com.example.lenovo.touristcompanion.MainActivity.lName;

public class GetTimeScreen extends AppCompatActivity {

    SharedPreferences sp;
    TextView name;
    Spinner selectTime;
    Button btnMaps;

    DrawerLayout mDrawerLayout2;
    ActionBarDrawerToggle mToggle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_time_screen);

        selectTime = (Spinner)findViewById(R.id.spinner_time);
        btnMaps = (Button)findViewById(R.id.btnMaps);

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMap = new Intent(GetTimeScreen.this, MapsActivity1.class);
                startActivity(iMap);
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("2hrs");
        categories.add("3hrs");
        categories.add("4hrs");
        categories.add("5hrs");
        categories.add("6hrs");
        categories.add("7hrs");
        categories.add("8hrs");
        categories.add("9hrs");
        categories.add("10hrs");
        categories.add("11hrs");
        categories.add("12hrs");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        selectTime.setAdapter(dataAdapter);
        sp = getApplication().getSharedPreferences(MyPreferences, MODE_PRIVATE);

        String fn = sp.getString(fName, "");
        String ln = sp.getString(lName, "");

        mDrawerLayout2 = (DrawerLayout)findViewById(R.id.drawer2);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        setupDrawerContent(navigationView);

//        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_drawer_header, null);
//        name = (TextView) headerView.findViewById(R.id.name);
//        name.setText(fn + " " + ln);
//
//        navigationView.addHeaderView(headerView);

        mToggle2 = new ActionBarDrawerToggle(this, mDrawerLayout2, R.string.open, R.string.close);
        mDrawerLayout2.addDrawerListener(mToggle2);
        mToggle2.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle2.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //selectItemDrawer(item);
                return true;
            }
        });
    }

//    public void selectItemDrawer(MenuItem menuItem) {
//        Fragment mFragment = null;
//        Class fragmentClass = null;
//        switch (menuItem.getItemId()) {
//            case R.id.currency_converter:
//                fragmentClass = ConvertCurrency.class;
//                break;
//        }
//        try {
//            mFragment = (Fragment) fragmentClass.newInstance();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.frame2, mFragment).commit();
//
//    }
}
