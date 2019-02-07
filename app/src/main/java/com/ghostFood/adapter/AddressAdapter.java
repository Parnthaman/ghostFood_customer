package com.ghostFood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.DeleteAddressAPI;
import com.ghostFood.model.AddressList;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android1 on 5/12/2016.
 */

public class AddressAdapter extends BaseAdapter {
    Context mContext;
    List<AddressList> mAddressList;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;
    ConstantsInternal.AddressAdapterType mAddressAdapterType;

    public AddressAdapter(Context context, List<AddressList> mAddressList, ConstantsInternal.AddressAdapterType mAddressAdapterType) {
        this.mContext = context;
        this.mAddressList = mAddressList;
        this.mAddressAdapterType = mAddressAdapterType;
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
        return mAddressList.size();
    }

    @Override
    public AddressList getItem(int position) {
        return mAddressList.get(position);
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
            if (mAddressAdapterType == ConstantsInternal.AddressAdapterType.ChooseAddress)
                convertView = inflater.inflate(R.layout.adapter_chooseaddress, null);
            else if (mAddressAdapterType == ConstantsInternal.AddressAdapterType.ChooseAddressDialog)
                convertView = inflater.inflate(R.layout.adapter_chooseaddress1, null);
            else
                convertView = inflater.inflate(R.layout.adapter_addresslist, null);
            mHolder.tvAddressName = (TextView) convertView.findViewById(R.id.tvAddressName);
            mHolder.tvAddressNameTitle = (TextView) convertView.findViewById(R.id.tvAddressNameTitle);
            mHolder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            mHolder.ivEdit = (ImageView) convertView.findViewById(R.id.ivEdit);

            FontFunctions.getInstance().FontCalibri(mContext, mHolder.tvAddressName);


            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        if (mAddressAdapterType == ConstantsInternal.AddressAdapterType.Checkout) {
            mHolder.ivEdit.setVisibility(View.GONE);
            mHolder.ivDelete.setVisibility(View.GONE);
        } else if (mAddressAdapterType == ConstantsInternal.AddressAdapterType.ChooseAddress) {
            mHolder.ivEdit.setVisibility(View.GONE);
            mHolder.ivDelete.setVisibility(View.GONE);
        } else if (mAddressAdapterType == ConstantsInternal.AddressAdapterType.ChooseAddressDialog) {
            mHolder.ivEdit.setVisibility(View.GONE);
            mHolder.ivDelete.setVisibility(View.GONE);

        }


        if (mAddressAdapterType == ConstantsInternal.AddressAdapterType.ChooseAddressDialog) {

            mHolder.tvAddressNameTitle.setText(getItem(position).getAddressContactName().equals("") ? "" : getItem(position).getAddressContactName());
            mHolder.tvAddressName.setText((SessionManager.User.getInstance().getFirstName() + " " + SessionManager.User.getInstance().getLastName() + ", " + "\n" +
                    (getItem(position).getBuildingName().equals("") ? "" : getItem(position).getBuildingName() + ", ") +
                    (getItem(position).getFlatNo().equals("") ? "" : getItem(position).getFlatNo() + ", ") +
                    (getItem(position).getAddressLine1().equals("") ? "" : getItem(position).getAddressLine1() + ", ") + "\n" +
                    (getItem(position).getAddressLine2().equals("") ? "" : getItem(position).getAddressLine2() + ", ") +
                    (getItem(position).getAddressArea().equals("") ? "" : getItem(position).getAddressArea() + ", ") +
                    (getItem(position).getAddressCity().equals("") ? "" : getItem(position).getAddressCity() + ", ") + "\n" +
                    (getItem(position).getAddressCountry().equals("") ? "" : getItem(position).getAddressCountry())
                    + " - " + getItem(position).getAddressZipCode() + " "
            ));
        } else {
            mHolder.tvAddressNameTitle.setText(getItem(position).getAddressContactName());
            mHolder.tvAddressName.setText((SessionManager.User.getInstance().getFirstName() + " " + SessionManager.User.getInstance().getLastName() + ", " + "\n" +
                    (getItem(position).getBuildingName().equals("") ? "" : getItem(position).getBuildingName() + ", ") +
                    (getItem(position).getFlatNo().equals("") ? "" : getItem(position).getFlatNo() + ", ") +
                    (getItem(position).getAddressLine1().equals("") ? "" : getItem(position).getAddressLine1() + ", ") + "\n" +
                    (getItem(position).getAddressLine2().equals("") ? "" : getItem(position).getAddressLine2() + ", ") +
                    (getItem(position).getAddressArea().equals("") ? "" : getItem(position).getAddressArea() + ", ") +
                    (getItem(position).getAddressCity().equals("") ? "" : getItem(position).getAddressCity() + ", ") + "\n" +
                    (getItem(position).getAddressCountry().equals("") ? "" : getItem(position).getAddressCountry())
                    + " - " + getItem(position).getAddressZipCode() + " "
            ));
        }

        mHolder.ivEdit.setTag(getItem(position));
        mHolder.ivDelete.setTag(position);

        mHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alerts);
                TextView tvAlertTxt = dialog.findViewById(R.id.tvAlertTxt);
                TextView tvAlertDescription = dialog.findViewById(R.id.tvAlertDescription);
                TextView tvCancel = dialog.findViewById(R.id.tvCancel);
                TextView tvYes = dialog.findViewById(R.id.tvYes);
                tvCancel.setText(Constants.Cancel);
                tvYes.setText(Constants.Yes + "," + " " + Constants.DeleteIt);
                tvAlertDescription.setText(Constants.thisAddressWillBeDeleteFromYourProfile);
                tvAlertTxt.setText(Constants.areYouSure);
                tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        final int mPosition = (int) v.getTag();
                        String mAddressKey = mAddressList.get(position).getAddressKey();
                        CustomProgressDialog.getInstance().show(mContext);
                        DeleteAddressAPI mAddAddressAPI = ApiConfiguration.getInstance2().getApiBuilder().create(DeleteAddressAPI.class);
                        final Call<DeleteAddressAPI.ResponseDelete> call = mAddAddressAPI.Delete(
                                SessionManager.User.getInstance().getKey(),
                                mAddressKey
                        );
                        call.enqueue(new Callback<DeleteAddressAPI.ResponseDelete>() {
                            @Override
                            public void onResponse(Call<DeleteAddressAPI.ResponseDelete> call, Response<DeleteAddressAPI.ResponseDelete> response) {

                                CustomProgressDialog.getInstance().dismiss();
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == 200) {
                                        CommonFunctions.getInstance().ShowSnackBar(mContext, response.body().getMessage());

                                        mAddressList.remove(position);
                                        notifyChange(mAddressList);
                                        //notifyDataSetChanged();
                                    } else {
                                        CommonFunctions.getInstance().ShowSnackBar(mContext, response.body().getMessage());
                                    }
                                } else {
                                    CommonFunctions.getInstance().ShowSnackBar(mContext, response.body().getMessage());
                                }


                            }

                            @Override
                            public void onFailure(Call<DeleteAddressAPI.ResponseDelete> call, Throwable t) {
                                CustomProgressDialog.getInstance().dismiss();
                                CommonFunctions.getInstance().ShowSnackBar(mContext, Constants.NoInternetConnection);
                            }
                        });

                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                window.setAttributes(wlp);
                dialog.show();
            }
        });


        mHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressList mAddressList = getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("contact_name", mAddressList.getAddressContactName());
                bundle.putInt("from", ConstantsInternal.Address.Update.getValue());
                bundle.putString("address_key", mAddressList.getAddressKey());
                bundle.putDouble("latitude", mAddressList.getLatitude());
                bundle.putDouble("logitude", mAddressList.getLongitude());
                MyActivity.getInstance().AddAddressMap(mContext, bundle);
            }
        });


        return convertView;
    }

    private void notifyChange(List<AddressList> mAddressList) {
        this.mAddressList = mAddressList;
        notifyDataSetChanged();

    }

    public class ViewHolder {
        TextView tvAddressName, tvAddressNameTitle;
        ImageView ivDelete, ivEdit;

    }
}