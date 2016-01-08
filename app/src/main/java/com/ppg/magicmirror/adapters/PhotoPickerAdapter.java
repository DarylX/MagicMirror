package com.ppg.magicmirror.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ppg.magicmirror.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Daryl on 1/7/2016.
 */
public class PhotoPickerAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> urls;

    public PhotoPickerAdapter(Context c, ArrayList urls){
        context = c;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView iv;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            iv = new ImageView(context);
            iv.setLayoutParams(new GridView.LayoutParams(85, 85));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(8, 8, 8, 8);
        } else {
            iv = (ImageView) convertView;
        }

        Picasso.with(context).load(urls.get(position)).into(iv);

        return iv;
    }
}
