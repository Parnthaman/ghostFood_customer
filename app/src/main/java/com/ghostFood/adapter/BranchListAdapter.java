package com.ghostFood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.ghostFood.R;
import com.ghostFood.model.Branches;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class BranchListAdapter extends BaseAdapter {
    Context mContext;
    List<Branches> mBranches;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;
    ConstantsInternal.ListType mType;
    LatLng mCurrentLL;

    public BranchListAdapter(Context context, List<Branches> mBranches, ConstantsInternal.ListType mType, LatLng mCurrentLL) {
        this.mContext = context;
        this.mBranches = mBranches;
        this.mType = mType;
        this.mCurrentLL = mCurrentLL;
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
        return mBranches.size();
    }

    @Override
    public Branches getItem(int position) {
        return mBranches.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_branchlist, null);
            mHolder.tvBranchName = (TextView) convertView.findViewById(R.id.tvBranchName);
            mHolder.tvBranchAddrss = (TextView) convertView.findViewById(R.id.tvBranchAddress);
            mHolder.tvDistance = (TextView) convertView.findViewById(R.id.tvBranchDistance);
            mHolder.tvDistanceText = (TextView) convertView.findViewById(R.id.tvDistanceText);
            mHolder.tvMapPosition = (TextView) convertView.findViewById(R.id.tvMapPosition);
            mHolder.btnDirection = (TextView) convertView.findViewById(R.id.btnDirection);
            mHolder.llDirection = (LinearLayout) convertView.findViewById(R.id.llDirection);
            mHolder.ivLocation = (ImageView) convertView.findViewById(R.id.ivLocation);

            if (this.mType == ConstantsInternal.ListType.LOCATION) {
                mHolder.btnDirection.setText(Constants.Direction);
            } else {
                mHolder.btnDirection.setText(Constants.PickUp);
            }
            mHolder.tvDistanceText.setText(Constants.Distance.toUpperCase());

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvBranchName.setText(getItem(position).getBranchName());
        mHolder.tvBranchAddrss.setText(getItem(position).getBranchAddress());
        mHolder.tvDistance.setText(getItem(position).getDistance() + " km");
        mHolder.tvMapPosition.setText((position + 1) + "");

        mHolder.btnDirection.setTag(getItem(position));
        mHolder.llDirection.setTag(getItem(position));
        mHolder.llDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Branches mBranches = (Branches) v.getTag();
                if (mType == ConstantsInternal.ListType.LOCATION) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + mCurrentLL.latitude + "," + mCurrentLL.longitude + "&daddr=" + mBranches.getLatitude() + "," + mBranches.getLongitude()));
                    mContext.startActivity(intent);


                } else {

                    /*if(!SessionManager.User.getInstance().getKey().trim().isEmpty()) */{
                        SessionManager.Cart.getInstance().setAddress(mBranches.getBranchName() + "\n" + mBranches.getBranchAddress());
                        SessionManager.Cart.getInstance().setBranchKey(mBranches.getBranchKey());
                        Bundle bundle = new Bundle();
                        MyActivity.getInstance().ItemListing(mContext, bundle);

                        SessionManager.Cart.getInstance().setLatitude(String.valueOf(mCurrentLL.latitude));
                        SessionManager.Cart.getInstance().setLongitude(String.valueOf(mCurrentLL.longitude));
                    }/*
                    else {
                        MyActivity.getInstance().LoginAct(mContext);
                    }*/
                }

            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tvBranchName, tvBranchAddrss, tvDistance, tvMapPosition,tvDistanceText;
        TextView btnDirection;
        LinearLayout llDirection;
        ImageView ivLocation;

    }
}