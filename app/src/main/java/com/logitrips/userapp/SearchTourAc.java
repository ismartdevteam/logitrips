package com.logitrips.userapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.logitrips.userapp.util.DateDialog;
import com.logitrips.userapp.util.SystemUiHider;
import com.logitrips.userapp.util.Utils;

import java.util.Calendar;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SearchTourAc extends FragmentActivity implements View.OnClickListener {
    private ImageView pick_date_btn;
    private ImageView drop_date_btn;
    private EditText pick_edit;
    private EditText drop_edit;
    private Spinner destination_sp;
    private TextView skip_btn;
    private Button find_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_tour);
        initView();
    }

    private void initView() {
        pick_edit = (EditText) findViewById(R.id.search_pick_date_edit);
        drop_edit = (EditText) findViewById(R.id.search_drop_date_edit);

        pick_date_btn = (ImageView) findViewById(R.id.search_pick_date_btn);
        drop_date_btn = (ImageView) findViewById(R.id.search_drop_date_btn);
        skip_btn = (TextView) findViewById(R.id.searh_skip_btn);
        find_btn=(Button) findViewById(R.id.search_find_btn);


        final Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-" +
                c.get(Calendar.DAY_OF_MONTH);
        pick_edit.setText(date);
        drop_edit.setText(date);
        find_btn.setOnClickListener(this);
        skip_btn.setOnClickListener(this);

        pick_date_btn.setOnClickListener(this);
        drop_date_btn.setOnClickListener(this);

//        drop_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    DateDialog dialog = new DateDialog(v);
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    dialog.show(ft, "DatePicker");
//                }
//            }
//        });
//        pick_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    DateDialog dialog = new DateDialog(v);
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    dialog.show(ft, "DatePicker");
//                }
//            }
//        });
        destination_sp=(Spinner) findViewById(R.id.search_destination_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Utils.dest_str); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination_sp.setAdapter(spinnerArrayAdapter);
    destination_sp.setSelection(0);

    }

    @Override
    public void onClick(View v) {
        if (v == pick_date_btn) {
            DateDialog dialog = new DateDialog(pick_edit,0);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            dialog.show(ft, "DatePicker");
        }
        if (v == drop_date_btn) {
            DateDialog dialog = new DateDialog(drop_edit,0);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            dialog.show(ft, "DatePicker");
        }
        if(v==find_btn){
            Bundle b=new Bundle();
            b.putInt("dest", destination_sp.getSelectedItemPosition());
            b.putString("pick_date", pick_edit.getText().toString()+"");
            b.putString("drop_date", drop_edit.getText().toString()+"");
            Intent intent=new Intent(SearchTourAc.this,MenuAc.class);
            intent.putExtras(b);
            startActivity(intent);
        }
        if(v==skip_btn)
        {
            finish();
            startActivity(new Intent(SearchTourAc.this,MenuAc.class));
        }
    }
}
