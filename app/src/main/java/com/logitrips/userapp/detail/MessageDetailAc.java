package com.logitrips.userapp.detail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.logitrips.userapp.LoginAc;
import com.logitrips.userapp.R;
import com.logitrips.userapp.adapter.ChatAdapter;
import com.logitrips.userapp.model.Chat;
import com.logitrips.userapp.model.FavouriteCars;
import com.logitrips.userapp.util.CustomRequest;
import com.logitrips.userapp.util.MySingleton;
import com.logitrips.userapp.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDetailAc extends ActionBarActivity {

    private static final String ARG_PARAM1 = "user_id";
    private static final String ARG_PARAM3 = "driver_id";
    private static final String ARG_PARAM5 = "driver_name";
    private static final String ARG_PARAM2 = "auth";
    private static final String ARG_PARAM4 = "profile_pic";
    private String mParam1;
    private String driver_name;

    private String mParam4;
    private String mParam2;


    private String mParam3;
    List<Chat> chats = new ArrayList<Chat>();
    private ListView lv;
    private ChatAdapter adapter;
    private ActionBar actionBar;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_messagedetail_list);
        b = getIntent().getExtras();
        mParam1 = b.getString(ARG_PARAM1);
        mParam2 = b.getString(ARG_PARAM2);
        mParam3 = b.getString(ARG_PARAM3);
        mParam4 = b.getString(ARG_PARAM4);
        driver_name = b.getString(ARG_PARAM5);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
   ;
        actionBar.setTitle(driver_name);
        getData(mParam1, mParam2, mParam3);
        lv = (ListView) findViewById(R.id.list);
        adapter = new ChatAdapter(this, chats, Integer.parseInt(mParam1), mParam4);
        lv.setAdapter(adapter);

    }

    private void getData(String user_id, final String auth, String driver_id) {
        if (!Utils.isNetworkAvailable(MessageDetailAc.this)) {
            Toast.makeText(MessageDetailAc.this, R.string.no_net, Toast.LENGTH_SHORT).show();
        } else {
            CustomRequest loginReq = new CustomRequest(Request.Method.GET,
                    getString(R.string.main_url) + "/mobile/getfavouritecars?user_id=" + user_id + "&driver_id=" + driver_id, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("chat data", response.toString());
                    try {
                        switch (response.getInt("response")) {
                            case 200:

                                getChats(response.getJSONArray("data"));
                                break;

                            case 100:
                                Toast.makeText(MessageDetailAc.this, R.string.error_request, Toast.LENGTH_SHORT).show();
                                break;
                            case 300:
                                Toast.makeText(MessageDetailAc.this, R.string.no_available_cars, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MessageDetailAc.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", auth);
                    return params;
                }
            };
            MySingleton.getInstance(MessageDetailAc.this).addToRequestQueue(loginReq);
        }

    }

    private void getChats(JSONArray array) throws JSONException {

        if (array.length() == 0) {
            Toast.makeText(MessageDetailAc.this, R.string.error_request, Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < array.length(); i++) {

            JSONObject item = array.getJSONObject(i);
            Chat chat = new Chat();
            chat.setDriver_id(item.getInt("driver_id"));
            chat.setUser_id(item.getInt("user_id"));
            chat.setDate_sent(item.getString("date_sent"));
            chat.setIs_recv(item.getBoolean("is_received"));
            chat.setMessage(item.getString("message"));

            chats.add(chat);

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
