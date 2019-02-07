package com.ghostFood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.ghostFood.R;
import java.util.ArrayList;


public class ViewPagerAdapters extends PagerAdapter {
    Context context;
    ArrayList<Integer> homeOffer;
    LayoutInflater inflater;

    public ViewPagerAdapters(Context context, ArrayList<Integer> homeOffer) {
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

        ImageView ivItemImage;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_tour, container,
                false);

        ivItemImage = (ImageView) itemView.findViewById(R.id.ivItemImage);
        ivItemImage.setImageResource(homeOffer.get(position));
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}
