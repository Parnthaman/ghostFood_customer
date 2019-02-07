package com.ghostFood.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.model.Item;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;

import java.util.List;

/**
 * Created by Tech on 19-Mar-2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
//    private final List<KitchenDetailApiResponse.CategoryId> category;

    Context context;
    List<Item> items;


    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemPrice;
        ImageView ivItemImage, ivVeg, ivNonveg;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivItemImage = (ImageView) itemView.findViewById(R.id.ivItemImage);
            ivVeg = (ImageView) itemView.findViewById(R.id.ivVeg);
            ivNonveg = (ImageView) itemView.findViewById(R.id.ivNonveg);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvItemPrice = (TextView) itemView.findViewById(R.id.tvItemPrice);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_itemlistinggrid, parent, false);
        return new ItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.MyViewHolder mHolder, int position) {
        final Item item = items.get(position);
        mHolder.tvItemName.setText(item.getItemName());
        mHolder.tvItemName.setTag(item.getItemKey());
        mHolder.tvItemPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(item.getItemPrice()));

        if (CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().dismiss();
        }


        if (item.getItemImage() == null) {
            CommonFunctions.getInstance().LoadImageByFrescoNew(mHolder.ivItemImage, "https://s3.amazonaws.com/ontabee/ontabee/frontend/download.jpeg");
        } else {
            CommonFunctions.getInstance().LoadImageByFrescoNew(mHolder.ivItemImage, item.getItemImage());
        }

//        if (item.getFood_type() != null && item.getFood_type() == 1) {
//            mHolder.ivVeg.setVisibility(View.VISIBLE);
//            mHolder.ivNonveg.setVisibility(View.GONE);
//        } else {
//            mHolder.ivVeg.setVisibility(View.GONE);
//            mHolder.ivNonveg.setVisibility(View.VISIBLE);
//        }
        mHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mTextView = view.findViewById(R.id.tvItemName);
                Bundle bundle = new Bundle();
                bundle.putString("item_key", mTextView.getTag() != null ? mTextView.getTag().toString() : "");
                MyActivity.getInstance().ItemDetails(context, bundle);
            }
        });


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
