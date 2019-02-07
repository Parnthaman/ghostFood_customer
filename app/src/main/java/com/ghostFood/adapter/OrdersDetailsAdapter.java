package com.ghostFood.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ghostFood.model.Item;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.R;

import java.util.List;


public class OrdersDetailsAdapter extends BaseAdapter {
    Context mContext;
    List<Item> mItems;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public OrdersDetailsAdapter(Context context, List<Item> mItems) {
        this.mContext = context;
        this.mItems = mItems;
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
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_orderdetailslist, null);
            mHolder.tvName = (TextView) convertView.findViewById(R.id.tvItemName);
            mHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvItemPrice);
            mHolder.tvIngredientsName = (TextView) convertView.findViewById(R.id.tvIngredientsName);
            mHolder.tvQuantityPrice = (TextView) convertView.findViewById(R.id.tvQuantityPrice);
            mHolder.view = (View) convertView.findViewById(R.id.view);


            FontFunctions.getInstance().FontBalooBhaijaan(mContext, mHolder.tvName);
            FontFunctions.getInstance().FontBalooBhaijaan(mContext, mHolder.tvQuantityPrice);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvPrice);
//            FontFunctions.getInstance().FontArialNovaLight(mContext, mHolder.tvIngredientsName);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (position == mItems.size() - 1) {
            mHolder.view.setVisibility(View.GONE);
        } else {
            mHolder.view.setVisibility(View.VISIBLE);
        }

        if (getItem(position).getIngredientsName() == null || getItem(position).getIngredientsName().isEmpty()) {
            mHolder.tvIngredientsName.setVisibility(View.GONE);
        } else {
            mHolder.tvIngredientsName.setVisibility(View.VISIBLE);
            mHolder.tvIngredientsName.setText("(" + getItem(position).getIngredientsName() + ")");
        }


        mHolder.tvQuantityPrice.setText(getItem(position).getQuantity() + " x " +
                CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(getItem(position).getTotalPrice()));
        mHolder.tvName.setText(getItem(position).getItemName());
        mHolder.tvPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(getItem(position).getTotalPrice()));

        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        TextView tvPrice;
        TextView tvIngredientsName, tvQuantityPrice;
        View view;

    }
}