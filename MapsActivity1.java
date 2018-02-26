package com.example.lenovo.touristcompanion;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

import static com.example.lenovo.touristcompanion.CategoryScreen2.MyPREFERENCES;
import static com.example.lenovo.touristcompanion.CategoryScreen2.categoryKey;
import static com.example.lenovo.touristcompanion.R.id.add;
import static com.example.lenovo.touristcompanion.R.id.map;

public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    SharedPreferences sharedPreferences;
    Button entertainment;
    Button confirm_route;
    Button show;

    String TAG = "placevicinity";

    String pLat = "";
    String pLng = "";
    String pName = "try";

    double Lat = 28.644800, Lng = 77.216721;

    FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRefPlace = firebasedatabase.getReference("places2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

        confirm_route = (Button)findViewById(R.id.btnConfirmRoute);

        confirm_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(MapsActivity1.this);
                alertdialog.setTitle("Confirm Route");
                alertdialog.setMessage("Do you wish to start the tour?");
                alertdialog.setIcon(R.drawable.start_tour_icon);
                alertdialog.setPositiveButton("Start Tour", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(MapsActivity1.this, SiteInformation.class);
                        startActivity(intent);
                    }
                });
                alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MapsActivity1.this, "Edit the Route MothaFoker", Toast.LENGTH_SHORT).show();
                    }
                });
                alertdialog.show();
            }
        });



        entertainment = (Button)findViewById(R.id.btnEntertainment);
        entertainment.setEnabled(false);

        show = (Button)findViewById(R.id.btnShow);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplication().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String category = sharedPreferences.getString(categoryKey, "");
                Toast.makeText(MapsActivity1.this, category, Toast.LENGTH_SHORT).show();
                ValueEventListener q = myRefPlace.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot places2 : dataSnapshot.getChildren()) {
                                Log.e(TAG, "places found");
                                places p2 = new places();
                                p2.getPlace_name();
//                                p2.getLatitude();
//                                p2.getLongitude();
//                                String value = dataSnapshot.getValue(String.class);
//                                Toast.makeText(MapsActivity1.this, value, Toast.LENGTH_LONG).show();
//                                String place_name = p2.getPlace_name();
//                                Toast.makeText(MapsActivity1.this, place_name, Toast.LENGTH_SHORT).show();
                                pName = places2.child("place_name").getValue(String.class);
                                pLat = places2.child("latitude").getValue(String.class);
                                pLng = places2.child("longitude").getValue(String.class);

                                Lat = Double.parseDouble(pLat);
                                Lng = Double.parseDouble(pLng);

                                Toast.makeText(MapsActivity1.this, pName + ": " + Lat + ", " + Lng, Toast.LENGTH_LONG).show();

                                addMarker(mMap);

                            }
                        } else
                            Log.e(TAG, "data not found");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                Log.e(TAG, q);
//                Toast.makeText(MapsActivity1.this, q, Toast.LENGTH_LONG).show();
//                String pid = myRefPlace.child("-L2GvpmLNyb1cUzOupR0").child("category").toString();
//                Toast.makeText(MapsActivity1.this, pid, Toast.LENGTH_LONG).show();
//                Query query = myRefPlace.child("places2").child("-L2GvpmLNyb1cUzOupR0").orderByChild("category").equalTo("shopping");
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()) {
//                            for(DataSnapshot places2 : dataSnapshot.getChildren()) {
//                                Log.e(TAG, "place found");
//                            }
//                        }
//                        else
//                            Log.e(TAG, "data not found");
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    //handles permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                //permission is denied
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            LatLng sydney = new LatLng(-33.852, 151.211);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Sydney"));
        }

    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();

    }

    public void onClick(View view) {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        DataParser dp = new DataParser();
        String url;
        switch (view.getId()) {
            case R.id.btnSearch:
                EditText tf_location = (EditText)findViewById(R.id.etSearchLocation);
                String location = tf_location.getText().toString();
                List<Address> addresslist = null;
                MarkerOptions mo = new MarkerOptions();

                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addresslist = geocoder.getFromLocationName(location, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < addresslist.size(); i++) {
                        Address myAddress = addresslist.get(i);
                        LatLng latlng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                        mo.position(latlng);
                        mo.title("Search Results");
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                      //  String result = addresslist.get(i).getAddressLine(i);
                      //  Toast.makeText(this, result+latlng, Toast.LENGTH_LONG).show();
                    }

                }
                break;

            case R.id.btnEntertainment: {
                mMap.clear();
                String entertainment = "supermarket";
                url = getUrl(latitude, longitude, entertainment);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);

                Toast.makeText(this, url, Toast.LENGTH_LONG).show();

               // Toast.makeText(this, "Nearby Entertainment Places", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyDtQgtYi0zd88FQasVm4fTtCBNNJvEfTZ0");

        Log.d("MapsActivity1", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //FusedLocationApi for getting current location
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }

    }

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }


    //places autocomplete
    /*public void findPlace(View view) {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String TAG = "";
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    } */

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void addMarker(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(Lat, Lng)).title(pName));
    }
}
