package com.projgaia.ereswe.surveypohon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Model.Persil;
import com.projgaia.ereswe.surveypohon.R;

import java.util.List;

/**
 * Created by ERESWE on 10/09/2017.
 */

public class PersilAdapter extends ArrayAdapter<Persil> {
    private Context context;
    private List<Persil> listPersil;
    private DBController mDatabase;
    private LayoutInflater inflater;

    public PersilAdapter(Context context, List<Persil> imag) {
        super(context, 0, imag);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // view lookup cache stored in tag
        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.card_persil, parent, false);
            viewHolder.persil_id =
                    (TextView) convertView.findViewById(R.id.persil_id);
            viewHolder.kth_id =
                    (TextView) convertView.findViewById(R.id.kth_id);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PersilAdapter.ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        Persil persil =  getItem(position);
        // set description text
        //  viewHolder.angle.setText(image.getId_angle());
        // set image icon

        viewHolder.persil_id.setText(persil.getId_persil());
        viewHolder.kth_id.setText(persil.getId_kth());




        // Return the completed view to render on screen
        return convertView;
    }

    public class ViewHolder {

        TextView persil_id;
        TextView kth_id;
        TextView desa_id;
        TextView persil_nomor;
    }
}