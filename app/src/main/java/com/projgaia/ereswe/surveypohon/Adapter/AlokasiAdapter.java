package com.projgaia.ereswe.surveypohon.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projgaia.ereswe.surveypohon.Model.Alokasi;
import com.projgaia.ereswe.surveypohon.R;

import java.util.List;

import static com.projgaia.ereswe.surveypohon.R.id.id_survey;
import static com.projgaia.ereswe.surveypohon.R.id.nama_user;

/**
 * Created by ERESWE on 20/02/2017.
 */

public class AlokasiAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Alokasi> items;

    public AlokasiAdapter(Activity activity, List<Alokasi> items) {
        this.activity = activity;
        this.items = items;
    }




    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_admin_alokasi, null);

        TextView id_sur = (TextView) convertView.findViewById(id_survey);
        TextView nama_us = (TextView) convertView.findViewById(nama_user);
        TextView nama_poh = (TextView) convertView.findViewById(R.id.nama_pohon);
        TextView status = (TextView) convertView.findViewById(R.id.statussurvey);


        Alokasi alokasi = items.get(position);

        id_sur.setText(alokasi.getIdAlokasi());
        nama_us.setText("Surveyor : " + alokasi.getNamauser());
        nama_poh.setText("(" +alokasi.getKodePoh()+ ") " + alokasi.getNamaPoh());
      // status.setText(alokasi.getStatus());


      if (alokasi.getStatus().equals("0")) {
            status.setText("Data Belum Diterima");
        } else  if (alokasi.getStatus().equals("1")) {
            status.setText("Data Sedang Disurvey");
        } else  if (alokasi.getStatus().equals("2")) {
            status.setText("Survey Selesai");
        }



        return convertView;
    }

}