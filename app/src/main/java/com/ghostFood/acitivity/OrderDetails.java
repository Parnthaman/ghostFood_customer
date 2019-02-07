package com.ghostFood.acitivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ghostFood.R;
import com.ghostFood.adapter.OrdersDetailsAdapter;
import com.ghostFood.api.ItemAPI;
import com.ghostFood.api.OrderDetailsAPI;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.db.Items;
import com.ghostFood.db.SpecialItems;
import com.ghostFood.model.Item;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity {


    OrdersDetailsAdapter mOrdersDetailsAdapter;

    List<Item> mItemList = new ArrayList<>();
    String orderKey = "";
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orderItems)
    ListView orderItems;

    String mFromLatitude = "", mFromLongitude = "";
    String mToLatitude = "", mToLongitude = "";
    String mOrderNumber = "", mOrderStatus = "";
    String mOrderDate = "";
    String mBranchno = "";

    TextView tvBranchNameValue, tvBranchContactNoValue, tvBranchName, tvBranchContactNo;

    TextView tvItems, tvOrderIdTxt, tvDateTxt, tvAmountTxt, tvOrderIdValue, tvDateValue, tvAmountValue, tvCall, tvTrackOrder;
    LinearLayout llCostingDynamic, llCall, llTrackOrder;
    @BindView(R.id.tvReorder)
    TextView tvReorder;
    @BindView(R.id.llReorder)
    LinearLayout llReorder;
    private String orderDetail;
    private OrderDetailsAPI.ResponseOrderDetails orderDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(OrderDetails.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(OrderDetails.this, toolbar);

        initView();

       /* mOrdersDetailsAdapter = new OrdersDetailsAdapter(OrderDetails.this, mItemList);
        orderItems.setAdapter(mOrdersDetailsAdapter);
        setListViewHeightBasedOnChildren(orderItems);*/
        //orderItems.setFocusable(false);
        if (getIntent().getExtras().getString("orderDetail") != null) {
            orderDetail = getIntent().getExtras().getString("orderDetail");
            Gson gson = new Gson();
            orderDetails = gson.fromJson(orderDetail, OrderDetailsAPI.ResponseOrderDetails.class);
            mItemList = new ArrayList<>();
            List<OrderDetailsAPI.Item> items = orderDetails.getData().getOrderDetails().getItems();
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

            List<OrderDetailsAPI.Offer> offers = orderDetails.getData().getOrderDetails().getOffers();
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

            List<OrderDetailsAPI.Coupon> coupons = orderDetails.getData().getOrderDetails().getCoupons();
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

            mFromLatitude = String.valueOf(orderDetails.getData().getOrderDetails().getLatitude());
            mFromLongitude = String.valueOf(orderDetails.getData().getOrderDetails().getLongitude());
            mToLatitude = String.valueOf(orderDetails.getData().getOrderDetails().getBranch().get(0).getLatitude());
            mToLongitude = String.valueOf(orderDetails.getData().getOrderDetails().getBranch().get(0).getLongitude());
            mBranchno = String.valueOf(orderDetails.getData().getOrderDetails().getBranch().get(0).getContact_number1());


            mOrderNumber = orderDetails.getData().getOrderDetails().getOrderNumber();
            mOrderDate = orderDetails.getData().getOrderDetails().getOrderDateTime();
            mOrderStatus = ConstantsInternal.OrderStatus.fromInteger(Integer.valueOf(orderDetails.getData().getOrderDetails().getOrderStatus())).toString();

            if (Integer.valueOf(orderDetails.getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.OnTheWay.getValue()) {
                llTrackOrder.setVisibility(View.GONE);
            } else {
                llTrackOrder.setVisibility(View.GONE);
            }

            tvOrderIdValue.setText(orderDetails.getData().getOrderDetails().getOrderNumber());
            tlbarText.setText(orderDetails.getData().getOrderDetails().getOrderNumber());
            orderKey = (orderDetails.getData().getOrderDetails().getOrderNumber());

            String strDate = "";
            try {
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdfSource.parse(orderDetails.getData().getOrderDetails().getOrderDateTime());
                SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy, EEEE");
                strDate = sdfDestination.format(date);
                System.out.println("Converted date is : " + strDate);
            } catch (ParseException pe) {
                System.out.println("Parse Exception : " + pe);
            }

            tvDateValue.setText(strDate);
            tvAmountValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(orderDetails.getData().getOrderDetails().getOrderTotal().toString()));

            if (orderDetails.getData().getOrderDetails().getBranch() != null && orderDetails.getData().getOrderDetails().getBranch().size() > 0) {
                tvBranchNameValue.setText(orderDetails.getData().getOrderDetails().getBranch().get(0).getBranch_name());
                tvBranchContactNoValue.setText(orderDetails.getData().getOrderDetails().getBranch().get(0).getContact_number1());
            } else {
                tvBranchNameValue.setText("");
                tvBranchContactNoValue.setText("");
            }


            //tvSubTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getSubTotalPrice().toString()));
            //tvDeliveryFeeValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getDeliveryPrice().toString()));
            //tvTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getTotalPrice().toString()));

            mOrdersDetailsAdapter = new OrdersDetailsAdapter(OrderDetails.this, mItemList);
            orderItems.setAdapter(mOrdersDetailsAdapter);

            createCostingView(orderDetails.getData().getPaymentDetails());


        }
//        orderKey = getIntent().getExtras() != null && getIntent().getExtras().getString("order_key") != null ? getIntent().getExtras().getString("order_key") : "";
//        callOrderDetailsApi();

    }

    private void initView() {


        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup orderHeader = (ViewGroup) myinflater.inflate(R.layout.header_orderdetails, orderItems, false);
        orderItems.addHeaderView(orderHeader, null, false);

        ViewGroup orderFooter = (ViewGroup) myinflater.inflate(R.layout.footer_orderdetails, orderItems, false);
        orderItems.addFooterView(orderFooter, null, false);

        llCostingDynamic = (LinearLayout) orderFooter.findViewById(R.id.llCostingDynamic);

        tvBranchName = (TextView) orderHeader.findViewById(R.id.tvBranchName);
        tvBranchContactNo = (TextView) orderHeader.findViewById(R.id.tvBranchContactNo);

        tvBranchNameValue = (TextView) orderHeader.findViewById(R.id.tvBranchNameValue);
        tvBranchContactNoValue = (TextView) orderHeader.findViewById(R.id.tvBranchContactNoValue);
        tvItems = (TextView) orderHeader.findViewById(R.id.tvItems);
        tvOrderIdTxt = (TextView) orderHeader.findViewById(R.id.tvOrderIdTxt);
        tvDateTxt = (TextView) orderHeader.findViewById(R.id.tvDateTxt);
        tvAmountTxt = (TextView) orderHeader.findViewById(R.id.tvAmountTxt);
        tvCall = (TextView) orderHeader.findViewById(R.id.tvCall);
//        btnCall = (Button) orderHeader.findViewById(R.id.btncall);
        tvOrderIdValue = (TextView) orderHeader.findViewById(R.id.tvOrderIdValue);
        llCall = (LinearLayout) orderHeader.findViewById(R.id.llCall);
//        llReorder = (LinearLayout) orderHeader.findViewById(R.id.llReorder);
        llTrackOrder = (LinearLayout) orderHeader.findViewById(R.id.llTrackOrder);
        tvDateValue = (TextView) orderHeader.findViewById(R.id.tvDateValue);
        tvTrackOrder = (TextView) orderHeader.findViewById(R.id.tvTrackOrder);
//        tvReorder = (TextView) orderHeader.findViewById(R.id.tvReorder);
        tvAmountValue = (TextView) orderHeader.findViewById(R.id.tvAmountValue);

        tvBranchName.setText(Constants.BranchName + ":");
        tvBranchContactNo.setText(Constants.BranchContactNo + ":");
        tvItems.setText(Constants.Items);
        tvOrderIdTxt.setText(Constants.OrderId + ":");
        tvDateTxt.setText(Constants.Date + ":");
        tvAmountTxt.setText(Constants.Amount + ":");


//        btnReOrder.setText(Constants.ReOrder);
        tvReorder.setText(Constants.ReOrder);
        tvCall.setText(Constants.Call);
//        btnTrackOrder.setText(Constants.Track);
        tvTrackOrder.setText(Constants.Track);


        llReorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOrderDetailsApiForReorder();
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
                MyActivity.getInstance().TrackOrder(OrderDetails.this, bundle);
            }
        });

        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvItems);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvOrderIdTxt);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvBranchName);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvBranchContactNo);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvBranchNameValue);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvBranchContactNoValue);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvDateTxt);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvAmountTxt);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvTrackOrder);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvCall);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvReorder);

        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvOrderIdValue);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvDateValue);
        FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvAmountValue);

        FontFunctions.getInstance().FontBalooBhaijaan(OrderDetails.this, tlbarText);
    }

    private void callOrderDetailsApi() {
        mItemList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(OrderDetails.this);
        OrderDetailsAPI mOrderDetailsAPI = ApiConfiguration.getInstance2().getApiBuilder().create(OrderDetailsAPI.class);
        final Call<OrderDetailsAPI.ResponseOrderDetails> call = mOrderDetailsAPI.Get(orderKey);
        call.enqueue(new Callback<OrderDetailsAPI.ResponseOrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetailsAPI.ResponseOrderDetails> call, Response<OrderDetailsAPI.ResponseOrderDetails> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

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

                            mFromLatitude = String.valueOf(response.body().getData().getOrderDetails().getLatitude());
                            mFromLongitude = String.valueOf(response.body().getData().getOrderDetails().getLongitude());
                            mToLatitude = String.valueOf(response.body().getData().getOrderDetails().getBranch().get(0).getLatitude());
                            mToLongitude = String.valueOf(response.body().getData().getOrderDetails().getBranch().get(0).getLongitude());
                            mBranchno = String.valueOf(response.body().getData().getOrderDetails().getBranch().get(0).getContact_number1());


                            mOrderNumber = response.body().getData().getOrderDetails().getOrderNumber();
                            mOrderDate = response.body().getData().getOrderDetails().getOrderDateTime();
                            mOrderStatus = ConstantsInternal.OrderStatus.fromInteger(Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus())).toString();

                            if (Integer.valueOf(response.body().getData().getOrderDetails().getOrderStatus()) == ConstantsInternal.OrderStatus.OnTheWay.getValue()) {
                                llTrackOrder.setVisibility(View.VISIBLE);
                            } else {
                                llTrackOrder.setVisibility(View.GONE);
                            }

                            tvOrderIdValue.setText(response.body().getData().getOrderDetails().getOrderNumber());
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

                            tvDateValue.setText(strDate);
                            tvAmountValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(response.body().getData().getOrderDetails().getOrderTotal().toString()));

                            if (response.body().getData().getOrderDetails().getBranch() != null && response.body().getData().getOrderDetails().getBranch().size() > 0) {
                                tvBranchNameValue.setText(response.body().getData().getOrderDetails().getBranch().get(0).getBranch_name());
                                tvBranchContactNoValue.setText(response.body().getData().getOrderDetails().getBranch().get(0).getContact_number1());
                            } else {
                                tvBranchNameValue.setText("");
                                tvBranchContactNoValue.setText("");
                            }


                            //tvSubTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getSubTotalPrice().toString()));
                            //tvDeliveryFeeValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getDeliveryPrice().toString()));
                            //tvTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getTotalPrice().toString()));

                            mOrdersDetailsAdapter = new OrdersDetailsAdapter(OrderDetails.this, mItemList);
                            orderItems.setAdapter(mOrdersDetailsAdapter);

                            createCostingView(response.body().getData().getPaymentDetails());
                        }

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<OrderDetailsAPI.ResponseOrderDetails> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, t.toString());
            }
        });

    }


    private void callOrderDetailsApiForReorder() {
        mItemList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(OrderDetails.this);
        OrderDetailsAPI mOrderDetailsAPI = ApiConfiguration.getInstance2().getApiBuilder().create(OrderDetailsAPI.class);
        final Call<OrderDetailsAPI.ResponseOrderDetails> call = mOrderDetailsAPI.Get(orderKey);
        call.enqueue(new Callback<OrderDetailsAPI.ResponseOrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetailsAPI.ResponseOrderDetails> call, Response<OrderDetailsAPI.ResponseOrderDetails> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        if (response.body().getData() != null && response.body().getData().getOrderDetails() != null) {

                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.delete(Items.class);
                                    realm.delete(SpecialItems.class);
                                }
                            });

                            List<OrderDetailsAPI.Item> items = response.body().getData().getOrderDetails().getItems();
                            if (items != null) {
                                Gson gson = new GsonBuilder().create();

                                for (OrderDetailsAPI.Item mItem : items) {

                                    ArrayList<ItemAPI.Ingredient> modifiers = new ArrayList<>();
                                    ArrayList<ItemAPI.Ingredient> ingredients = new ArrayList<>();
                                    if (mItem.getIngredientsGroup() != null) {
                                        for (OrderDetailsAPI.IngredientsGroup ingredientsGroup : mItem.getIngredientsGroup()) {

                                            for (OrderDetailsAPI.Ingredient ingredient : ingredientsGroup.getIngredients()) {
                                                String groupKey = ingredientsGroup.getItemIngredientGroupKey();
                                                for (OrderDetailsAPI.Ingredient_ ingredient2 : ingredient.getIngredients()) {

                                                    ItemAPI.Details details = new ItemAPI.Details();
                                                    details.setIngredientKey(ingredient.getIngredientKey());
                                                    details.setIngredientDescription(ingredient2.getIngredientDescription());
                                                    details.setIngredientName(ingredient2.getIngredientName());

                                                    ItemAPI.Ingredient ing = new ItemAPI.Ingredient();
                                                    ing.setDetails(details);
                                                    ing.setSelected(true);
                                                    ing.setPrice(ingredient.getIngredientPrice());
                                                    ing.setGroupKey(groupKey);

                                                    if (ConstantsInternal.IngredientType.fromInteger(ingredientsGroup.getIngredientType()) == ConstantsInternal.IngredientType.Modifier) {
                                                        modifiers.add(ing);
                                                    } else {
                                                        ing.setSelectedDrink(ingredient.getIsSelectedDrink() == 1 ? true : false);
                                                        ingredients.add(ing);
                                                    }

                                                    //To-do - Hardcoded - it should be handled from api side
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    String modifiersJSON = gson.toJsonTree(modifiers).toString();
                                    String ingredientsJSON = gson.toJsonTree(ingredients).toString();


                                    String itemImagePath = "";
                                    if (mItem.getItemImages().size() > 0) {
                                        itemImagePath = mItem.getItemImages().get(0).getItemImagePath();
                                    }

                                    for (int Count = 1; Count <= mItem.getQuantity(); Count++) {
                                        CartDB.getInstance().Add(
                                                mItem.getItemKey(),
                                                mItem.getItemName(),
                                                modifiersJSON,
                                                ingredientsJSON,
                                                "",
                                                mItem.getItemPrice(),
                                                itemImagePath, new CommonCallback.Listener() {
                                                    @Override
                                                    public void onSuccess() {

                                                    }

                                                    @Override
                                                    public void onFailure() {

                                                    }
                                                }
                                        );
                                    }
                                }
                            }

                            List<OrderDetailsAPI.Offer> offers = response.body().getData().getOrderDetails().getOffers();
                            if (items != null) {
                                for (OrderDetailsAPI.Offer mItem : offers) {
                                    CartDB.getInstance().AddSpecialOffer(
                                            mItem.getOfferKey(),
                                            mItem.getOfferType(),
                                            mItem.getOfferTitle(),
                                            mItem.getOfferPrice(),
                                            mItem.getOfferImage(),
                                            ConstantsInternal.CouponScope.None.getValue(), new CommonCallback.Listener() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onFailure() {

                                                }
                                            }
                                    );
                                }
                            }

                            List<OrderDetailsAPI.Coupon> coupons = response.body().getData().getOrderDetails().getCoupons();
                            if (items != null) {
                                for (OrderDetailsAPI.Coupon mItem : coupons) {
                                    if (ConstantsInternal.CouponType.fromInteger(mItem.getCouponType()) == ConstantsInternal.CouponType.OnlineItem) {

                                        CartDB.getInstance().AddCouponItems(
                                                mItem.getCouponKey(),
                                                ConstantsInternal.OfferType.CouponItem.getValue(),
                                                mItem.getCouponName(),
                                                mItem.getCouponValue(),
                                                mItem.getCouponImage(),
                                                mItem.getCouponScope(),
                                                new CommonCallback.Listener() {
                                                    @Override
                                                    public void onSuccess() {

                                                    }

                                                    @Override
                                                    public void onFailure() {

                                                    }
                                                }
                                        );
                                    }
                                }
                            }

                            final Bundle mBundle = new Bundle();
                            //CommonFunctions.getInstance().FinishActivityWithDelay(OrderDetails.this);
                            SessionManager.Cart.getInstance().setBranchKey("");
                            SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Delivery.getValue());
                            CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, Constants.AddedToCart);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MyActivity.getInstance().Cart(OrderDetails.this, mBundle);
                                }
                            }, 2000);

                        }

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<OrderDetailsAPI.ResponseOrderDetails> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(OrderDetails.this, t.toString());
            }
        });

    }

    private void createCostingView(List<OrderDetailsAPI.PaymentDetail> paymentDetails) {

        llCostingDynamic.removeAllViews();

        for (int count = 0; count < paymentDetails.size(); count++) {
            LayoutInflater li = LayoutInflater.from(OrderDetails.this);
            View child = li.inflate(R.layout.inflate_order_summary, null, false);

            final TextView tvAttribute = child.findViewById(R.id.tvAttribute);
            final TextView tvValue = child.findViewById(R.id.tvValue);
            final View view = child.findViewById(R.id.view);


            if (count == paymentDetails.size() - 1) {
                view.setVisibility(View.GONE);
            }

            FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvAttribute);
            FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvValue);

            tvAttribute.setText(paymentDetails.get(count).getName());
            tvAttribute.setTextColor(paymentDetails.get(count).getColor() ? Color.GRAY : Color.GRAY);
            tvValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(CommonFunctions.getInstance().getIntORFloat(paymentDetails.get(count).getValue())));
            llCostingDynamic.addView(child);


        }

