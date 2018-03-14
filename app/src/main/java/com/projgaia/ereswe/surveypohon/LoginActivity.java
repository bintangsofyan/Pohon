package com.projgaia.ereswe.surveypohon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.projgaia.ereswe.surveypohon.DataEntry.BerandaActivity;
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Helper.Helper;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.projgaia.ereswe.surveypohon.R.id.logtxtEmail;
import static com.projgaia.ereswe.surveypohon.R.id.logtxtPassword;

public class LoginActivity extends BaseApp {
    SessionManager sessionManager;
    private MaterialEditText username, password;
    private Button logbtnLogin;
    private NestedScrollView nestedScrollView;
    // DB Class to perform DB related operations
    DBController controller = new DBController(this);
    HashMap<String, String> provinsiValues;
    HashMap<String, String> kabkotaValues;
    HashMap<String, String> kecamatanValues;
    HashMap<String, String> desaValues;
    HashMap<String, String> gapoktanValues;
    HashMap<String, String> kthValues;
    HashMap<String, String> persilValues;
    HashMap<String, String> pohonValues;
    HashMap<String, String> pohonSurveyValues;


    //Progress Dialog Object
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (MaterialEditText) findViewById(logtxtEmail);
        password = (MaterialEditText) findViewById(logtxtPassword);
        logbtnLogin = (Button) findViewById(R.id.logbtnLogin);

        sessionManager = new SessionManager(getApplicationContext());

        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> userList = controller.getAllUsers();
            System.out.println(userList.toString());



        logbtnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                validasiUser();




                }



            });


    }



    //validasi hak akses user
    private void validasiUser() {
        username.setError(null);
        password.setError(null);
        /*check kebaradaan teks*/
        if (Helper.isEmpty(username)) {
            username.setError("Email masih kosong");
            username.requestFocus();
        } else if (Helper.isEmpty(password)) {
            password.setError("Password masih kosong");
            password.requestFocus();

        } else {
            if (controller.checkUser(username.getText().toString().trim(), password.getText().toString().trim())) {

                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSession(username.getText().toString());
                startActivity(new Intent(context, BerandaActivity.class));
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Maaf, Kamu Belum Terdaftar Menjadi Pengguna", Toast.LENGTH_LONG).show();
                emptyInputEditText();
            }
        }
    }


    private void emptyInputEditText() {
        username.setText(null);
        password.setText(null);
    }


    // Method to Sync MySQL to SQLite DB
    public void downloadDataPohon() {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
   /*     prgProv = new ProgressDialog(LoginActivity.this);
        prgProv.setMessage("Tunggu.. Sedang Proses Unduh Data Provinsi");
        prgProv.setIndeterminate(false);
        prgProv.setCancelable(true);
        prgProv.show();*/
        // Make Http call to getusers.php
        client.post("http://gaia.id/monitoring/downloaddata/getprovinsi.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
            // prgProv.hide();
                // Update SQLite DB with response sent by getusers.php
                updateProvinsi(response);
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
/*
        prgKab = new ProgressDialog(LoginActivity.this);
        prgKab.setMessage("Tunggu.. Sedang Proses Unduh Data Kabupaten Kota");
        prgKab.setIndeterminate(false);
        prgKab.setCancelable(true);
        prgKab.show();*/
        // Make Http call to getkabkota.php
        client.post("http://gaia.id/monitoring/downloaddata/getkabkota.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
            //  prgKab.hide();
                // Update SQLite DB with response sent by getusers.php
                updateKabKota(response);
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

        // Make Http call to getkabkota.php
        client.post("http://gaia.id/monitoring/downloaddata/getkecamatan.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updateKecamatan(response);
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

        // Make Http call to getkabkota.php
        client.post("http://gaia.id/monitoring/downloaddata/getdesa.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updateDesa(response);
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

        // Make Http call to getkabkota.php
        client.post("http://gaia.id/monitoring/downloaddata/getgapoktan.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updateGapoktan(response);
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

        // Make Http call to getkabkota.php
        client.post("http://gaia.id/monitoring/downloaddata/getkth.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updateKTH(response);
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

        // Make Http call to getkabkota.php
        client.post("http://gaia.id/monitoring/downloaddata/getpersil.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updatePersil(response);
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

        // Make Http call to getpohon.php
        client.post("http://gaia.id/monitoring/downloaddata/getpohon.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar

                // Update SQLite DB with response sent by getusers.php
                updatePohon(response);
            }
            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub

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

        // Make Http call to getsurvey.php
        client.post("http://gaia.id/monitoring/downloaddata/getsurvey.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
//                prgDownload.hide();
                // Update SQLite DB with response sent by getusers.php
                updatePohonSurvey(response);
            }
            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub

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

    public void updateProvinsi(String response){

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
                //    System.out.println(obj.get("provinsi_id"));
                //    System.out.println(obj.get("provinsi_name"));

                    // DB QueryValues Object to insert into SQLite
                    provinsiValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    provinsiValues.put("provinsi_id", obj.get("provinsi_id").toString());
                    // Add userName extracted from Object
                    provinsiValues.put("provinsi_name", obj.get("provinsi_name").toString());
                    provinsiValues.put("provinsi_kode", obj.get("provinsi_kode").toString());

                    if(controller.checkProvinsi(obj.get("provinsi_id").toString().trim())) {
                    // Insert User into SQLite DB
                    controller.insertProvinsi(provinsiValues);
                    } else {
                        //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                   }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateKabKota(String response){
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
//                    System.out.println(obj.get("kabkota_id"));
  //                  System.out.println(obj.get("kabkota_name"));

                    // DB QueryValues Object to insert into SQLite
                    kabkotaValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    kabkotaValues.put("kabkota_id", obj.get("kabkota_id").toString());
                    // Add userName extracted from Object
                    kabkotaValues.put("kabkota_name", obj.get("kabkota_name").toString());
                    kabkotaValues.put("kabkota_kode", obj.get("kabkota_kode").toString());
                    kabkotaValues.put("kabkota_prov_id", obj.get("provinsi_id").toString());
                    // Insert User into SQLite DB

                    if(controller.checkKabKota(obj.get("kabkota_id").toString().trim())) {
                    controller.insertKabKota(kabkotaValues);
                                      } else {
                    //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                   }


                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateKecamatan(String response){
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
    //                System.out.println(obj.get("kecamatan_id"));
      //              System.out.println(obj.get("kecamatan_name"));

                    // DB QueryValues Object to insert into SQLite
                    kecamatanValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    kecamatanValues.put("kecamatan_id", obj.get("kecamatan_id").toString());
                    // Add userName extracted from Object
                    kecamatanValues.put("kecamatan_name", obj.get("kecamatan_name").toString());
                    kecamatanValues.put("kecamatan_kode", obj.get("kecamatan_kode").toString());
                    kecamatanValues.put("kecamatan_kab_id", obj.get("kabkota_id").toString());
                    // Insert User into SQLite DB

                    if(controller.checkKecamatan(obj.get("kecamatan_id").toString().trim())) {
                    controller.insertKecamatan(kecamatanValues);
                                   } else {
                 //       Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                   }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void updateDesa(String response){

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
        //            System.out.println(obj.get("desa_id"));
          //          System.out.println(obj.get("desa_name"));

                    // DB QueryValues Object to insert into SQLite
                    desaValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    desaValues.put("desa_id", obj.get("desa_id").toString());
                    // Add userName extracted from Object
                    desaValues.put("desa_name", obj.get("desa_name").toString());
                    desaValues.put("desa_initial", obj.get("desa_initial").toString());
                    desaValues.put("desa_kec_id", obj.get("kecamatan_id").toString());
                    // Insert User into SQLite DB
                    if(controller.checkDesa(obj.get("desa_id").toString().trim())) {
                    controller.insertDesa(desaValues);
                                     } else {
                    //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                   }
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateGapoktan(String response){

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
//                    System.out.println(obj.get("gapoktan_id"));
  //                  System.out.println(obj.get("gapoktan_name"));

                    // DB QueryValues Object to insert into SQLite
                    gapoktanValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    gapoktanValues.put("gapoktan_id", obj.get("gapoktan_id").toString());
                    // Add userName extracted from Object
                    gapoktanValues.put("gapoktan_name", obj.get("gapoktan_name").toString());
                    gapoktanValues.put("gapoktan_desa_id", obj.get("desa_id").toString());
                    // Insert User into SQLite DB

                    if(controller.checkGapoktan(obj.get("gapoktan_id").toString().trim())) {
                    controller.insertGapoktan(gapoktanValues);
                                      } else {
                    //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                    }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateKTH(String response){
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
    //                System.out.println(obj.get("kth_id"));
      //              System.out.println(obj.get("kth_name"));

                    // DB QueryValues Object to insert into SQLite
                    kthValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    kthValues.put("kth_id", obj.get("kth_id").toString());
                    // Add userName extracted from Object
                    kthValues.put("kth_name", obj.get("kth_name").toString());
                    kthValues.put("kth_gapoktan_id", obj.get("gapoktan_id").toString());
                    // Insert User into SQLite DB

                    if(controller.checkKTH(obj.get("kth_id").toString().trim())) {
                    controller.insertKTH(kthValues);
                                     } else {
                    //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                   }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updatePersil(String response){

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
//                    System.out.println(obj.get("persil_id"));
  //                  System.out.println(obj.get("persil_nomor"));

                    // DB QueryValues Object to insert into SQLite
                    persilValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    persilValues.put("persil_id", obj.get("persil_id").toString());
                    // Add userName extracted from Object
                    persilValues.put("persil_desa_id", obj.get("desa_id").toString());
                    persilValues.put("persil_kth_id", obj.get("kth_id").toString());
                    persilValues.put("persil_nomor", obj.get("persil_nomor").toString());
                    // Insert User into SQLite DB

                    if(controller.checkPersil(obj.get("persil_id").toString().trim())) {
                    controller.insertPersil(persilValues);
                                      } else {
                    //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                    }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updatePohon(String response){

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
//                    System.out.println(obj.get("pohon_id"));
  //                  System.out.println(obj.get("pohon_namalokal"));

                    // DB QueryValues Object to insert into SQLite
                    pohonValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    pohonValues.put("pohon_id", obj.get("pohon_id").toString());
                    // Add userName extracted from Object
                    pohonValues.put("pohon_kode", obj.get("pohon_kode").toString());
                    pohonValues.put("pohon_persil_id", obj.get("persil_id").toString());
                    pohonValues.put("pohon_initial", obj.get("pohon_initial").toString());
                    pohonValues.put("pohon_namalatin", obj.get("pohon_namalatin").toString());
                    pohonValues.put("pohon_namalokal", obj.get("pohon_namalokal").toString());
                    pohonValues.put("pohon_status", obj.get("pohon_status").toString());
                    pohonValues.put("pohon_lastupdate", "");
                    // Insert User into SQLite DB

                    if(controller.checkPohon(obj.get("pohon_id").toString().trim())) {
                    controller.insertPohon(pohonValues);
                                      } else {
                    //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                                    }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void updatePohonSurvey(String response){

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
//                    System.out.println(obj.get("survey_id"));
  //                  System.out.println(obj.get("pohon_id"));

                    // DB QueryValues Object to insert into SQLite
                    pohonSurveyValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    pohonSurveyValues.put("survey_id", obj.get("survey_id").toString());
                    // Add userName extracted from Object
                    pohonSurveyValues.put("pohon_id", obj.get("pohon_id").toString());
                    // Insert User into SQLite DB

                    if(controller.checkPohonSurvey(obj.get("survey_id").toString().trim())) {
                        controller.insertPohonSurvey(pohonSurveyValues);
                    } else {
                        //     Toast.makeText(getApplicationContext(), "Data Petani Di HP Sudah Sama Dengan Data Di Server", Toast.LENGTH_LONG).show();
                    }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //fitur untuk nonaktifkan back diperangkat
    private long backPressedTime;
    private Toast backToast;

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);
            exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
        }else {
            backToast = Toast.makeText(getBaseContext(), "Tekan Sekali lagi untuk keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}