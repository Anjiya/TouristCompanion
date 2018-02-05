package com.example.lenovo.touristcompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    ImageView profilePicture;
    Button btnNext;

    SharedPreferences sp;

    public static final String MyPreferences = "MyPrefs";
    public static final String Email = "KEY_EMAIL";
    public static final String fName = "KEY_FNAME";
    public static final String lName = "KEY_LNAME";

    FirebaseAuth auth;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryScreen2.class);
                startActivity(intent);
            }
        });

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        auth = FirebaseAuth.getInstance();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
//        myRef2 = database.getReference("places");
//
//        String place_id = myRef2.push().getKey();
//        String category = "shopping", location = "sea view, clifton";
//        double star_rating = 4.0;
//        places p = new places(place_id, category, location, star_rating);
//        myRef2.push().setValue(p);

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
                        Intent i = new Intent(MainActivity.this, CategoryScreen2.class);
                        startActivity(i);
                        displayUserInfo(object);
//                        saveUserInfo(object);
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

    }

    public void displayUserInfo(JSONObject object)
    {
        sp = getApplicationContext().getSharedPreferences(MyPreferences, MODE_PRIVATE);
        String email, fname, lname, placesVisited;
  //      email = "";
        fname = "";
        lname = "";
        placesVisited = "";
        try {
 //           email = object.getString("email");
            fname = object.getString("first_name");
            lname = object.getString("last_name");

 //           Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

 //       Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor ed = sp.edit();

 //       ed.putString(Email, email);
        ed.putString(fName, fname);
        ed.putString(lName, lname);
        ed.commit();

       // loadPreferences();

//        Intent i2 = new Intent(MainActivity.this, CategoryScreen.class);
//        startActivity(i2);

        String id = myRef.push().getKey();
        User user = new User(id, fname, lname, placesVisited);
        myRef.push().setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
                final String TAG = "TasksSample";
                if (databaseError != null) {
                    Log.e(TAG, "Failed to write message", databaseError.toException());
                }
            }
        });

      /*  TextView tv_name;

        tv_name = (TextView)findViewById(R.id.TV_name);
        tv_name.setText("Welcome " + fname + " " + lname); */
    }
//    public void saveUserInfo(JSONObject object)
//    {
//        String email = "";
//        String fname ="", lname = "";
//        try {
//            email = object.getString("email");
//            fname = object.getString("first_name");
//            lname = object.getString("last_name");
//
//            Toast.makeText(this, email+fname+lname, Toast.LENGTH_SHORT).show();
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String id = myRef.push().getKey();
//        User user = new User(id, email, fname, lname);
//        myRef.push().setValue(user, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
//                final String TAG = "TasksSample";
//                if (databaseError != null) {
//                    Log.e(TAG, "Failed to write message", databaseError.toException());
//                }
//            }
//        });
//    }

   /* public void loadPreferences()
    {
        tv_name.setText(sp.getString(fName, ""));
    } */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {

//            Intent i = new Intent(MainActivity.this, CategoryScreen.class);
//            startActivity(i);
            btnNext.setVisibility(View.VISIBLE);

        }
    }
}
