package com.logitrips.userapp.menu;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.logitrips.userapp.detail.CarDetailAc;
import com.logitrips.userapp.R;
import com.logitrips.userapp.adapter.CarAdapter;
import com.logitrips.userapp.model.Car;
import com.logitrips.userapp.util.CustomRequest;
import com.logitrips.userapp.util.DateDialog;
import com.logitrips.userapp.util.MySingleton;
import com.logitrips.userapp.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends Fragment implements OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "pick_date";
    private static final String ARG_PARAM2 = "drop_date";
    private static final String ARG_PARAM3 = "location";
    private static final String ARG_PARAM4 = "venicle_class";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mParam3;
    private String mParam4;
    private OnFragmentInteractionListener mListener;
    List<Car> cars = new ArrayList<Car>();
    private TextView pick_date_tv;
    private TextView drop_date_tv;
    private LinearLayout pick_date_ln;
    private LinearLayout drop_date_ln;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private ProgressBar home_list_prog;
    private SimpleDateFormat df;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private CarAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static HomeFragment newInstance(String param1, String param2, int param3, String param4) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    ActionBar actionBar;
    private View action_date_view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        inflater.inflate(R.menu.menu_main, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {

            final Dialog dialog = new Dialog(getActivity());

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.filter_dialog);
            final Spinner locaitonSp = (Spinner) dialog.findViewById(R.id.filter_dialog_location_spinner);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Utils.dest_str); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            locaitonSp.setAdapter(spinnerArrayAdapter);
            locaitonSp.setSelection(mParam3 - 1);
            SegmentedGroup segmentedGroup = (SegmentedGroup) dialog.findViewById(R.id.filter_class_type_group);
            Log.e("param4", mParam4);
            if (mParam4.equals(""))
                segmentedGroup.check(R.id.filter_any_btn);
            if (mParam4.equals("premium"))
                segmentedGroup.check(R.id.filter_prem_btn);
            if (mParam4.equals("business"))
                segmentedGroup.check(R.id.filter_bus_btn);
            if (mParam4.equals("standart"))
                segmentedGroup.check(R.id.filter_stan_btn);
            segmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.e("bla", checkedId + "");
                    switch (checkedId) {
                        case R.id.filter_any_btn:
                            mParam4 = "";
                            break;
                        case R.id.filter_prem_btn:
                            mParam4 = getActivity().getString(R.string.premium).toLowerCase();
                            break;
                        case R.id.filter_bus_btn:
                            mParam4 = getActivity().getString(R.string.business).toLowerCase();
                            break;
                        case R.id.filter_stan_btn:
                            mParam4 = getActivity().getString(R.string.standart).toLowerCase();
                            break;
                    }
                }
            });


            Button apply = (Button) dialog.findViewById(R.id.filter_apply_btn);
            apply.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParam3 = locaitonSp.getSelectedItemPosition() + 1;

                    getAvailableCar(mParam1, mParam2, mParam3, mParam4);
                    dialog.dismiss();

                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        home_list_prog = (ProgressBar) view.findViewById(R.id.home_list_prog);

        actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_date);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_home_orange);
        action_date_view = actionBar.getCustomView();

        pick_date_tv = (TextView) action_date_view.findViewById(R.id.action_pick_date);
        drop_date_tv = (TextView) action_date_view.findViewById(R.id.action_drop_date);

        pick_date_ln = (LinearLayout) action_date_view.findViewById(R.id.pick_date_ln);
        pick_date_ln.setOnClickListener(this);
        drop_date_ln = (LinearLayout) action_date_view.findViewById(R.id.drop_date_ln);
        drop_date_ln.setOnClickListener(this);

        setupDateBundle();


        MenuUtils.inflateMenu(view, getActivity());
        mAdapter = new CarAdapter(getActivity(), cars);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!pick_date_tv.getText().equals(getString(R.string.select_date)) && !drop_date_tv.getText().equals(getString(R.string.select_date))) {
                    Intent intent = new Intent(getActivity(), CarDetailAc.class);
                    intent.putExtra("Car", cars.get(position));
                    Bundle b = new Bundle();
                    b.putString("pick_date", pick_date_tv.getText() + "");
                    b.putString("drop_date", drop_date_tv.getText() + "");
                    intent.putExtras(b);
                    startActivity(intent);
                } else
                    Toast.makeText(getActivity(), R.string.select_date, Toast.LENGTH_SHORT).show();

            }
        });
        df = new SimpleDateFormat("yyyy-MM-dd");
        getAvailableCar(mParam1, mParam2, mParam3, mParam4);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == pick_date_ln) {
            DateDialog dialog = new DateDialog(pick_date_tv, 1);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            dialog.show(ft, "DatePicker");

        }
        if (v == drop_date_ln) {
            DateDialog dialog = new DateDialog(drop_date_tv, 1);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            dialog.show(ft, "DatePicker");
        }

    }

    private void setupDateBundle() {
        if (!mParam1.equals("") && !mParam2.equals("")) {
            if (mParam1.length() > 5) {
                pick_date_tv.setText(mParam1);
            } else
                pick_date_tv.setText(R.string.select_date);
            if (mParam2.length() > 5) {
                drop_date_tv.setText(mParam2);
            } else
                drop_date_tv.setText(R.string.select_date);
        } else {
            final Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + "-"
                    + (c.get(Calendar.MONTH) + 1) + "-" +
                    c.get(Calendar.DAY_OF_MONTH);
            pick_date_tv.setText(date);
            mParam1=date;
            drop_date_tv.setText(R.string.select_date);
        }
        pick_date_tv.addTextChangedListener(new TextWatcher() {
            boolean mToggle = false;
            String befStr;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                befStr=s+"";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mToggle) {
                    mParam1 = s.toString();
                    if (!mParam1.equals(getString(R.string.select_date))&& !mParam1.equals(befStr)) {

                        try {
                            if (df.parse(mParam1).before(df.parse(mParam2)))
                                getAvailableCar(mParam1, mParam2, mParam3, mParam4);
                            else {
                                Toast.makeText(getActivity(), R.string.enter_valid_date, Toast.LENGTH_SHORT).show();

                            }

                        } catch (ParseException e) {
                            getAvailableCar(mParam1, mParam2, mParam3, mParam4);
                            e.printStackTrace();
                        }
                    }
                }
                mToggle = !mToggle;
            }
        });
        drop_date_tv.addTextChangedListener(new TextWatcher() {
            boolean mToggle = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mToggle) {
                    Log.e(mParam1+"",mParam2+"");
                    mParam2 = s.toString();
                    //check date after than pick date
                    if (!mParam2.equals(getString(R.string.select_date))) {
                        try {
                            if (df.parse(mParam1).before(df.parse(mParam2)))
                                getAvailableCar(mParam1, mParam2, mParam3, mParam4);
                            else {
                                Toast.makeText(getActivity(), R.string.enter_valid_date, Toast.LENGTH_SHORT).show();
                                drop_date_tv.setText(R.string.select_date);
                            }

                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                    }
                }
                mToggle = !mToggle;

            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private void getAvailableCar(String pick_date, String drop_date, int location, String venicle_class) {
        Log.e("params", pick_date + drop_date + location + venicle_class);
        if (!Utils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_net, Toast.LENGTH_SHORT).show();
        } else {
            home_list_prog.setVisibility(View.VISIBLE);
            if (cars.size() > 0) {
                cars.clear();
                mAdapter.notifyDataSetChanged();
            }

            CustomRequest loginReq = new CustomRequest(Request.Method.GET,
                    getActivity().getString(R.string.main_url) + "/mobile/getavailablecars?" + "location=" + location + "&start_date=" + pick_date + "&end_date=" + drop_date
                            + "&vehicle_class=" + venicle_class, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("register res", response.toString());
                    home_list_prog.setVisibility(View.GONE);
                    try {
                        switch (response.getInt("response")) {
                            case 200:
                                getCars(response.getJSONArray("data"));
                                break;
                            case 300:
                                Toast.makeText(getActivity(), R.string.no_available_cars, Toast.LENGTH_SHORT).show();
                                break;
                            case 100:
                                Toast.makeText(getActivity(), R.string.error_request, Toast.LENGTH_SHORT).show();
                                break;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    home_list_prog.setVisibility(View.GONE);
                }

            });

            MySingleton.getInstance(getActivity()).addToRequestQueue(loginReq);
        }

    }

    private void getCars(JSONArray array) throws JSONException {
        if (array.length() == 0) {
            setEmptyText(getString(R.string.no_available_cars));
        }
        for (int i = 0; i < array.length(); i++) {
            Car car = new Car();
            JSONObject item = array.getJSONObject(i);
            car.setCar_id(item.getInt("car_id"));
            car.setCar_model(item.getString("car_model"));
            car.setYear(item.getInt("car_year"));
            car.setCar_class(item.getString("car_class"));
            car.setCar_rating(item.getDouble("car_rating"));
            JSONArray car_image_ar = item.getJSONArray("car_pic_urls");
            String[] car_image = new String[car_image_ar.length()];
            for (int j = 0; j < car_image_ar.length(); j++) {
                car_image[j] = car_image_ar.getString(j);
            }
            car.setCar_pic_urls(car_image);
            car.setLocation(item.getInt("location"));
            if (!item.isNull("hourly_price"))
                car.setHourly_price(item.getDouble("hourly_price"));
            car.setDaily_price(item.getDouble("daily_price"));
            car.setDay2_price(item.getDouble("day2_price"));
            car.setDriver_id(item.getInt("driver_id"));
            car.setDriver_name(item.getString("driver_name"));
            car.setDriver_pic_url(item.getString("driver_pic_url"));
            car.setDriver_knowledge(item.getString("driver_knowledge"));
            car.setDriver_smoking(item.getInt("driver_smoking"));
            JSONArray driver_lang_ar = item.getJSONArray("driver_languages");
            String[] driver_lang = new String[driver_lang_ar.length()];
            for (int k = 0; k < driver_lang_ar.length(); k++) {
                driver_lang[k] = driver_lang_ar.getString(k);
            }
            car.setDriver_lang(driver_lang);
            car.setDriver_year(item.getInt("driver_year"));
            cars.add(car);

        }
        mAdapter.notifyDataSetChanged();

    }
}
