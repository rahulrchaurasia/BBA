package com.beldara.bba.splash;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;


    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "BBA_USER";

    private static final String IS_PRODUCT_MASTER_UPDATE = "isProductMasterUpdate";

    private static final String USER = "BBA_USER";
    private static final String PASSWORD = "BBA__PASSWORD";
    private static final String USER_ID = "BBA_USER_ID";
    private static final String BBA_TYPE = "BBA_TYPE";
    public static String CALL_STATUS = "callstatus";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void setIsProductMasterUpdate(boolean isFirstTime) {
        editor.putBoolean(IS_PRODUCT_MASTER_UPDATE, isFirstTime);
        editor.commit();
    }

    public boolean IsProductMasterUpdate() {
        return pref.getBoolean(IS_PRODUCT_MASTER_UPDATE, true);
    }

   
    public  void setUser(String user)
    {
        editor.putString(USER, user);

        editor.commit();
    }

    public String getUser() {
        return pref.getString(USER, "");
    }




    public  void setUserID(String userId)
    {
        editor.putString(USER_ID, userId);

        editor.commit();
    }

    public String getUserID() {
        return pref.getString(USER_ID, "");
    }

    public  void setType(String type)
    {
        editor.putString(BBA_TYPE, type);

        editor.commit();
    }

    public String getType() {
        return pref.getString(BBA_TYPE, "");
    }


    public  void setPassword(String pwd)
    {

        editor.putString(PASSWORD, pwd);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }

    public  void setCallStatus(String callStatus)
    {

        editor.putString(CALL_STATUS, callStatus);
        editor.commit();
    }

    public String getCallStatus() {
        return pref.getString(CALL_STATUS, "");
    }


    public void clearAll() {
        pref.edit().remove(USER)
                .remove(PASSWORD)
                .remove(USER_ID)
                .remove(CALL_STATUS)
                .remove(BBA_TYPE).commit();

    }


}
