package com.ghostFood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ghostFood.util.CommonFunctions;
import com.ghostFood.R;


public class ItemImagesPageAdapter extends PagerAdapter {
    Context context;
    String[] itemImages;
    LayoutInflater inflater;

    public ItemImagesPageAdapter(Context context, String[] itemImages) {
        this.context = context;
        this.itemImages = itemImages;
    }

    @Override
    public int getCount() {
        return itemImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView iv_ItemImage;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_itemimage, container,
                false);

        iv_ItemImage = (ImageView) itemView.findViewById(R.id.ivItemImage);
        CommonFunctions.getInstance().LoadImageByFrescoNew(iv_ItemImage, itemImages[position]);
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}
