package com.beldara.bba.followup;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.controller.register.RegisterController;
import com.beldara.bba.core.model.LeadEntity;
import com.beldara.bba.core.model.StatusEntity;
import com.beldara.bba.core.model.SupplierEntity;
import com.beldara.bba.core.requestmodel.FollowupRequestEntity;
import com.beldara.bba.core.response.DocumentResponse;
import com.beldara.bba.core.response.FollowUpSaveResponse;
import com.beldara.bba.core.response.StatusResponse;
import com.beldara.bba.core.response.SupplierListResponse;
import com.beldara.bba.dashboard.HomeActivity;
import com.beldara.bba.location.ILocationStateListener;
import com.beldara.bba.location.LocationTracker;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.splash.PrefManager;
import com.beldara.bba.utility.AudioRecorder;
import com.beldara.bba.utility.Constants;
import com.beldara.bba.utility.DateTimePicker;
import com.beldara.bba.utility.Utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MultipartBody;

public class FollowUpActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber, ILocationStateListener {

    ArrayList<String> arrayStatus;
    ArrayAdapter<String> statusAdapter;
    ArrayAdapter<String> companyAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    EditText etSupplierName, etSupplierMobile, etCompanyName, etemailID, etDate, etRemark;
    Spinner spStatus;
    Button btnSubmit;
    List<StatusEntity> lstStatus;
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    LeadEntity leadEntity;
    String SELLER_ID = "0";

    FollowupRequestEntity followupRequestEntity;
    PrefManager prefManager;
    String FOLLOWUP_ID = "0";
    AudioRecorder audioRecorder;
    File audioFile;
    SupplierEntity supplierEntity;

