package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.touristcompanion.MainActivity.MyPreferences;
import static com.example.lenovo.touristcompanion.MainActivity.fName;
import static com.example.lenovo.touristcompanion.MainActivity.lName;

public class CategoryScreen2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemSelectedListener{

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String categoryKey = "categoryKey";

   // SharedPreferences spCategory;

    String item;

    SharedPreferences sp;
    Spinner selectCategory;
    Button btnNext;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNext = (Button)findViewById(R.id.btnSet);
        selectCategory = (Spinner)findViewById(R.id.spinner_category);
        selectCategory.setOnItemSelectedListener(this);

        final SharedPreferences spCategory = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CategoryScreen2.this, item, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor ed = spCategory.edit();
                ed.putString(categoryKey, item);
                ed.commit();
                Intent intent = new Intent(CategoryScreen2.this, GetTimeScreen.class);
                startActivity(intent);
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("historical_places");
        categories.add("entertainment");
        categories.add("shopping");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        selectCategory.setAdapter(dataAdapter);

        sp = getApplication().getSharedPreferences(MyPreferences, MODE_PRIVATE);

        String fn = sp.getString(fName, "");
        String ln = sp.getString(lName, "");

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_category_screen2, null);
        TextView name = headerView.findViewById(R.id.name);
        CharSequence n = name.getText();
       // Toast.makeText(this, n, Toast.LENGTH_LONG).show();

        n = fn + " " + ln;
       // Toast.makeText(this, n, Toast.LENGTH_LONG).show();

        name.setText(fn + " " + ln);
       // Toast.makeText(this, "textview " + n, Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.addHeaderView(headerView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_screen2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings unwanted", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cc) {
            startActivity(new Intent(CategoryScreen2.this, CurrencyConverter.class));
            Toast.makeText(this, "Currency Converter", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_ride) {
            startActivity(new Intent(CategoryScreen2.this, BookARide.class));
            Toast.makeText(this, "Book a ride", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "View Profile", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_hungry) {
            Toast.makeText(this, "Hungry", Toast.LENGTH_LONG).show();

        } else if(id == R.id.nav_logout) {
            Toast.makeText(this, "please work2", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryScreen2.this, LogoutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}