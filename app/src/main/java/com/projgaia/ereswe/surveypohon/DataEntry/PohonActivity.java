package com.projgaia.ereswe.surveypohon.DataEntry;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.projgaia.ereswe.surveypohon.Adapter.PohonAdapter;
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.Model.Pohon;
import com.projgaia.ereswe.surveypohon.Model.Survey;
import com.projgaia.ereswe.surveypohon.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.projgaia.ereswe.surveypohon.R.id.idpersil;
import static java.lang.System.currentTimeMillis;

public class PohonActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String name;
    TextView textdateDB;
    String IDPersil, idPohon;
    Cursor cursor;
    DBController dbController;
    private Toolbar mToolbar;
    ListView list;
    PohonAdapter pohonAdapter,adapterUpload;
    boolean isThereUpdate = false;

    ProgressDialog prgUpload;

    //TAMBAHAN
    //TODO : EDITED
    private Button btnUpload;
    private RequestQueue requestQueue;
    private List<Pohon> callPohon,pohonUpload = new ArrayList<>();
    private String temp = "";
    private int indexUpload = 0;
    //TODO : END OF EDITED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohon);

        //TAMBAHAN
        requestQueue = Volley.newRequestQueue(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setLogoDescription(getResources().getString(R.string.app_name));

        TextView text = (TextView) findViewById(idpersil);
        final TextView idpetani = (TextView) findViewById(R.id.idpetani);

        list    = (ListView) findViewById(R.id.listPohon);

        showDateNow();

        prgUpload = new ProgressDialog(PohonActivity.this);
        prgUpload.setMessage("Tunggu.. Proses Kirim Data Sedang Berlangsung");
        prgUpload.setIndeterminate(false);
        prgUpload.setCancelable(false);


        sessionManager = new SessionManager(PohonActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(sessionManager.kunci_email);
//        Toast.makeText(getContext(), "Pohon Fragment " + name, Toast.LENGTH_LONG).show();

        Log.d("TAG","create");
        IDPersil = getIntent().getStringExtra("persilID");
//        text.setText(IDPersil);
//        Toast.makeText(getApplicationContext(), IDPersil.toString()+" Di Pilih",Toast.LENGTH_LONG).show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,final int position, final long id) {
                // TODO Auto-generated method stub
                // final String idP = callPohon.get(position).getId_pohon();
                idPohon = ((TextView) view.findViewById(R.id.id)).getText().toString();

                Intent in = new Intent(getApplicationContext(), SurveiActivity.class);
                in.putExtra("idPohon", idPohon);
                in.putExtra("persilID", IDPersil);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSession(name);
                startActivity(in);
            }
        });

        //TAMBAHAN
        //TODO : EDITED
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        //TODO : END OF EDITED

    /*    list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, final long id) {
                // TODO Auto-generated method stub
                final String idP = callPohon.get(position).getId_pohon();
                Intent in = new Intent(getApplicationContext(), SurveiActivity.class);
                in.putExtra("idPohon", idP);
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSession(name);
                startActivity(in);
            }
        });
*/
    }

    //TODO : EDITED
    @Override
    protected void onResume() {
        super.onResume();
        isThereUpdate = false;

        settingUpload();

    }
    //TODO : END OF EDITED

    private void settingUpload() {
        dbController = new DBController(PohonActivity.this);

        callPohon = dbController.getPohonbyPersilId(IDPersil);
        //TODO : EDITED
        pohonUpload.addAll(callPohon);
        //TODO : END OF EDITED
        if(callPohon.size() > 0){
            list.setVisibility(View.VISIBLE);
            // Create the adapter to convert the array to views
            //TODO : EDITED
            pohonAdapter = new PohonAdapter(this, (ArrayList<Pohon>) callPohon);
            adapterUpload = new PohonAdapter(this, (ArrayList<Pohon>) pohonUpload);
            adapterUpload.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    callPohon.get(indexUpload).setPohon_lastupdate(temp);
                    pohonAdapter.notifyDataSetChanged();
                    indexUpload++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (pohonUpload.size() > 0 && indexUpload < callPohon.size()) {
                                if (dbController.getSurvey(pohonUpload.get(0).getId_pohon()).getId_survey() != null) {
                                    upload();
                                }
                                else {
                                    callPohon.get(indexUpload).setPohon_lastupdate("uploading");
                                    temp = "";
                                    pohonAdapter.notifyDataSetChanged();

                                    pohonUpload.remove(0);
                                    adapterUpload.notifyDataSetChanged();
                                }
                            }
                            else {
                                pohonUpload.addAll(callPohon);
                                indexUpload = 0;
                                if (isThereUpdate) {
                                    prgUpload.hide();

                                    Toast.makeText(PohonActivity.this, "Data Survey Selesai Dikirim", Toast.LENGTH_SHORT).show();
                                    reloadActivity();
                                }
                                else {
                                    prgUpload.hide();
                                    Toast.makeText(PohonActivity.this, "Tidak Ada Data Yang Bisa Dikirim", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    },1000);
                }
            });
            //TODO : END OF EDITED
            list.setAdapter(pohonAdapter);
        }else {
            list.setVisibility(View.GONE);
            Toast.makeText(PohonActivity.this, "Pohon tidak tersedia !!!", Toast.LENGTH_LONG).show();
        }
    }

    //TAMBAHAN
    //TODO : EDITED
    private void upload() {
        prgUpload.show();
        final Survey survey = dbController.getSurvey(pohonUpload.get(0).getId_pohon());
        if (dbController.getSurvey(pohonUpload.get(0).getId_pohon()).getId_survey() != null) {
            isThereUpdate = true;
            temp = callPohon.get(indexUpload).getPohon_lastupdate();
            callPohon.get(indexUpload).setPohon_lastupdate("uploading");
            pohonAdapter.notifyDataSetChanged();
            String url = "http://gaia.id/monitoring/uploaddata/upload.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("save_status") || jsonObject.getBoolean("exist")) {

                                    pohonUpload.remove(0);
                                    adapterUpload.notifyDataSetChanged();
                                    updateLastUpdatePohon();

                                }
                            } catch (JSONException e) {
                                Toast.makeText(PohonActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                prgUpload.hide();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            prgUpload.hide();
                            Toast.makeText(PohonActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("pohon-id",survey.getId_pohon());
                    hashMap.put("petani-id",survey.getId_petani());
                    hashMap.put("survey-dbh",survey.getDiameter());
                    hashMap.put("survey-latitude",survey.getLatitude());
                    hashMap.put("survey-longitude",survey.getLongitude());
                    hashMap.put("survey-keterangan",survey.getKeterangan());
                    if (survey.getStatuspohon().equals("Pohon Lama")) {
                        hashMap.put("survey-pohonstatus","0");
                    } else if (survey.getStatuspohon().equals("Pohon Baru")){
                        hashMap.put("survey-pohonstatus","1");
                    } else if (survey.getStatuspohon().equals("Pohon Sulam")) {
                        hashMap.put("survey-pohonstatus","2");
                    }
                    if (survey.getKatagoripohon().equals("Pohon Mati")) {
                        hashMap.put("survey-pohonkategori","0");
                    } else if (survey.getKatagoripohon().equals("Pohon Hidup")) {
                        hashMap.put("survey-pohonkategori","1");
                    }
                    Bitmap bm = BitmapFactory.decodeFile(survey.getImg());
                    final String images = getStringImage(bm);
                    hashMap.put("survey-img","gdb_"+survey.getId_pohon()+"_"+currentTimeMillis()+".jpeg");
                    hashMap.put("survey-img64",images);
                    hashMap.put("survey-tgl",survey.getTgl());
                    hashMap.put("survey-tglupload",textdateDB.getText().toString());
                    if (survey.getTipe().equals("Penanaman")) {
                        hashMap.put("survey-tipe","0");
                    } else if (survey.getTipe().equals("Pemantauan")) {
                        hashMap.put("survey-tipe","1");
                    }
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }
        else {
            pohonUpload.remove(0);
            adapterUpload.notifyDataSetChanged();
        }

    }
    //TODO : END OF EDITED

    public void reloadActivity() {
        IDPersil = getIntent().getStringExtra("persilID");
        Intent in = new Intent(getApplicationContext(), PohonActivity.class);
        in.putExtra("persilID", IDPersil);
        startActivity(in);
    }

    private void updateLastUpdatePohon(){

        dbController.updatePohon(Integer.parseInt(idPohon.toString()), textdateDB.getText().toString());
      //  finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_filter, menu);

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getPohon(newText);
                    return false;
                }

            });

        return true;

    }

    private  void showDateNow (){
        //menampilkan tanggal sekarang
        DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateDB =   new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateNow = datef.format(date);
        String dateNowDB = dateDB.format(date);
        TextView textdate = (TextView) findViewById(R.id.dateUpload);
        textdateDB = (TextView) findViewById(R.id.dateUploadDB);
        textdate.setText(dateNow);
        textdateDB.setText(dateNowDB);
    }

    private void getPohon(String searchTerm) {

        //  itemList.clear();

        List<Pohon> data=new ArrayList<>();
        DBController dbController = new DBController(this);
        SQLiteDatabase db = dbController.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM pohon WHERE (pohon_namalokal LIKE '%"+searchTerm+"%' OR pohon_kode LIKE '%"+searchTerm+"%')",null);
        //cursor.moveToFirst();
        if (cursor.getCount()>0) {
            try {
                //  cursor.moveToPosition(0);
                while (cursor.moveToNext()){
                    Pohon p = new Pohon();
                    String id=cursor.getString(0);
                    String name=cursor.getString(5);
                    String kode=cursor.getString(1);
                    String date=cursor.getString(7);
                    p.setId_pohon(id);
                    p.setNamalokal_pohon(name);
                    p.setKode_pohon(kode);
                    p.setPohon_lastupdate(date);
                    data.add(p);
                }

                pohonAdapter = new PohonAdapter(PohonActivity.this, (ArrayList<Pohon>) data);
                list.setAdapter(pohonAdapter);
                pohonAdapter.notifyDataSetChanged();
                // dbController.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pohonAdapter.notifyDataSetChanged();

        }else {
            Toast.makeText(getApplication(), "Data Tidak Ada !", Toast.LENGTH_LONG).show();
        }

    }

    //PINDAHAN DARI SURVEI ACTIVITY
    //TODO : EDITED
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //TODO : END OF EDITED

}
