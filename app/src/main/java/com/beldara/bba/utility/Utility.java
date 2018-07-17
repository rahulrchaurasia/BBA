package com.beldara.bba.utility;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class  Utility {

    public static String ID = "ID";      //User id for all field (Main ID)
    public static String EMAIL_ID = "EMAIL_ID";
    public static String PASSWORD = "password";
    public static String LOGIN_ID = "loginid";
    public static String CALL_STATUS = "callstatus";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static long TIME_INTERVAL = 1800000;//30 Min
    public static String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
    };
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 1115;
    public static long TIME = 4 * 60000;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean checkInternetStatus(Context context) {
      /*  ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        }*/
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            return true;
        }
        return false;
    }


    public static MultipartBody.Part getMultipartImage(File file) {
        RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("docfile", file.getName(), imgBody);
        return imgFile;
    }


    public static HashMap<String, String> getBody(Context context, int userid, int doctype) {
        HashMap<String, String> body = new HashMap<String, String>();


        body.put("userid", String.valueOf(userid));
        body.put("doctype", String.valueOf(doctype));

        return body;
    }

    public static File createDirIfNotExists() {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), "/Elite-Cust");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return file;
    }

    public static String getCurrentMobileDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
