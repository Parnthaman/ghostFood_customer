package com.ghostFood.acitivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.adapter.CartAdapter;
import com.ghostFood.api.CheckoutAPI;
import com.ghostFood.api.ItemAPI;
import com.ghostFood.db.Items;
import com.ghostFood.db.SpecialItems;
import com.ghostFood.events.EBLogin;
import com.ghostFood.events.EBRefreshCart;
import com.ghostFood.model.CartItem;
import com.ghostFood.model.CheckoutDetails;
import com.ghostFood.model.CheckoutItemDetails;
import com.ghostFood.model.SpecialOffer;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    EventBus myEventBus = EventBus.getDefault();

    ArrayList<CartItem> mCartItemList = new ArrayList<>();
    CartItem mCartItem;

    CartAdapter mCartAdapter;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lvCartItems)
    ListView lvCartItems;
    @BindView(R.id.tvConfirmOrder)
    TextView tvConfirmOrder;
    @BindView(R.id.llConfirmOrder)
    LinearLayout llConfirmOrder;

    TextView tvAddress, tvSelectedAddress, tvMobileTxt, tvMobileValue, tvMobileEdit, tvMobileSave, tvEmailTxt, tvEmailValue, tvEmailEdit, tvEmailSave, tvLogin;
    TextView btnChange, btnLogin;
    LinearLayout llChange, llLoginss;
    EditText edtMobileNumber, edtEmail;
    LinearLayout llAddressDetails, llLogin;

    public static Activity mCartActivity;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.tvSubTotalValue)
    TextView tvSubTotalValue;
    @BindView(R.id.tvEmptyTxt)
    TextView tvEmptyTxt;
    @BindView(R.id.tvCartText)
    TextView tvCartText;
    @BindView(R.id.tvShopNow)
    TextView tvShopNow;
    @BindView(R.id.rly_shopNow)
    LinearLayout rlyShopNow;
    @BindView(R.id.llEmptyCart)
    LinearLayout llEmptyCart;
    @BindView(R.id.llCartDetail)
    LinearLayout llCartDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(Cart.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(Cart.this, toolbar);
        mCartActivity = this;
        myEventBus.register(this);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup cartHeader = (ViewGroup) myinflater.inflate(R.layout.header_cart, lvCartItems, false);
        lvCartItems.addHeaderView(cartHeader, null, false);

        tvAddress = (TextView) cartHeader.findViewById(R.id.tvAddress);
        tvSelectedAddress = (TextView) cartHeader.findViewById(R.id.tvSelectedAddress);
        tvMobileTxt = (TextView) cartHeader.findViewById(R.id.tvMobileTxt);
        tvMobileValue = (TextView) cartHeader.findViewById(R.id.tvMobileValue);
        tvMobileEdit = (TextView) cartHeader.findViewById(R.id.tvMobileEdit);
        tvMobileSave = (TextView) cartHeader.findViewById(R.id.tvMobileSave);
        tvEmailTxt = (TextView) cartHeader.findViewById(R.id.tvEmailTxt);
        tvEmailValue = (TextView) cartHeader.findViewById(R.id.tvEmailValue);
        tvEmailEdit = (TextView) cartHeader.findViewById(R.id.tvEmailEdit);
        tvEmailSave = (TextView) cartHeader.findViewById(R.id.tvEmailSave);
        btnChange = (TextView) cartHeader.findViewById(R.id.btnChange);
        llChange = (LinearLayout) cartHeader.findViewById(R.id.llChange);
        llLoginss = (LinearLayout) cartHeader.findViewById(R.id.llLoginss);
        edtMobileNumber = (EditText) cartHeader.findViewById(R.id.edtMobileNumber);
        edtEmail = (EditText) cartHeader.findViewById(R.id.edtEmail);

        llAddressDetails = (LinearLayout) cartHeader.findViewById(R.id.llAddressDetails);
        llLogin = (LinearLayout) cartHeader.findViewById(R.id.llLogin);
        btnLogin = (TextView) cartHeader.findViewById(R.id.btnLogin);
        tvLogin = (TextView) cartHeader.findViewById(R.id.tvLogin);

        tvLogin.setText(Constants.PleaseLoginToCheckout);

        if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            llAddressDetails.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
        }

        if (CartDB.getInstance().GetItems().size() == 0) {
            llCartDetail.setVisibility(View.GONE);
            llEmptyCart.setVisibility(View.VISIBLE);
        } else {
            llEmptyCart.setVisibility(View.GONE);
            llCartDetail.setVisibility(View.VISIBLE);
        }

        tvAddress.setText(Constants.Address);
        btnChange.setText(Constants.Change);
        tvMobileTxt.setText(Constants.Mobile);
        tvEmailTxt.setText(Constants.Email);
        tvEmailEdit.setText(Constants.Edit);
        btnLogin.setText(Constants.Login);
        tvMobileEdit.setText(Constants.Edit);
        tvEmailSave.setText(Constants.Save);
        tvMobileSave.setText(Constants.Save);
        tvConfirmOrder.setText(Constants.Checkout);
        tlbarText.setText(Constants.Cart);
        tvSubTotal.setText(Constants.SubTotal.toUpperCase());
        tvShopNow.setText(Constants.ShopNow);

        tvEmailValue.setText(SessionManager.User.getInstance().getEmail());
        tvMobileValue.setText(SessionManager.User.getInstance().getMobile());

        llChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.getInstance().ChooseAddress(Cart.this);
            }
        });

        llLoginss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.getInstance().LoginAct(Cart.this);
            }
        });

        rlyShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtMobileNumber.setVisibility(View.GONE);
        tvMobileSave.setVisibility(View.GONE);
        tvMobileValue.setVisibility(View.VISIBLE);
        tvMobileEdit.setVisibility(View.VISIBLE);

        edtEmail.setVisibility(View.GONE);
        tvEmailSave.setVisibility(View.GONE);
        tvEmailValue.setVisibility(View.VISIBLE);
        tvEmailEdit.setVisibility(View.VISIBLE);


        tvMobileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMobileNumber.requestFocus();
                edtMobileNumber.setText(tvMobileValue.getText().toString().trim());
                edtMobileNumber.setVisibility(View.VISIBLE);
                tvMobileSave.setVisibility(View.VISIBLE);
                tvMobileValue.setVisibility(View.GONE);
                tvMobileEdit.setVisibility(View.GONE);

            }
        });

        tvMobileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtMobileNumber.getText().toString().isEmpty()) {
                    CommonFunctions.getInstance().EmptyField(Cart.this, Constants.MobileNumber);
                    return;
                }

                if (edtMobileNumber.getText().toString().length() < 7 && edtMobileNumber.getText().toString().length() > 16) {
                    CommonFunctions.getInstance().ValidField(Cart.this, Constants.MobileNumber);
                    return;
                }

                tvMobileValue.setText(edtMobileNumber.getText().toString().trim());
                edtMobileNumber.setVisibility(View.GONE);
                tvMobileSave.setVisibility(View.GONE);
                tvMobileValue.setVisibility(View.VISIBLE);
                tvMobileEdit.setVisibility(View.VISIBLE);
            }
        });

        tvEmailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                edtEmail.setText(tvEmailValue.getText().toString().trim());
                edtEmail.setVisibility(View.VISIBLE);
                tvEmailSave.setVisibility(View.VISIBLE);
                tvEmailValue.setVisibility(View.GONE);
                tvEmailEdit.setVisibility(View.GONE);

            }
        });

        tvEmailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtEmail.getText().toString().isEmpty()) {
                    CommonFunctions.getInstance().EmptyField(Cart.this, Constants.Email);
                    return;
                }
                if (!CommonFunctions.getInstance().isValidEmail(edtEmail.getText().toString())) {
                    CommonFunctions.getInstance().ValidField(Cart.this, Constants.Email);
                    return;
                }

                tvEmailValue.setText(edtEmail.getText().toString().trim());
                edtEmail.setVisibility(View.GONE);
                tvEmailSave.setVisibility(View.GONE);
                tvEmailValue.setVisibility(View.VISIBLE);
                tvEmailEdit.setVisibility(View.VISIBLE);

            }
        });


        LoadCart();
        mCartAdapter = new CartAdapter(Cart.this, mCartItemList);
        lvCartItems.setAdapter(mCartAdapter);
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SessionManager.Cart.getInstance().getOrderType() == ConstantsInternal.OrderType.Delivery.getValue()) {
            btnChange.setVisibility(View.VISIBLE);
            llChange.setVisibility(View.VISIBLE);

            tvSelectedAddress.setText(SessionManager.Cart.getInstance().getAddress());
            tvAddress.setText(Constants.DeliveryAddess);
        } else {
            btnChange.setVisibility(View.GONE);
            llChange.setVisibility(View.GONE);
            tvSelectedAddress.setText(SessionManager.Cart.getInstance().getAddress());
            tvAddress.setText(Constants.PickUpAddress);
        }
    }

    private void LoadCart() {

        Double subTotal = 0.0;

        mCartItemList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        Gson gson = new GsonBuilder().create();

        for (Items item : realm.where(Items.class).findAll()) {
            mCartItem = new CartItem();
            mCartItem.setsNo(item.getsNo());
            mCartItem.setItemName(item.getItemName());
            mCartItem.setItemPrice(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(item.getPrice()));
            mCartItem.setItemImage(item.getItemImagePath());
            mCartItem.setItemKey(item.getItemKey());
            mCartItem.setQuantity(item.getQuantity());


            Double total = item.getPrice();

            String mModifierList = "";
            String mIngredientsList = "";
            String mSpecialOfferList = "";

            if (!item.getModifier().trim().isEmpty()) {
                ArrayList<ItemAPI.Ingredient> modifiers = gson.fromJson(item.getModifier(), new TypeToken<List<ItemAPI.Ingredient>>() {
                }.getType());
                for (ItemAPI.Ingredient modifier : modifiers) {
                    total = total + (modifier.getPrice() != null ? modifier.getPrice() : 0);
                    mModifierList = mModifierList + Constants.No + " " + modifier.getDetails().getIngredientName() + ", ";
                }
            }

            if (!item.getIngredients().trim().isEmpty()) {
                ArrayList<ItemAPI.Ingredient> ingredients = gson.fromJson(item.getIngredients(), new TypeToken<List<ItemAPI.Ingredient>>() {
                }.getType());
                for (ItemAPI.Ingredient ingredient : ingredients) {
                    total = total + (ingredient.getPrice() != null ? ingredient.getPrice() : 0);

                    String withIce = "";
                    if (ingredient.getSelectedDrink() != null && ingredient.getSelectedDrink()) {
                        withIce = "(" + Constants.WithIce + ")";
                    }
                    mIngredientsList = mIngredientsList + ingredient.getDetails().getIngredientName() + withIce + ", ";
                }
            }

            if (!item.getSpecialOffer().trim().isEmpty()) {
                ArrayList<SpecialOffer> specialOffers = gson.fromJson(item.getSpecialOffer(), new TypeToken<List<SpecialOffer>>() {
                }.getType());
                for (SpecialOffer specialOffer : specialOffers) {
                    total = total + (specialOffer.getOfferPrice() != null ? specialOffer.getOfferPrice() : 0);
                    mSpecialOfferList = mSpecialOfferList + specialOffer.getOffername() + ", ";
                }
            }

            mCartItem.setTotalPrice(String.valueOf(total /** item.getQuantity()*/));
            mCartItem.setIngrdientsList(mIngredientsList);
            mCartItem.setModifierList(mModifierList);
            mCartItem.setSpecialOffers(mSpecialOfferList);
            mCartItem.setUserScope(0);
            mCartItem.setItemType(ConstantsInternal.ItemType.Item);
            mCartItemList.add(mCartItem);

            subTotal = subTotal + (total * item.getQuantity());
        }

        for (SpecialItems item : realm.where(SpecialItems.class).findAll()) {
            mCartItem = new CartItem();
            mCartItem.setItemName(item.getSpecialOfferName());
            mCartItem.setItemPrice(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(item.getPrice()));
            mCartItem.setItemImage(item.getSpecialOfferImagePath());
            mCartItem.setItemKey(item.getSpecialOfferKey());
            mCartItem.setQuantity(item.getQuantity());
            mCartItem.setTotalPrice(String.valueOf(item.getPrice() /** item.getQuantity()*/));
            mCartItem.setUserScope(item.getUserScope());

            mCartItem.setIngrdientsList("");
            mCartItem.setModifierList("");
            mCartItem.setItemType(ConstantsInternal.ItemType.SpecialItem);
            mCartItemList.add(mCartItem);
        }

        tvSubTotalValue.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(subTotal));
    }

    private void Checkout() {
        if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(Cart.this, tvLogin.getText().toString());
        } else {
//            SessionManager.Cart.getInstance().getLatitude().equals("") || SessionManager.Cart.getInstance().getLatitude().equals("0.0") ||
//                    SessionManager.Cart.getInstance().getLongitude().equals("") || SessionManager.Cart.getInstance().getLongitude().equals("0.0") ||
//                    SessionManager.Cart.getInstance().getAddressKey().isEmpty() ||
            if (tvSelectedAddress.getText().toString().isEmpty()) {
                tvSelectedAddress.setText(Constants.PleaseLoginToCheckout);
                CommonFunctions.getInstance().ShowSnackBar(Cart.this, Constants.PleaseSelectAnAddressToContinue);
            } else {
                Gson gson = new GsonBuilder().create();
                ArrayList<CheckoutItemDetails> items = CartDB.getInstance().GetItems();
                ArrayList<CheckoutItemDetails> offers = CartDB.getInstance().GetOffers();
                ArrayList<CheckoutItemDetails> specialOffers = CartDB.getInstance().GetSpecialOffers();
                ArrayList<CheckoutItemDetails> specialItems = CartDB.getInstance().GetSpecialItems();
                ArrayList<CheckoutItemDetails> couponItems = CartDB.getInstance().GetCouponItems();

                if (items.size() <= 0 && specialOffers.size() <= 0 && specialItems.size() <= 0 && couponItems.size() <= 0) {
                    CommonFunctions.getInstance().ShowSnackBar(Cart.this, Constants.PleaseAddItemsToCartAndCheckout);
                    return;
                }
                CheckoutDetails checkoutDetails = new CheckoutDetails();
                checkoutDetails.setOrder_type(SessionManager.Cart.getInstance().getOrderType());
                checkoutDetails.setBranch_key(SessionManager.Cart.getInstance().getBranchKey());
                checkoutDetails.setUser_key(SessionManager.User.getInstance().getKey());
                checkoutDetails.setAddress_key(SessionManager.Cart.getInstance().getAddressKey());
                checkoutDetails.setMobile_number(tvMobileValue.getText().toString());
                checkoutDetails.setEmail(tvEmailValue.getText().toString());
                checkoutDetails.setLatitude(Double.parseDouble(SessionManager.Cart.getInstance().getLatitude()));
                checkoutDetails.setLongitude(Double.parseDouble(SessionManager.Cart.getInstance().getLongitude()));
                checkoutDetails.setItems(items);

                checkoutDetails.setPayment_option(1);
                checkoutDetails.setSpecial_offers(specialOffers);
                checkoutDetails.setSpecial_items(specialItems);
                checkoutDetails.setCoupon_items(couponItems);
                checkoutDetails.setCoupon_code("");
                checkoutDetails.setUse_loyalty_points(false);

                final String output = gson.toJsonTree(checkoutDetails).toString();
                RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), output);

                CustomProgressDialog.getInstance().show(Cart.this);
                CheckoutAPI mCheckoutAPI = ApiConfiguration.getInstance2().getApiBuilder().create(CheckoutAPI.class);
                final Call<CheckoutAPI.ResponseCheckout> call = mCheckoutAPI.Verify(mRequestBody);
                call.enqueue(new Callback<CheckoutAPI.ResponseCheckout>() {
                    @Override
                    public void onResponse(Call<CheckoutAPI.ResponseCheckout> call, Response<CheckoutAPI.ResponseCheckout> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                CustomProgressDialog.getInstance().dismiss();

                                if (response.body().getData().getTimeSlot().size() <= 0) {
                                    CommonFunctions.getInstance().ShowSnackBar(Cart.this, Constants.YouCantOrderToday);
                                    return;
                                }

                                Bundle bundle = new Bundle();
                                bundle.putString("details", output);
                                bundle.putSerializable("data", (Serializable) response.body().getData());
                                MyActivity.getInstance().Checkout(Cart.this, bundle);


                            } else {
                                CommonFunctions.getInstance().ShowSnackBar(Cart.this, response.body().getMessage());
                                CustomProgressDialog.getInstance().dismiss();
                            }
                        } else {
                            CommonFunctions.getInstance().ShowSnackBar(Cart.this, response.message());
                            CustomProgressDialog.getInstance().dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<CheckoutAPI.ResponseCheckout> call, Throwable t) {
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().ShowSnackBar(Cart.this, Constants.NoInternetConnection);
                    }
                });

            }
        }
    }


    @OnClick({R.id.tlbar_back, R.id.llConfirmOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.llConfirmOrder:
                Checkout();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myEventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBRefreshCart event) {
//        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
//        tvSubTotalValue.startAnimation(animShake);
        LoadCart();
        mCartAdapter.notifyDataSet(mCartItemList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBLogin event) {

        tvEmailValue.setText(SessionManager.User.getInstance().getEmail());
        tvMobileValue.setText(SessionManager.User.getInstance().getMobile());

        if (!SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            llAddressDetails.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        }


    }
}
