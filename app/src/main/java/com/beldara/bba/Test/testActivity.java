package com.beldara.bba.Test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;

public class testActivity   extends BaseActivity implements View.OnClickListener {
    EditText etPassword, etMobile;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
    }

    private void initialize() {

   //     etPassword = (EditText) findViewById(R.id.etPassword);
    //    etMobile = (EditText) findViewById(R.id.etMobile);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
      //  tvRegistration = (TextView) findViewById(R.id.tvRegistration);
      ///  tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);



        btnSignIn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignIn:
//                if (!isEmpty(etMobile)) {
//                    etMobile.requestFocus();
//                    Snackbar.make(etMobile, "Enter UserID", Snackbar.LENGTH_LONG).show();
//                    // etMobile.setError("Enter Mobile");
//                    return;
//                }
//                if (!isEmpty(etPassword)) {
//                    etPassword.requestFocus();
//                    Snackbar.make(etMobile, "Enter Password", Snackbar.LENGTH_LONG).show();
//                    //  etPassword.setError("Enter Password");
//                    return;
//                }

                new Async(testActivity.this, "9920298619").execute();
             //   new InsertAddress(testActivity.this).test("9920298619");

            //    new InsertAddress(testActivity.this).insertaddrs(etMobile.getText().toString(),etPassword.getText().toString());

                showDialog("Please Wait...");


                break;
        }
    }

}
