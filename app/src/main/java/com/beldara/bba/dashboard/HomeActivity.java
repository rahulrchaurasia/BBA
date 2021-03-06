package com.beldara.bba.dashboard;

import android.content.Intent;
import android.location.Location;
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
import com.beldara.bba.dial_pad.DialPadActivity;
import com.beldara.bba.lead.LeadActivity;
import com.beldara.bba.location.ILocationStateListener;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.splash.PrefManager;

public class HomeActivity extends BaseActivity implements ILocationStateListener, View.OnClickListener {

    LinearLayout lyOpenLead , lyMyLead, lyAccpLead,lydialpad;
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
        lydialpad = (LinearLayout) findViewById(R.id.lydialpad);
    }

    private void setListner()
    {
        lyOpenLead.setOnClickListener(this);
        lyMyLead.setOnClickListener(this);
        lyAccpLead.setOnClickListener(this);
        lydialpad.setOnClickListener(this);
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
            case R.id.lydialpad:
                startActivity(new Intent(HomeActivity.this, DialPadActivity.class));
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectionFailed() {

    }
}
