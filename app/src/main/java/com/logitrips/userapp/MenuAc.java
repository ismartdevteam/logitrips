package com.logitrips.userapp;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.logitrips.userapp.menu.AccountFragment;
import com.logitrips.userapp.menu.HomeFragment;
import com.logitrips.userapp.util.DateDialog;
import com.logitrips.userapp.util.Utils;

import java.util.Calendar;

import info.hoang8f.android.segmented.SegmentedGroup;


public class MenuAc extends ActionBarActivity implements HomeFragment.OnFragmentInteractionListener {
    private Bundle b;
    private ActionBar actionBar;



    public static FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft=getFragmentManager().beginTransaction();
        b = getIntent().getExtras();

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


        String pickdate="";
        String dropdate="";
        String venicle_class="";
        int location=1;
        if(b!=null) {
            if (b.getString("pick_date", "") != null)
                pickdate = b.getString("pick_date", "");

            if (b.getString("drop_date", "") != null)
                dropdate = b.getString("drop_date", "");


            if (b.getString("venicle_class", "") != null)
                venicle_class = b.getString("venicle_class", "");
            if(b.get("locaiton")!=null)
            location=b.getInt("location", 1);

        }

        HomeFragment homeFragment = HomeFragment.newInstance(pickdate,
               dropdate ,location,venicle_class);
        ft.add(R.id.frame, homeFragment);
        ft.commit();

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
