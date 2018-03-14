package com.projgaia.ereswe.surveypohon.DataEntry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.R;

import java.util.HashMap;

public class SearchDataActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    SessionManager sessionManager;
    String name;

    DBController controller = new DBController(this);
    Spinner spProvinsi, spKab, spKec, spDes, spGap, spKTH;
    String kth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);

        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(sessionManager.kunci_email);
   //     Toast.makeText(getApplicationContext(), "Selamat Datang " + name, Toast.LENGTH_LONG).show();



        spProvinsi = (Spinner) findViewById(R.id.spProvinsi);
        spKab = (Spinner) findViewById(R.id.spKabupaten);
        spKec = (Spinner) findViewById(R.id.spKecamatan);
        spDes = (Spinner) findViewById(R.id.spDesa);
        spGap = (Spinner) findViewById(R.id.spGapoktan);
        spKTH = (Spinner) findViewById(R.id.spKth);

        spinnerProvinsi();
        spinnerKabKota();
        spinnerKecamatan();
        spinnerDesa();
        spinnerGapoktan();
        spinnerKTH();

    }



    private void spinnerProvinsi (){
// Spinner Drop down cursor
        Cursor servicesCursor = controller.getProvinsiCursor();

        // map the cursor column names to the TextView ids in the layout
        String[] from = { "provinsi_name" };
        int[] to = { android.R.id.text1 };

        // Creating adapter for spinner
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                servicesCursor, from, to, 0);
        // attaching data adapter to spinner
        spProvinsi.setAdapter(dataAdapter);
    }


    private void spinnerKabKota (){
        // Spinner Drop down cursor
        Cursor servicesCursor = controller.getKabkotaCursor();

        // map the cursor column names to the TextView ids in the layout
        String[] from = { "kabkota_name" };
        int[] to = { android.R.id.text1 };

        // Creating adapter for spinner
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                servicesCursor, from, to, 0);
        // attaching data adapter to spinner
        spKab.setAdapter(dataAdapter);
    }


    private void spinnerKecamatan (){
        // Spinner Drop down cursor
        Cursor servicesCursor = controller.getKecamatanCursor();

        // map the cursor column names to the TextView ids in the layout
        String[] from = { "kecamatan_name" };
        int[] to = { android.R.id.text1 };

        // Creating adapter for spinner
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                servicesCursor, from, to, 0);
        // attaching data adapter to spinner
        spKec.setAdapter(dataAdapter);
    }


    private void spinnerDesa (){
        // Spinner Drop down cursor
        Cursor servicesCursor = controller.getDesaCursor();

        // map the cursor column names to the TextView ids in the layout
        String[] from = { "desa_name" };
        int[] to = { android.R.id.text1 };

        // Creating adapter for spinner
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                servicesCursor, from, to, 0);
        // attaching data adapter to spinner
        spDes.setAdapter(dataAdapter);
    }


    private void spinnerGapoktan (){
// Spinner Drop down cursor
        Cursor servicesCursor = controller.getGapoktanCursor();

        // map the cursor column names to the TextView ids in the layout
        String[] from = { "gapoktan_name" };
        int[] to = { android.R.id.text1 };

        // Creating adapter for spinner
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                servicesCursor, from, to, 0);
        // attaching data adapter to spinner
        spGap.setAdapter(dataAdapter);
    }


    private void spinnerKTH(){
        // Spinner Drop down cursor
        Cursor servicesCursor = controller.getKTHCursor();

        // map the cursor column names to the TextView ids in the layout
        String[] from = { "kth_name" };
        int[] to = { android.R.id.text1 };

        // Creating adapter for spinner
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                servicesCursor, from, to, 0);

        // attaching data adapter to spinner
        spKTH.setAdapter(dataAdapter);
    }


    public void viewPersil (View v) {

        Cursor kthCursor=(Cursor) spKTH.getSelectedItem();
        String kthId=kthCursor.getString(kthCursor.getColumnIndex("kth_id"));
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.createSession(name);

        Intent in = new Intent(getApplicationContext(), PersilActivity.class);
        in.putExtra("kthId", kthId);
        startActivity(in);

    //    Toast.makeText(SearchDataActivity.this, kthId, Toast.LENGTH_LONG).show();
    }
}
