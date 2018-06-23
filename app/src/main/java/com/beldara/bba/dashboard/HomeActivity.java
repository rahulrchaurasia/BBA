package com.beldara.bba.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.lead.LeadActivity;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.splash.PrefManager;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout lyOpenLead , lyMyLead, lyAccpLead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
        setListner();
    }

    public void initialize()
    {
        lyOpenLead = (LinearLayout) findViewById(R.id.lyOpenLead);
        lyMyLead = (LinearLayout) findViewById(R.id.lyMyLead);
        lyAccpLead = (LinearLayout) findViewById(R.id.lyAccpLead);
    }

    private void setListner()
    {
        lyOpenLead.setOnClickListener(this);
        lyMyLead.setOnClickListener(this);
        lyAccpLead.setOnClickListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:

                new PrefManager(HomeActivity.this).clearAll();
                Intent intent = new Intent(HomeActivity.this, loginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
