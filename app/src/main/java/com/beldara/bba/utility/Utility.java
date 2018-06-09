package com.beldara.bba.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class Utility {



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

}
