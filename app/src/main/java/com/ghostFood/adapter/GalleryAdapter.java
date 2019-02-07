package com.ghostFood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.api.GalleryApi;
import com.ghostFood.util.CommonFunctions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private final List<GalleryApi.GalleryApiResponse.Datum> gallery_data;
    Context mContext;
    private static LayoutInflater inflater = null;
    Typeface mTypeface;

    public GalleryAdapter(Context mContext, List<GalleryApi.GalleryApiResponse.Datum> gallery_data) {
        this.mContext = mContext;
        this.gallery_data = gallery_data;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getCount() {
        return gallery_data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GalleryAdapter.ViewHolder mHolder = new GalleryAdapter.ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_categorygrid, null);
            mHolder.ivCategoryImage = (SimpleDraweeView) convertView.findViewById(R.id.ivCategoryImage);
            mHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);

            mHolder.tvCategoryName.setVisibility(View.GONE);

            convertView.setTag(mHolder);
        } else {
            mHolder = (GalleryAdapter.ViewHolder) convertView.getTag();
        }

        if (gallery_data.get(position).getGallery_image_path() != null && !gallery_data.get(position).getGallery_image_path().equals("")) {
            CommonFunctions.getInstance().LoadImageByFresco(mHolder.ivCategoryImage, gallery_data.get(position).getGallery_image_path());
        }

        mHolder.ivCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gallery_data.get(position).getGallery_image_path() != null && !gallery_data.get(position).getGallery_image_path().equals("")) {
                    imgDialogView(mContext, gallery_data.get(position).getGallery_image_path());
                }
                else {

                }
            }
        });


        return convertView;
    }

    private void imgDialogView(Context mContext, String item_image) {
        // Display Alert Dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_img_view_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView img_cancel = (ImageView) dialog.findViewById(R.id.img_cancel);
        ImageView img_shop = (ImageView) dialog.findViewById(R.id.img_shop);

        // Shop Img
        Uri imageUri = Uri.parse(item_image);
        img_shop.setImageURI(imageUri);

        // Img Cancel
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        dialog.show();
    }

    public class ViewHolder {
        SimpleDraweeView ivCategoryImage;
        TextView tvCategoryName;

    }
}