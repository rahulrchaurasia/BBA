package com.beldara.bba.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.lead.LeadActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout lyOpenLead , lyMyLead, lyAccpLead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
    }

    public void initialize()
    {
        lyOpenLead = (LinearLayout) findViewById(R.id.lyOpenLead);
        lyMyLead = (LinearLayout) findViewById(R.id.lyMyLead);
        lyAccpLead = (LinearLayout) findViewById(R.id.lyAccpLead);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lyOpenLead:
                 startActivity(new Intent(HomeActivity.this, LeadActivity.class)
                             .putExtra("LEAD_TYP","O"));
                break;

            case R.id.lyMyLead:
                startActivity(new Intent(HomeActivity.this, LeadActivity.class)
                        .putExtra("LEAD_TYP","M"));
                break;

            case R.id.lyAccpLead:
                startActivity(new Intent(HomeActivity.this, LeadActivity.class)
                        .putExtra("LEAD_TYP","A"));
                break;

        }
    }
}
