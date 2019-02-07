package com.ghostFood.acitivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.ghostFood.api.PaymentAPI;
import com.ghostFood.model.CheckoutDetails;
import com.ghostFood.model.CheckoutItemDetails;
import com.ghostFood.model.DeliveryTime;
import com.ghostFood.payment.PaystackParams;
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

public class CheckOut extends AppCompatActivity {

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
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvTotalValue)
    TextView tvTotalValue;
    @BindView(R.id.tvLoyaltyPoints)
    TextView tvLoyaltyPoints;
    @BindView(R.id.txtCouponCode)
    TextView txtCouponCode;
    @BindView(R.id.tvOrderSummary)
    TextView tvOrderSummary;
    @BindView(R.id.tvDeliveryDetail)
    TextView tvDeliveryDetail;
    @BindView(R.id.tvAddressContactName)
    TextView tvAddressContactName;
    @BindView(R.id.edtAddressContactName)
    EditText edtAddressContactName;
    @BindView(R.id.tvAddressContactEmail)
    TextView tvAddressContactEmail;
    @BindView(R.id.edtAddressContactEmail)
    EditText edtAddressContactEmail;
    @BindView(R.id.tvAddressContactNumber)
    TextView tvAddressContactNumber;
    @BindView(R.id.edtAddressContactNumber)
    EditText edtAddressContactNumber;
    @BindView(R.id.tvAddressLine1)
    TextView tvAddressLine1;
    @BindView(R.id.edtAddressLine1)
    EditText edtAddressLine1;
    @BindView(R.id.tvChooseYourDeliveryOption)
    TextView tvChooseYourDeliveryOption;
    @BindView(R.id.tvChooseYourPaymentOption)
    TextView tvChooseYourPaymentOption;
    @BindView(R.id.tvLaterTime)
    TextView tvLaterTime;

    private String details;

    CheckoutDetails checkoutDetails = new CheckoutDetails();

    public static Activity mCheckoutActivity;

    private static final int PURCHASE_REQUEST = 37;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(CheckOut.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(CheckOut.this, toolbar);

        Gson gson = new GsonBuilder().create();

        mCheckoutActivity = this;

        tlbarText.setText(Constants.Checkout);
        FontFunctions.getInstance().FontBalooBhaijaan(CheckOut.this, tlbarText);

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

        for (int count = 0; count < data.getPaymentDetails().size(); count++) {
            LayoutInflater li = LayoutInflater.from(CheckOut.this);
            View child = li.inflate(R.layout.inflate_order_summary, null, false);

            final TextView tvAttribute = child.findViewById(R.id.tvAttribute);
            final TextView tvValue = child.findViewById(R.id.tvValue);
            final View view = child.findViewById(R.id.view);
            if (!CommonFunctions.getInstance().getIntORFloat(data.getPaymentDetails().get(count).getValue()).equals("0")) {


                if (count == data.getPaymentDetails().size() - 1) {
                    view.setVisibility(View.GONE);
                }

                if (data.getPaymentDetails().get(count).getName().equals("Total")) {
                    tvTotal.setText(data.getPaymentDetails().get(count).getName() + ":");
                    tvTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(CommonFunctions.getInstance().getIntORFloat(data.getPaymentDetails().get(count).getValue())));
                    tvAttribute.setVisibility(View.GONE);
                    tvValue.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }

                FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvAttribute);
                FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvValue);

                tvAttribute.setText(data.getPaymentDetails().get(count).getName());
                tvAttribute.setTextColor(data.getPaymentDetails().get(count).getColor() ? Color.GRAY : Color.GRAY);
                tvValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(CommonFunctions.getInstance().getIntORFloat(data.getPaymentDetails().get(count).getValue())));
                llCostingDynamic.addView(child);
            } else {
                tvAttribute.setVisibility(View.GONE);
                tvValue.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            }
        }
