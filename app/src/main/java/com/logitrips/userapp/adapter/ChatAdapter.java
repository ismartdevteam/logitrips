package com.logitrips.userapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.logitrips.userapp.R;
import com.logitrips.userapp.model.Car;
import com.logitrips.userapp.model.Chat;
import com.logitrips.userapp.util.CircleImageView;
import com.logitrips.userapp.util.MySingleton;

import java.util.List;


public class ChatAdapter extends ArrayAdapter<Chat> {
    private final Context mContext;
    private final int user_id;
    private final ImageLoader mImageLoader;
    private final String profile_pic;

    public ChatAdapter(Context context, List<Chat> mListItems, int user_id, String profile_pic) {
        super(context, 0, 0, mListItems);
        this.mContext = context;
        this.user_id = user_id;
        this.profile_pic = profile_pic;
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Chat item = getItem(position);
        Holder hol = null;
        if (v == null) {
            int layout = -1;
            if (user_id == item.getUser_id())
                v = ((Activity) mContext).getLayoutInflater().inflate(
                        R.layout.chat_user, parent, false);
            else
                v = ((Activity) mContext).getLayoutInflater().inflate(
                        R.layout.chat_driver, parent, false);
            hol = new Holder();
            if (user_id != item.getUser_id())
                hol.driver_image = (CircleImageView) v.findViewById(R.id.chat_det_driver_img);
            hol.text = (TextView) v.findViewById(R.id.chat_det_msg);
            hol.date = (TextView) v.findViewById(R.id.chat_det_date);
            v.setTag(hol);
        } else
            hol = (Holder) v.getTag();
        hol.text.setText(item.getMessage() + "");
        hol.date.setText(item.getDate_sent() + "");
        if (user_id != item.getUser_id()) {
            hol.driver_image.setImageUrl(profile_pic, mImageLoader);
            hol.driver_image.setDefaultImageResId(R.drawable.nodriver);
        }

        return v;
    }

    class Holder {
        CircleImageView driver_image;
        TextView text;
        TextView date;

    }
}
