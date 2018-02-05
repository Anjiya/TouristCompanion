package com.example.lenovo.touristcompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by LENOVO on 31-Oct-17.
 */

public class SplashScreen extends Activity {

    private static int splash_timeout = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i1 = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i1);
                finish();
            }
        }, splash_timeout);
    }
}
