package com.exampledemo.Group02_Group03.coe125;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import android.location.Address;
import android.location.Geocoder;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.app.NotificationManager;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = "MainActivity";


    private double dist;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mCurrentLocationTextView;
    private TextView mCurrentRead;
    private TextView mCurrentRead_textview;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private Location homeLocation;
    private LocationManager mLocationManager;
    private ToggleButton Togg;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */



    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeTextView = (TextView) findViewById((R.id.latitude_textview));
        mLongitudeTextView = (TextView) findViewById((R.id.longitude_textview));
        mCurrentLocationTextView = (TextView) findViewById((R.id.location_textview));
        mCurrentRead = (TextView) findViewById((R.id.CurrentRead));
        mCurrentRead_textview =  (TextView) findViewById((R.id.CurrentRead_textview));
        mCurrentLocationTextView = (TextView) findViewById((R.id.location_textview));
        Togg=(ToggleButton) findViewById(R.id.toggleButton);
        Togg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCurrentRead.setVisibility(View.VISIBLE);
                    mCurrentRead_textview.setVisibility(View.VISIBLE);
                } else {
                    mCurrentRead.setVisibility(View.INVISIBLE);
                    mCurrentRead_textview.setVisibility(View.INVISIBLE);
                }
            }
        });

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);


        checkLocation(); //check whether location service is enable or not in your  phone


    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)

                        .setContentTitle("Notifications Example")
                        .setContentText("You are not at home");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onConnected(Bundle bundle) {
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

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

            mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLngA = new LatLng(14.763322,121.021325);
        LatLng latLngB = new LatLng(location.getLatitude(),location.getLongitude());

        Location locationA = new Location("point A");
        locationA.setLatitude(latLngA.latitude);
        locationA.setLongitude(latLngA.longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(latLngB.latitude);
        locationB.setLongitude(latLngB.longitude);
        double distance = locationA.distanceTo(locationB);


            String msg = "Updated Location: " +
                    Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude());
            mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
            mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
            mCurrentLocationTextView.setText(getCompleteAddressString(location.getLatitude(), location.getLongitude()));
            //

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            // You can now create a LatLng Object for use with maps
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


            addNotification();

        //dist = mLocation.distanceTo(homeLocation);



    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }

                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
