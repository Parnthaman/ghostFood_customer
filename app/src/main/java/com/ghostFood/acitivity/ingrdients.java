package com.ghostFood.acitivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ghostFood.R;
import com.ghostFood.adapter.DeliveryTimeAdapter;
import com.ghostFood.api.CheckoutAPI;
import com.ghostFood.api.CheckoutConfirmAPI;
import com.ghostFood.model.CheckoutDetails;
import com.ghostFood.model.CheckoutItemDetails;
import com.ghostFood.model.DeliveryTime;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.text.MessageFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ingrdients extends AppCompatActivity {

    CheckoutAPI.Data data = new CheckoutAPI.Data();
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.llCostingDynamic)
    LinearLayout llCostingDynamic;
    @BindView(R.id.tvDeliveryOption)
    TextView tvDeliveryOption;
    @BindView(R.id.rbASAP)
    RadioButton rbASAP;
    @BindView(R.id.rbLater)
    RadioButton rbLater;
    @BindView(R.id.rgDeliveryOption)
    RadioGroup rgDeliveryOption;
    @BindView(R.id.tvDeliveryNote)
    TextView tvDeliveryNote;
    @BindView(R.id.tvPaymentOption)
    TextView tvPaymentOption;
    @BindView(R.id.rbCOD)
    RadioButton rbCOD;
    @BindView(R.id.rbOnline)
    RadioButton rbOnline;
    @BindView(R.id.rgPaymentOption)
    RadioGroup rgPaymentOption;
    @BindView(R.id.tvPaymentNote)
    TextView tvPaymentNote;
    @BindView(R.id.llTotalFooter)
    LinearLayout llTotalFooter;
    @BindView(R.id.tvConfirmOrder)
    TextView tvConfirmOrder;
    @BindView(R.id.llConfirmOrder)
    LinearLayout llConfirmOrder;
    @BindView(R.id.llLoyalityPoints)
    LinearLayout llLoyalityPoints;
    @BindView(R.id.tvLoyalityPoints)
    TextView tvLoyalityPoints;
    @BindView(R.id.cbLoyalityPoints)
    CheckBox cbLoyalityPoints;
    @BindView(R.id.tvCouponTxt)
    TextView tvCouponTxt;
    @BindView(R.id.tvCouponValue)
    TextView tvCouponValue;
    @BindView(R.id.edtCouponNumber)
    EditText edtCouponNumber;
    @BindView(R.id.tvCouponEdit)
    TextView tvCouponEdit;
    @BindView(R.id.tvCouponApply)
    TextView tvCouponApply;
    @BindView(R.id.llCouponCodeDetails)
    LinearLayout llCouponCodeDetails;

    private String details;

    CheckoutDetails checkoutDetails = new CheckoutDetails();

    public static Activity mCheckoutActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(ingrdients.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(ingrdients.this, toolbar);

        Gson gson = new GsonBuilder().create();

        mCheckoutActivity = this;

        tlbarText.setText(Constants.Checkout);
        FontFunctions.getInstance().FontBalooBhaijaan(ingrdients.this, tlbarText);

        data = getIntent().getExtras() != null && getIntent().getExtras().getSerializable("data") != null ? (CheckoutAPI.Data) getIntent().getExtras().getSerializable("data") : new CheckoutAPI.Data();
        details = getIntent().getExtras() != null && getIntent().getExtras().getString("details") != null ? getIntent().getExtras().getString("details") : "";

        if (CartDB.getInstance().GetCouponItems().size() > 0) {
            llCouponCodeDetails.setVisibility(View.GONE);
            tvCouponValue.setText("");
            edtCouponNumber.setText("");
        }

        if (!details.trim().isEmpty()) {
            this.checkoutDetails = gson.fromJson(details, new TypeToken<CheckoutDetails>() {
            }.getType());
        }
        initView();
        createCostingView();
//        CommonFunctions.getInstance().HideSoftKeyboard(CheckOut.this);

    }

    private void createCostingView() {

        llCostingDynamic.removeAllViews();
        final float scale = getResources().getDisplayMetrics().density;
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        for (CheckoutAPI.PaymentDetail paymentDetail : data.getPaymentDetails()) {

            if (!CommonFunctions.getInstance().getIntORFloat(paymentDetail.getValue()).equals("0")) {
                LinearLayout linearLayoutParent = new LinearLayout(ingrdients.this);
                linearLayoutParent.setLayoutParams(params);
                linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout linearLayoutChild = new LinearLayout(ingrdients.this);
                linearLayoutChild.setLayoutParams(params);
                linearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);

                int pixelsDummy = (int) (0 * scale);
                LinearLayout.LayoutParams lpDummyView = new LinearLayout.LayoutParams(pixelsDummy, LinearLayout.LayoutParams.WRAP_CONTENT);
                lpDummyView.weight = (float) 0.6;

                TextView tvDummy = new TextView(ingrdients.this);
                tvDummy.setLayoutParams(lpDummyView);
                tvDummy.setText("");
                tvDummy.setVisibility(View.INVISIBLE);

                TextView tvAttribute = (TextView) inflater.inflate(R.layout.dynamictextview1, null);
                tvAttribute.setLayoutParams(lpDummyView);
                tvAttribute.setText(paymentDetail.getName());
                tvAttribute.setVisibility(View.VISIBLE);

                tvAttribute.setTextColor(paymentDetail.getColor() ? Color.YELLOW : Color.WHITE);
                //tvAttribute.setTextSize(paymentDetail.getColor() ? getResources().getDimension(R.dimen.fontsize) : getResources().getDimension(R.dimen.fontsize));

                TextView tvValue = (TextView) inflater.inflate(R.layout.dynamictextview1, null);
                tvValue.setLayoutParams(lpDummyView);
                //tvValue.setTextSize(getResources().getDimension(R.dimen.fontsize));
                tvValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(CommonFunctions.getInstance().getIntORFloat(paymentDetail.getValue())));
                tvValue.setTextColor(Color.WHITE);
                tvValue.setVisibility(View.VISIBLE);


                FontFunctions.getInstance().FontBabeNeueBold(ingrdients.this, tvDummy);
                FontFunctions.getInstance().FontBabeNeueBold(ingrdients.this, tvAttribute);
                FontFunctions.getInstance().FontBabeNeueBold(ingrdients.this, tvValue);

                linearLayoutChild.addView(tvDummy);
                linearLayoutChild.addView(tvAttribute);
                linearLayoutChild.addView(tvValue);

                llCostingDynamic.addView(linearLayoutChild);
            }
        }
    }

    private void initView() {
        tvDeliveryOption.setText(Constants.DeliveryOption);
        tvPaymentOption.setText(Constants.PaymentOption);
        FontFunctions.getInstance().FontPristina(ingrdients.this, tvDeliveryOption);
        FontFunctions.getInstance().FontPristina(ingrdients.this, tvPaymentOption);

        rbASAP.setText(Constants.ASAP);
        rbCOD.setText(Constants.CashOnDelivery);
        rbLater.setText(Constants.Later);
        rbOnline.setText(Constants.Online);

        tvPaymentNote.setText(Constants.PayByCOD);
        cbLoyalityPoints.setText(Constants.UseLoyaltyPoints);

        llLoyalityPoints.setVisibility(View.GONE);
        if (data.getShow_loyalty_points()) {
            llLoyalityPoints.setVisibility(View.VISIBLE);
        }


        SetASAP();
        if (ConstantsInternal.PaymentOption.fromInteger(data.getPaymentOption()) == ConstantsInternal.PaymentOption.Both) {
            rbCOD.setVisibility(View.VISIBLE);
            rbOnline.setVisibility(View.VISIBLE);
        } else if (ConstantsInternal.PaymentOption.fromInteger(data.getPaymentOption()) == ConstantsInternal.PaymentOption.Online) {
            rbOnline.setVisibility(View.VISIBLE);
            tvPaymentNote.setText(Constants.OnceYouConfirmingTheOrderYouWillBeRedirectedIntoThePaymentGateway);
            rbOnline.setChecked(true);
            rbCOD.setChecked(false);
        } else if (ConstantsInternal.PaymentOption.fromInteger(data.getPaymentOption()) == ConstantsInternal.PaymentOption.COD) {
            rbCOD.setVisibility(View.VISIBLE);
        }

        //if (data.getLoyaltyPoints() > 0)
        {
            llLoyalityPoints.setVisibility(View.VISIBLE);
            tvLoyalityPoints.setVisibility(View.VISIBLE);
            tvLoyalityPoints.setText(MessageFormat.format(Constants.YouHaveLoyaltyPoints, data.getLoyaltyPoints()));

            cbLoyalityPoints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    applyOfferAndLoyalty();
                }
            });
        }

        edtCouponNumber.clearFocus();
        tvCouponEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edtCouponNumber.requestFocus();
                edtCouponNumber.setText(tvCouponValue.getText().toString().trim());
                edtCouponNumber.setVisibility(View.VISIBLE);
                tvCouponApply.setVisibility(View.VISIBLE);
                tvCouponValue.setVisibility(View.GONE);
                tvCouponEdit.setVisibility(View.GONE);

            }
        });

        tvCouponApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtCouponNumber.getText().toString().isEmpty()) {
                    CommonFunctions.getInstance().EmptyField(ingrdients.this, Constants.CouponCode);
                }

                tvCouponValue.setText(edtCouponNumber.getText().toString().trim());
                edtCouponNumber.setVisibility(View.GONE);
                tvCouponApply.setVisibility(View.GONE);
                tvCouponValue.setVisibility(View.VISIBLE);
                tvCouponEdit.setVisibility(View.VISIBLE);

                applyOfferAndLoyalty();
            }
        });
        rbCOD.setText(Constants.CashOnDelivery);
        rbOnline.setText(Constants.Online);

        FontFunctions.getInstance().FontBerlin(ingrdients.this, cbLoyalityPoints);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, rbASAP);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, rbCOD);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, rbLater);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, rbOnline);


        tvCouponTxt.setText(Constants.CouponCode);
        tvCouponApply.setText(Constants.Apply);
        tvCouponEdit.setText(Constants.Edit);
        tvCouponTxt.setText(Constants.CouponCode);

        FontFunctions.getInstance().FontBerlin(ingrdients.this, tvCouponValue);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, edtCouponNumber);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, tvCouponApply);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, tvCouponEdit);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, tvCouponTxt);
        FontFunctions.getInstance().FontBerlin(ingrdients.this, tvCouponValue);

        FontFunctions.getInstance().FontSegoeSemiBold(ingrdients.this, tvDeliveryNote);
        FontFunctions.getInstance().FontSegoeSemiBold(ingrdients.this, tvPaymentNote);
        FontFunctions.getInstance().FontSegoeSemiBold(ingrdients.this, tvLoyalityPoints);

        tvConfirmOrder.setText(Constants.ConfirmOrder);
        FontFunctions.getInstance().FontBabeNeueBold(ingrdients.this, tvConfirmOrder);

        rbASAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetASAP();
            }
        });

        rbLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowModifierDialog();
            }
        });

        rbCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPaymentNote.setText(Constants.PayByCOD);
            }
        });

        rbOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPaymentNote.setText(Constants.OnceYouConfirmingTheOrderYouWillBeRedirectedIntoThePaymentGateway);
            }
        });

    }

    private void SetASAP() {
        if (data.getTimeSlot() != null && data.getTimeSlot().size() > 0) {
            tvDeliveryNote.setText(MessageFormat.format(Constants.YourOrderWillBeDeliveredTodayAt, (data.getTimeSlot().size() > 0 ? data.getTimeSlot().get(0) : "")));
            tvDeliveryNote.setTag(data.getTimeSlot().size() > 0 ? data.getTimeSlot().get(0) : "");
        } else {
            rgDeliveryOption.setVisibility(View.GONE);
            tvDeliveryNote.setText(Constants.YouCantOrderToday);
            tvDeliveryNote.setTag("");
        }
    }

    private void applyOfferAndLoyalty() {
        Gson gson = new GsonBuilder().create();

        checkoutDetails.setUse_loyalty_points(cbLoyalityPoints.isChecked());
        checkoutDetails.setCoupon_code(tvCouponValue.getText().toString().trim());
        final String output = gson.toJsonTree(checkoutDetails).toString();
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), output);

        CustomProgressDialog.getInstance().show(ingrdients.this);
        CheckoutAPI mCheckoutAPI = ApiConfiguration.getInstance2().getApiBuilder().create(CheckoutAPI.class);
        final Call<CheckoutAPI.ResponseCheckout> call = mCheckoutAPI.Verify(mRequestBody);
        call.enqueue(new Callback<CheckoutAPI.ResponseCheckout>() {
            @Override
            public void onResponse(Call<CheckoutAPI.ResponseCheckout> call, Response<CheckoutAPI.ResponseCheckout> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        data = response.body().getData();
                        initView();
                        createCostingView();

                        CustomProgressDialog.getInstance().dismiss();


                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<CheckoutAPI.ResponseCheckout> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, Constants.NoInternetConnection);
            }
        });
    }


    private void ShowModifierDialog() {

        final Dialog dialog = new Dialog(ingrdients.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_modifier);
        dialog.setCancelable(false);
        ListView lvDelieryTime = (ListView) dialog.findViewById(R.id.lvModifier);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setVisibility(View.GONE);

        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final ArrayList<DeliveryTime> mDeliveryTimeList = new ArrayList<>();
        DeliveryTime mDeliveryTime = new DeliveryTime();

        if (data.getTimeSlot() != null) {
            for (String time : data.getTimeSlot()) {
                mDeliveryTime = new DeliveryTime();
                mDeliveryTime.setId(time);
                mDeliveryTime.setDeliveryTime(time);
                mDeliveryTimeList.add(mDeliveryTime);
            }
        }

        DeliveryTimeAdapter mModifierAdapter = new DeliveryTimeAdapter(ingrdients.this, mDeliveryTimeList);
        lvDelieryTime.setAdapter(mModifierAdapter);

        lvDelieryTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvDeliveryNote.setText(MessageFormat.format(Constants.YourOrderWillBeDeliveredTodayAt, mDeliveryTimeList.get(position).getDeliveryTime()));
                tvDeliveryNote.setTag(mDeliveryTimeList.get(position).getId());
                //Toast.makeText(CheckOut.this, "Item Selected " + mDeliveryTimeList.get(position).getDeliveryTime(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void CheckoutConfirm() {
        Gson gson = new GsonBuilder().create();

        ArrayList<CheckoutItemDetails> items = CartDB.getInstance().GetItems();
        ArrayList<CheckoutItemDetails> offers = CartDB.getInstance().GetOffers();
        ArrayList<CheckoutItemDetails> specialOffers = CartDB.getInstance().GetSpecialOffers();
        ArrayList<CheckoutItemDetails> specialItems = CartDB.getInstance().GetSpecialItems();
        ArrayList<CheckoutItemDetails> couponItems = CartDB.getInstance().GetCouponItems();

        if (items.size() <= 0 && specialOffers.size() <= 0 && specialItems.size() <= 0 && couponItems.size() <= 0) {
            CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, Constants.PleaseAddItemsToCartAndCheckout);
            return;
        }

        CheckoutDetails checkoutDetails = new CheckoutDetails();
        checkoutDetails.setOrder_type(SessionManager.Cart.getInstance().getOrderType());
        checkoutDetails.setBranch_key(SessionManager.Cart.getInstance().getBranchKey());
        checkoutDetails.setUser_key(SessionManager.User.getInstance().getKey());
        checkoutDetails.setAddress_key(SessionManager.Cart.getInstance().getAddressKey());
        if (this.checkoutDetails != null) {
            checkoutDetails.setMobile_number(this.checkoutDetails.getMobile_number());
            checkoutDetails.setEmail(this.checkoutDetails.getEmail());
        }

        checkoutDetails.setLatitude(Double.parseDouble(SessionManager.Cart.getInstance().getLatitude()));
        checkoutDetails.setLongitude(Double.parseDouble(SessionManager.Cart.getInstance().getLongitude()));
        checkoutDetails.setItems(items);
        //checkoutDetails.setOffers(offers);
        checkoutDetails.setSpecial_offers(specialOffers);
        checkoutDetails.setSpecial_items(specialItems);
        checkoutDetails.setCoupon_items(couponItems);
        checkoutDetails.setCoupon_code(tvCouponValue.getText().toString().trim());
        checkoutDetails.setUse_loyalty_points(cbLoyalityPoints.isChecked());
        checkoutDetails.setPayment_option(rbCOD.isChecked() ? ConstantsInternal.PaymentOptionSelected.COD.getValue() : ConstantsInternal.PaymentOptionSelected.Online.getValue());
        checkoutDetails.setDelivery_option(rbASAP.isChecked() ? ConstantsInternal.DeliveryOption.ASAP.getValue() : ConstantsInternal.DeliveryOption.Later.getValue());
        checkoutDetails.setDelivery_time(tvDeliveryNote.getTag().toString());

        final String output = gson.toJsonTree(checkoutDetails).toString();
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), output);

        CustomProgressDialog.getInstance().show(ingrdients.this);
        CheckoutConfirmAPI mCheckoutConfirmAPI = ApiConfiguration.getInstance2().getApiBuilder().create(CheckoutConfirmAPI.class);
        final Call<CheckoutConfirmAPI.ResponseCheckoutConfirm> call = mCheckoutConfirmAPI.Confirm(mRequestBody);
        call.enqueue(new Callback<CheckoutConfirmAPI.ResponseCheckoutConfirm>() {
            @Override
            public void onResponse(Call<CheckoutConfirmAPI.ResponseCheckoutConfirm> call, Response<CheckoutConfirmAPI.ResponseCheckoutConfirm> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();

                        Bundle bundle = new Bundle();
                        bundle.putString("order_number", response.body().getData().getOrderNumber());
                        bundle.putString("loyalty_points", response.body().getData().getLoyaltyPoints());
                        MyActivity.getInstance().OrderConfirmation(ingrdients.this, bundle);
                        finish();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<CheckoutConfirmAPI.ResponseCheckoutConfirm> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, t.toString());
            }
        });

    }

    @OnClick({R.id.tlbar_back, R.id.llConfirmOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.llConfirmOrder:
                if (data.getTimeSlot() != null && data.getTimeSlot().size() <= 0) {
                    CommonFunctions.getInstance().ShowSnackBar(ingrdients.this, Constants.YouCantOrderToday);
                    return;
                } else {
                    CheckoutConfirm();
                }
                break;
        }
    }
}
