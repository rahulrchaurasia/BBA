package com.beldara.bba.Test;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Async extends AsyncTask<Void, Void, String> {
        String json;
        Context context;

        Async(Context context, String mob) {
            this.context = context;
            json = mob;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //createDialog();
           // cancelDialog();
        }

        @Override
        protected String doInBackground(Void... params) {


            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://uat.beldara.com/kapi.php?m=FP");
                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(300000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("token", "1234567890");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                // Connecting to url
                //urlConnection.connect();

                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                os.writeBytes(json.toString());

                os.flush();
                os.close();

                int responseCode = urlConnection.getResponseCode();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            } catch (Exception e) {
                data = e.toString();
            } finally {

                urlConnection.disconnect();
            }
            return data;



        }

    }