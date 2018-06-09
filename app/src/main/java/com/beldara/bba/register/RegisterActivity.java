package com.beldara.bba.register;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;

import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.controller.register.RegisterController;
import com.beldara.bba.core.model.RegisterEnity;
import com.beldara.bba.core.response.RegisterResponse;
import com.beldara.bba.login.loginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {


    EditText etFirstName, etLastName, etPanNo, etMobile1, etPassworde, etEmail, etConfirmEmail,
            etPincode, etCity, etState, etOtp;
    // ImageView ivMale, ivFemale;
    Dialog dialog;
    ArrayList<String> healthList, generalList, lifeList;

    Boolean isValidPersonalInfo = false;
    Button btnSubmit;
    RegisterEnity registerRequestEntity;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat passdateFormat = new SimpleDateFormat("ddMMyyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerRequestEntity = new RegisterEnity();
        initWidgets();
        setListener();

    }

    private void initWidgets() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);

        etMobile1 = (EditText) findViewById(R.id.etMobile1);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPanNo = (EditText) findViewById(R.id.etPanNo);
        etPassworde = (EditText) findViewById(R.id.etPassworde);
        etCity = (EditText) findViewById(R.id.etCity);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }
    private void setListener() {

        btnSubmit.setOnClickListener(this);

    }

    private Boolean validateRegister() {
        if (!isEmpty(etFirstName)) {
            etFirstName.requestFocus();
            etFirstName.setError("Enter First Name");
            return false;
        }

        if (!isEmpty(etLastName)) {
            etLastName.requestFocus();
            etLastName.setError("Enter Last Name");
            return false;
        }

        if (!isValidePhoneNumber(etMobile1)) {
            etMobile1.requestFocus();
            etMobile1.setError("Enter Mobile ");
            return false;
        }
        if (!isValideEmailID(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Email");
            return false;
        }

        if (!isEmpty(etPassworde)) {
            etPincode.requestFocus();
            etPincode.setError("Enter Pincode");
            return false;
        }
        if (!isEmpty(etCity)) {
            etCity.requestFocus();
            etCity.setError("Enter City");
            return false;
        }

        return true;
    }

    private void setRegisterPersonalRequest() {
        registerRequestEntity.setFirst_Name("" + etFirstName.getText());
        registerRequestEntity.setLast_Name("" + etLastName.getText());

        registerRequestEntity.setContact_No("" + etMobile1.getText());
        registerRequestEntity.setPAN_No("" + etMobile1.getText());
        registerRequestEntity.setUserPassword("" + etEmail.getText());
        registerRequestEntity.setCity("" + etCity.getText());

            registerRequestEntity.setNewEmpCode("0");

            registerRequestEntity.setParentBrokerId("0");

        registerRequestEntity.setParentEmpCode("0");
        registerRequestEntity.setSource("0");

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSubmit:
                isValidPersonalInfo = validateRegister();

                if (isValidPersonalInfo) {
                    setRegisterPersonalRequest();;

                        showDialog();
                        new RegisterController(this).SaveRegister(registerRequestEntity, this);

                }
                break;
        }
    }
    @Override
    public void OnSuccess(APIResponse response, String message) {
        if (response instanceof RegisterResponse) {
            cancelDialog();
            Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, loginActivity.class));
        }else
        {
            cancelDialog();
        }
    }

    @Override
    public void OnFailure(Throwable t) {

    }
}
