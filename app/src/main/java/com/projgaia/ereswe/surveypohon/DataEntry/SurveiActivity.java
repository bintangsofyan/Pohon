package com.projgaia.ereswe.surveypohon.DataEntry;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Helper.Helper;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.Model.Survey;
import com.projgaia.ereswe.surveypohon.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.media.ThumbnailUtils.extractThumbnail;

public class SurveiActivity extends AppCompatActivity implements LocationListener  {

    private RadioButton radioKatagoriButton;
    private RadioGroup radioKatagoriGrup;
    private final int THUMBSIZE = 100;

    private static final int PERMISSION_REQUEST_CODE = 200;
    Button camBar;
    private static final String IMAGE_DIRECTORY_NAME = "GAIA DB";

    ProgressDialog prgUpload;

    Uri capturaImage;
    SessionManager sessionManager;
    private Toolbar mToolbar;
    protected Cursor cursor;
    DBController dbController = new DBController(this);

    Button ton1;
    EditText textkd, textnama, textdmtr, textkgr, textfeedback, textstatus, textaktivitas;
    TextView textDate, textlat, textlong, textidpetani, textidpohon, textdateDB, dateImg, textidsurvey, textpath;

    String IDPersil;
    String path ="";
    LocationManager locationManager;
    String provider, name;
    Location location;
    String idPohon;
    ImageView imgTmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pohon);


        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setLogoDescription(getResources().getString(R.string.app_name));

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(sessionManager.kunci_email);

        //menampilkan tanggal sekarang
        DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        final DateFormat dateDB =   new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDateandTime = datef.format(date);
        String currentDate = dateDB.format(date);
        TextView set_date = (TextView) findViewById(R.id.set_date);
        textdateDB = (TextView) findViewById(R.id.dateDB);
        set_date.setText(currentDateandTime);
        textdateDB.setText(currentDate);

        prgUpload = new ProgressDialog(SurveiActivity.this);
        prgUpload.setMessage("Tunggu.. Proses Kirim Data Sedang Berlangsung");
        prgUpload.setIndeterminate(false);
        prgUpload.setCancelable(true);


        requestPermission();

        //mendeskripsikan variable berdasarkan layout xml
        textkd = (EditText) findViewById(R.id.kd_pohon);
        textnama = (EditText) findViewById(R.id.nm_pohon);
        textDate = (TextView) findViewById(R.id.set_date);
        textdmtr = (EditText) findViewById(R.id.diameter);

        textlat = (TextView) findViewById(R.id.lat_view);
        textlong = (TextView) findViewById(R.id.long_view);
        textidpetani = (TextView) findViewById(R.id.id_petani);
        textkgr = (EditText) findViewById(R.id.pohon_kat);
        textidpohon = (TextView) findViewById(R.id.id_pohon);
        textidsurvey = (TextView) findViewById(R.id.id_survey);
        textstatus = (EditText) findViewById(R.id.status_pohon);
        textpath = (TextView) findViewById(R.id.path);
        textfeedback = (EditText) findViewById(R.id.ket);
        textaktivitas  = (EditText) findViewById(R.id.aktifitas_pohon);
        imgTmb = (ImageView) findViewById(R.id.img);
        dateImg = (TextView)findViewById(R.id.date_img);
        ton1 = (Button) findViewById(R.id.butsimpan);
        radioKatagoriGrup = (RadioGroup) findViewById(R.id.rG_Katagori);

        //mendeskripsikan location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "GPS belum stabil", Toast.LENGTH_SHORT).show();
        }else{

        }


        //get id petani
        SQLiteDatabase db = dbController.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM petani WHERE petani_uname = '" + name
                + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            textidpetani.setText(cursor.getString(0).toString());
        }else {
            Toast.makeText(getApplication(), "ID Petani Tidak Ditemukan", Toast.LENGTH_LONG).show();
        }

        //get identitas pohon
        idPohon = getIntent().getStringExtra("idPohon");
        cursor = db.rawQuery("SELECT * FROM pohon WHERE pohon_id = '" + idPohon
                + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            textidpohon.setText(cursor.getString(0).toString());
            textnama.setText(cursor.getString(5).toString());

            textkd.setText(cursor.getString(1).toString());
            if (cursor.getString(6).toString().equals("0") ){
                textstatus.setText("Pohon Lama");
            } else if (cursor.getString(6).toString().equals("1") ){
                textstatus.setText("Pohon Baru");
            } else if (cursor.getString(6).toString().equals("2")) {
                textstatus.setText("Pohon Sulam");
            }

        //validasi menentukan status dan aktivitas pohon
         if (cursor.getString(6).toString().equals("1")  | cursor.getString(6).toString().equals("2") ) {
                cursor = db.rawQuery("SELECT * FROM pohonsurvey WHERE pohonsurvey_pohon_id = '" + idPohon
                        + "'", null);
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToPosition(0);
                    textaktivitas.setText("Pemantauan");
                } else  {
                    textaktivitas.setText("Penanaman");
                }
            }
            else if (cursor.getString(6).toString().equals("0")) {
                textaktivitas.setText("Pemantauan");
         }
        } else {
            Toast.makeText(getApplication(), "Data Survey Tidak Ditemukan", Toast.LENGTH_LONG).show();
        }

        //menampilkan data survey
        cursor = db.rawQuery("SELECT * FROM survey WHERE survey_pohon_id = '" + idPohon
                + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            textidsurvey.setText(cursor.getString(0).toString());
            if(cursor.getString(3).toString().isEmpty() & cursor.getString(4).toString().isEmpty()
                    & cursor.getString(5).toString().isEmpty() & cursor.getString(6).toString().isEmpty() & cursor.getString(9).toString().isEmpty()) {
                textdmtr.setText("");
                textlat.setText("");
                textlong.setText("");
                textfeedback.setText("");
                textstatus.setText("");
                textaktivitas.setText("");
        } else  {
                textdmtr.setText(cursor.getString(3).toString());
                textlat.setText(cursor.getString(4).toString());
                textlong.setText(cursor.getString(5).toString());
                textfeedback.setText(cursor.getString(6).toString());

                dateImg.setText(cursor.getString(9).toString());
                textpath.setText(cursor.getString(9).toString());

                imgTmb.setImageBitmap((
                       extractThumbnail(BitmapFactory.decodeFile(cursor.getString(9).toString()),
                               THUMBSIZE, THUMBSIZE)));
            }
        }else {
       //   Toast.makeText(getApplication(), "Survey baru ..", Toast.LENGTH_LONG).show();
        }

        //camera intent
        camBar = (Button) findViewById(R.id.btnTakeFoto);
        ambilfoto();
        showFullImg();
        addRBKatagoriPohon();


        // daftarkan even onClick pada btnSimpan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

        mandatoryData();



           //   uploadDataSurvey();

            }
        });

        imgTmb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String path = textpath.getText().toString();
                Toast.makeText(SurveiActivity.this, path, Toast.LENGTH_LONG).show();
                if (path != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri imgUri = Uri.parse("file://" + path);
                    intent.setDataAndType(imgUri, "image/*");
                    startActivity(intent);
                }
            }
        });
    }

    // Reload Activity
    public void reloadActivity() {
        Intent objIntent = new Intent(getApplicationContext(), PohonActivity.class);
        startActivity(objIntent);
    }

    private void mandatoryData() {
        textdmtr.setError(null);
        textlat.setError(null);
        textlong.setError(null);
        textfeedback.setError(null);
        dateImg.setError(null);
        /*check kebaradaan teks*/
        if (Helper.isEmpty(textdmtr)) {
            textdmtr.setError("Lengkapi Diameter Pohon");
            textdmtr.requestFocus();
        } else if (Helper.isEmpty(dateImg)) {
            dateImg.setError("Foto Belum Diambil");
            Toast.makeText(getApplicationContext(), "Silahkan Ambil Foto", Toast.LENGTH_LONG).show();
            dateImg.requestFocus();
        } else if (Helper.isEmpty(textlat, textlong)) {
            textlat.setError("Silahkan Tentukan Koordinat");
            textlong.setError("Silahkan Tentukan Koordinat");
            textlat.requestFocus();
            textlong.requestFocus();
            Toast.makeText(getApplicationContext(), "Silahkan Tentukan Koordinat", Toast.LENGTH_LONG).show();


        } else {
            if(dbController.checkSurvey(textidsurvey.getText().toString().trim())) {
                addDataSurvey();
            } else {
                updateDataSurvey();
            }
        }
    }

    private void addDataSurvey(){

        addRBKatagoriPohon();
        // Inserting Image
      //  Log.d("Data: ", "Updating ..");
        Log.d("dbh: ", textdmtr.getText().toString());
        Log.d("lat: ", textlat.getText().toString());
        Log.d("long: ", textlong.getText().toString());
        Log.d("fee: ", textfeedback.getText().toString());
        Log.d("sta: ", textstatus.getText().toString());
        Log.d("kond: ", radioKatagoriButton.getText().toString());
        Log.d("path: ", textpath.getText().toString());
        Log.d("date: ", textdateDB.getText().toString());
        Log.d("ack: ", textaktivitas.getText().toString());

      //  if(dbController.checkSurvey(idPohon.toString().trim())) {
            // Inserting Image
            Log.d("Data: ", "Inserting ..");
            dbController.insertSurvey(new Survey(idPohon.toString(), textidpetani.toString(), textdmtr.getText().toString(),
                    textlat.getText().toString(), textlong.getText().toString(),
                    textfeedback.getText().toString(), textstatus.getText().toString(),
                    radioKatagoriButton.getText().toString(), textpath.getText().toString(), textdateDB.getText().toString(), "", "Pemantauan"));

            dbController.updatePohonStatus(Integer.parseInt(idPohon.toString()), "0");
            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
      //      uploadDataSurvey();
            //TODO : EDITED
//            IDPersil = getIntent().getStringExtra("persilID");
//            Intent in = new Intent(getApplicationContext(), PohonActivity.class);
//            in.putExtra("persilID", IDPersil);
//            startActivity(in);
           finish();
            //TODO : EDITED

     //   } else {

       //     Toast.makeText(getApplicationContext(), "Data Gagal Disimpan", Toast.LENGTH_LONG).show();
       // }
    }



    private void updateDataSurvey(){

        addRBKatagoriPohon();

        Log.d("dbh: ", textdmtr.getText().toString());
        Log.d("lat: ", textlat.getText().toString());
        Log.d("long: ", textlong.getText().toString());
        Log.d("fee: ", textfeedback.getText().toString());
        Log.d("sta: ", textstatus.getText().toString());
        Log.d("kond: ", radioKatagoriButton.getText().toString());
        Log.d("path: ", textpath.getText().toString());
        Log.d("date: ", textdateDB.getText().toString());
        Log.d("ack: ", textaktivitas.getText().toString());
        Log.d("Data: ", "Updating ..");
        dbController.updateSurvey(Integer.parseInt(textidsurvey.getText().toString()), idPohon.toString(), textidpetani.getText().toString(), textdmtr.getText().toString(),
                textlat.getText().toString(), textlong.getText().toString(),
                textfeedback.getText().toString(), textstatus.getText().toString(),
                radioKatagoriButton.getText().toString(), textpath.getText().toString(),
                textdateDB.getText().toString(), "", "Pemantauan");

        dbController.updatePohonStatus(Integer.parseInt(idPohon.toString()), "0");

        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
    //    uploadDataSurvey();

        //TODO : EDITED
//        IDPersil = getIntent().getStringExtra("persilID");
//        Intent in = new Intent(getApplicationContext(), PohonActivity.class);
//        in.putExtra("persilID", IDPersil);
//        startActivity(in);
        finish();
        //TODO : END OF EDITED
  // reloadActivity();
    }



    private void updateLastUpdatePohon(){
        dbController.updatePohon(Integer.parseInt(idPohon.toString()), textdateDB.getText().toString());
    }

    public void addRBKatagoriPohon() {
        // get selected radio button from radioGroup
     int selectedId = radioKatagoriGrup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioKatagoriButton = (RadioButton) findViewById(selectedId);
      //  Toast.makeText(getApplicationContext(), radioKatagoriButton.getText(), Toast.LENGTH_LONG).show();
    }

    public void selesai (View v) {
        Intent in = new Intent(getApplicationContext(), BerandaActivity.class);
        startActivity(in);
    }

    public void persil (View v) {
        Intent in = new Intent(getApplicationContext(), PersilActivity.class);
        startActivity(in);
    }

    public void sortir (View v) {
        Intent in = new Intent(getApplicationContext(), PohonActivity.class);
        startActivity(in);
    }


    public void ambilfoto() {
        camBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // lokasi penyimpanan di SDcard
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        IMAGE_DIRECTORY_NAME);

                //membuat direktori
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        Log.d(IMAGE_DIRECTORY_NAME, "Oops! Gagal membuat folder" + IMAGE_DIRECTORY_NAME);
                    }
                }

                //membuat tanggal foto
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());

                //intent camera
                Intent takePicture0 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(mediaStorageDir.getPath() + File.separator + textkd.getText().toString()+currentDateandTime+".jpg");
                capturaImage = Uri.fromFile(file);
                try {
                    takePicture0.putExtra(MediaStore.EXTRA_OUTPUT, capturaImage);
                    takePicture0.putExtra("return-data", true);
                    startActivityForResult(takePicture0, 0);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0: // fix
                if (resultCode == RESULT_OK) {

                    path = capturaImage.getPath();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDateandTime = sdf.format(new Date());


                 imgTmb.setImageBitmap((extractThumbnail(BitmapFactory.decodeFile(path), THUMBSIZE, THUMBSIZE)));
                    dateImg.setText(path);
                    textpath.setText(path);

                    showFullImg();

                }
                break;

        }
    }


    private void showFullImg (){

        imgTmb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            //    Toast.makeText(SurveiActivity.this, path, Toast.LENGTH_LONG).show();
                String path = capturaImage.toString();

                if (path != null) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri imgUri = Uri.parse("file://" + path);
                    intent.setDataAndType(imgUri, "image/*");
                    startActivity(intent);

                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri imgUri = Uri.parse("file://" + textpath.getText().toString());
                    intent.setDataAndType(imgUri, "image/*");
                    startActivity(intent);
                }
            }
        });

    }


    private void uploadDataSurvey(){
        //TAMBAHAN
        //TODO : EDITED
     //   updateLastUpdatePohon();
     //   finish();


        prgUpload.show();
      //  List<Survey> surveyList = dbController.getAllSurvey();

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
                    //    DBController db = new DBController(SurveiActivity.this);
                     //   db.deleteSurvey();
                        updateLastUpdatePohon();
                        Toast.makeText(getApplicationContext(), "Data Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                     //   reloadActivity();
                        IDPersil = getIntent().getStringExtra("persilID");
                        Intent in = new Intent(getApplicationContext(), PohonActivity.class);
                        in.putExtra("persilID", IDPersil);
                        startActivity(in);
                    //    Intent in = new Intent(getApplicationContext(), PohonActivity.class);
                       // in.putExtra("tglUpload", textDate.getText().toString());
                     //   startActivity(in);

                    } else if (response.equals("kirim data gagal")) {

                        Toast.makeText(getApplicationContext(), "Data Gagal Dikirim", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), PohonActivity.class);
                        startActivity(in);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Maaf, Data Tidak Bisa dikirim. Periksa Jaringan Internet !", Toast.LENGTH_SHORT).show();
                    prgUpload.hide();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
        //            List<Survey> surveyList = dbController.getAllSurvey();

              //      for (Survey survey : surveyList) {
                        final String idPohon = textidpohon.getText().toString().trim();
                        final String idPetani = textidpetani.getText().toString().trim();
                        final String dbh = textdmtr.getText().toString().trim();
                        final String lat = textlat.getText().toString().trim();
                        final String lng = textlong.getText().toString().trim();
                        final String keterangan = textfeedback.getText().toString().trim();
                        final String img = textpath.getText().toString().trim();
                        final String tgl = textdateDB.getText().toString().trim();
                        final String tglUpload = textdateDB.getText().toString().trim();
                        final String kategori = radioKatagoriButton.getText().toString();
                        final String tipe = textaktivitas.getText().toString().trim();

                        Log.d("idPohon: ", idPohon);
                        Log.d("idPetani: ", idPetani);
                        Log.d("diameter: ", dbh);
                        Log.d("lat: ", lat);
                        Log.d("long: ", lng);
                        Log.d("keterangan: ", keterangan);
                        Log.d("path: ", img);
                        Log.d("tglSurvey: ", tgl);
                        Log.d("tglUpload: ", tglUpload);
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
                        Log.d("kond: ", katPohon);

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
                        Log.d("aktivitas: ", aktivitas);
                        params.put("tipe", aktivitas);
                //    }
                    return params;
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(strReq);

            //TODO : END OF EDITED

        }

    //TODO : EDITED
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //TODO : END OF EDITED


    public void onLocationChanged(Location location) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        textlat.setText(String.valueOf(location.getLatitude()));
        textlong.setText(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //mengambil lokasi
    public void getLokasi(View v) {
        if (location != null){

            textlat.setText(String.valueOf(location.getLatitude()));
            textlong.setText(String.valueOf(location.getLongitude()));
          //  showLayerBawah();
        }  else{

            Toast.makeText(getBaseContext(), "Lokasi Tidak Ditemukan", Toast.LENGTH_SHORT).show();

        }}


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && writeAccepted && readAccepted){}

                        //    Snackbar.make(view, "Permission sudah diaktifkan. Silahkan akses lokasi data dan kamera", Snackbar.LENGTH_LONG).show();
                       // Toast.makeText(getApplicationContext(), "Permisson Sudah Aktif", Toast.LENGTH_LONG).show();
                }  else {

                    Toast.makeText(getApplicationContext(), "Permission Belum Aktif", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        showMessageOKCancel("Anda Harus Mengaktifkan Permissions yang Lain",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{ACCESS_FINE_LOCATION, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    }
                                });
                        return;

                    }

                }
                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SurveiActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //TODO : EDITED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //TODO : END OF EDITED
}