    LocationTracker locationTracker;
    Location location;
    HashMap<String, Integer> body;
    MultipartBody.Part part;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //  audioFile = (File) extras.get("AUDIO_FILE");
            if (getIntent().hasExtra("AUDIO_PATH")) {
                String audio_path = extras.getString("AUDIO_PATH");
                audioFile = new File(audio_path);
            }


        }

        //region init location
        locationTracker = new LocationTracker(FollowUpActivity.this);
        //location callback method
        locationTracker.setLocationStateListener(this);

        //GoogleApiClient initialisation and location update
        locationTracker.init();

        //GoogleApiclient connect
        locationTracker.onResume();
        //endregion
        showDialog("Please Wait...");

        new RegisterController(FollowUpActivity.this).getStatusMaster(FollowUpActivity.this);
    }

    private void initialize() {

        prefManager = new PrefManager(FollowUpActivity.this);
        spStatus = (Spinner) findViewById(R.id.spStatus);

        etSupplierName = (EditText) findViewById(R.id.etSupplierName);
        etSupplierMobile = (EditText) findViewById(R.id.etSupplierMobile);
        etCompanyName = (EditText) findViewById(R.id.etCompanyName);
        etemailID = (EditText) findViewById(R.id.etemailID);
        etRemark = (EditText) findViewById(R.id.etRemark);
        etDate = (EditText) findViewById(R.id.etDate);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        etDate.setOnClickListener(datePickerDialog);
        // loadSpinner();


    }

    private ArrayList<String> getStatusList(List<StatusEntity> lstStatus) {

        map.put("SELECT", "0");
        for (StatusEntity empObject : lstStatus) {

            map.put(empObject.getStatus(), empObject.getId());    // adding in Map

        }

        arrayStatus = new ArrayList<String>(map.keySet());
        return arrayStatus;
    }

    private String getDateFormat(String date_month_year) {
        String dateSelected = "";
        long select_milliseconds = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = new Date();
        try {
            d = f.parse(date_month_year);
        } catch (Exception ex) {

        }
        select_milliseconds = d.getTime();

        Date date = new Date(select_milliseconds); //Another date Formate ie yyyy-mm-dd
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        dateSelected = df2.format(date);
        return dateSelected;

    }

    protected View.OnClickListener datePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utility.hideKeyBoard(view, FollowUpActivity.this);
            DateTimePicker.showDataPickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    String currentDay = simpleDateFormat.format(calendar.getTime());
                    etDate.setText(currentDay);
                    //etDate.setTag(R.id.et_date, calendar.getTime());
                }
            });
        }
    };

    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof StatusResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {
                lstStatus = ((StatusResponse) response).getResult();
                if (lstStatus != null) {
                    statusAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, getStatusList(lstStatus));
                    statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spStatus.setAdapter(statusAdapter);

                    Bundle extras = getIntent().getExtras();

                    //region Get Lead
                    if (extras != null) {
                        if (getIntent().hasExtra("LEAD_DETAILS")) {
                            if (extras.getParcelable("LEAD_DETAILS") != null) {
                                leadEntity = extras.getParcelable("LEAD_DETAILS");

                                SELLER_ID = leadEntity.getSellerid();
                                etSupplierName.setText(leadEntity.getSellerName());
                                etCompanyName.setText(leadEntity.getCompany());
                                etSupplierMobile.setText(leadEntity.getMobile());
                                etemailID.setText(leadEntity.getEmail());
                                etDate.setText("");


                            }
                        }

                        else if (getIntent().hasExtra("DIAL_NUMBER")) {

                            etCompanyName.setEnabled(true);
                            String dialMobNo = extras.getString("DIAL_NUMBER", "");

                            if (!dialMobNo.equals("")) {

                                showDialog();
                                new RegisterController(FollowUpActivity.this).getSupplierList(dialMobNo, this);
                            }

                        }
                    }

                    //endregion
                }
            }
        } else if (response instanceof FollowUpSaveResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {

                FOLLOWUP_ID = ((FollowUpSaveResponse) response).getResult().getFid();
                //  Toast.makeText(FollowUpActivity.this,((FollowUpSaveResponse) response).getMessage(),Toast.LENGTH_SHORT).show();

                if (audioFile != null) {
                    savAudioToDb();
                } else {
                    Toast.makeText(FollowUpActivity.this, ((DocumentResponse) response).getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FollowUpActivity.this, HomeActivity.class));
                    finish();
                }


            }
        } else if (response instanceof DocumentResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {

                if (audioFile != null) {
                    boolean isDeleted = Utility.deleteAudioFile(audioFile.getAbsolutePath());
                }
                Toast.makeText(FollowUpActivity.this, ((DocumentResponse) response).getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FollowUpActivity.this, HomeActivity.class));

                this.finish();

            }
        } else if (response instanceof SupplierListResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {

                List<SupplierEntity> supplierLst = ((SupplierListResponse) response).getResult();

                supplierEntity = supplierLst.get(0);
                SELLER_ID = supplierEntity.getSellerid();
                etSupplierName.setText(supplierEntity.getName());
                etCompanyName.setText(supplierEntity.getCompany());
                etSupplierMobile.setText(supplierEntity.getMobile());
                etemailID.setText(supplierEntity.getEmail());
                etDate.setText("");
            }else{
                showAlert("Not Registered Client...");
            }

    }

}

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FollowUpActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        Log.d("FileUpload", t.getMessage());
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_submit) {

            Utility.hideKeyBoard(view, FollowUpActivity.this);

            if (!Utility.checkGpsStatus(getApplicationContext())) {

                showSettingsAlert();
            }
            //Gps On
           else {

                //  region Validation

                if (etCompanyName.getText().toString().matches("")) {
                    etCompanyName.setError("Enter Company  Name");
                    etCompanyName.requestFocus();
                    return;
                }
                if (etSupplierName.getText().toString().matches("")) {
                    etSupplierName.setError("Enter Supplier Name");
                    etSupplierName.requestFocus();
                    return;
                }

                if (etSupplierMobile.getText().toString().matches("")) {
                    etSupplierMobile.setError("Enter Mobile");
                    etSupplierMobile.requestFocus();
                    return;
                }
                if (etSupplierMobile.getText().toString().trim().length() < 10) {
                    etSupplierMobile.setError("Enter Valid Mobile");
                    etSupplierMobile.requestFocus();
                    return;
                }
                if (etemailID.getText().toString().matches("")) {
                    etemailID.setError("Enter Email ID");
                    etemailID.requestFocus();
                    return;
                }
                if (!isValideEmailID(etemailID)) {
                    etemailID.setError("Enter Valid Email ID");
                    etemailID.requestFocus();
                    return;
                }


                if (spStatus.getSelectedItem().toString().toUpperCase().equals("SELECT")) {
                    Snackbar.make(etSupplierMobile, "Select the Status", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (etDate.getText().toString().matches("")) {
                    Snackbar.make(etSupplierMobile, "Select the Date", Snackbar.LENGTH_SHORT).show();
                    return;
                }


                if (etRemark.getText().toString().matches("")) {
                    etRemark.setError("Enter Remark");
                    etRemark.requestFocus();
                    return;
                }

                followupRequestEntity = new FollowupRequestEntity();
                String strDate = "";
                try {
                    strDate = getDateFormat(etDate.getText().toString());
                } catch (Exception ex) {
                    strDate = "";
                }

                String id = map.get(spStatus.getSelectedItem().toString());
                followupRequestEntity.setUser_id(prefManager.getUserID());
                followupRequestEntity.setStatus_id(id);
                followupRequestEntity.setSeller_id(SELLER_ID);
                followupRequestEntity.setRemark(etRemark.getText().toString());
                followupRequestEntity.setFollowup_date(strDate);
                followupRequestEntity.setIp("");
                followupRequestEntity.setLang("" + location.getLongitude());
                followupRequestEntity.setLat("" + location.getLatitude());

                //endregion

                showDialog();
                new RegisterController(FollowUpActivity.this).saveFollowUp(followupRequestEntity, this);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCE_TITLE, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Utility.CALL_STATUS, "NO");
        editor.commit();

        finish();
        super.onBackPressed();
    }

    public void savAudioToDb() {
        audioRecorder = new AudioRecorder();
        if (audioFile != null) {

            part = Utility.getMultipartAudio(audioFile);
            //  body = Utility.getBody(FollowUpSupplierActivity.this, Integer.valueOf(userID), 0, 0, 1, 0);
            body = Utility.getBody(FollowUpActivity.this, Integer.valueOf(prefManager.getUserID()), Integer.valueOf(SELLER_ID), 1, Integer.valueOf(FOLLOWUP_ID));

            new RegisterController(this).uploadDocuments(part, body, FollowUpActivity.this);
        }
    }

    @Override
    public void onLocationChanged(Location tmplocation) {
        location = locationTracker.mLocation;
    }

    @Override
    public void onConnected() {
        location = locationTracker.mLocation;
    }

    @Override
    public void onConnectionFailed() {
        location = null;
    }
}
