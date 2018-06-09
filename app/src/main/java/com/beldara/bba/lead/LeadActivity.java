package com.beldara.bba.lead;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.controller.register.RegisterController;
import com.beldara.bba.core.model.LeadEntity;
import com.beldara.bba.core.response.LeadResponse;
import com.beldara.bba.core.response.LoginResponse;
import com.beldara.bba.dashboard.HomeActivity;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.splash.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class LeadActivity extends BaseActivity  implements IResponseSubcriber {


    RecyclerView rvLead;
    List<LeadEntity> LeadLst;
    LeadAdapter mAdapter;
//    DBPersistanceController dbPersistanceController;
//    LoginResponseEntity loginEntity;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialize();
        showDialog("Please Wait...");
        new RegisterController(LeadActivity.this).getOpenLead( this);



    }

    private void initialize() {

        prefManager = new PrefManager(LeadActivity.this);
        LeadLst = new ArrayList<LeadEntity>();



        rvLead = (RecyclerView) findViewById(R.id.rvLead);
        rvLead.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LeadActivity.this);
        rvLead.setLayoutManager(layoutManager);



    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof LeadResponse) {
            cancelDialog();
            if (response.getStatusId() == 1) {
                if ( ((LeadResponse) response).getResult() != null) {
                    LeadLst = ((LeadResponse) response).getResult();
                    mAdapter = new LeadAdapter(LeadActivity.this,LeadLst);
                    rvLead.setAdapter(mAdapter);
                }else{
                    rvLead.setAdapter(null);
                    Snackbar.make(rvLead, "No Notification  Data Available", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
