package com.projgaia.ereswe.surveypohon.DataEntry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projgaia.ereswe.surveypohon.Adapter.PersilAdapter;
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.Model.Persil;
import com.projgaia.ereswe.surveypohon.R;

import java.util.HashMap;
import java.util.List;

public class PersilActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ListView lv;
    SessionManager sessionManager;
    String name;


    SearchView sv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persil);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setLogoDescription(getResources().getString(R.string.app_name));

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(sessionManager.kunci_email);
    //    Toast.makeText(getApplicationContext(), "Selamat Datang " + name, Toast.LENGTH_LONG).show();

        lv = (ListView) findViewById(R.id.list);

        String idKTH = getIntent().getStringExtra("kthId");
        final DBController db = new DBController(this);

        final List<Persil> callPersil = db.getPersil(idKTH);
        // Attach the adapter to a ListView
        if(callPersil.size() > 0){
            lv.setVisibility(View.VISIBLE);
            // Create the adapter to convert the array to views
            PersilAdapter persilAdapter = new PersilAdapter(this, callPersil);
            lv.setAdapter(persilAdapter);
        }else {
            lv.setVisibility(View.GONE);
            Toast.makeText(this, "Persil tidak tersedia ...", Toast.LENGTH_LONG).show();
        }

        lv.setSelected(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                String persilID = ((TextView) view.findViewById(R.id.persil_id)).getText().toString();
                sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createSession(name);
                Intent in = new Intent(getApplicationContext(), PohonActivity.class);
                in.putExtra("persilID", persilID);
                startActivity(in);



            }
        });

    }



}
