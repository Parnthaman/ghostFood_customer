package com.ghostFood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.acitivity.HelpContentActivity;
import com.ghostFood.api.CmsApi;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.R;

import java.util.List;


public class HelpAdapter extends BaseAdapter {
    private final Context context;
    private static LayoutInflater inflater = null;
    private final List<CmsApi.CmsApiResponse.Datum> responses;

    public HelpAdapter(Context context, List<CmsApi.CmsApiResponse.Datum> responses) {
        this.context = context;
        this.responses = responses;
    }


    @Override
    public int getCount() {
        return responses.size();
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
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        HelpAdapter.ViewHolder mHolder = new HelpAdapter.ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_help, null);
            mHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            mHolder.img_arrow = (ImageView) convertView.findViewById(R.id.img_arrow);



            convertView.setTag(mHolder);
        } else {
            mHolder = (HelpAdapter.ViewHolder) convertView.getTag();
        }

        FontFunctions.getInstance().FontArialNovaLight(context,mHolder.txt_name);
        mHolder.txt_name.setText(responses.get(position).getCms_title());
      //  mHolder.txt_name.setText(Html.fromHtml(responses.get(position).getCms_content(), Html.FROM_HTML_MODE_COMPACT));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HelpContentActivity.class);
                intent.putExtra("title",responses.get(position).getCms_title());
                intent.putExtra("content",responses.get(position).getCms_content());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView txt_name;
        ImageView img_arrow;
    }
}
