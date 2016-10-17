package com.example.windows8.mirasoltiresupplyv3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Rescue extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText et_name, et_location, et_contact;
    private Button btn_SR, back;
    private CheckBox tires, wheels, batteries;
    GPSTracker gps = new GPSTracker(this);
    Location location;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);
        setUpMapIfNeeded();


    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(10.678923,122.9623906) , 14.0f) );
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Rescue.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable GPS. Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Rescue.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        et_name = (EditText) findViewById(R.id.rescueName);
        et_location = (EditText) findViewById(R.id.rescueLocation);
        et_contact = (EditText) findViewById(R.id.rescueContact);

        tires = (CheckBox) findViewById(R.id.cb_tires);
        wheels = (CheckBox) findViewById(R.id.cb_wheels);
        batteries = (CheckBox) findViewById(R.id.cb_batteries);
        location = gps.getLocation();

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomm = new Intent(Rescue.this, MainMenu.class);
                Rescue.this.startActivity(backtomm);
                finish();
            }
        });
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());

        } else {
            showSettingsAlert();
        }



        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()) , 14.0f) );
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));


        if(Integer.parseInt(globalValues.user_Id) > 0){
            et_name.setText(globalValues.user_LName + ", " + globalValues.user_FName);
        }
        if(!globalValues.user_Contact.equals("")){
            et_contact.setText(globalValues.user_Contact);
        }
        btn_SR = (Button) findViewById(R.id.sendRescue);

        btn_SR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, loc, no;
                String requisition = "";
                ArrayList<String> s = new ArrayList<String>();

                if(tires.isChecked()){
                    s.add("Tires");
                }
                if(wheels.isChecked()){
                    s.add("Wheels");
                }
                if(batteries.isChecked()){
                    s.add("Batteries");
                }

                for(int i = 0; i < s.size(); i++){
                    requisition += s.get(i).toString() + " ";
                }
                name = et_name.getText().toString();
                loc = et_location.getText().toString();
                no = et_contact.getText().toString();
                NetRequestAsync request = new NetRequestAsync(name, loc, no, requisition, 0);
                request.execute();

                Toast toast = Toast.makeText(getApplicationContext(), "Rescue details sent to Mirasol Tire Supply", Toast.LENGTH_SHORT);
                toast.show();

                Intent mainIntent = new Intent(Rescue.this, MainMenu.class);
                Rescue.this.startActivity(mainIntent);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));

                if (location != null) {
                    double latitude = point.latitude;
                    double longitude = point.longitude;
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());

                } else {
                    showSettingsAlert();
                }
            }
        });
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            et_location.setText(locationAddress);
        }
    }


}



