package com.ghostFood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghostFood.callback.CommonCallback;
import com.ghostFood.model.HomeOffer;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;

import java.util.ArrayList;


public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<HomeOffer> homeOffer;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, ArrayList<HomeOffer> homeOffer) {
        this.context = context;
        this.homeOffer = homeOffer;
    }

    @Override
    public int getCount() {
        return homeOffer.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        SimpleDraweeView iv_ItemImage, iv_OfferImage;
        TextView tvHeading, tvItemName, tvItemPrice;
        Button btnBuyNow;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_offer, container,
                false);

        iv_ItemImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_ItemImage);
        iv_OfferImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_OfferImage);
        //tvHeading = (TextView) itemView.findViewById(R.id.tvHeading);
        tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
        tvItemPrice = (TextView) itemView.findViewById(R.id.tvItemPrice);
        btnBuyNow = (Button) itemView.findViewById(R.id.btn_buyNow);

        //btnBuyNow.setText(homeOffer.get(position).getOfferText());
        //tvHeading.setText(homeOffer.get(position).getHeading());
        String price = CommonFunctions.getInstance().getIntORFloat(homeOffer.get(position).getItemPrice());
        tvItemPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(price));
        tvItemName.setText(homeOffer.get(position).getItemName());

        CommonFunctions.getInstance().LoadImageByFresco(iv_ItemImage, homeOffer.get(position).getItemImage());
        CommonFunctions.getInstance().LoadImageByFresco(iv_OfferImage, homeOffer.get(position).getOfferImage());

        //FontFunctions.getInstance().FontSketchBoldExtra(context, tvHeading);
        FontFunctions.getInstance().FontKGSecondSketchBold(context, tvItemName);
        FontFunctions.getInstance().FontSketchBoldExtra(context, tvItemPrice);

        btnBuyNow.setTag(homeOffer.get(position));
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeOffer homeOffer = (HomeOffer)v.getTag();
                CartDB.getInstance().AddSpecialOffer(homeOffer.getKey(), homeOffer.getOfferType().getValue(), homeOffer.getItemName(), homeOffer.getItemPrice(), homeOffer.getItemImage(), 0, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess() {
                        CommonFunctions.getInstance().ShowSnackBar(context, Constants.AddedToCart);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}
