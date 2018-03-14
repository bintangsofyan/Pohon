package com.projgaia.ereswe.surveypohon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projgaia.ereswe.surveypohon.Model.Pohon;
import com.projgaia.ereswe.surveypohon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ERESWE on 01/02/2017.
 */

public class PohonAdapter extends BaseAdapter {
    Context c;
   // private Activity activity;
    LayoutInflater inflater;
    ArrayList<Pohon> pohon;
    List<Pohon> filter;

    public PohonAdapter(Context c, ArrayList<Pohon> pohon) {
        this.c = c;
        this.pohon = pohon;
    }




    @Override
    public int getCount() {
        return pohon.size();
    }

    @Override
    public Object getItem(int position) {
        return pohon.get(position);
    }

    @Override
    public long getItemId(int position) {
        int itemID;

        // orig will be null only if we haven't filtered yet:
        if (filter == null)
        {
            itemID = position;
        }
        else
        {
            itemID = filter.indexOf(pohon.get(position));
        }
        return itemID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {


            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_admin_datapohon, parent, false);
        }

   //     Intent i = new Intent(convertView.getContext(), PohonActivity.class);
     //   Intent tglIntent = ((Activity)convertView.getContext()).getIntent();
      //  String tglUpload = tglIntent.getStringExtra("tglUpload");


        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView kode = (TextView) convertView.findViewById(R.id.kode);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView tgl = (TextView) convertView.findViewById(R.id.tglupload);
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress);

        Pohon data = pohon.get(position);

        id.setText(data.getId_pohon());
        nama.setText(data.getNamalokal_pohon());
        kode.setText(data.getKode_pohon());
        if (data.getPohon_lastupdate() == "uploading") {
            progressBar.setVisibility(View.VISIBLE);
            tgl.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            tgl.setVisibility(View.VISIBLE);
            tgl.setText("Terakhir kirim data : "+ data.getPohon_lastupdate());
        }
      //  tgl.setText(tglUpload);

        return convertView;
    }


}