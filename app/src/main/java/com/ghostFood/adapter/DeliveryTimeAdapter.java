package com.ghostFood.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ghostFood.model.DeliveryTime;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.R;

import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class DeliveryTimeAdapter extends BaseAdapter {
    Context mContext;
    List<DeliveryTime> mDeliveryTime;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public DeliveryTimeAdapter(Context context, List<DeliveryTime> mDeliveryTime) {
        this.mContext = context;
        this.mDeliveryTime = mDeliveryTime;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getCount() {
        return mDeliveryTime.size();
    }

    @Override
    public DeliveryTime getItem(int position) {
        return mDeliveryTime.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder mHolder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_deliverytime, null);
            mHolder.tvDeliveryTime = (TextView) convertView.findViewById(R.id.tvDeliveryTime);

            FontFunctions.getInstance().FontSketchBold(mContext, mHolder.tvDeliveryTime);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvDeliveryTime.setText(mDeliveryTime.get(position).getDeliveryTime());


        return convertView;
    }

    public class ViewHolder {
        TextView tvDeliveryTime;

    }
}