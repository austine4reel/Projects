package com.example.austi.thingsee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import javax.xml.datatype.Duration;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int    MAXPOSITIONS = 40;
    private static final String  data = "places";

    private GoogleMap mMap;
    LatLng latLng;
    Duration timeTaken;
    private static final int LOCATION_REQUEST= 500;
    List<LatLng> lt;
    PolylineOptions polylineOptions;
    private String               username, password, list;
    String nam, pas;



    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extra = getIntent().getExtras();
        latLng = new LatLng(extra.getDouble("lat"), extra.getDouble("lon"));


        //timeTaken = new Duration(extra.getLong("timeTaken"));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getTrack()
    {

            polylineOptions = new PolylineOptions();

            for (int i = 0; i < User.getsize(); i++) {

                polylineOptions.add(new LatLng(User.getlat(i),User.getlng(i)));
            }


            polylineOptions.color( Color.BLUE );
            polylineOptions.width( 10 );
            polylineOptions.visible( true );




    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("username", nam);
        intent.putExtra("password", pas);
        startActivity(intent);
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Turku"));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }

        mMap.setMyLocationEnabled(true);
        getTrack();
        mMap.addPolyline(polylineOptions);


    }

    private void getLatLang()
    {
        SharedPreferences prefGet = getSharedPreferences(data, MODE_PRIVATE);

        String list = prefGet.getString("name", "set");

    }






    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case  LOCATION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }
}
