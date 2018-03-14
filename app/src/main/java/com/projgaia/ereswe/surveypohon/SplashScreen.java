package com.projgaia.ereswe.surveypohon;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ERESWE on 03/01/2017.
 */

public class SplashScreen extends Activity {
    HashMap<String, String> queryValues;
    // DB Class to perform DB related operations
    DBController controller = new DBController(this);
    SessionManager sessionManager;

    //Set waktu lama splashscreen
    private static int splashInterval = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen);
        sessionManager = new SessionManager(getApplicationContext());
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                sessionManager.checkLogin();
                downloadDataUser();
                // TODO Auto-generated method stub

                //jeda selesai Splashscreen
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub

            }
        }, splashInterval);

    }



    // Method to Sync MySQL to SQLite DB
    public void downloadDataUser() {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
//        prgDownload.show();
        // Make Http call to getusers.php
        client.post("http://gaia.id/monitoring/petani/getusers.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updateSQLite(response);
            }
            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub
                // Hide ProgressBar
                //          prgDownload.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Tidak bisa mengakses database", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!! Anda Belum Terhubung Ke Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateSQLite(String response){
        ArrayList<HashMap<String, String>> usersynclist;
        usersynclist = new ArrayList<HashMap<String, String>>();

        try {
            // Extract JSON array from the response
            JSONArray arr = new JSONArray(response);
            System.out.println(arr.length());
            // If no of array elements is not zero
            if(arr.length() != 0){
                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < arr.length(); i++) {
                    // Get JSON object
                    JSONObject obj = (JSONObject) arr.get(i);
                    System.out.println(obj.get("petani_id"));
                    System.out.println(obj.get("petani_uname"));
                    System.out.println(obj.get("petani_password"));

                    // DB QueryValues Object to insert into SQLite
                    queryValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    queryValues.put("petani_id", obj.get("petani_id").toString());
                    // Add userName extracted from Object
                    queryValues.put("petani_name", obj.get("petani_name").toString());
                    queryValues.put("petani_alamat", obj.get("petani_alamat").toString());
                    queryValues.put("petani_email", obj.get("petani_email").toString());
                    queryValues.put("petani_uname", obj.get("petani_uname").toString());
                    queryValues.put("petani_password", obj.get("petani_password").toString());
                    queryValues.put("persil_id", obj.get("persil_id").toString());
                    // Insert User into SQLite DB

                    if(controller.checkUser(obj.get("petani_id").toString().trim())) {
                        controller.insertUser(queryValues);
                        HashMap<String, String> map = new HashMap<String, String>();
                        // Add status for each User in Hashmap
                        map.put("petani_id", obj.get("petani_id").toString());
                        map.put("petani_uname", obj.get("petani_uname").toString());
                        map.put("petani_password", obj.get("petani_password").toString());
                        //       map.put("status", "1");
                        usersynclist.add(map);
                    } else {
                        //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                    }

                }
                //   reloadActivity();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}

