package com.beldara.bba.Test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.beldara.bba.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Sachin on 28-06-2017.
 */

public class InsertAddress {

    private Context context;
    OkHttpClient client;
    Handler handler;
    private String ucity,lmark,pwd,message,zip,u_id;
    int ack;

    public InsertAddress(Context context) {
        this.context = context;
        client = new OkHttpClient();
        handler = new Handler();
    }

    protected void insertaddrs(String user_id,String pwd){

            this.u_id=user_id;
            this.pwd=pwd;
      //  /    this.lmark=landmark;
         //   this.ucity=city;
       //     this.zip=zip;

        RequestBody body = new FormBody.Builder()
                .add("user", String.valueOf(u_id))
                .add("pass",pwd)
                .add("lat","")
                .add("lng","")
                .add("typ","")
                .build();

        Request request = new Request.Builder().url(context.getString(R.string.domain).concat(context.getString(R.string.insert_user))).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context,"Error contacting server",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Error "+response);
                }
                else {
                    try {
                        final JSONObject resjson = new JSONObject(response.body().string());
                        message=resjson.getString("message");
                     //   ack=resjson.getInt("ack");



                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
