package com.beldara.bba.location;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.beldara.bba.utility.Constants;
import com.beldara.bba.utility.Utility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import java.util.Calendar;


/**
 * Created by devdeeds.com on 27-09-2017.
 */

public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final String TAG = LocationService.class.getSimpleName();
    private static final String LOC = "LOCATION";
    GoogleApiClient mLocationClient;

    LocationRequest mLocationRequest ;
    Location mylocation;
    String mMsgView ="";

    public static final String ACTION_LOCATION_BROADCAST = LocationService.class.getName() + "LocationBroadcast";
    public static final String ACTION_GPS_BROADCAST = LocationService.class.getName() + "GPSBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create();
      //  mLocationRequest.setSmallestDisplacement(100);
        mLocationRequest.setInterval(Constants.LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(Constants.FASTEST_LOCATION_INTERVAL);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes

        mLocationRequest.setPriority(priority);
        mLocationClient.connect();
        Log.d(LOC,"Start Command Fired....!!!");
        if(mylocation != null) {
            if (!Utility.checkGpsStatus(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "NO GPS Connecte", Toast.LENGTH_SHORT).show();
            } else {
                mMsgView = ("Lat and Long " + "\n Latitude : " + String.valueOf(mylocation.getLatitude()) + "\n Longitude: " + String.valueOf(mylocation.getLongitude()));
                Toast.makeText(getApplicationContext(), mMsgView, Toast.LENGTH_LONG).show();
            }
        }


        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }


    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");


        if (location != null) {
            Log.d(TAG, "== location != null");
            Log.d(LOC,"Location "+ String.valueOf(location.getLatitude()+ " "+ String.valueOf(location.getLongitude())));
            mylocation = location;
            //Send result to activities
         //   sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

          //  Toast.makeText(getApplicationContext(), "Loc Changed .....", Toast.LENGTH_LONG).show();
        }

    }



    private void sendMessageToUI(String lat, String lng) {

        Log.d(TAG, "Sending info...");

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void sendGPSMessageToUI() {

        Log.d(TAG, "Sending GPS OFF info...");

        Intent intent = new Intent(ACTION_GPS_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getAlarmService();
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        getAlarmService();
    }


    private void getAlarmService()
    {

        Intent myIntent = new Intent(getApplicationContext(), LocationService.class);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.SECOND, 10);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Utility.TIME_INTERVAL, pendingIntent);

        Toast.makeText(getApplicationContext(), "Start Alarm", Toast.LENGTH_SHORT).show();
    }
}