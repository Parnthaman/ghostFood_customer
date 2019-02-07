package com.ghostFood.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;
import com.ghostFood.api.ItemAPI;
import com.ghostFood.events.EBModifiers;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.FontFunctions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class ModifierAdapter extends BaseAdapter {
    Context mContext;
    List<ItemAPI.Ingredient> mModifierList;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public ModifierAdapter(Context context, List<ItemAPI.Ingredient> mModifierList) {
        this.mContext = context;
        this.mModifierList = mModifierList;
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
        return mModifierList.size();
    }

    @Override
    public ItemAPI.Ingredient getItem(int position) {
        return mModifierList.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_modifier, null);
            mHolder.sdItemImage = (SimpleDraweeView) convertView.findViewById(R.id.iv_ItemImage);
            mHolder.tvName = (TextView) convertView.findViewById(R.id.tvModifierName);
            mHolder.tvYes = (TextView) convertView.findViewById(R.id.tvYes);
            mHolder.tvNo = (TextView) convertView.findViewById(R.id.tvNo);
            mHolder.swYesNo = (ToggleButton) convertView.findViewById(R.id.swYesNo);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvName);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvNo);
            FontFunctions.getInstance().FontBabeNeueBold(mContext, mHolder.tvYes);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvName.setText(mModifierList.get(position).getDetails().getIngredientName());
        if (mModifierList.get(position).getSelected()) {
            mHolder.tvYes.setVisibility(View.VISIBLE);
            mHolder.tvNo.setVisibility(View.INVISIBLE);
            mHolder.swYesNo.setChecked(true);
        } else {
            mHolder.tvYes.setVisibility(View.INVISIBLE);
            mHolder.tvNo.setVisibility(View.VISIBLE);
            mHolder.swYesNo.setChecked(false);
        }

       /* mHolder.swYesNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mModifierList.get(position).setSelected(true);
                }else
                {
                    mModifierList.get(position).setSelected(false);
                }
                notifyDataSetChanged();
            }
        });*/

        mHolder.swYesNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mModifierList.get(position).getSelected())
                {
                    mModifierList.get(position).setSelected(false);
                }else
                {
                    mModifierList.get(position).setSelected(true);
                }
                EventBus.getDefault().post(new EBModifiers());
                notifyDataSetChanged();
            }
        });

        CommonFunctions.getInstance().LoadImageByFresco(mHolder.sdItemImage, mModifierList.get(position).getDetails().getIngredientImage());


        return convertView;
    }

    public class ViewHolder {
        TextView tvName, tvYes, tvNo;
        SimpleDraweeView sdItemImage;
        ToggleButton swYesNo;

    }
}