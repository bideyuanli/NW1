package com.bideyuanli.numberwar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Siyi on 2014/11/19.
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private NumberModel model = NumberModel.get();
    private NumberView[] views;

    public GridAdapter(Context c) {
        mContext = c;
        reset();
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

    public NumberView getView(int i) {
        return views[i];
    }

    public void reset() {
        views = new NumberView[model.getSize()];
        for (int i = 0; i < views.length; i++) {
            views[i] = new NumberView(mContext);
            int padding = 4;
            views[i].setPadding(padding, padding, padding, padding);
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        NumberView view;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            view = views[position];
        } else {
            view = (NumberView) convertView;
        }

        return view;
    }

}