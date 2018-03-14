package com.projgaia.ereswe.surveypohon.DataEntry;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.Model.Survey;
import com.projgaia.ereswe.surveypohon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BerandaActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private static final String TAG = BerandaActivity.class.getSimpleName();
    private Toolbar mToolbar;
    SessionManager sessionManager;
    String name;
    DBController controller = new DBController(this);
    TextView textdateDB, textidpetani;
    String encodeImage, katagori, tipe;

    HashMap<String, String> provinsiValues;
    HashMap<String, String> kabkotaValues;
    HashMap<String, String> kecamatanValues;
    HashMap<String, String> desaValues;
    HashMap<String, String> gapoktanValues;
    HashMap<String, String> kthValues;
    HashMap<String, String> persilValues;
    HashMap<String, String> pohonValues;
    HashMap<String, String> pohonSurveyValues;
    ProgressDialog prgDownload;
    ProgressDialog prgUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_data_entry);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(sessionManager.kunci_email);

        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setLogoDescription(getResources().getString(R.string.app_home));

        showDateNow();

        jsonObject = new JSONObject();

        TextView txtPetani = (TextView) findViewById(R.id.namaPetani);
        txtPetani.setText(Html.fromHtml("Nama Pengguna : "+"<b>" + name + "</b>"));

        prgUpload = new ProgressDialog(BerandaActivity.this);
        prgUpload.setMessage("Tunggu.. Proses Kirim Data Sedang Berlangsung");
        prgUpload.setIndeterminate(false);
        prgUpload.setCancelable(true);

        final Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                prgDownload.incrementProgressBy(2); // Incremented By Value 2
            }
        };

        prgDownload = new ProgressDialog(BerandaActivity.this);
        prgDownload.setMax(100); // Progress Dialog Max Value
        prgDownload.setMessage("Tunggu Sebentar, Sedang Samakan Data ..."); // Setting Message
        prgDownload.setTitle("Perhatian !!"); // Setting Title
        prgDownload.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Horizontal
        prgDownload.show(); // Display Progress Dialog
        prgDownload.setCancelable(false);

        downloadDataPohon();

        new Thread(new Runnable() {

            @Override
            public void run() {



                try {
                    while (prgDownload.getProgress() <= prgDownload.getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (prgDownload.getProgress() == prgDownload.getMax()) {

                            prgDownload.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


/*
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> provinsiList = controller.getAllProvinsi();
        System.out.println(provinsiList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> kabkotaList = controller.getAllKabKota();
        System.out.println(kabkotaList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> kecamatanList = controller.getAllKecamatan();
        System.out.println(kecamatanList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> desaList = controller.getAllDesa();
        System.out.println(desaList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> gapoktanList = controller.getAllGapoktan();
        System.out.println(gapoktanList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> kthList = controller.getAllKTH();
        System.out.println(kthList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> persilList = controller.getAllPersil();
        System.out.println(persilList.toString());
        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> pohonList = (ArrayList<HashMap<String, String>>) controller.getAllPohon();
        System.out.println(pohonList.toString());

        // Get User records from SQLite DB
        final ArrayList<HashMap<String, String>> pohonSurveyList = (ArrayList<HashMap<String, String>>) controller.getAllPohonSurvey();
        System.out.println(pohonSurveyList.toString());*/


    }

    private  void showDateNow (){
        //menampilkan tanggal sekarang
        DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateDB =   new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateNow = datef.format(date);
        String dateNowDB = dateDB.format(date);
        TextView textdate = (TextView) findViewById(R.id.dateBeranda);
        textdateDB = (TextView) findViewById(R.id.dateBerandaDB);
        textdate.setText(dateNow);
        textdateDB.setText(dateNowDB);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadDataSurvey() {
        prgUpload.show();
        if (controller.getAllSurvey().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Belum Ada Data Survey Yang Dapat Dikirim", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String response = null;

            final String finalResponse = response;

            String url = "http://gaia.id/monitoring/sqlitemysql/upload_gambar.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
                    prgUpload.hide();
                    if (response.equals("kirim data sukses")) {
                        //    DBController db = new DBController(BerandaActivity.this);
                        //       db.deleteSurvey();
                        Toast.makeText(getApplicationContext(), "Data Berhasil Dikirim", Toast.LENGTH_SHORT).show();

                    } else if (response.equals("kirim data gagal")) {
                        Toast.makeText(getApplicationContext(), "Data Gagal Dikirim", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Ada kesalahan Kirim Data", Toast.LENGTH_SHORT).show();
                    prgUpload.hide();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    List<Survey> surveyList = controller.getAllSurvey();

                    for (Survey survey : surveyList) {
                        final String idPohon = survey.getId_pohon().toString().trim();
                        final String idPetani = survey.getId_petani().toString().trim();
                        final String dbh = survey.getDiameter().toString().trim();
                        final String lat = survey.getLatitude().toString().trim();
                        final String lng = survey.getLongitude().toString().trim();
                        final String keterangan = survey.getKeterangan().toString().trim();
                        final String img = survey.getImg().toString().trim();
                        final String tgl = survey.getTgl().toString().trim();
                        final String tglUpload = textdateDB.getText().toString().trim();
                        final String kategori = survey.getKatagoripohon().toString().trim();
                        final String tipe = survey.getTipe().toString().trim();

                        Bitmap bm = BitmapFactory.decodeFile(img);
                        final String images = getStringImage(bm);

                        params.put("idPohon", idPohon);
                        params.put("idPetani", idPetani);
                        params.put("dbh", dbh);
                        params.put("lat", lat);
                        params.put("lng", lng);
                        params.put("keterangan", keterangan);

                        String katPohon = "";
                        if (kategori.equals("Pohon Mati")) {
                            katPohon = "0";
                        } else if (kategori.equals("Pohon Hidup")) {
                            katPohon = "1";
                        }
                        params.put("katagori", katPohon);
                        params.put("image", images);
                        params.put("tgl", tgl);
                        params.put("tglUpload", tglUpload);

                        String aktivitas = "";
                        if (tipe.equals("Penanaman")) {
                            aktivitas = "0";
                        } else if (tipe.equals("Pemantauan")) {
                            aktivitas = "1";
                        }
                        params.put("tipe", aktivitas);
                    }
                    return params;
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(strReq);
        }
    }

    public void viewFilter (View v) {
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.createSession(name);
        Intent in = new Intent(getApplicationContext(), SearchDataActivity.class);
        startActivity(in);
    }


    public void uploaddata (View v) {
      //  List<Survey> surveyList = controller.getAllSurvey();
       // for (int i = 0; i < surveyList.size(); i++ ) {
            uploadDataSurvey();
       // }
    }
    public void selesai (View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Apakah Kamu Ingin Keluar Dari Aplikasi ?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Starting login activity
                        sessionManager.logout();

                    }
                });
        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        //Showing the alert dialog
        alertDialog.show();
    }

    // Method to Sync MySQL to SQLite DB
    public void downloadDataPohon() {


        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar

        // Make Http call to getusers.php
        client.post("http://gaia.id/monitoring/downloaddata/getprovinsi.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
                //    prgProv.hide();
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

        // Make Http call to getkabkota.php
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
              //      System.out.println(obj.get("provinsi_name"));

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
                  //  System.out.println(obj.get("kabkota_id"));
                  //  System.out.println(obj.get("kabkota_name"));

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
              //      System.out.println(obj.get("kecamatan_id"));
              //      System.out.println(obj.get("kecamatan_name"));

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
//                    System.out.println(obj.get("kth_id"));
  //                  System.out.println(obj.get("kth_name"));

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
    //                System.out.println(obj.get("persil_id"));
      //              System.out.println(obj.get("persil_nomor"));

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
                    System.out.println(obj.get("pohon_id"));
                    System.out.println(obj.get("pohon_namalokal"));

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
    @Override
    public void onBackPressed() {

    }
}