//        final float scale = getResources().getDisplayMetrics().density;
//        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//
//
//        for (CheckoutAPI.PaymentDetail paymentDetail : data.getPaymentDetails()) {
//
//            if (!CommonFunctions.getInstance().getIntORFloat(paymentDetail.getValue()).equals("0")) {
//                LinearLayout linearLayoutParent = new LinearLayout(CheckOut.this);
//                linearLayoutParent.setLayoutParams(params);
//                linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);
//
//                LinearLayout linearLayoutChild = new LinearLayout(CheckOut.this);
//                linearLayoutChild.setLayoutParams(params);
//                linearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);
//
//                int pixelsDummy = (int) (0 * scale);
//                LinearLayout.LayoutParams lpDummyView = new LinearLayout.LayoutParams(pixelsDummy, LinearLayout.LayoutParams.WRAP_CONTENT);
//                lpDummyView.weight = (float) 0.6;
//
//                if (paymentDetail.getName().equals("Total")) {
//                    tvTotal.setText(paymentDetail.getName());
//                    tvTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(CommonFunctions.getInstance().getIntORFloat(paymentDetail.getValue())));
//                }
//
//
//
//                TextView tvAttribute = (TextView) inflater.inflate(R.layout.dynamictextview1, null);
//                tvAttribute.setLayoutParams(lpDummyView);
//                tvAttribute.setText(paymentDetail.getName());
//                tvAttribute.setVisibility(View.VISIBLE);
//
//                tvAttribute.setTextColor(paymentDetail.getColor() ? Color.BLACK : Color.BLACK);
//                //tvAttribute.setTextSize(paymentDetail.getColor() ? getResources().getDimension(R.dimen.fontsize) : getResources().getDimension(R.dimen.fontsize));
//
//
//                TextView tvDummy = new TextView(CheckOut.this);
//                tvDummy.setLayoutParams(lpDummyView);
//                tvDummy.setText("");
//                tvDummy.setVisibility(View.INVISIBLE);
//
//                TextView tvValue = (TextView) inflater.inflate(R.layout.dynamictextview1, null);
//                tvValue.setLayoutParams(lpDummyView);
//                //tvValue.setTextSize(getResources().getDimension(R.dimen.fontsize));
//                tvValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(CommonFunctions.getInstance().getIntORFloat(paymentDetail.getValue())));
//                tvValue.setTextColor(Color.BLACK);
//                tvValue.setVisibility(View.VISIBLE);
//
//                if (paymentDetail.getName().equals("Total")) {
//                    tvAttribute.setVisibility(View.GONE);
//                    tvValue.setVisibility(View.GONE);
//                }
//
//
//                FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvDummy);
//                FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvAttribute);
//                FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvValue);
//
//                linearLayoutChild.addView(tvDummy);
//                linearLayoutChild.addView(tvAttribute);
//                linearLayoutChild.addView(tvValue);
//
//                llCostingDynamic.addView(linearLayoutChild);
//            }
//        }
    }

    private void initView() {
        if (ConstantsInternal.OrderType.fromInteger(SessionManager.Cart.getInstance().getOrderType()) == ConstantsInternal.OrderType.Delivery) {
            tvDeliveryOption.setText(Constants.DeliveryOption);
        } else {
            tvDeliveryOption.setText(Constants.PickupOption);
        }

        tvDeliveryDetail.setText(Constants.DeliveryDetails);
        tvAddressContactName.setText(Constants.Username);
        edtAddressContactName.setText(SessionManager.User.getInstance().getFirstName() + " " + SessionManager.User.getInstance().getLastName());
        edtAddressContactEmail.setText(SessionManager.User.getInstance().getEmail());
        tvAddressContactEmail.setText(Constants.Email);
        tvAddressContactNumber.setText(Constants.MobileNumber);
        edtAddressContactNumber.setText(SessionManager.User.getInstance().getMobile());
        tvAddressLine1.setText(Constants.Address);
        edtAddressLine1.setText(SessionManager.Cart.getInstance().getAddress());
        tvChooseYourDeliveryOption.setText(Constants.ChooseYourDeliveryOption);
        tvChooseYourPaymentOption.setText(Constants.ChooseYourPaymentOption);


        tvPaymentOption.setText(Constants.PaymentOption);
        FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvDeliveryOption);
        FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvPaymentOption);

        rbASAP.setText(Constants.ASAP);
        rbCOD.setText(Constants.CashOnDelivery);
        rbLater.setText(Constants.Later);
        rbOnline.setText(Constants.online_payment);
        tvTotal.setText(Constants.Total);
        tvLoyaltyPoints.setText(Constants.LoyaltyPoints);
        txtCouponCode.setText(Constants.EnterCouponCode);
        tvOrderSummary.setText(Constants.orderSummary);


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

        if (data.getLoyaltyPoints() > 0) {
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
                    CommonFunctions.getInstance().EmptyField(CheckOut.this, Constants.CouponCode);
                } else {
                    tvCouponValue.setText(edtCouponNumber.getText().toString().trim());
                    edtCouponNumber.setVisibility(View.GONE);
                    tvCouponApply.setVisibility(View.GONE);
                    tvCouponValue.setVisibility(View.VISIBLE);
                    tvCouponEdit.setVisibility(View.VISIBLE);

                    applyOfferAndLoyalty();
                }
            }
        });
        rbCOD.setText(Constants.CashOnDelivery);
        rbOnline.setText(Constants.online_payment);

        FontFunctions.getInstance().FontBerlin(CheckOut.this, cbLoyalityPoints);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, rbASAP);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, rbCOD);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, rbLater);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, rbOnline);


        tvCouponTxt.setText(Constants.CouponCode);
        tvCouponApply.setText(Constants.Apply);
        tvCouponEdit.setText(Constants.Edit);
        tvCouponTxt.setText(Constants.CouponCode);

        FontFunctions.getInstance().FontBerlin(CheckOut.this, tvCouponValue);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, edtCouponNumber);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, tvCouponApply);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, tvCouponEdit);
        FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvCouponTxt);
        FontFunctions.getInstance().FontBerlin(CheckOut.this, tvCouponValue);

        FontFunctions.getInstance().FontSegoeSemiBold(CheckOut.this, tvDeliveryNote);
        FontFunctions.getInstance().FontSegoeSemiBold(CheckOut.this, tvPaymentNote);
        FontFunctions.getInstance().FontSegoeSemiBold(CheckOut.this, tvLoyalityPoints);

        tvConfirmOrder.setText(Constants.ConfirmOrder);
        FontFunctions.getInstance().FontBabeNeueBold(CheckOut.this, tvConfirmOrder);

        rbASAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbLater.setChecked(false);
                tvLaterTime.setVisibility(View.GONE);
                SetASAP();
            }
        });

        rbLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLaterTime.setVisibility(View.VISIBLE);
                rbASAP.setChecked(false);
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
            if (ConstantsInternal.OrderType.fromInteger(SessionManager.Cart.getInstance().getOrderType()) == ConstantsInternal.OrderType.Delivery) {
                tvDeliveryNote.setText(MessageFormat.format(Constants.YourOrderWillBeDeliveredTodayAt, (data.getTimeSlot().size() > 0 ? data.getTimeSlot().get(0) : "")));
            } else {
                tvDeliveryNote.setText(MessageFormat.format(Constants.YourOrderCabBePickedUpTodayAt, (data.getTimeSlot().size() > 0 ? data.getTimeSlot().get(0) : "")));
            }
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

        CustomProgressDialog.getInstance().show(CheckOut.this);
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
                        CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<CheckoutAPI.ResponseCheckout> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, Constants.NoInternetConnection);
            }
        });
    }


    private void ShowModifierDialog() {

        final Dialog dialog = new Dialog(CheckOut.this);
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

        DeliveryTimeAdapter mModifierAdapter = new DeliveryTimeAdapter(CheckOut.this, mDeliveryTimeList);
        lvDelieryTime.setAdapter(mModifierAdapter);

        lvDelieryTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ConstantsInternal.OrderType.fromInteger(SessionManager.Cart.getInstance().getOrderType()) == ConstantsInternal.OrderType.Delivery) {
                    tvDeliveryNote.setText(MessageFormat.format(Constants.YourOrderWillBeDeliveredTodayAt, mDeliveryTimeList.get(position).getDeliveryTime()));
                } else {
                    tvDeliveryNote.setText(MessageFormat.format(Constants.YourOrderCabBePickedUpTodayAt, mDeliveryTimeList.get(position).getDeliveryTime()));
                }
                tvLaterTime.setText(Constants.Time + ":" + mDeliveryTimeList.get(position).getDeliveryTime());
                tvDeliveryNote.setTag(mDeliveryTimeList.get(position).getId());
                tvLaterTime.setTag(mDeliveryTimeList.get(position).getId());
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
            CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, Constants.PleaseAddItemsToCartAndCheckout);
            return;
        }

        final CheckoutDetails checkoutDetails = new CheckoutDetails();
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
        checkoutDetails.setPayment_type("vpc");
        checkoutDetails.setPayment_option(rbCOD.isChecked() ? ConstantsInternal.PaymentOptionSelected.COD.getValue() : ConstantsInternal.PaymentOptionSelected.Online.getValue());
        checkoutDetails.setDelivery_option(rbASAP.isChecked() ? ConstantsInternal.DeliveryOption.ASAP.getValue() : ConstantsInternal.DeliveryOption.Later.getValue());
        checkoutDetails.setDelivery_time(tvDeliveryNote.getTag().toString());

        final String output = gson.toJsonTree(checkoutDetails).toString();
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), output);

        CustomProgressDialog.getInstance().show(CheckOut.this);
        CheckoutConfirmAPI mCheckoutConfirmAPI = ApiConfiguration.getInstance2().getApiBuilder().create(CheckoutConfirmAPI.class);
        final Call<CheckoutConfirmAPI.ResponseCheckoutConfirm> call = mCheckoutConfirmAPI.Confirm(mRequestBody);
        call.enqueue(new Callback<CheckoutConfirmAPI.ResponseCheckoutConfirm>() {
            @Override
            public void onResponse(Call<CheckoutConfirmAPI.ResponseCheckoutConfirm> call, Response<CheckoutConfirmAPI.ResponseCheckoutConfirm> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        if (checkoutDetails.getPayment_option().equals(ConstantsInternal.PaymentOptionSelected.COD.getValue())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("order_number", response.body().getData().getOrderNumber());
                            bundle.putString("loyalty_points", response.body().getData().getLoyaltyPoints());
                            MyActivity.getInstance().OrderConfirmation(CheckOut.this, bundle);
                            finish();
                        } else {
                            paymentApicall(response.body().getData());
                        }
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<CheckoutConfirmAPI.ResponseCheckoutConfirm> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, Constants.NoInternetConnection);
            }
        });

    }

    private void paymentApicall(final CheckoutConfirmAPI.Data data) {
        CustomProgressDialog.getInstance().show(CheckOut.this);
        PaymentAPI paymentAPI = ApiConfiguration.getInstance2().getApiBuilder().create(PaymentAPI.class);
        Call<PaymentAPI.PaymentResponse> paymentResponseCall = paymentAPI.getPaymentDeatils(data.getOrder_key(), "vpc");
        paymentResponseCall.enqueue(new Callback<PaymentAPI.PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentAPI.PaymentResponse> call, Response<PaymentAPI.PaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        if (response.body().getData() != null) {
                            String url = response.body().getData().getUrl();
                            Bundle bundle = new Bundle();
                            bundle.putString("url", url);
                            bundle.putString("order_number", data.getOrderNumber());
                            bundle.putString("loyalty_points", data.getLoyaltyPoints());
                            bundle.putString("order_key", data.getOrder_key());
                            MyActivity.getInstance().WebviewOnlinePayment(CheckOut.this, bundle);


//                            Bundle bundle = new Bundle();
//
//                            MyActivity.getInstance().OnlinePayment(CheckOut.this, bundle);
                        }

                        /*bundle.putString(PaystackParams.ACCESS_CODE, "AVDO77FC02BU06ODUB");
                        bundle.putString(PaystackParams.ORDER_ID, "r2ro5hvwspzvqvls_1525256802");
                        bundle.putString(PaystackParams.AMOUNT, "135.45");
                        bundle.putString(PaystackParams.MERCHANT_ID, "170791");
                        bundle.putString(PaystackParams.CURRENCY, "INR");
                        bundle.putString(PaystackParams.CANCEL_URL, "http://www.foodninjas.in/api/v1/payment/cancel");
                        bundle.putString(PaystackParams.REDIRECT_URL, "http://www.foodninjas.in/api/v1/payment/success?pg=ccavenue&pgRef=1fzeqsiplet7dvei");
                        bundle.putString(PaystackParams.RSA_KEY_URL, "");
                        bundle.putString(PaystackParams.ENC_VAL, "");
                        MyActivity.getInstance().OnlinePayment(CheckOut.this, bundle);*/

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }
            }

            @Override
            public void onFailure(Call<PaymentAPI.PaymentResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, t.toString());
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
             /*   if (rbOnline.isChecked()){
                    Double total = 0.0;
                    for (int i=0; i<data.getPaymentDetails().size(); i++){
                        if (data.getPaymentDetails().get(i).getName().toLowerCase().equals("total")){
                            total = data.getPaymentDetails().get(i).getValue();
                        }
                    }
                    Intent intent = new Intent(CheckOut.this, OnlinePayment.class);
                    intent.putExtra("total",total+"");
                    startActivityForResult(intent, PURCHASE_REQUEST);
                }
                else {
                    if (data.getTimeSlot() != null && data.getTimeSlot().size() <= 0) {
                        CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, Constants.YouCantOrderToday);
                        return;
                    } else {
                        CheckoutConfirm();
                    }
                }*/
                if (data.getTimeSlot() != null && data.getTimeSlot().size() <= 0) {
                    CommonFunctions.getInstance().ShowSnackBar(CheckOut.this, Constants.YouCantOrderToday);
                    return;
                } else {
                    CheckoutConfirm();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PURCHASE_REQUEST && resultCode == RESULT_OK) {
            String transactionID = data.getExtras().getString("transactionID");


        }
    }


}
