package com.beldara.bba.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by IN-RB on 02-02-2018.
 */

public class Constants {
    public static String SHAREDPREFERENCE_TITLE = "beldara_android";
    public static int SPLASH_DISPLAY_LENGTH = 2000;
    public static int PAYMENT_SUCCESS = 1;
    public static int PAYMENT_FAILURE = 0;
    public static String PAYMENT_AMOUNT = "10.05";
    public static final int LOCATION_INTERVAL = 10000;    //10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;
    public static int REQ_CODE_LEAD = 2;
    public static int REQ_CODE_DIAL = 3;


    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
}
