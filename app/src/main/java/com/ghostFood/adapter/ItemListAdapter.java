package com.ghostFood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.model.Category;
import com.ghostFood.model.Item;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.FontFunctions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Category> _listDataHeader;
    LayoutInflater inflater;
    private HashMap<Category, List<Item>> _listDataChild;
    ExpandableListView _expListView;

    public ItemListAdapter(Context context, ArrayList<Category> listDataHeader,
                           HashMap<Category, List<Item>> listChildData, ExpandableListView expListView) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._expListView = expListView;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Item item = (Item) getChild(groupPosition, childPosition);

        inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemHolder itemHolder = new ItemHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_item, null);
            itemHolder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
            itemHolder.ivItemImage = (SimpleDraweeView) convertView.findViewById(R.id.ivItemImage);
            itemHolder.tvItemPrice = (TextView) convertView.findViewById(R.id.tvItemPrice);
            FontFunctions.getInstance().FontBalooBhaijaan(_context, itemHolder.tvItemName);
            FontFunctions.getInstance().FontBalooBhaijaan(_context, itemHolder.tvItemPrice);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

/*
        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
            txtListChild.setGravity(Gravity.LEFT);
        } else {
            txtListChild.setGravity(Gravity.RIGHT);
        }
*/

        CommonFunctions.getInstance().LoadImageByFresco(itemHolder.ivItemImage, item.getItemImage());
        itemHolder.tvItemName.setText(item.getItemName());
        itemHolder.tvItemPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(item.getItemPrice()));
        itemHolder.tvItemName.setTag(item.getItemKey());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this._listDataHeader.get(groupPosition) == null) {
            return 0;
        } else {

            if (this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    == null) {
                return 0;
            } else {

                return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                        .size();
            }
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Category mCategory = (Category) getGroup(groupPosition);
        inflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CategoryHolder categoryHolder = new CategoryHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_category, null);
            categoryHolder.tvCatagoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
            categoryHolder.tvCatagoryImage = (SimpleDraweeView) convertView.findViewById(R.id.tvCategoryImage);
            categoryHolder.ivCatagoryIndicator = (ImageView) convertView.findViewById(R.id.ivCatagoryIndicator);
            FontFunctions.getInstance().FontBalooBhaijaan(_context, categoryHolder.tvCatagoryName);
            convertView.setTag(categoryHolder);
        } else {
            categoryHolder = (CategoryHolder) convertView.getTag();
        }

        CommonFunctions.getInstance().LoadImageByFresco(categoryHolder.tvCatagoryImage, mCategory.getCategoryImage());
        categoryHolder.tvCatagoryName.setText(mCategory.getCategoryName());
        //categoryHolder.tvCatagoryName.setAllCaps(true);

        if (isExpanded) {
            categoryHolder.ivCatagoryIndicator.setImageResource(R.drawable.ic_uparrow);
        } else {
            categoryHolder.ivCatagoryIndicator.setImageResource(R.drawable.ic_downarrow);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ItemHolder {
        public TextView tvItemName,tvItemPrice;
        SimpleDraweeView ivItemImage;
    }

    public class CategoryHolder {
        TextView tvCatagoryName;
        SimpleDraweeView tvCatagoryImage;
        ImageView ivCatagoryIndicator;
    }
}