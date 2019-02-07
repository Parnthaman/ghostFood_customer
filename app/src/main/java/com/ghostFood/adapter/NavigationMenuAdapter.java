package com.ghostFood.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.SessionManager;
import com.ghostFood.R;

import java.util.HashMap;
import java.util.List;

public class NavigationMenuAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private List<Integer> _listDataHeaderIcon;
    private HashMap<String, List<String>> _listDataChild;

    public NavigationMenuAdapter(Context context, List<String> listDataHeader, List<Integer> listDataHeaderIcons,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listDataHeaderIcon = listDataHeaderIcons;
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

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_list_item, null);
        }

        CommonFunctions.getInstance().ChangeFontFragment(convertView);

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        ImageView iv_arrow = (ImageView) convertView
                .findViewById(R.id.iv_arrow);

        TextView tvCount = (TextView) convertView.findViewById(R.id.tv_count);
        tvCount.setVisibility(View.GONE);
        if (groupPosition == 3 && childPosition == 2)//Pending Request Count
        {
            tvCount.setVisibility(View.GONE);
        } else if (groupPosition == 4 && childPosition == 0) // Pending Transaction Count
        {
            tvCount.setVisibility(View.GONE);
        }

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
            txtListChild.setGravity(Gravity.LEFT | Gravity.CENTER);
        } else {
            txtListChild.setGravity(Gravity.RIGHT | Gravity.CENTER);
        }

        txtListChild.setText(childText);
        FontFunctions.getInstance().FontBalooBhaijaan(_context, txtListChild);
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

    public Object getGroupIcon(int groupPosition) {
        return this._listDataHeaderIcon.get(groupPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        int headerImage = (Integer) getGroupIcon(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_list_group, null);
        }


        CommonFunctions.getInstance().ChangeFontFragment(convertView);

        ImageView iv_icon = (ImageView) convertView
                .findViewById(R.id.iv_icon);

        LinearLayout llParent = (LinearLayout) convertView.findViewById(R.id.llParent);
        //Sequent.origin(llParent).duration(150).anim(_context, Animation.FADE_IN_UP).start();

        ImageView ivArrow = (ImageView) convertView
                .findViewById(R.id.ivArrow);

        iv_icon.setImageResource(headerImage);

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            ivArrow.setImageResource(R.drawable.ic_leftarrow);
        } else {
            ivArrow.setImageResource(R.drawable.ic_rightarrow);
        }

        TextView tvCount = (TextView) convertView.findViewById(R.id.tv_count);
        tvCount.setVisibility(View.GONE);
        if (groupPosition == 6)//Pending Request Count
        {
            tvCount.setVisibility(View.GONE);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
            lblListHeader.setGravity(Gravity.LEFT | Gravity.CENTER);
        } else {
            lblListHeader.setGravity(Gravity.RIGHT | Gravity.CENTER);
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
}