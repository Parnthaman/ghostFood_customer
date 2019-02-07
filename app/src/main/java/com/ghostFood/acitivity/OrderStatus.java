package com.ghostFood.acitivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ghostFood.R;
import com.ghostFood.adapter.OrdersDetailsAdapter;
import com.ghostFood.api.OrderDetailsAPI;
import com.ghostFood.model.Item;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatus extends AppCompatActivity {


    OrdersDetailsAdapter mOrdersDetailsAdapter;

    List<Item> mItemList = new ArrayList<>();

    String mFromLatitude = "", mFromLongitude = "";
    String mToLatitude = "", mToLongitude = "";
    String mOrderNumber = "", mOrderStatus = "";
    String mOrderDate = "";
    String mBranchno = "";


    OrderDetailsAPI.ResponseOrderDetails orderDetails;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.tvCartCounts)
    TextView tvCartCounts;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvOrderDetail)
    TextView tvOrderDetail;
    @BindView(R.id.llOrderDetail)
    LinearLayout llOrderDetail;
    @BindView(R.id.tvOrderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.tvOrderPlacedTime)
    TextView tvOrderPlacedTime;
    @BindView(R.id.ivOrderPlaced)
    ImageView ivOrderPlaced;
    @BindView(R.id.tvOrderPlacedTxt)
    TextView tvOrderPlacedTxt;
    @BindView(R.id.tvOrderPlacedDescription)
    TextView tvOrderPlacedDescription;
    @BindView(R.id.viewOrderPlaced)
    View viewOrderPlaced;
    @BindView(R.id.tvOrderConfirmTime)
    TextView tvOrderConfirmTime;
    @BindView(R.id.ivOrderConfirm)
    ImageView ivOrderConfirm;
    @BindView(R.id.tvOrderConfirm)
    TextView tvOrderConfirm;
    @BindView(R.id.tvOrderConfirmDescription)
    TextView tvOrderConfirmDescription;
    @BindView(R.id.viewOrderConfirm)
    View viewOrderConfirm;
    @BindView(R.id.tvOrderProcessingTime)
    TextView tvOrderProcessingTime;
    @BindView(R.id.ivOrderProcessed)
    ImageView ivOrderProcessed;
    @BindView(R.id.tvOrderProcessed)
    TextView tvOrderProcessed;
    @BindView(R.id.tvOrderProcessedDescription)
    TextView tvOrderProcessedDescription;
    @BindView(R.id.viewOrderProcessed)
    View viewOrderProcessed;
    @BindView(R.id.tvOrderCompletedTime)
    TextView tvOrderCompletedTime;
    @BindView(R.id.ivOrderCompleted)
    ImageView ivOrderCompleted;
    @BindView(R.id.tvOrderCompleted)
    TextView tvOrderCompleted;
    @BindView(R.id.tvOrderCompletedDescription)
    TextView tvOrderCompletedDescription;
    @BindView(R.id.viewOrderCompleted)
    View viewOrderCompleted;
    @BindView(R.id.tvOrderDeliveredTime)
    TextView tvOrderDeliveredTime;
    @BindView(R.id.ivOrderDelivered)
    ImageView ivOrderDelivered;
    @BindView(R.id.tvOrderDelivered)
    TextView tvOrderDelivered;
    @BindView(R.id.tvOrderDeliveredDescription)
    TextView tvOrderDeliveredDescription;
    @BindView(R.id.tvTrackOrder)
    TextView tvTrackOrder;
    @BindView(R.id.llTrackOrder)
    LinearLayout llTrackOrder;
    @BindView(R.id.tvBranchName)
    TextView tvBranchName;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.llOrderStatus)
    LinearLayout llOrderStatus;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    private String orderKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        ButterKnife.bind(this);

        CommonFunctions.getInstance().ChangeDirection(OrderStatus.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(OrderStatus.this, toolbar);

        orderKey = getIntent().getExtras() != null && getIntent().getExtras().getString("order_key") != null ? getIntent().getExtras().getString("order_key") : "";
        initializeViews();

        callOrderDetailsApi();

    }

    private void initializeViews() {
        tvOrderStatus.setText(Constants.orderstatus);
        tvOrderPlacedTxt.setText(Constants.orderPlaced);
        tvOrderPlacedDescription.setText(Constants.weReceivedOrder);
        tvOrderConfirm.setText(Constants.orderConfirm);
        tvOrderConfirmDescription.setText(Constants.yourOrderConfirmed);
        tvOrderProcessed.setText(Constants.orderProcessed);
        tvOrderProcessedDescription.setText(Constants.wePreparingYourOrder);
        tvOrderCompleted.setText(Constants.orderCompleted);
        tvOrderCompletedDescription.setText(Constants.yourOrderOnTheWay);
        tvOrderDelivered.setText(Constants.orderDelivered);
        tvOrderDeliveredDescription.setText(Constants.enjoyYourFood);
        tvTrackOrder.setText(Constants.Track + " " + Constants.Order);
        tvOrderDetail.setText(Constants.moreInfo);


        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

        llOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String orderDetail = gson.toJson(orderDetails);
                bundle.putString("orderDetail", orderDetail);
                MyActivity.getInstance().OrderDetails(OrderStatus.this, bundle);
            }
        });

        tlbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("order_key", orderKey);
                bundle.putString("from_lat", mFromLatitude);
                bundle.putString("from_lon", mFromLongitude);
                bundle.putString("to_lat", mToLatitude);
                bundle.putString("to_lon", mToLongitude);
                bundle.putString("order_number", mOrderNumber);
                bundle.putString("order_status", mOrderStatus);
                bundle.putString("order_date", mOrderDate);
                MyActivity.getInstance().TrackOrder(OrderStatus.this, bundle);
            }
        });

    }

    private void callOrderDetailsApi() {
        mItemList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(OrderStatus.this);
        OrderDetailsAPI mOrderDetailsAPI = ApiConfiguration.getInstance2().getApiBuilder().create(OrderDetailsAPI.class);
        final Call<OrderDetailsAPI.ResponseOrderDetails> call = mOrderDetailsAPI.Get(orderKey);
        call.enqueue(new Callback<OrderDetailsAPI.ResponseOrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetailsAPI.ResponseOrderDetails> call, Response<OrderDetailsAPI.ResponseOrderDetails> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        orderDetails = response.body();
                        llOrderStatus.setVisibility(View.VISIBLE);

                        if (response.body().getData() != null && response.body().getData().getOrderDetails() != null) {

                            List<OrderDetailsAPI.Item> items = response.body().getData().getOrderDetails().getItems();
                            if (items != null) {
                                for (OrderDetailsAPI.Item mItem : items) {
                                    Item item = new Item();
                                    item.setItemName(mItem.getItemName());
                                    item.setItemPrice(mItem.getItemPrice());
                                    item.setTotalPrice(mItem.getTotalPrice());
                                    item.setQuantity(mItem.getQuantity());
                                    item.setItemKey(mItem.getItemKey());
                                    item.setItemImage(mItem.getItemImage());
                                    item.setIngredientsName(mItem.getIngredientsName());
                                    mItemList.add(item);
                                }
                            }

                            List<OrderDetailsAPI.Offer> offers = response.body().getData().getOrderDetails().getOffers();
                            if (items != null) {
                                for (OrderDetailsAPI.Offer mItem : offers) {
                                    Item item = new Item();
                                    item.setItemName(mItem.getOfferTitle());
                                    item.setItemPrice(mItem.getOfferPrice());
                                    item.setTotalPrice(mItem.getQuantity() * mItem.getOfferPrice());
                                    item.setQuantity(mItem.getQuantity());
                                    item.setItemKey("");
                                    item.setItemImage(mItem.getOfferImage());
                                    item.setIngredientsName(mItem.getOfferDescription());
                                    mItemList.add(item);
                                }
                            }

                            List<OrderDetailsAPI.Coupon> coupons = response.body().getData().getOrderDetails().getCoupons();
                            if (items != null) {
                                for (OrderDetailsAPI.Coupon mItem : coupons) {
                                    if (ConstantsInternal.CouponType.fromInteger(mItem.getCouponType()) == ConstantsInternal.CouponType.OnlineItem) {
                                        Item item = new Item();
                                        item.setItemName(mItem.getCouponName());
                                        item.setItemPrice(mItem.getCouponValue());
                                        item.setTotalPrice(mItem.getCouponValue());
                                        item.setQuantity(1);
                                        item.setItemKey("");
                                        item.setItemImage(mItem.getCouponImage());
                                        item.setIngredientsName(mItem.getCouponDescription());
                                        mItemList.add(item);
                                    }
                                }
                            }
                            if (response.body().getData().getOrderDetails().getBranch() != null && response.body().getData().getOrderDetails().getBranch().size() > 0) {
                                tvBranchName.setText(response.body().getData().getOrderDetails().getBranch().get(0).getBranch_name());
                            } else {
                                tvBranchName.setText("");
                            }
                            mFromLatitude = String.valueOf(response.body().getData().getOrderDetails().getLatitude());
                            mFromLongitude = String.valueOf(response.body().getData().getOrderDetails().getLongitude());
                            mToLatitude = String.valueOf(response.body().getData().getOrderDetails().getBranch().get(0).getLatitude());
                            mToLongitude = String.valueOf(response.body().getData().getOrderDetails().getBranch().get(0).getLongitude());
                            mBranchno = String.valueOf(response.body().getData().getOrderDetails().getBranch().get(0).getContact_number1());


                            mOrderNumber = response.body().getData().getOrderDetails().getOrderNumber();
                            mOrderDate = response.body().getData().getOrderDetails().getOrderDateTime();
                            mOrderStatus = ConstantsInternal.OrderStatus.fromInteger(Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus())).toString();

                            if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.OnTheWay.getValue()) {
                                llTrackOrder.setVisibility(View.INVISIBLE);
                            } else {
                                llTrackOrder.setVisibility(View.INVISIBLE);
                            }

                            if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.Pending.getValue()) {
                                ivOrderPlaced.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderConfirm.setBackground(getResources().getDrawable(R.drawable.ic_checked_blue));
                                ivOrderProcessed.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_grey));
                                ivOrderCompleted.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_grey));
                                ivOrderDelivered.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_grey));

                                viewOrderPlaced.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderConfirm.setBackgroundColor(getResources().getColor(R.color.clr_blue));
                                viewOrderProcessed.setBackgroundColor(getResources().getColor(R.color.gray_dialog));
                                viewOrderCompleted.setBackgroundColor(getResources().getColor(R.color.gray_dialog));

                                tvOrderConfirm.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderConfirmDescription.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderPlacedTxt.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderPlacedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));

                            } else if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.Accepted.getValue()) {
                                ivOrderPlaced.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderConfirm.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderProcessed.setBackground(getResources().getDrawable(R.drawable.ic_checked_blue));
                                ivOrderCompleted.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_grey));
                                ivOrderDelivered.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_grey));

                                viewOrderPlaced.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderConfirm.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderProcessed.setBackgroundColor(getResources().getColor(R.color.clr_blue));
                                viewOrderCompleted.setBackgroundColor(getResources().getColor(R.color.gray_dialog));

                                tvOrderProcessed.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderProcessedDescription.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderPlacedTxt.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderPlacedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirm.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirmDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                            } else if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.Prepared.getValue()) {
                                ivOrderPlaced.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderConfirm.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderProcessed.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderCompleted.setBackground(getResources().getDrawable(R.drawable.ic_checked_blue));
                                ivOrderDelivered.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_grey));

                                viewOrderPlaced.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderConfirm.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderProcessed.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderCompleted.setBackgroundColor(getResources().getColor(R.color.clr_blue));

                                tvOrderCompleted.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderCompletedDescription.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderPlacedTxt.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderPlacedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirm.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirmDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderProcessed.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderProcessedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                            } else if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.OnTheWay.getValue()) {
                                ivOrderPlaced.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderConfirm.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderProcessed.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderCompleted.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderDelivered.setBackground(getResources().getDrawable(R.drawable.ic_checked_blue));

                                viewOrderPlaced.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderConfirm.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderProcessed.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderCompleted.setBackgroundColor(getResources().getColor(R.color.clr_blue));

                                tvOrderDelivered.setTextColor(getResources().getColor(R.color.clr_blue));
                                tvOrderDeliveredDescription.setTextColor(getResources().getColor(R.color.clr_blue));

                                tvOrderPlacedTxt.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderPlacedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirm.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirmDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderProcessed.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderProcessedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderCompleted.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderCompletedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));

                            } else if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.Delivered.getValue()) {
                                ivOrderPlaced.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderConfirm.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderProcessed.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderCompleted.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));
                                ivOrderDelivered.setBackground(getResources().getDrawable(R.drawable.ic_checked_green));

                                viewOrderPlaced.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderConfirm.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderProcessed.setBackgroundColor(getResources().getColor(R.color.green));
                                viewOrderCompleted.setBackgroundColor(getResources().getColor(R.color.green));

                                tvOrderPlacedTxt.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderPlacedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirm.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderConfirmDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderProcessed.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderProcessedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderCompleted.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderCompletedDescription.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderDelivered.setTextColor(getResources().getColor(R.color.textcolor_black));
                                tvOrderDeliveredDescription.setTextColor(getResources().getColor(R.color.textcolor_black));

                            }


