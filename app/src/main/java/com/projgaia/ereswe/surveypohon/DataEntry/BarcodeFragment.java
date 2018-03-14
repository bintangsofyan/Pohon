package com.projgaia.ereswe.surveypohon.DataEntry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projgaia.ereswe.surveypohon.R;

/**
 * Created by ERESWE on 20/12/2016.
 */

public class BarcodeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_dataentry, container, false);


        return rootView;
    }


}
