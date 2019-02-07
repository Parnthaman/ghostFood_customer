package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.UserAddressAPI;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.SessionManager;
import com.ghostFood.fragment.Address;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressUpdate extends AppCompatActivity {

    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edtAddressLine1)
    EditText edtAddressLine1;
    @BindView(R.id.edtAddressLine2)
    EditText edtAddressLine2;
    @BindView(R.id.edtArea)
    EditText edtArea;
    @BindView(R.id.edtCity)
    EditText edtCity;
    @BindView(R.id.edtCountry)
    EditText edtCountry;
    @BindView(R.id.edtZipCode)
    EditText edtZipCode;
    @BindView(R.id.btnSaveAddress)
    Button btnSaveAddress;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.edtAddressContactName)
    EditText edtAddressContactName;

    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(AddressUpdate.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(AddressUpdate.this, toolbar);

        initView();
    }

    private void initView() {
        edtAddressLine1.setHint(Constants.AddressLine1);
        edtAddressLine2.setHint(Constants.AddressLine2);
        edtArea.setHint(Constants.Area);
        edtCity.setHint(Constants.City);
        edtCountry.setHint(Constants.Country);
        edtZipCode.setHint(Constants.ZipCode);

        tlbarSave.setVisibility(View.GONE);
        tlbarText.setText(Constants.AddAddress);

        tlbarText.setAllCaps(true);
        FontFunctions.getInstance().FontBalooBhaijaan(AddressUpdate.this, tlbarText);

        FontFunctions.getInstance().FontKGSecondSketch(AddressUpdate.this, btnSaveAddress);
    }

    private void callAddressApi() {
        CustomProgressDialog.getInstance().show(AddressUpdate.this);
        UserAddressAPI mUserAddressAPI = ApiConfiguration.getInstance2().getApiBuilder().create(UserAddressAPI.class);
        final Call<UserAddressAPI.ResponseAddress> call = mUserAddressAPI.Get(
                SessionManager.User.getInstance().getKey(),
                ""
        );
        call.enqueue(new Callback<UserAddressAPI.ResponseAddress>() {
            @Override
            public void onResponse(Call<UserAddressAPI.ResponseAddress> call, Response<UserAddressAPI.ResponseAddress> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        if (response.body().getData() != null && response.body().getData().size() > 0) {
                            UserAddressAPI.Data data = response.body().getData().get(0);

                            edtAddressContactName.setText(data.getAddressContactName());
                            edtAddressLine1.setText(data.getAddressLine1());
                            edtAddressLine2.setText(data.getAddressLine2());
                            edtArea.setText(data.getAddressArea());
                            edtCity.setText(data.getAddressCity());
                            edtCountry.setText(data.getAddressCountry());
                            edtZipCode.setText(data.getAddressZipCode());

                            //latitude = mAddress.getLatitude();
                            //longitude = mAddress.getLongitude();
                        }

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(AddressUpdate.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(AddressUpdate.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserAddressAPI.ResponseAddress> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(AddressUpdate.this, Constants.NoInternetConnection);
            }
        });

    }


    @OnClick({R.id.tlbar_back, R.id.btnSaveAddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.btnSaveAddress:
                //AddAddress();
                finish();
                if (AddAddressMap.mAddAddressMap != null)
                    AddAddressMap.mAddAddressMap.finish();
                Address.needToRefresh = 1;
                break;
        }
    }
}
