package com.projgaia.ereswe.surveypohon.DataEntry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projgaia.ereswe.surveypohon.Adapter.PohonAdapter;
import com.projgaia.ereswe.surveypohon.Helper.DBController;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.Model.Pohon;
import com.projgaia.ereswe.surveypohon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ERESWE on 20/12/2016.
 */

public class PohonFragment extends Fragment {
    SessionManager sessionManager;
    String name;
    private List<Pohon> items;


    protected Cursor cursor;
    DBController dbController;


    ListView list;
    List<Pohon> itemList = new ArrayList<Pohon>();
    PohonAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_datapohon, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.idpersil);
        final TextView idpetani = (TextView) rootView.findViewById(R.id.idpetani);

        list    = (ListView) rootView.findViewById(R.id.listPohon);

        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(sessionManager.kunci_email);
  //      Toast.makeText(getContext(), "Pohon Fragment " + name, Toast.LENGTH_LONG).show();



      // String IDPersil = getArguments().getString("persilID");
     //   text.setText(IDPersil);
      //  Toast.makeText(getActivity(), IDPersil.toString()+" Di Pilih",Toast.LENGTH_LONG).show();
        final DBController dbs = new DBController(getActivity());

        final List<Pohon> callPohon = dbs.getPohon();
       if(callPohon.size() > 0){
            list.setVisibility(View.VISIBLE);
            // Create the adapter to convert the array to views
       //     PohonAdapter pohonAdapter = new PohonAdapter(getActivity(), callPohon);
            list.setAdapter(adapter);
        }else {
            list.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Pohon tidak tersedia ...", Toast.LENGTH_LONG).show();
        }



                           itemList.clear();



        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, final long id) {
                // TODO Auto-generated method stub
                final String idP = callPohon.get(position).getId_pohon();
                Intent in = new Intent(getActivity(), SurveiActivity.class);
                in.putExtra("idPohon", idP);
                sessionManager = new SessionManager(getContext());
                sessionManager.createSession(name);
                startActivity(in);
            }
        });

        return rootView;





    }


}
