package com.dotcom.maptestapp;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{


    GoogleMap mMap;
    LocationManager locationManager;
    double latitude,longitude;
    double end_latitide,end_longitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GSA()) { Toast.makeText(this, "Services Perfact", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_maps);

        initMap();

        }
        else
        {

        }


    }

    private void initMap()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager() .findFragmentById(R.id.map); mapFragment.getMapAsync(this);
    }
    public boolean GSA()
    {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS)
            return true;
        else if (googleApiAvailability.isUserResolvableError(isAvailable))
        {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }
        else
        {
            Toast.makeText(this, "Can't connect to Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;

//gotoLocation(29.964744, 74.692349,10);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
// TODO: Consider calling
// ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
// public void onRequestPermissionsResult(int requestCode, String[] permissions,
// int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(latitude,longitude); CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,13);
        mMap.animateCamera(cameraUpdate);
        gotoLocationzoom(latitude,longitude,5);
    }

    private void gotoLocationzoom(double lat,double lng,float zoom)
    {
        LatLng latLng = new LatLng(lat,lng); CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,zoom);

        mMap.animateCamera(cameraUpdate);
    }
    public void Search(View view) throws IOException
    {
        EditText et = (EditText) findViewById(R.id.search);
        String location = et.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location,1);
        Address address = list.get(0);
        String locality = address.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        gotoLocationzoom(lat,lng,15);
        LatLng latLng = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        if (id == R.id.mapTypeNone) {
            mMap.setMapType(mMap.MAP_TYPE_NONE);

            return true;
        }
        if (id == R.id.mapTypeSatellite) {
            mMap.setMapType(mMap.MAP_TYPE_SATELLITE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}