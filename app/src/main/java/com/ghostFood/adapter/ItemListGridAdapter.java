package com.ghostFood.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.model.Category;
import com.ghostFood.model.Item;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;
import com.ghostFood.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class ItemListGridAdapter extends BaseAdapter {
    Context mContext;
    List<Item> items;
    HashMap<Category, List<Item>> mItem;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public ItemListGridAdapter(Context context, List<Item> items) {
        this.mContext = context;
        this.items = items;
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
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder mHolder = new ViewHolder();
        CustomProgressDialog.getInstance().dismiss();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_itemlistinggrid, null);
            mHolder.ivItemImage = (ImageView) convertView.findViewById(R.id.ivItemImage);
            mHolder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
            mHolder.tvItemPrice = (TextView) convertView.findViewById(R.id.tvItemPrice);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        mHolder.tvItemName.setText(getItem(position).getItemName());
        mHolder.tvItemName.setTag(getItem(position).getItemKey());
        mHolder.tvItemPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(getItem(position).getItemPrice()));
        CommonFunctions.getInstance().LoadImageByFrescoNew(mHolder.ivItemImage, getItem(position).getItemImage());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mTextView = view.findViewById(R.id.tvItemName);
                Bundle bundle = new Bundle();
                bundle.putString("item_key", mTextView.getTag() != null ? mTextView.getTag().toString() : "");
                MyActivity.getInstance().ItemDetails(mContext, bundle);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName, tvItemPrice;

    }
}