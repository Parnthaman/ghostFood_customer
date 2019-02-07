package com.ghostFood.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.model.OrdersList;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class OrdersAdapter extends BaseAdapter {
    Context mContext;
    List<OrdersList> mOrdersList;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public OrdersAdapter(Context context, List<OrdersList> mOrdersList) {
        this.mContext = context;
        this.mOrdersList = mOrdersList;
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
        return mOrdersList.size();
    }

    @Override
    public OrdersList getItem(int position) {
        return mOrdersList.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_orderlist, null);
            mHolder.tvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);
            mHolder.tvStatusValue = (TextView) convertView.findViewById(R.id.tvStatusValue);
            mHolder.tvAmountValue = (TextView) convertView.findViewById(R.id.tvAmountValue);
            mHolder.tvOrderDateValue = (TextView) convertView.findViewById(R.id.tvOrderDateValue);
            mHolder.tvOrderIdTxt = (TextView) convertView.findViewById(R.id.tvOrderIdTxt);
            mHolder.tvOrderAmountTxt = (TextView) convertView.findViewById(R.id.tvOrderAmountTxt);
            mHolder.tvOrderStatusTxt = (TextView) convertView.findViewById(R.id.tvOrderStatusTxt);

            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvOrderId);

            FontFunctions.getInstance().FontSegoeSemiBold(mContext, mHolder.tvStatusValue);
            FontFunctions.getInstance().FontSegoeSemiBold(mContext, mHolder.tvOrderDateValue);
            FontFunctions.getInstance().FontSegoeSemiBold(mContext, mHolder.tvAmountValue);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        String strDate = "";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdfSource.parse(getItem(position).getOrderDate());
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy, EEEE");
            strDate = sdfDestination.format(date);
            System.out.println("Converted date is : " + strDate);
        } catch (ParseException pe) {
            System.out.println("Parse Exception : " + pe);
        }

        mHolder.tvOrderId.setText("#" + getItem(position).getOrderNumber());

        if (getItem(position).getStatus().trim().equals("Accepted")) {
            mHolder.tvStatusValue.setTextColor(mContext.getResources().getColor(R.color.clr_blue));
        } else if (getItem(position).getStatus().trim().equals("Pending")) {
            mHolder.tvStatusValue.setTextColor(mContext.getResources().getColor(R.color.clr_blue));
        } else if (getItem(position).getStatus().trim().equals("Rejected")) {
            mHolder.tvStatusValue.setTextColor(mContext.getResources().getColor(R.color.clr_red));
        } else if (getItem(position).getStatus().trim().equals("Delivered")) {
            mHolder.tvStatusValue.setTextColor(mContext.getResources().getColor(R.color.green));
        }else if (getItem(position).getStatus().trim().equals("On the way")) {
            mHolder.tvStatusValue.setTextColor(mContext.getResources().getColor(R.color.clr_blue));
        }

        mHolder.tvStatusValue.setText(getItem(position).getStatus());
        mHolder.tvAmountValue.setText(getItem(position).getOrderAmount());
        mHolder.tvOrderDateValue.setText(strDate);
        mHolder.tvOrderAmountTxt.setText(Constants.Amount);
        mHolder.tvOrderIdTxt.setText(Constants.OrderId);
        mHolder.tvOrderStatusTxt.setText(Constants.Status);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("order_key", getItem(position).getOrderKey());
                MyActivity.getInstance().OrderStatus(mContext, bundle);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tvOrderId;
        TextView tvStatusValue;
        LinearLayout llStatus;
        TextView tvOrderDateValue;
        TextView tvAmountValue, tvOrderIdTxt, tvOrderAmountTxt, tvOrderStatusTxt;
    }
}