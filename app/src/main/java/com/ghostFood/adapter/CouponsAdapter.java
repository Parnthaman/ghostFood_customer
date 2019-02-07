package com.ghostFood.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghostFood.acitivity.CouponDetails;
import com.ghostFood.acitivity.MyApplication;
import com.ghostFood.model.Coupons;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
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

public class CouponsAdapter extends BaseAdapter {
    Context mContext;
    List<Coupons> mCouponsList;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;
    private String CouponCode = "";

    public CouponsAdapter(Context context, List<Coupons> mCouponsList) {
        this.mContext = context;
        this.mCouponsList = mCouponsList;
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
        return mCouponsList.size();
    }

    @Override
    public Coupons getItem(int position) {
        return mCouponsList.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_coupons, null);
//            mHolder.tvItemImage = (ImageView) convertView.findViewById(R.id.iv_ItemImage);
            mHolder.tvCouponName = (TextView) convertView.findViewById(R.id.tvOfferName);
            mHolder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
            mHolder.tvCouponDesc = (TextView) convertView.findViewById(R.id.tvOfferDescription);
            mHolder.tvCouponType = (TextView) convertView.findViewById(R.id.tvCouponType);
            mHolder.tvCopyCode = (TextView) convertView.findViewById(R.id.tvCopyCode);
            mHolder.llCouponDetail = (LinearLayout) convertView.findViewById(R.id.llCouponDetail);
            mHolder.llDescription = (LinearLayout) convertView.findViewById(R.id.llDescription);
            mHolder.ivArrow = (ImageView) convertView.findViewById(R.id.ivArrow);
            mHolder.rlCouponDetail = (RelativeLayout) convertView.findViewById(R.id.rlCouponDetail);
//            mHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);


            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvCouponType);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvCouponName);
            FontFunctions.getInstance().FontBalooBhaijaan(mContext, mHolder.tvCouponDesc);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvAmount);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        final ViewHolder finalMHolder = mHolder;
        mHolder.rlCouponDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMHolder.llDescription.getVisibility() == View.VISIBLE) {
                    finalMHolder.ivArrow.setBackground(mContext.getResources().getDrawable(R.drawable.ic_down_arrow_black));
                    finalMHolder.llDescription.setVisibility(View.GONE);
                } else {
                    finalMHolder.ivArrow.setBackground(mContext.getResources().getDrawable(R.drawable.ic_up_arrow_black));
                    finalMHolder.llDescription.setVisibility(View.VISIBLE);
                }
            }
        });

        String strDate = "";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdfSource.parse(getItem(position).getCouponStartDate());
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy hh:mm");
            strDate = sdfDestination.format(date);
            System.out.println("Converted date is : " + strDate);
        } catch (ParseException pe) {
            System.out.println("Parse Exception : " + pe);
        }

        String endDate = "";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdfSource.parse(getItem(position).getCouponEndDate());
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy hh:mm");
            endDate = sdfDestination.format(date);
            System.out.println("Converted date is : " + endDate);
        } catch (ParseException pe) {
            System.out.println("Parse Exception : " + pe);
        }

        mHolder.tvCouponName.setText(getItem(position).getCouponName());
//        mHolder.tvDate.setText(strDate + "  " + Constants.to + "  " + endDate);
        mHolder.tvCouponDesc.setText(getItem(position).getCouponDescription());
        mHolder.tvCouponType.setText(ConstantsInternal.CouponType.fromInteger(getItem(position).getCouponType()).toString());

        String price = CommonFunctions.getInstance().getIntORFloat(getItem(position).getCouponPrice());

        if (ConstantsInternal.CouponType.fromInteger(getItem(position).getCouponType()) == ConstantsInternal.CouponType.OnlinePercentOfferCoupon) {
            mHolder.tvAmount.setText(price + "%");
        } else {
            mHolder.tvAmount.setText(Constants.Price + ":" + CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(price));
        }
        mHolder.tvAmount.setTag(getItem(position));

//        CommonFunctions.getInstance().LoadImagePicasso(mContext, mHolder.tvItemImage, getItem(position).getCouponImage());

        //mHolder.tvOrderId.setText();

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewHolder mHolder = (ViewHolder) v.getTag();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("details", ((Coupons) mHolder.tvAmount.getTag()));
//                MyActivity.getInstance().CouponDetails(mContext, bundle);
//            }
//        });
        CouponCode = getItem(position).getCouponCode();
        mHolder.tvCopyCode.setText(Constants.CopyToClipboard);
        mHolder.tvCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(MyApplication.context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(Constants.Copied, CouponCode);
                clipboard.setPrimaryClip(clip);
                CommonFunctions.getInstance().ShowSnackBar(mContext, Constants.CopiedToClipboard);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        ImageView tvItemImage, ivArrow;
        LinearLayout llCouponDetail, llDescription;
        RelativeLayout rlCouponDetail;
        TextView tvCouponType, tvCouponName, tvCouponDesc, tvAmount, tvDate, tvCopyCode;

    }
}