//        final float scale = getResources().getDisplayMetrics().density;
//        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//
//
//        if (paymentDetails == null) {
//            return;
//        }
//
//        for (OrderDetailsAPI.PaymentDetail paymentDetail : paymentDetails) {
//            LinearLayout linearLayoutParent = new LinearLayout(OrderDetails.this);
//            linearLayoutParent.setLayoutParams(params);
//            linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);
//
//            LinearLayout linearLayoutChild = new LinearLayout(OrderDetails.this);
//            linearLayoutChild.setLayoutParams(params);
//            linearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);
//            linearLayoutChild.setPadding(0, 10, 0, 10);
//
//            int pixelsDummy = (int) (0 * scale);
//            LinearLayout.LayoutParams lpDummyView = new LinearLayout.LayoutParams(pixelsDummy, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lpDummyView.weight = 1;
//
//            TextView tvDummy = new TextView(OrderDetails.this);
//            tvDummy.setLayoutParams(lpDummyView);
//            tvDummy.setText("");
//            tvDummy.setVisibility(View.INVISIBLE);
//
//            TextView tvAttribute = (TextView) inflater.inflate(R.layout.dynamictextview1, null);
//            tvAttribute.setLayoutParams(lpDummyView);
//            tvAttribute.setText(paymentDetail.getName());
//            tvAttribute.setVisibility(View.VISIBLE);
//            tvAttribute.setPadding(0, 10, 0, 10);
//            tvAttribute.setTextColor(paymentDetail.getColor() ? Color.BLACK : Color.BLACK);
//            // tvAttribute.setTextSize(paymentDetail.getColor() ? getResources().getDimension(R.dimen._8sdp) : getResources().getDimension(R.dimen._7sdp));
//
//            TextView tvValue = (TextView) inflater.inflate(R.layout.dynamictextview8, null);
//            tvValue.setLayoutParams(lpDummyView);
//            //tvValue.setTextSize(getResources().getDimension(R.dimen._6sdp));
//            tvValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(paymentDetail.getValue().toString()));
//            tvValue.setTextColor(Color.BLACK);
//            tvValue.setVisibility(View.VISIBLE);
//            tvValue.setPadding(0, 10, 0, 10);
//
//
//            FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvDummy);
//            FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvAttribute);
//            FontFunctions.getInstance().FontBabeNeueBold(OrderDetails.this, tvValue);
//
//
//            linearLayoutChild.addView(tvAttribute);
//            linearLayoutChild.addView(tvDummy);
//            linearLayoutChild.addView(tvValue);
//
//            LinearLayout tvValue1 = (LinearLayout) inflater.inflate(R.layout.dynamictextview7, null);
//            llCostingDynamic.addView(linearLayoutChild);
//            llCostingDynamic.addView(tvValue1);
//        }
    }

    @OnClick({R.id.tlbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;

        }
    }


    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(OrderDetails.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    OrderDetails.this,
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
