package com.projgaia.ereswe.surveypohon.DataEntry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.projgaia.ereswe.surveypohon.R;

public class SinkronActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinkron);
    }

    public void sinkron (View v) {
        Intent in = new Intent(getApplicationContext(), BerandaActivity.class);
        startActivity(in);

    }
}
