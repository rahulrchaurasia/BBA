package com.beldara.bba.lead;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.FollowUpHistory.FollowUpHistoryActivity;
import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.controller.register.RegisterController;
import com.beldara.bba.core.model.FollowUpHistoryEntity;
import com.beldara.bba.core.model.LeadEntity;
import com.beldara.bba.core.response.CommonResponse;
import com.beldara.bba.core.response.LeadResponse;
import com.beldara.bba.core.response.LoginResponse;
import com.beldara.bba.dashboard.HomeActivity;
import com.beldara.bba.followup.FollowUpActivity;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.splash.PrefManager;
import com.beldara.bba.utility.AudioRecorder;
import com.beldara.bba.utility.Constants;
import com.beldara.bba.utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeadActivity extends BaseActivity implements IResponseSubcriber {
    public static final String FROM_ID = "id_from_quote";
    TelephonyManager telephonyManager;
    PhoneStateListener callStateListener;
    AudioRecorder audioRecorder;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    long startTime, endTime = 0;

    RecyclerView rvLead;
    List<LeadEntity> LeadLst;
    LeadAdapter mAdapter;
    //    DBPersistanceController dbPersistanceController;
//    LoginResponseEntity loginEntity;
    PrefManager prefManager;
    String LeadType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            LeadType = extras.getString("LEAD_TYP", "");

        }
        initialize();
        showDialog("Please Wait...");

        if (LeadType.equals("M")) {

            new RegisterController(LeadActivity.this).getMyLead(prefManager.getUserID(), this);
        } else {
            new RegisterController(LeadActivity.this).getOpenLead(this);
        }


    }

    private void initialize() {

        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCE_TITLE, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Utility.CALL_STATUS, "NO");
        editor.commit();
        prefManager = new PrefManager(LeadActivity.this);
        LeadLst = new ArrayList<LeadEntity>();
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


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
                if (((LeadResponse) response).getResult() != null) {
                    LeadLst = ((LeadResponse) response).getResult();
                    mAdapter = new LeadAdapter(LeadActivity.this, LeadLst, LeadType);
                    rvLead.setAdapter(mAdapter);
                } else {
                    rvLead.setAdapter(null);
                    Snackbar.make(rvLead, "No  Data Available", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                rvLead.setAdapter(null);
                Snackbar.make(rvLead, "No  Data Available", Snackbar.LENGTH_SHORT).show();
            }
        } else if (response instanceof CommonResponse) {
            cancelDialog();
            //   showAlert( ((CommonResponse) response).getMessage());

            Toast.makeText(this, ((CommonResponse) response).getMessage().toString(), Toast.LENGTH_SHORT).show();
            showDialog("Please Wait...");
            new RegisterController(LeadActivity.this).getOpenLead(this);
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void dialCall(final String mbNumber, final LeadEntity leadEntity) {

        if (mbNumber == null || mbNumber.isEmpty()) {
            showAlert("Mobile No. Required.");
        } else {
            callStateListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber) {

                    if (state == TelephonyManager.CALL_STATE_RINGING) {

                    }
                    if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

                        try {
                            if (audioRecorder == null) {
                                startTime = Calendar.getInstance().getTimeInMillis();
                                audioRecorder = new AudioRecorder();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    audioRecorder.startRecording(mbNumber, MediaRecorder.AudioSource.MIC, prefManager.getUserID(), LeadActivity.this);
                                } else {
                                    audioRecorder.startRecording(mbNumber, MediaRecorder.AudioSource.VOICE_CALL, prefManager.getUserID(), LeadActivity.this);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                    if (state == TelephonyManager.CALL_STATE_IDLE) {

                        if (sharedPreferences.getString(Utility.CALL_STATUS, "").matches("YES")) {

                            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE);

                            editor.putString(Utility.CALL_STATUS, "NO");

                            try {
                                if (audioRecorder != null) {
                                    endTime = Calendar.getInstance().getTimeInMillis();
                                    audioRecorder.stopRecording();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(LeadActivity.this, FollowUpActivity.class);
                            intent.putExtra("LEAD_DETAILS", leadEntity);
                            intent.putExtra("AUDIO_PATH", audioRecorder.getFilePath());

                            startActivity(intent);

                        } else {
                             editor.putString(Utility.CALL_STATUS, "YES");

                        }
                          editor.commit();


                    }
                }
            };
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            Intent intent = new Intent(Intent.ACTION_CALL);
          //  intent.setData(Uri.parse("tel:" + mbNumber));

             intent.setData(Uri.parse("tel:" + "9224624999"));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    }

    public void redirectQuotefollowup(String sellerid) {
        Intent intent = new Intent(LeadActivity.this, FollowUpHistoryActivity.class);
        intent.putExtra(FROM_ID, sellerid);
        startActivity(intent);

    }

    public void redirectfollowup(LeadEntity leadEntity) {
        Intent intent = new Intent(LeadActivity.this, FollowUpActivity.class);
        intent.putExtra("LEAD_DETAILS", leadEntity);
        startActivity(intent);

    }

    public void getAcceptLead(String sellerid) {


        showDialog("Please Wait...");

        new RegisterController(LeadActivity.this).getAcceptLead(prefManager.getUserID(), sellerid, this);

    }




}
