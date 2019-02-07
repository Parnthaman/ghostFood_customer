package com.ghostFood.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ghostFood.acitivity.ItemListing;
import com.ghostFood.model.Category;
import com.ghostFood.model.Item;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class CategoryGridAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Category> mCategory;
    HashMap<Category, List<Item>> mItem;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public CategoryGridAdapter(Context context, ArrayList<Category> mCategory, HashMap<Category, List<Item>> mItem) {
        this.mContext = context;
        this.mCategory = mCategory;
        this.mItem = mItem;
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
        return mCategory.size();
    }

    @Override
    public Category getItem(int position) {
        return mCategory.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_categorygrid, null);
            mHolder.ivCategoryImage = (SimpleDraweeView) convertView.findViewById(R.id.ivCategoryImage);
            mHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvCategoryName);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        if (getItem(position).getCategoryName() != null) {
            mHolder.tvCategoryName.setText(getItem(position).getCategoryName());
        }
        if (getItem(position).getCategoryImage() != null) {

            CommonFunctions.getInstance().LoadImageByFresco(mHolder.ivCategoryImage, getItem(position).getCategoryImage());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomProgressDialog.getInstance().show(mContext);
                ConstantsInternal.mCurrentPosition = position;
                ConstantsInternal.mCategory = mCategory;
                ConstantsInternal.mItems = mItem;
                MyActivity.getInstance().ItemListingGrid(mContext);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        SimpleDraweeView ivCategoryImage;
        TextView tvCategoryName;

    }
}