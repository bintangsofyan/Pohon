package com.projgaia.ereswe.surveypohon.DataEntry;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.projgaia.ereswe.surveypohon.Adapter.PohonAdapter;
import com.projgaia.ereswe.surveypohon.Manager.SessionManager;
import com.projgaia.ereswe.surveypohon.Model.Pohon;
import com.projgaia.ereswe.surveypohon.R;

import java.util.ArrayList;
import java.util.List;

public class DataPohonActivity extends AppCompatActivity {


    SearchView sv = null;

    SessionManager sessionManager;
    String name;

    //listview
    ListView list;
    List<Pohon> itemList = new ArrayList<Pohon>();
    PohonAdapter adapter;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        String IDPersil = getIntent().getStringExtra("persilID");
      //  Toast.makeText(getApplicationContext(), IDPersil.toString()+" Di Pilih",Toast.LENGTH_LONG).show();

        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("persilID", IDPersil);//put string, int, etc in bundle with a key value
        PohonFragment pohonFragment = new PohonFragment();//Get Fragment Instance
        pohonFragment.setArguments(data);//Finally set argument bundle to fragment

    //    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, pohonFragment).commit();
    }
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PohonFragment(), "Manual");
        adapter.addFragment(new BarcodeFragment(), "QR Barcode");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
