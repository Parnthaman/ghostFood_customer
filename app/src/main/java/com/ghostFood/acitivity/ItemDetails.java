package com.ghostFood.acitivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.adapter.ItemImagesPageAdapter;
import com.ghostFood.adapter.ModifierAdapter;
import com.ghostFood.adapter.SpecialOfferAdapter;
import com.ghostFood.api.ItemAPI;
import com.ghostFood.api.OfferAPI;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.events.EBCalculateCart;
import com.ghostFood.events.EBModifiers;
import com.ghostFood.model.SpecialOffer;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetails extends AppCompatActivity implements View.OnTouchListener {
    EventBus myEventBus = EventBus.getDefault();
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
    @BindView(R.id.vp_offer)
    ViewPager vpOffer;
    @BindView(R.id.tvitemPrice)
    TextView tvitemPrice;
    @BindView(R.id.tvitemName)
    TextView tvitemName;
    @BindView(R.id.tvIncludes)
    TextView tvIncludes;
    @BindView(R.id.btnModify)
    Button btnModify;
    @BindView(R.id.llSubCourseDynamic)
    LinearLayout llSubCourseDynamic;
    @BindView(R.id.vpSpecialOffer)
    ViewPager vpSpecialOffer;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.fl_offer)
    FrameLayout flOffer;
    @BindView(R.id.tvTotalTxt)
    TextView tvTotalTxt;
    @BindView(R.id.tvTotalValues)
    TextView tvTotalValues;
    @BindView(R.id.btnAddMore)
    Button btnAddMore;
    @BindView(R.id.btnAddToCarts)
    Button btnAddToCarts;
    @BindView(R.id.llParentLayout)
    LinearLayout llParentLayout;
    @BindView(R.id.tvTotalValue)
    TextView tvTotalValue;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tvItemDetailCartCount)
    TextView tvItemDetailCartCount;
    @BindView(R.id.flItemDetailCart)
    FrameLayout flItemDetailCart;
    @BindView(R.id.ivAddCart)
    ImageView ivAddCart;
    @BindView(R.id.llCartDetail)
    LinearLayout llCartDetail;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.flBottom)
    FrameLayout flBottom;
    @BindView(R.id.ivVeg)
    ImageView ivVeg;
    @BindView(R.id.ivNonveg)
    ImageView ivNonveg;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvAddCart)
    TextView tvAddCart;


    private String itemKey = "";
    ItemAPI.Data itemData;
    private String[] itemImage;
    private ItemImagesPageAdapter adapter;

    public ItemAPI.ItemIngredient itemModifier;
    public ArrayList<ItemAPI.ItemIngredient> itemIngredients = new ArrayList<>();

    ArrayList<SpecialOffer> mSpecialOffer;
    SpecialOfferAdapter specialOfferAdapter;
    SpecialOffer mSpecial;

    public static Activity mItemDetailsActivity;

    String msg = "testing";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 6000;
    private FrameLayout.LayoutParams layoutParams;

    float dX;
    float dY;
    int lastAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(ItemDetails.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(ItemDetails.this, toolbar);
        mItemDetailsActivity = this;
        myEventBus.register(this);
        llParentLayout.setVisibility(View.GONE);

        itemKey = getIntent().getExtras() != null ? getIntent().getExtras().getString("item_key") != null ? getIntent().getExtras().getString("item_key").toString() : "" : "";
        tlbarCart.setVisibility(View.VISIBLE);
        flCart.setVisibility(View.GONE);

        flItemDetailCart.setVisibility(View.VISIBLE);
        tlbarCart.setVisibility(View.VISIBLE);
        tvItemDetailCartCount.setVisibility(View.VISIBLE);


        btnAddMore.setText(Constants.AddMore);
        tvAddCart.setText(Constants.AddMore);
        //btnAddToCart.setText(Constants.AddToCart);
        tvTotalTxt.setText(Constants.Total);
        btnModify.setText(Constants.Modify);

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            tlbarCart.setImageResource(R.drawable.ic_cart_ar);
        } else {
            tlbarCart.setImageResource(R.drawable.ic_cart);
        }

        tvTotalValue.setText(Constants.Price + ": " + CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(ConstantsInternal.CurrencyZero));
        callIngredientsApi();
        CommonFunctions.getInstance().ShowCartCount(tvItemDetailCartCount);

