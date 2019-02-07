package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.adapter.AddressAdapter;
import com.ghostFood.api.UserAddressListAPI;
import com.ghostFood.model.AddressList;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseAddress extends AppCompatActivity {

    ArrayList<AddressList> mAddressList;
    AddressList mAddress;
    AddressAdapter mAddressAdapter;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_logo)
    ImageView tlbarLogo;
    @BindView(R.id.tlbar_add)
    ImageView tlbarAdd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lvAddress)
    ListView lvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(ChooseAddress.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(ChooseAddress.this, toolbar);

        tlbarAdd.setVisibility(View.VISIBLE);

        TextView tlbarText = (TextView) toolbar.findViewById(R.id.tlbar_text);
        ImageView tlbarLogo = (ImageView) toolbar.findViewById(R.id.tlbar_logo);
        FontFunctions.getInstance().FontBalooBhaijaan(ChooseAddress.this, tlbarText);

        callAddressApi();
       /* if(SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
            tlbarText.setText(Constants.Login);
            tlbarText.setVisibility(View.GONE);
            tlbarLogo.setVisibility(View.GONE);
        }
        else {
            callAddressApi();
        }*/

        tlbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MyActivity.getInstance().AddAddressMap(ChooseAddress.this, bundle);
            }
        });
    }

    private void callAddressApi() {
        mAddressList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(ChooseAddress.this);
        UserAddressListAPI mUserAddressListAPI = ApiConfiguration.getInstance2().getApiBuilder().create(UserAddressListAPI.class);
        final Call<UserAddressListAPI.ResponseAddress> call = mUserAddressListAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<UserAddressListAPI.ResponseAddress>() {
            @Override
            public void onResponse(Call<UserAddressListAPI.ResponseAddress> call, Response<UserAddressListAPI.ResponseAddress> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        for (UserAddressListAPI.Data data : response.body().getData()) {
                            mAddress = new AddressList();
                            mAddress.setAddressKey(data.getAddressKey());
                            mAddress.setAddressContactName(data.getAddressContactName());
                            mAddress.setAddressLine1(data.getAddressLine1());
                            mAddress.setAddressLine2(data.getAddressLine2());
                            mAddress.setAddressArea(data.getAddressArea());
                            mAddress.setAddressCity(data.getAddressCity());
                            mAddress.setAddressCountry(data.getAddressCountry());
                            mAddress.setAddressZipCode(data.getAddressZipCode());
                            mAddressList.add(mAddress);
                        }

                        mAddressAdapter = new AddressAdapter(ChooseAddress.this, mAddressList, ConstantsInternal.AddressAdapterType.Checkout);
                        lvAddress.setAdapter(mAddressAdapter);
                        lvAddress.setSmoothScrollbarEnabled(true);

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ChooseAddress.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(ChooseAddress.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserAddressListAPI.ResponseAddress> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(ChooseAddress.this, Constants.NoInternetConnection);
            }
        });

    }

}
