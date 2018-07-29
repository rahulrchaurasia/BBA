package com.beldara.bba.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.dashboard.HomeActivity;
import com.beldara.bba.location.ILocationStateListener;
import com.beldara.bba.location.LocationTracker;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.utility.Constants;
import com.google.android.gms.location.LocationRequest;

public class SplashScreenActivity extends BaseActivity  implements ILocationStateListener {

    TextView txtGroup;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    PrefManager prefManager;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1111;
    LocationTracker locationTracker;
    LocationRequest locationRequest;
    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.SEND_SMS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.CALL_PHONE",
            "android.permission.RECORD_AUDIO",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.ACCESS_COARSE_LOCATION"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        txtGroup = (TextView) findViewById(R.id.txtGroup);
        prefManager = new PrefManager(SplashScreenActivity.this);
        locationTracker = new LocationTracker(this);


        if (!checkPermission()) {
            requestPermission();
        }else{
            verify();
        }

    }

    private boolean checkPermission() {

        int camera = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);
        int fineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int sendSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[2]);
        int readSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[3]);
        int receiveSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[4]);
        int WRITE_EXTERNAL = ContextCompat.checkSelfPermission(getApplicationContext(), perms[5]);
        int READ_EXTERNAL = ContextCompat.checkSelfPermission(getApplicationContext(), perms[6]);
        int callPhone = ContextCompat.checkSelfPermission(getApplicationContext(), perms[7]);
        int recordAudio = ContextCompat.checkSelfPermission(getApplicationContext(), perms[8]);
        int writeLog = ContextCompat.checkSelfPermission(getApplicationContext(), perms[9]);
        int access_coarse_location = ContextCompat.checkSelfPermission(getApplicationContext(), perms[10]);
        return camera == PackageManager.PERMISSION_GRANTED
                && fineLocation == PackageManager.PERMISSION_GRANTED
                && sendSms == PackageManager.PERMISSION_GRANTED
                && readSms == PackageManager.PERMISSION_GRANTED
                && receiveSms == PackageManager.PERMISSION_GRANTED
                && WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED
                && callPhone == PackageManager.PERMISSION_GRANTED
                && recordAudio == PackageManager.PERMISSION_GRANTED
                && writeLog == PackageManager.PERMISSION_GRANTED
                && access_coarse_location == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, REQUEST_CODE_ASK_PERMISSIONS);
    }

    public void verify() {
        if (!Constants.checkInternetStatus(SplashScreenActivity.this)) {

            Snackbar snackbar = Snackbar.make(txtGroup, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.CYAN);

            snackbar.show();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //location callback method
                    locationTracker.setLocationStateListener(SplashScreenActivity.this);

                    //GoogleApiClient initialisation and location update
                    locationTracker.init();

                    //GoogleApiclient connect
                    locationTracker.onResume();

                if (prefManager.getUserID() != null  && !prefManager.getUserID().equals("")) {
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, loginActivity.class));
                }
                }
            }, SPLASH_DISPLAY_LENGTH);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean sendSms = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean readSms = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean receiveSms = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternal = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean callPhone = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean recordAudio = grantResults[8] == PackageManager.PERMISSION_GRANTED;
                    boolean writeLog = grantResults[9] == PackageManager.PERMISSION_GRANTED;
                    boolean accessCoraseLoc = grantResults[9] == PackageManager.PERMISSION_GRANTED;



                    if (camera && fineLocation && sendSms && readSms && receiveSms && writeExternal && readExternal && callPhone && recordAudio && writeLog && accessCoraseLoc) {


                        verify();

                    } else {

                        //Permission Denied, You cannot access location data and camera
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            showMessageOKCancel("Required permissions to proceed BBA..!",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // finish();
                                            requestPermission();
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashScreenActivity.this)
                .setTitle("Retry")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected() {
        Location location = locationTracker.mLocation;
    }

    @Override
    public void onConnectionFailed() {

    }
}
