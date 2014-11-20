package com.bideyuanli.numberwar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Siyi on 2014/11/19.
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private NumberModel model = NumberModel.get();

    public GridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return model.getWidth() * model.getHeight();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new TextView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setPadding(8, 8, 8, 8);
            imageView.setTextSize(20);
            imageView.setElevation(50);
        } else {
            imageView = (TextView) convertView;
        }

        imageView.setText("" + position);
        return imageView;
    }

}