//                            tvOrderIdValue.setText(response.body().getData().getOrderDetails().getOrderNumber());
                            tlbarText.setText(response.body().getData().getOrderDetails().getOrderNumber());

                            String strDate = "";
                            try {
                                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date date = sdfSource.parse(response.body().getData().getOrderDetails().getOrderDateTime());
                                SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy, EEEE");
                                strDate = sdfDestination.format(date);
                                System.out.println("Converted date is : " + strDate);
                            } catch (ParseException pe) {
                                System.out.println("Parse Exception : " + pe);
                            }
                            tvDate.setText("Order Date: " + strDate);

//                            tvDateValue.setText(strDate);
//                            tvAmountValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(response.body().getData().getOrderDetails().getOrderTotal().toString()));

//                            if (response.body().getData().getOrderDetails().getBranch() != null && response.body().getData().getOrderDetails().getBranch().size() > 0) {
//                                tvBranchNameValue.setText(response.body().getData().getOrderDetails().getBranch().get(0).getBranch_name());
//                                tvBranchContactNoValue.setText(response.body().getData().getOrderDetails().getBranch().get(0).getContact_number1());
//                            } else {
//                                tvBranchNameValue.setText("");
//                                tvBranchContactNoValue.setText("");
//                            }


                            //tvSubTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getSubTotalPrice().toString()));
                            //tvDeliveryFeeValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getDeliveryPrice().toString()));
                            //tvTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getTotalPrice().toString()));

//                            mOrdersDetailsAdapter = new OrdersDetailsAdapter(OrderStatus.this, mItemList);
//                            orderItems.setAdapter(mOrdersDetailsAdapter);

//                            createCostingView(response.body().getData().getPaymentDetails());
                        }

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(OrderStatus.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(OrderStatus.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<OrderDetailsAPI.ResponseOrderDetails> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(OrderStatus.this, t.toString());
            }
        });

    }

    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(OrderStatus.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    OrderStatus.this,
                    new String[]{Manifest.permission.CALL_PHONE}, 123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + mBranchno)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
}
