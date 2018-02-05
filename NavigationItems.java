package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by LENOVO on 15-Dec-17.
 */

public class NavigationItems extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.currency_converter:
//                Intent i = new Intent(NavigationItems.this, CurrencyConvertor.class);
//                startActivity(i);
//            case R.id.hungry:
//                Intent i2 = new Intent(NavigationItems.this, Hungry.class);
//                startActivity(i2);
//        }
        return true;
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigationViewListner();
    }
}