//        ivAddCart.setOnTouchListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void DynamicLoadSubCourse(final ItemAPI.ItemIngredient itemIngredient) {

        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parms.gravity = Gravity.BOTTOM;

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        List<ItemAPI.Ingredient> ingredients = itemIngredient.getIngredients();

        int validIngCount = 0;
        for (ItemAPI.Ingredient ingredient : ingredients) {
            if (ingredient.getDetails() != null) {
                validIngCount++;
            }
        }
        if (validIngCount == 0) {
            return;
        }

        ImageView imageView = new ImageView(ItemDetails.this);
        imageView.setBackground(getResources().getDrawable(R.drawable.img_subcourse));
        imageView.setLayoutParams(parms);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (getResources().getDimension(R.dimen._50sdp) * scale);

        final float scalea = getResources().getDisplayMetrics().density;
        int pixels1 = (int) (150 * scalea + 0.5f);

        FrameLayout.LayoutParams layoutParamsFrame = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels1);
        FrameLayout frameLayout = new FrameLayout(ItemDetails.this);
        frameLayout.setLayoutParams(layoutParamsFrame);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        HorizontalScrollView.LayoutParams layoutParamsHorizontalScrollView = new HorizontalScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final HorizontalScrollView horizontalScrollView = new HorizontalScrollView(ItemDetails.this);
        horizontalScrollView.setLayoutParams(layoutParamsHorizontalScrollView);
        horizontalScrollView.setVerticalScrollBarEnabled(false);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);

        LinearLayout linearLayoutScroll = new LinearLayout(ItemDetails.this);
        linearLayoutScroll.setLayoutParams(layoutParams);
        linearLayoutScroll.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutScroll.setGravity(Gravity.CENTER);

        int pixelsFrameLayout = (int) (getResources().getDimension(R.dimen.fontsize) * scale);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.gravity = Gravity.LEFT;

        TextView textView = (TextView) inflater.inflate(R.layout.dynamictextview3, null);
        textView.setText(itemIngredient.getGroupName());
        llSubCourseDynamic.addView(textView);
        FontFunctions.getInstance().fontAppFont(ItemDetails.this, textView);


        Integer selectedItemsCount = 0;
        int count = 0;
        itemImage = new String[ingredients.size()];
        for (ItemAPI.Ingredient ingredient : ingredients) {
            if (ingredient.getDetails() != null) {
                itemImage[count] = ingredient.getDetails().getIngredientImage();

                final View child = getLayoutInflater().inflate(R.layout.adapter_subcourse, null);

                ImageView ivImage = (ImageView) child.findViewById(R.id.ivImage);
                TextView tvName = (TextView) child.findViewById(R.id.tvName);
                TextView tvPrice = (TextView) child.findViewById(R.id.tvPrice);
                ImageView ivAdd = (ImageView) child.findViewById(R.id.ivAdd);
                ivAdd.setTag(count);
                CommonFunctions.getInstance().LoadImageByFrescoNew(ivImage, itemImage[count]);

                tvName.setText(ingredient.getDetails().getIngredientName());
                tvPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(ingredient.getPrice()));

                if (ingredient.getSelected()) {
                    selectedItemsCount++;
                    ivAdd.setImageResource(R.drawable.ic_greentick);
                    ivAdd.setVisibility(View.VISIBLE);
                }

                child.setTag(ingredient);
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        final ItemAPI.ItemIngredient itmIng = ((ItemAPI.ItemIngredient) ((HorizontalScrollView) v.getParent().getParent()).getTag());
                        ImageView ivAdd = (ImageView) child.findViewById(R.id.ivAdd);
                        final ItemAPI.Ingredient ingredientLocal = ((ItemAPI.Ingredient) child.getTag());

                        if (itmIng.getMaximum() == 1) {
                            int counts = 0;
                            for (ItemAPI.Ingredient ingredientMaster : itmIng.getIngredients()) {
                                if (ingredientMaster.getDetails() == null) {
                                    continue;
                                }
                                for (int m = 0; m < ((LinearLayout) v.getParent()).getChildCount(); m++) {
                                    View tempChld = ((LinearLayout) v.getParent()).getChildAt(m);

                                    ImageView ivAdds = (ImageView) tempChld.findViewById(R.id.ivAdd);
                                    final ItemAPI.Ingredient ingredientLocals = ((ItemAPI.Ingredient) tempChld.getTag());
                                    ivAdds.setImageResource(0);
                                    ivAdds.setVisibility(View.INVISIBLE);
                                    ingredientLocals.setSelected(false);
                                    ingredientLocals.setSelectedDrink(false);
                                    tempChld.setTag(ingredientLocals);

                                    ItemAPI.ItemIngredient itmIngs = ((ItemAPI.ItemIngredient) ((HorizontalScrollView) tempChld.getParent().getParent()).getTag());

                                    itmIngs.getIngredients().get(counts).setSelected(false);
                                    itmIngs.getIngredients().get(counts).setSelectedDrink(false);
                                    itmIngs.setSelectedItemsCount(itmIngs.getSelectedItemsCount() - 1);
                                    ((HorizontalScrollView) tempChld.getParent().getParent()).setTag(itmIngs);

                                    int countIng = 0;
                                    for (ItemAPI.ItemIngredient ing : itemIngredients) {
                                        if (ing.getItemIngredientGroupKey().equalsIgnoreCase(itmIngs.getItemIngredientGroupKey())) {
                                            itemIngredients.set(countIng, itmIngs);
                                        }
                                        countIng++;
                                    }
                                }
                                counts++;
                            }
                        }

                        int count = 0;
                        for (ItemAPI.Ingredient ingredientMaster : itmIng.getIngredients()) {
                            if (ingredientMaster.getDetails() == null) {
                                continue;
                            }

                            if (ingredientLocal.getSelected() &&
                                    ingredientMaster.getDetails().getIngredientKey().equalsIgnoreCase(ingredientLocal.getDetails().getIngredientKey())) {

                                ivAdd.setImageResource(0);
                                ivAdd.setVisibility(View.INVISIBLE);
                                ingredientLocal.setSelected(false);
                                ingredientLocal.setSelectedDrink(false);
                                child.setTag(ingredientLocal);

                                itmIng.getIngredients().get(count).setSelected(false);
                                itmIng.getIngredients().get(count).setSelectedDrink(false);
                                itmIng.setSelectedItemsCount(itmIng.getSelectedItemsCount() - 1);
                                ((HorizontalScrollView) v.getParent().getParent()).setTag(itmIng);


                                int countIng = 0;
                                for (ItemAPI.ItemIngredient ing : itemIngredients) {
                                    if (ing.getItemIngredientGroupKey().equalsIgnoreCase(itmIng.getItemIngredientGroupKey())) {
                                        itemIngredients.set(countIng, itmIng);
                                    }
                                    countIng++;
                                }


                                break;
                            } else if (!ingredientLocal.getSelected() &&
                                    ingredientMaster.getDetails().getIngredientKey().equalsIgnoreCase(ingredientLocal.getDetails().getIngredientKey())) {


                                if (itmIng.getSelectedItemsCount() < itmIng.getMaximum()) {

                                    ivAdd.setImageResource(R.drawable.ic_greentick);
                                    ivAdd.setVisibility(View.VISIBLE);
                                    ingredientLocal.setSelected(true);


                                    final ItemAPI.Ingredient ingDrink = itmIng.getIngredients().get(count);
                                    if (itemIngredient.getIsDrink() == 1) {
                                        ShowDrinksDialog(new CommonCallback.Listener() {
                                            @Override
                                            public void onSuccess() {
                                                ingredientLocal.setSelectedDrink(true);
                                                child.setTag(ingredientLocal);
                                                ingDrink.setSelectedDrink(true);
                                                ingDrink.setSelected(true);
                                                itmIng.setSelectedItemsCount(itmIng.getSelectedItemsCount() + 1);
                                                ((HorizontalScrollView) v.getParent().getParent()).setTag(itmIng);


                                                int countIng = 0;
                                                for (ItemAPI.ItemIngredient ing : itemIngredients) {
                                                    if (ing.getItemIngredientGroupKey().equalsIgnoreCase(itmIng.getItemIngredientGroupKey())) {
                                                        itemIngredients.set(countIng, itmIng);
                                                    }

                                                    countIng++;
                                                }
                                            }

                                            @Override
                                            public void onFailure() {
                                                ingredientLocal.setSelectedDrink(false);
                                                child.setTag(ingredientLocal);

                                                ingDrink.setSelectedDrink(false);
                                                ingDrink.setSelected(true);
                                                itmIng.setSelectedItemsCount(itmIng.getSelectedItemsCount() + 1);
                                                ((HorizontalScrollView) v.getParent().getParent()).setTag(itmIng);


                                                int countIng = 0;
                                                for (ItemAPI.ItemIngredient ing : itemIngredients) {
                                                    if (ing.getItemIngredientGroupKey().equalsIgnoreCase(itmIng.getItemIngredientGroupKey())) {
                                                        itemIngredients.set(countIng, itmIng);
                                                    }

                                                    countIng++;
                                                }
                                            }
                                        });
                                    } else {
                                        ingredientLocal.setSelectedDrink(false);
                                        child.setTag(ingredientLocal);

                                        ingDrink.setSelectedDrink(false);
                                        ingDrink.setSelected(true);
                                        itmIng.setSelectedItemsCount(itmIng.getSelectedItemsCount() + 1);
                                        ((HorizontalScrollView) v.getParent().getParent()).setTag(itmIng);


                                        int countIng = 0;
                                        for (ItemAPI.ItemIngredient ing : itemIngredients) {
                                            if (ing.getItemIngredientGroupKey().equalsIgnoreCase(itmIng.getItemIngredientGroupKey())) {
                                                itemIngredients.set(countIng, itmIng);
                                            }

                                            countIng++;
                                        }
                                    }


                                } else {
                                    CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, Constants.MaximumSelectionReached);
                                }
                                break;
                            }

                            count++;
                        }


                        CalculateCart();
                    }
                });

                linearLayoutScroll.addView(child);
                count++;
            }
        }


        itemIngredient.setSelectedItemsCount(selectedItemsCount);
        horizontalScrollView.addView(linearLayoutScroll);
        horizontalScrollView.setTag(itemIngredient);

        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                    }
                }

            }
        }, 10);

        frameLayout.addView(imageView);
        frameLayout.addView(horizontalScrollView);
        llSubCourseDynamic.addView(frameLayout);
        llSubCourseDynamic.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

    }


    private void callIngredientsApi() {
        CustomProgressDialog.getInstance().show(ItemDetails.this);
        ItemAPI mItemAPI = ApiConfiguration.getInstance2().getApiBuilder().create(ItemAPI.class);
        final Call<ItemAPI.ResponseItem> call = mItemAPI.Get(this.itemKey);
        call.enqueue(new Callback<ItemAPI.ResponseItem>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ItemAPI.ResponseItem> call, Response<ItemAPI.ResponseItem> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        itemData = response.body().getData();
                        tlbarText.setText(response.body().getData().getItemName());
                        tvitemName.setText(response.body().getData().getItemName());
                        tvitemPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(response.body().getData().getItemPrice()));
                        tvTotalValue.setText(Constants.Price + ": " + CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(response.body().getData().getItemPrice()));
                        llParentLayout.setVisibility(View.VISIBLE);
                        tvDescription.setText(response.body().getData().getItemDescription());
                        LoadItemImagesPager(response.body().getData().getItemImage());

//                        if (response.body().getData().getFood_type() == 1) {
//                            ivVeg.setVisibility(View.VISIBLE);
//                            ivNonveg.setVisibility(View.GONE);
//                        } else {
//                            ivVeg.setVisibility(View.GONE);
//                            ivNonveg.setVisibility(View.VISIBLE);
//                        }
                        if (response.body().getData().getItemIngredient() != null) {
                            for (ItemAPI.ItemIngredient itemIngredient : response.body().getData().getItemIngredient()) {
                                if (ConstantsInternal.IngredientType.fromInteger(itemIngredient.getIngredientType()) == ConstantsInternal.IngredientType.Modifier) {
                                    itemModifier = itemIngredient;
                                    LoadIncludedModifiers();
                                } else {
                                    itemIngredients.add(itemIngredient);

                                    DynamicLoadSubCourse(itemIngredient);
                                }
                            }
                        }

                        CustomProgressDialog.getInstance().dismiss();
                        //callSpecialOffersApi();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }
            }

            @Override
            public void onFailure(Call<ItemAPI.ResponseItem> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, t.toString());
            }
        });

    }

    private void callSpecialOffersApi() {
        CustomProgressDialog.getInstance().show(ItemDetails.this);
        OfferAPI mOfferAPI = ApiConfiguration.getInstance2().getApiBuilder().create(OfferAPI.class);
        final Call<OfferAPI.ResponseOffer> call = mOfferAPI.Get(ConstantsInternal.OfferType.SpecialItem.getValue());
        call.enqueue(new Callback<OfferAPI.ResponseOffer>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<OfferAPI.ResponseOffer> call, Response<OfferAPI.ResponseOffer> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        LoadSpecicalOffer(response.body().getData());
                        CalculateCart();

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }
            }

            @Override
            public void onFailure(Call<OfferAPI.ResponseOffer> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, t.toString());
            }
        });

    }

    private void LoadItemImagesPager(List<ItemAPI.ItemImage> itemImages) {
        int count = 0;
        itemImage = new String[itemImages.size()];
        for (ItemAPI.ItemImage img : itemImages) {
            itemImage[count] = img.getItemImagePath();
            count++;
        }
        adapter = new ItemImagesPageAdapter(ItemDetails.this, itemImage);
        vpOffer.setAdapter(adapter);
    }

    private void LoadIncludedModifiers() {

        //We have modifiers and so display the text field and button
        tvIncludes.setVisibility(View.VISIBLE);
        btnModify.setVisibility(View.VISIBLE);

        String mIncludeTitle = "<b><font color=\"#F6A524\">" + Constants.Includes.toUpperCase() + "</font></b><br>";

        String mIncludeContent = "";
        for (ItemAPI.Ingredient ingredient : itemModifier.getIngredients()) {
            if (ingredient.getSelected()) {
                mIncludeContent = mIncludeContent.trim().isEmpty() ? ingredient.getDetails().getIngredientName() : mIncludeContent + ", " + ingredient.getDetails().getIngredientName();
            }
        }

        tvIncludes.setText(CommonFunctions.fromHtml(mIncludeTitle + mIncludeContent));
        CalculateCart();
        FontFunctions.getInstance().FontSegoeSemiBold(ItemDetails.this, tvIncludes);
        FontFunctions.getInstance().FontBabeNeueBold(ItemDetails.this, btnModify);


    }

    private void LoadSpecicalOffer(List<OfferAPI.Data> datas) {
        mSpecialOffer = new ArrayList<>();

        if (datas.size() > 0) {
            flOffer.setVisibility(View.VISIBLE);
        }

        for (OfferAPI.Data data : datas) {
            if (data.getOfferTitle() != null && !data.getOfferTitle().trim().isEmpty()) {
                mSpecial = new SpecialOffer();
                mSpecial.setOfferKey(data.getOfferKey());
                mSpecial.setOfferType(ConstantsInternal.OfferType.SpecialItem);
                mSpecial.setHeading(data.getOfferTitle());
                mSpecial.setItemImage(data.getOfferItemImage());
                mSpecial.setOffername(data.getOfferText1());
                mSpecial.setOfferDescription(data.getOfferText2());
                mSpecial.setOfferPrice(data.getOfferPrice());
                mSpecial.setSelected(false);
                mSpecialOffer.add(mSpecial);
            }
        }

        specialOfferAdapter = new SpecialOfferAdapter(ItemDetails.this, mSpecialOffer);
        vpSpecialOffer.setAdapter(specialOfferAdapter);
        indicator.setViewPager(vpSpecialOffer);

        if (mSpecialOffer.size() > 1) {
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (vpOffer != null) {
                        if (currentPage == mSpecialOffer.size()) {
                            currentPage = 0;
                        }
                        vpOffer.setCurrentItem(currentPage++, true);
                    }
                }
            };
            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        }


    }

    private void CalculateCart() {

        Double total = Double.parseDouble(CommonFunctions.DeciamlDigitsAfterDotValue.get(itemData.getItemPrice()));

        if (itemIngredients != null) {
            for (ItemAPI.ItemIngredient itemIngredient : itemIngredients) {
                for (ItemAPI.Ingredient ingredient : itemIngredient.getIngredients()) {
                    if (ingredient.getSelected()) {
                        total = total + ingredient.getPrice();
                    }
                }
            }
        }

        if (itemModifier != null && itemModifier.getIngredients() != null) {
            for (ItemAPI.Ingredient ingredient : itemModifier.getIngredients()) {
                if (ingredient.getSelected()) {
                    total = total + ingredient.getPrice();
                }
            }
        }

        if (specialOfferAdapter != null && specialOfferAdapter.specialOffer != null) {
            for (SpecialOffer specialOffer : specialOfferAdapter.specialOffer) {
                if (specialOffer.getSelected()) {
                    total = total + specialOffer.getOfferPrice();
                }
            }
        }

        tvTotalValue.setText(Constants.Price + ": " + CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(total));

        // tvTotalTxt.setText(Constants.Total + " : ");

        btnAddMore.setText(Constants.AddMore);
        // btnAddToCart.setText(Constants.AddToCart);

    }

    private void AddToCart() {

        Gson gson = new GsonBuilder().create();

        ArrayList<ItemAPI.Ingredient> modifiers = new ArrayList<>();


        if (itemModifier != null) {
            for (ItemAPI.Ingredient ingredient : itemModifier.getIngredients()) {
                if (!ingredient.getSelected()) {
                    ingredient.setGroupKey(itemModifier.getItemIngredientGroupKey());
                    modifiers.add(ingredient);
                }
            }
        }


        String modifiersJSON = gson.toJsonTree(modifiers).toString();


        ArrayList<ItemAPI.Ingredient> ingredients = new ArrayList<>();
        if (itemIngredients != null) {
            for (ItemAPI.ItemIngredient itemIngredient : itemIngredients) {

                Integer minimum = itemIngredient.getMinimum();
                Integer maximum = itemIngredient.getMaximum();

                Integer itemIngredientCount = 0;
                for (ItemAPI.Ingredient ingredient : itemIngredient.getIngredients()) {
                    if (ingredient.getSelected() && ingredient.getDetails() != null) {
                        ingredient.setGroupKey(itemIngredient.getItemIngredientGroupKey());
                        ingredients.add(ingredient);
                        itemIngredientCount++;
                    }
                }

                if (itemIngredientCount < minimum) {
                    CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, MessageFormat.format(Constants.PleaseSelectMinimumIngredients, minimum.toString(), itemIngredient.getGroupName()));
                    return;
                } else if (itemIngredientCount > maximum) {
                    CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, MessageFormat.format(Constants.PleaseSelectMaximumIngredients, maximum.toString(), itemIngredient.getGroupName()));
                    return;
                }
            }
        }
        String ingredientsJSON = gson.toJsonTree(ingredients).toString();


        String imagePath = itemData.getItemImage().size() > 0 ? itemData.getItemImage().get(0).getItemImagePath() : "";


        // as we are showing special offers as seperate item we are making "specialOffersJSON" as empty string to avoid any calcualtion issues
        CartDB.getInstance().Add(itemData.getItemKey(), itemData.getItemName(), modifiersJSON, ingredientsJSON, "", itemData.getItemPrice(), imagePath, new CommonCallback.Listener() {
            @Override
            public void onSuccess() {
                CommonFunctions.getInstance().ShowCartCount(tvItemDetailCartCount);
                CommonFunctions.getInstance().FinishActivityWithDelay(ItemDetails.this);
            }

            @Override
            public void onFailure() {
                CommonFunctions.getInstance().ShowCartCount(tvItemDetailCartCount);
            }
        });

        /*for(SpecialOffer specialOffer : specialOfferAdapter.specialOffer) {
            if(specialOffer.getSelected()) {
                CartDB.getInstance().AddSpecialOffer(specialOffer.getOfferKey(), specialOffer.getOffername(), specialOffer.getOfferPrice(), specialOffer.getItemImage());
            }
        }*/

        CommonFunctions.getInstance().ShowCartCount(tvItemDetailCartCount);
        TastyToast.makeText(ItemDetails.this, Constants.AddedToCart, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
//        CommonFunctions.getInstance().ShowSnackBar(ItemDetails.this, Constants.AddedToCart);
    }

    @OnClick({R.id.btnAddMore, R.id.btnModify, R.id.tlbar_back, R.id.llCartDetail, R.id.tvAddCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddMore:
                AddToCart();
                break;
            case R.id.tvAddCart:
                AddToCart();
                break;
            case R.id.btnModify:
                ShowModifierDialog();
                break;
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.llCartDetail:
                Bundle mBundle = new Bundle();
                MyActivity.getInstance().Cart(ItemDetails.this, mBundle);
                finish();
                break;
        }
    }

    private void ShowModifierDialog() {

        final Dialog dialog = new Dialog(ItemDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_modifier);

        ListView lvModifier = (ListView) dialog.findViewById(R.id.lvModifier);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setText(Constants.OK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnOk.setVisibility(View.GONE);

        if (itemModifier != null) {
            ModifierAdapter mModifierAdapter = new ModifierAdapter(ItemDetails.this, itemModifier.getIngredients());
            lvModifier.setAdapter(mModifierAdapter);


            dialog.show();
        }
    }

    private void ShowDrinksDialog(final CommonCallback.Listener callback) {
        final Dialog dialog = new Dialog(ItemDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_drinks);
        dialog.setCancelable(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);

        FontFunctions.getInstance().FontBerlin(ItemDetails.this, tvMessage);
        FontFunctions.getInstance().FontSketchBold(ItemDetails.this, btnCancel);
        FontFunctions.getInstance().FontSketchBold(ItemDetails.this, btnContinue);

        tvMessage.setText(Constants.DoYouNeedIce);

        btnCancel.setText(Constants.No);
        btnContinue.setText(Constants.Yes);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onFailure();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onSuccess();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myEventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBModifiers event) {
        LoadIncludedModifiers();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBCalculateCart event) {
        CalculateCart();
        CommonFunctions.getInstance().ShowCartCount(tvItemDetailCartCount);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    //Toast.makeText(ItemDetails.this, "Clicked!", Toast.LENGTH_SHORT).show();
                    break;

            default:
                return false;
        }
        return true;

    }
}
