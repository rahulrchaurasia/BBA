package com.beldara.bba.dial_pad;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.lead.LeadActivity;
import com.beldara.bba.utility.AudioRecorder;
import com.beldara.bba.utility.Constants;
import com.beldara.bba.utility.Utility;

import java.util.Calendar;

public class DialPadActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    TelephonyManager telephonyManager;
    PhoneStateListener callStateListener;
    AudioRecorder audioRecorder;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText etPhoneNumber;
    Button btnNum0, btnNum1, btnNum2, btnNum3, btnNum4;
    Button btnNum5, btnNum6, btnNum7, btnNum8, btnNum9;

    ImageView btnCall, btnDelete;
    String mbNumber;
    long startTime, endTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_pad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialise_widget();
        setListner();
    }

    private void initialise_widget() {

        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCE_TITLE, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnNum0 = (Button) findViewById(R.id.btnNum0);
        btnNum1 = (Button) findViewById(R.id.btnNum1);
        btnNum2 = (Button) findViewById(R.id.btnNum2);
        btnNum3 = (Button) findViewById(R.id.btnNum3);
        btnNum4 = (Button) findViewById(R.id.btnNum4);
        btnNum5 = (Button) findViewById(R.id.btnNum5);

        btnNum6 = (Button) findViewById(R.id.btnNum6);
        btnNum7 = (Button) findViewById(R.id.btnNum7);
        btnNum8 = (Button) findViewById(R.id.btnNum8);
        btnNum9 = (Button) findViewById(R.id.btnNum9);

        btnCall = (ImageView) findViewById(R.id.btnCall);
        btnDelete = (ImageView) findViewById(R.id.btnDelete);
    }

    private void setListner() {

        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);

        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDelete.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {

      /*  final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        new Thread() {
            @Override
            public void run() {
                vibrator.vibrate(500);
            }
        }.start();*/


        switch (v.getId()) {
            case R.id.btnNum0:
                onCharacterPressed('0');
                break;
            case R.id.btnNum1:
                onCharacterPressed('1');
                break;
            case R.id.btnNum2:
                onCharacterPressed('2');
                break;
            case R.id.btnNum3:
                onCharacterPressed('3');
                break;
            case R.id.btnNum4:
                onCharacterPressed('4');
                break;
            case R.id.btnNum5:
                onCharacterPressed('5');
                break;
            case R.id.btnNum6:
                onCharacterPressed('6');
                break;
            case R.id.btnNum7:
                onCharacterPressed('7');
                break;
            case R.id.btnNum8:
                onCharacterPressed('8');
                break;
            case R.id.btnNum9:
                onCharacterPressed('9');
                break;

            case R.id.btnDelete:
                onDeletePressed();
                break;
            case R.id.btnCall:
                if (etPhoneNumber.getText().length() != 0) {
                    mbNumber = etPhoneNumber.getText().toString();
                    dialCall(etPhoneNumber.getText().toString());
                }
                break;

            case R.id.etPhoneNumber:
                etPhoneNumber.setCursorVisible(true);
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {

        switch (view.getId()) {
            case R.id.btnDelete:
                etPhoneNumber.setText("");
                return true;
            default:
                break;
        }
        return false;
    }

    private void onCharacterPressed(char digit) {
        try {
            CharSequence cur = etPhoneNumber.getText();

            int start = etPhoneNumber.getSelectionStart();
            int end = etPhoneNumber.getSelectionEnd();
            int len = cur.length();
            if (cur.length() < 15) {

                if (cur.length() == 0) {
                    etPhoneNumber.setCursorVisible(false);
                }

                cur = cur.subSequence(0, start).toString() + digit + cur.subSequence(end, len).toString();
                etPhoneNumber.setText(cur);
                etPhoneNumber.setSelection(start + 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void onDeletePressed() {
        CharSequence cur = etPhoneNumber.getText();
        int start = etPhoneNumber.getSelectionStart();
        int end = etPhoneNumber.getSelectionEnd();
        if (start == end) { // remove the item behind the cursor
            if (start != 0) {
                cur = cur.subSequence(0, start - 1).toString() + cur.subSequence(start, cur.length()).toString();
                etPhoneNumber.setText(cur);
                etPhoneNumber.setSelection(start - 1);
                if (cur.length() == 0) {
                    etPhoneNumber.setCursorVisible(false);
                }
            }
        } else { // remove the whole selection
            cur = cur.subSequence(0, start).toString() + cur.subSequence(end, cur.length()).toString();
            etPhoneNumber.setText(cur);
            etPhoneNumber.setSelection(end - (end - start));
            if (cur.length() == 0) {
                etPhoneNumber.setCursorVisible(false);
            }
        }
    }



    public void dialCall(final String mbNumber  ) {

        if(mbNumber == null  || mbNumber.isEmpty()  )
        {
            showAlert("Mobile No. Required.");
        }
        else {
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
                                    audioRecorder.startRecording(mbNumber, MediaRecorder.AudioSource.MIC, "102", DialPadActivity.this);
                                } else {
                                    audioRecorder.startRecording(mbNumber, MediaRecorder.AudioSource.VOICE_CALL, "102", DialPadActivity.this);
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
                                    // audiofile = audioRecorder.getFile();

                                    //  savAudioToDb();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(DialPadActivity.this, LeadActivity.class);
                            intent.putExtra("DIAL_NUMBER", mbNumber);
                            intent.putExtra("AUDIO_PATH", audioRecorder.getFilePath());
                            // intent.putExtra("AUDIO_FILE",audiofile);
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
            intent.setData(Uri.parse("tel:" + mbNumber));

            //  intent.setData(Uri.parse("tel:" + "9702943935"));

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

}
