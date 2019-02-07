package com.ghostFood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghostFood.callback.CommonCallback;
import com.ghostFood.events.EBCalculateCart;
import com.ghostFood.model.SpecialOffer;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class SpecialOfferAdapter extends PagerAdapter {
    Context context;
    public ArrayList<SpecialOffer> specialOffer;
    LayoutInflater inflater;

    public SpecialOfferAdapter(Context context, ArrayList<SpecialOffer> specialOffer) {
        this.context = context;
        this.specialOffer = specialOffer;
    }

    @Override
    public int getCount() {
        return specialOffer.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        SimpleDraweeView ivItemImage;
        TextView tvOfferName, tvOfferDescription, tvPrice, tvHeading;
        final ImageView tvAdd;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_specialoffer, container,
                false);

        ivItemImage = (SimpleDraweeView) itemView.findViewById(R.id.ivItemImage);
        tvHeading = (TextView) itemView.findViewById(R.id.tvHeading);
        tvOfferName = (TextView) itemView.findViewById(R.id.tvOfferName);
        tvOfferDescription = (TextView) itemView.findViewById(R.id.tvOfferDescription);
        tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        tvAdd = (ImageView) itemView.findViewById(R.id.tvAdd);

        tvHeading.setText(specialOffer.get(position).getHeading());
        tvOfferName.setText(specialOffer.get(position).getOffername());
        tvOfferDescription.setText(specialOffer.get(position).getOfferDescription());

        String price = CommonFunctions.getInstance().getIntORFloat(specialOffer.get(position).getOfferPrice());
        tvPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(price));

        CommonFunctions.getInstance().LoadImageByFresco(ivItemImage, specialOffer.get(position).getItemImage());

        FontFunctions.getInstance().FontPristina(context, tvHeading);
        FontFunctions.getInstance().FontBerlinBold(context, tvOfferName);
        FontFunctions.getInstance().FontLatoFont(context, tvOfferDescription);
        FontFunctions.getInstance().FontBabeNeueBold(context, tvOfferDescription);
        FontFunctions.getInstance().FontBabeNeueExtraBold(context, tvPrice);

        ((ViewPager) container).addView(itemView);

        if(specialOffer.get(position).getSelected()) {
            tvAdd.setImageResource(R.drawable.ic_greentick);
        }

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (specialOffer.get(position).getSelected()) {
                    tvAdd.setImageResource(R.drawable.ic_cartplus);
                    specialOffer.get(position).setSelected(false);
                } else {
                    tvAdd.setImageResource(R.drawable.ic_greentick);
                    specialOffer.get(position).setSelected(true);
                }*/


                CartDB.getInstance().AddSpecialOffer(specialOffer.get(position).getOfferKey(), specialOffer.get(position).getOfferType().getValue(), specialOffer.get(position).getOffername(), specialOffer.get(position).getOfferPrice(), specialOffer.get(position).getItemImage(), 0, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess() {
                        CommonFunctions.getInstance().ShowSnackBar(context, Constants.AddedToCart);

                        EventBus.getDefault().post(new EBCalculateCart());
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}