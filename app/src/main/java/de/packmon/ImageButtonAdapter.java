package de.packmon;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by georg_000 on 27/09/2015.
 */
public class ImageButtonAdapter extends BaseAdapter {
    private Context mContext;

    public ImageButtonAdapter(Context c) {
        mContext = c;
    }

    public ImageButtonAdapter(Context c, Integer[] buttons){
        mContext = c;
        mThumbIds = buttons;
    }

    public void changeButtons(Integer[] buttons){
        mThumbIds = buttons;
        notifyDataSetChanged();
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(256, 256));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(15, 1, 15, 1);
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.packmon_blue));
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    //TODO change reference image
    private Integer[] mThumbIds;



}
