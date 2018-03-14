package com.projgaia.ereswe.surveypohon.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projgaia.ereswe.surveypohon.Model.User;
import com.projgaia.ereswe.surveypohon.R;

import java.util.List;

/**
 * Created by ERESWE on 29/01/2017.
 */

public class DataEntryAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<User> items;

    public DataEntryAdapter(Activity activity, List<User> items) {
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
            convertView = inflater.inflate(R.layout.list_admin_dataentry, null);

        TextView id_user = (TextView) convertView.findViewById(R.id.id_user);
        TextView nama_user = (TextView) convertView.findViewById(R.id.nama_user);
        TextView password = (TextView) convertView.findViewById(R.id.password);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView level = (TextView) convertView.findViewById(R.id.level);
        TextView id_level = (TextView) convertView.findViewById(R.id.id_level);

        User dataEntry = items.get(position);

        id_user.setText(dataEntry.getId());
        nama_user.setText(dataEntry.getNama());
        level.setText(dataEntry.getLevel());
        username.setText(dataEntry.getUsername());
        password.setText(dataEntry.getPassword());
        id_level.setText(dataEntry.getIdLevel());


        return convertView;
    }

}