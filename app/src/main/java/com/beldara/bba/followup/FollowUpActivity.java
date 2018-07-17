package com.beldara.bba.followup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
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
import com.beldara.bba.core.requestmodel.FollowupRequestEntity;
import com.beldara.bba.core.response.FollowUpSaveResponse;
import com.beldara.bba.core.response.StatusResponse;
import com.beldara.bba.dashboard.HomeActivity;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.splash.PrefManager;
import com.beldara.bba.utility.DateTimePicker;
import com.beldara.bba.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class FollowUpActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {

    ArrayList<String> arrayStatus;
    ArrayAdapter<String> statusAdapter;
    ArrayAdapter<String> companyAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    EditText etSupplierName, etSupplierMobile, etCompanyName, etemailID, etDate, etRemark;
    Spinner spStatus ;
    Button btnSubmit;
    List<StatusEntity> lstStatus;
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    LeadEntity leadEntity;

    FollowupRequestEntity followupRequestEntity;
    PrefManager prefManager;

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
                                //  audioFile = (File) extras.get("SUPPLIER_DETAILS");

                                etSupplierName.setText(leadEntity.getSellerName());
                                etCompanyName.setText(leadEntity.getCompany());
                                etSupplierMobile.setText(leadEntity.getMobile());
                                etemailID.setText(leadEntity.getEmail());
                                etDate.setText("");


                            }
                        }
                    }

                    //endregion
                }
            }
        }
      else  if (response instanceof FollowUpSaveResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {

                Toast.makeText(FollowUpActivity.this,((FollowUpSaveResponse) response).getMessage(),Toast.LENGTH_SHORT).show();

                startActivity(new Intent(FollowUpActivity.this, HomeActivity.class));
                finish();


            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_submit) {

            Utility.hideKeyBoard(view, FollowUpActivity.this);

            // region Validation

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
            followupRequestEntity.setSeller_id(leadEntity.getSellerid());
            followupRequestEntity.setRemark(etRemark.toString());
            followupRequestEntity.setFollowup_date(strDate);
            followupRequestEntity.setIp("");
            followupRequestEntity.setLang("0");
            followupRequestEntity.setLat("0");

            showDialog();
            new RegisterController(FollowUpActivity.this).saveFollowUo( followupRequestEntity ,this);
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
        finish();
        super.onBackPressed();
    }
}
