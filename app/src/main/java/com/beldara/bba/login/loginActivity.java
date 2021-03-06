package com.beldara.bba.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.controller.register.RegisterController;
import com.beldara.bba.core.response.LoginResponse;
import com.beldara.bba.dashboard.HomeActivity;
import com.beldara.bba.location.LocationTracker;
import com.beldara.bba.register.RegisterActivity;
import com.beldara.bba.splash.PrefManager;

public class loginActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {

    TextView tvRegistration, tvForgotPassword;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1111;
    EditText etPassword, etMobile;
    LocationTracker locationTracker;
    PrefManager prefManager;
    Button btnSignIn;
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
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        initialize();
        prefManager = new PrefManager(this);

//        String mob = prefManager.getMobile();
//        String pwd = prefManager.getPassword();
//
//        etMobile.setText(mob);
//        etPassword.setText(pwd);

        if (!checkPermission()) {
            requestPermission();
        }

    }

    private void initialize() {

        etPassword = (EditText) findViewById(R.id.etPassword);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);
        tvRegistration.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvRegistration:
                startActivity(new Intent(loginActivity.this, RegisterActivity.class));
                break;

            case R.id.tvForgotPassword:
               // startActivity(new Intent(loginActivity.this, ForgotPasswordActivity.class));
                break;
            case R.id.btnSignIn:
                if (!isEmpty(etMobile)) {
                    etMobile.requestFocus();
                    Snackbar.make(etMobile, "Enter UserID", Snackbar.LENGTH_LONG).show();
                    // etMobile.setError("Enter Mobile");
                    return;
                }
                if (!isEmpty(etPassword)) {
                    etPassword.requestFocus();
                    Snackbar.make(etMobile, "Enter Password", Snackbar.LENGTH_LONG).show();
                    //  etPassword.setError("Enter Password");
                    return;
                }

                showDialog("Please Wait...");
                new RegisterController(loginActivity.this).getLogin(etMobile.getText().toString(), etPassword.getText().toString(),"0","0","2", this);


                break;
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


    @Override
    public void OnSuccess(APIResponse response, String message) {


        if (response instanceof LoginResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {


                prefManager.setUser(etMobile.getText().toString());
                prefManager.setPassword(etPassword.getText().toString());
                prefManager.setUserID(((LoginResponse) response).getResult().get(0).getId());
                prefManager.setType(((LoginResponse) response).getResult().get(0).getType());

                startActivity(new Intent(loginActivity.this, HomeActivity.class));
                finish();
            }else {
                cancelDialog();
            }
        }
        else {
            cancelDialog();
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, REQUEST_CODE_ASK_PERMISSIONS);
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
                        // you can do all necessary steps
                        // new Dialer().getObject().getLeadData(String.valueOf(Utility.EmpCode), this, this);
                        // Toast.makeText(this, "All permission granted", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(loginActivity.this)
                .setTitle("Retry")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
