package com.ghostFood.acitivity;

import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.AddAddressAPI;
import com.ghostFood.api.UpdateAddressAPI;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddress extends AppCompatActivity {

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

    ConstantsInternal.Address addressStatus = ConstantsInternal.Address.Add;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.edtBuildingName)
    EditText edtBuildingName;
    @BindView(R.id.edtFlatNo)
    EditText edtFlatNo;
    private Double latitude, longitude;
    String mAddressKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(AddAddress.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(AddAddress.this, toolbar);

        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Address mAddress = (Address) bundle.getParcelable("address");
            edtAddressContactName.setText(SessionManager.User.getInstance().getFirstName() + " " + SessionManager.User.getInstance().getLastName());
            edtBuildingName.setText("");
            edtFlatNo.setText((mAddress.getFeatureName() == null) ? "" : mAddress.getFeatureName());
            edtAddressLine1.setText((mAddress.getSubLocality() == null) ? "" : mAddress.getSubLocality());
            edtAddressLine2.setText((mAddress.getThoroughfare() == null) ? "" : mAddress.getThoroughfare());
            edtArea.setText((mAddress.getLocality() == null) ? "" : mAddress.getLocality());
            edtCity.setText((mAddress.getAdminArea() == null) ? "" : mAddress.getAdminArea());
            edtCountry.setText((mAddress.getCountryName() == null) ? "" : mAddress.getCountryName());
            edtZipCode.setText((mAddress.getPostalCode() == null) ? "" : mAddress.getPostalCode());

            latitude = mAddress.getLatitude();
            longitude = mAddress.getLongitude();

            if (ConstantsInternal.Address.fromInteger(bundle.getInt("from")) == ConstantsInternal.Address.Update) {
                addressStatus = ConstantsInternal.Address.Update;
                tlbarText.setText(Constants.UpdateAddress);
                mAddressKey = bundle.getString("address_key");
            }

            if (bundle.getString("contact_name") != null) {
                edtAddressContactName.setText(bundle.getString("contact_name"));
            }


        }


    }

    private void initView() {
        edtBuildingName.setHint(Constants.BuildingName);
        edtFlatNo.setHint(Constants.FlatRoomNo);

        edtAddressLine1.setHint(Constants.AddressLine1);
        edtAddressLine2.setHint(Constants.Landmark);
        edtArea.setHint(Constants.Area);
        edtCity.setHint(Constants.City);
        edtCountry.setHint(Constants.Country);
        edtZipCode.setHint(Constants.ZipCode);

        tlbarSave.setVisibility(View.GONE);
        tlbarText.setText(Constants.AddAddress);
       // btnSaveAddress.setText(Constants.SaveAddress);
    }

    private void AddAddress() {
        CustomProgressDialog.getInstance().show(AddAddress.this);
        AddAddressAPI mAddAddressAPI = ApiConfiguration.getInstance2().getApiBuilder().create(AddAddressAPI.class);
        final Call<AddAddressAPI.ResponseAddress> call = mAddAddressAPI.Create(
                SessionManager.User.getInstance().getKey(),
                this.edtAddressContactName.getText().toString().trim(),
                this.edtBuildingName.getText().toString().trim(),
                this.edtFlatNo.getText().toString().trim(),
                this.edtAddressLine1.getText().toString().trim(),
                this.edtAddressLine2.getText().toString().trim(),
                this.edtArea.getText().toString().trim(),
                this.edtCity.getText().toString().trim(),
                this.edtCountry.getText().toString().trim(),
                this.edtZipCode.getText().toString().trim(),
                this.latitude,
                this.longitude
        );
        call.enqueue(new Callback<AddAddressAPI.ResponseAddress>() {
            @Override
            public void onResponse(Call<AddAddressAPI.ResponseAddress> call, Response<AddAddressAPI.ResponseAddress> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();

                        finish();
                        if (AddAddressMap.mAddAddressMap != null)
                            AddAddressMap.mAddAddressMap.finish();
                        com.ghostFood.fragment.Address.needToRefresh = 1;

                        //MyActivity.getInstance().OrderConfirmation(AddAddress.this);
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(AddAddress.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(AddAddress.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<AddAddressAPI.ResponseAddress> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(AddAddress.this, Constants.NoInternetConnection);
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

                if (addressStatus == ConstantsInternal.Address.Add) {
                    AddAddress();
                } else if (addressStatus == ConstantsInternal.Address.Update && !mAddressKey.isEmpty()) {

                    UpdateAddress();

                }
                break;
        }
    }

    private void UpdateAddress() {
        CustomProgressDialog.getInstance().show(AddAddress.this);
        UpdateAddressAPI mAddAddressAPI = ApiConfiguration.getInstance2().getApiBuilder().create(UpdateAddressAPI.class);
        final Call<UpdateAddressAPI.ResponseUpate> call = mAddAddressAPI.Update(
                SessionManager.User.getInstance().getKey(),
                mAddressKey,
                this.edtAddressContactName.getText().toString().trim(),
                this.edtBuildingName.getText().toString().trim(),
                this.edtFlatNo.getText().toString().trim(),
                this.edtAddressLine1.getText().toString().trim(),
                this.edtAddressLine2.getText().toString().trim(),
                this.edtArea.getText().toString().trim(),
                this.edtCity.getText().toString().trim(),
                this.edtCountry.getText().toString().trim(),
                this.edtZipCode.getText().toString().trim(),
                this.latitude,
                this.longitude
        );
        call.enqueue(new Callback<UpdateAddressAPI.ResponseUpate>() {
            @Override
            public void onResponse(Call<UpdateAddressAPI.ResponseUpate> call, Response<UpdateAddressAPI.ResponseUpate> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();

                        finish();
                        if (AddAddressMap.mAddAddressMap != null)
                            AddAddressMap.mAddAddressMap.finish();
                        com.ghostFood.fragment.Address.needToRefresh = 1;

                        //MyActivity.getInstance().OrderConfirmation(AddAddress.this);
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(AddAddress.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(AddAddress.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<UpdateAddressAPI.ResponseUpate> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(AddAddress.this, Constants.NoInternetConnection);
            }
        });
    }
}
