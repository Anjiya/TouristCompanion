package com.example.lenovo.touristcompanion;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class BookARide extends AppCompatActivity {

    TextView link;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_aride);

        animate();

        link = (TextView)findViewById(R.id.careemlink);
        String linkText = "Get a  <a href = 'http://www.careem.com'>Careem</a>!";
        link.setText(Html.fromHtml(linkText));
     //   link.setText(Html.fromHtml(linkText, FROM_HTML_MODE_LEGACY));
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void animate()
    {
        ImageView imgView = (ImageView)findViewById(R.id.img1);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) imgView.getBackground();
        if (frameAnimation.isRunning())
        {
            frameAnimation.stop();
        }
        else
        {
            frameAnimation.stop();
            frameAnimation.start();
        }
    }
}
