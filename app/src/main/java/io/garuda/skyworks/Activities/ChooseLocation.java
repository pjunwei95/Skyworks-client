package io.garuda.skyworks.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;

import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseLocation extends AppCompatActivity implements Serializable, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    Service service;
    User user;
    Bundle extras;
    Button pilot_button;
    GoogleMap googleMap;
    ArrayList<LatLng> arrayPoints = new ArrayList<LatLng>();
    PolylineOptions polylineOptions;
    boolean checkClick = false;
    LocationListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.garuda.skyworks.R.layout.activity_choose_location);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Draw Your Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();
        service = (Service) extras.get("SERVICE");


        //bind views
        pilot_button = (Button) findViewById(io.garuda.skyworks.R.id.pilot);

        //listener
        pilot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLocation.this, Providers.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("SERVICE", service);
                mBundle.putSerializable("USER", user);
                mBundle.putSerializable("LOC", arrayPoints);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });


        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = fm.getMap();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        LatLng coordinate = new LatLng(1.3521, 103.8198);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, (float) 10.5);
        googleMap.animateCamera(yourLocation);
    }


    @Override
    public void onMapClick(LatLng point) {
        if (checkClick == false) {
            googleMap.addMarker(new MarkerOptions().position(point));
            arrayPoints.add(point);
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {
        googleMap.clear();
        arrayPoints.clear();
        checkClick = false;
    }

    public void countPolygonPoints() {
        if (arrayPoints.size() >= 3) {
            checkClick = true;
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(arrayPoints);
            polygonOptions.strokeColor(R.color.colorPrimaryDark);
            polygonOptions.strokeWidth(7);
            polygonOptions.fillColor(0x55607D8B);
            Polygon polygon = googleMap.addPolygon(polygonOptions);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (arrayPoints.get(0).equals(marker.getPosition())) {
            countPolygonPoints();
        }
        return false;
    }




    //setups links for menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(this, ChooseService.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.home2:
                i = new Intent(ChooseLocation.this, ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.editProfile:
                i = new Intent(ChooseLocation.this, EditProfile.class);
                extras.putSerializable("CALLER", ChooseLocation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(ChooseLocation.this, Notifications.class);
                extras.putSerializable("CALLER", ChooseLocation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(ChooseLocation.this, MyWallet.class);
                extras.putSerializable("CALLER", ChooseLocation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(ChooseLocation.this, MyServices.class);
                extras.putSerializable("CALLER", ChooseLocation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}
