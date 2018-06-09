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

    private static final String MOBILE = "ELITE_CUSTOMER_MOBILE";
    private static final String PASSWORD = "ELITE_CUSTOMER_PASSWORD";


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

   
    public  void setMobile(String mob)
    {
        editor.putString(MOBILE, mob);

        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }

    public  void setPassword(String pwd)
    {

        editor.putString(PASSWORD, pwd);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }


}
