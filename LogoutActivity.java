package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class LogoutActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        animate();

        loginButton = (LoginButton)findViewById(R.id.login_button);
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                Profile profile = Profile.getCurrentProfile();
//                if (profile != null) {
//                    new LoadProfileImage(profilePicture).execute(profile.getProfilePictureUri(200, 200).toString());
//
//                }

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Intent i = new Intent(LogoutActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });

                Bundle parameters = new Bundle();

                parameters.putString("fields", "first_name, last_name, email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                Bundle params = new Bundle();
                params.putString("fields", "id,email,gender,cover,picture.type(large)");
                new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
//                        if (response != null) {
//                            try {
//                                JSONObject data = response.getJSONObject();
//                                if (data.has("picture")) {
//                                    URL profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
//                                    Bitmap profilePic= BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream());
//                                    mImageView.setBitmap(profilePic);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {

        }
    }

    private void animate()
    {
        ImageView imgView = (ImageView)findViewById(R.id.img2);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.animation2);
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
