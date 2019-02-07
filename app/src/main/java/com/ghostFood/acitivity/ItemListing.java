package com.ghostFood.acitivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ghostFood.R;
import com.ghostFood.api.CategoryAPI;
import com.ghostFood.fragment.CategoryItem;
import com.ghostFood.model.Category;
import com.ghostFood.model.Item;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.ghostFood.util.slidingtab.SlidingFragmentPagerAdapter;
import com.ghostFood.util.slidingtab.SlidingTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListing extends AppCompatActivity {

    ArrayList<Category> listDataHeader;
    HashMap<Category, List<Item>> listDataChild;

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
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    SlidingTabLayout tabLayout;
    @BindView(R.id.tab_host)
    SlidingTabLayout tabHost;
    @BindView(R.id.llCartLayout)
    FrameLayout llCartLayout;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(ItemListing.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(ItemListing.this, toolbar);


        tlbarText.setText(Constants.Items);
        flCart.setVisibility(View.GONE);
        tlbarCart.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            tlbarCart.setImageResource(R.drawable.ic_cart_ar);
        } else {
            tlbarCart.setImageResource(R.drawable.ic_cart);
        }
//        callCategoryApi();
        loadCategoryItem();
        llCartLayout.setVisibility(View.VISIBLE);
    }

    private void loadCategoryItem() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader = ConstantsInternal.mCategory;
        listDataChild = ConstantsInternal.mItems;

        LoadTabs(listDataHeader, listDataHeader);
        llCartLayout.setVisibility(View.VISIBLE);

    }


    private void callCategoryApi() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        CustomProgressDialog.getInstance().show(ItemListing.this);
        CategoryAPI mCategoryAPI = ApiConfiguration.getInstance2().getApiBuilder().create(CategoryAPI.class);
        final Call<CategoryAPI.ResponseCategory> call = mCategoryAPI.Get(SessionManager.Cart.getInstance().getOrderType() + "",
                SessionManager.Cart.getInstance().getLatitude(),
                SessionManager.Cart.getInstance().getLongitude(),
                SessionManager.Cart.getInstance().getAddressKey(),
                SessionManager.Cart.getInstance().getBranchKey(),
                SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<CategoryAPI.ResponseCategory>() {
            @Override
            public void onResponse(Call<CategoryAPI.ResponseCategory> call, Response<CategoryAPI.ResponseCategory> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {


                        //"http://www.freepngimg.com/thumb/burger%20sandwich/5-hamburger-burger-png-image-thumb.png"
                        //"http://www.freepngimg.com/thumb/burger%20sandwich/14-hamburger-burger-png-image-thumb.png"
                        List<Item> itemList = null;
                        int categoryCount = 0;
                        for (CategoryAPI.Data data : response.body().getData()) {
                            Category mCategory = new Category();
                            mCategory.setCategoryKey(data.getCategoryKey());
                            mCategory.setCategoryName(data.getCategoryName());
                            mCategory.setCategoryImage(data.getCategoryImage());
                            listDataHeader.add(mCategory);
                            itemList = new ArrayList<Item>();
                            if (data.getItems() != null) {
                                for (CategoryAPI.Item mItem : data.getItems()) {
                                    Item item = new Item();
                                    item.setItemKey(mItem.getItemKey());
                                    item.setItemName(mItem.getItemName());
                                    item.setFood_type(mItem.getFood_type());
                                    item.setItemPrice(Double.parseDouble(mItem.getItem_price()));
                                    if (mItem.getItemImage().size() > 0) {
                                        item.setItemImage(mItem.getItemImage().get(0).getItemImagePath());
                                    }
                                    itemList.add(item);
                                }
                            }
                            listDataChild.put(listDataHeader.get(categoryCount), itemList);
                            categoryCount++;
                        }

                   /*     listDataHeader = ConstantsInternal.mCategory;
                        listDataChild = ConstantsInternal.mItems;
*/
                        LoadTabs(listDataHeader, listDataHeader);
                        llCartLayout.setVisibility(View.VISIBLE);
                        if (viewPager.getVisibility() == View.VISIBLE) {
                            CustomProgressDialog.getInstance().dismiss();
                        }
                        // Sequent.origin(llCartLayout).duration(500).anim(ItemListing.this, R.anim.overshoot).start();

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ItemListing.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().FinishActivityWithDelay(ItemListing.this);
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(ItemListing.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().FinishActivityWithDelay(ItemListing.this);
                }

            }

            @Override
            public void onFailure(Call<CategoryAPI.ResponseCategory> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(ItemListing.this, Constants.NoInternetConnection);
                CommonFunctions.getInstance().FinishActivityWithDelay(ItemListing.this);
            }
        });

    }


    void LoadTabs(ArrayList<Category> dataHeader, ArrayList<Category> listDataHeader) {

        if (dataHeader.size() == 0) {
            tvNoDataFound.setVisibility(View.VISIBLE);
            tvNoDataFound.setText(Constants.no_data_found);
        } else {
            tvNoDataFound.setVisibility(View.GONE);
            tabLayout = (SlidingTabLayout) findViewById(R.id.tab_host);

            viewPager = (ViewPager) findViewById(R.id.view_pager);

            TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this, dataHeader, listDataHeader);
            viewPager.setAdapter(adapter);
            tabLayout.setViewPager(viewPager);
            viewPager.setPageTransformer(true, new RotateUpTransformer());
            viewPager.setCurrentItem(ConstantsInternal.mCurrentPosition);
            CommonFunctions.getInstance().changeTabsFont(ItemListing.this, tabLayout);
            tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            tabLayout.setSelectedIndicatorColors(R.color.white);
            //   tabLayout.setCustomFocusedColor(R.color.accent_color);
            CommonFunctions.getInstance().changeTabsFont(ItemListing.this, tabLayout);

            viewPager.setOffscreenPageLimit(dataHeader.size());

            tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //   edtSearch.setText("");
                    //   EventBus.getDefault().post(new EBSearchItem(""));
                }

                @Override
                public void onPageSelected(int position) {
                    //   edtSearch.setText("");
                    //    EventBus.getDefault().post(new EBSearchItem(""));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        CommonFunctions.getInstance().ShowCartCount(tvCartCount);
    }


    @OnClick({R.id.tlbar_back, R.id.tlbar_cart, R.id.flCart, R.id.llCartLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.tlbar_cart:
                Bundle mBundle = new Bundle();
                MyActivity.getInstance().Cart(ItemListing.this, mBundle);
                break;
            case R.id.flCart:
                Bundle mBundle1 = new Bundle();
                MyActivity.getInstance().Cart(ItemListing.this, mBundle1);
                break;
            case R.id.llCartLayout:
                Bundle mBundle2 = new Bundle();
                MyActivity.getInstance().Cart(ItemListing.this, mBundle2);
                break;
        }
    }

    public class TabAdapter extends SlidingFragmentPagerAdapter {

        ArrayList<Category> mListDataHeader;
        ArrayList<Category> mDataHeader;
        private Context context;

        public TabAdapter(FragmentManager fm, Context context, ArrayList<Category> dataHeader, ArrayList<Category> listDataHeader) {
            super(fm);
            this.context = context;
            this.mListDataHeader = listDataHeader;
            this.mDataHeader = dataHeader;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(CategoryItem.POSITION, position + 1);


            Constants.mListDataHeader = mListDataHeader;
            Constants.listDataChild = listDataChild;

           /* bundle.putSerializable("data", mListDataHeader);
            bundle.putSerializable("header", listDataChild);*/

            Fragment fragment = new CategoryItem();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return mListDataHeader.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mListDataHeader.get(position).getCategoryName();
        }

        @Override
        public Drawable getPageDrawable(int position) {
            return context.getResources().getDrawable(R.drawable.ic_add);
        }

        @Override
        public String getToolbarTitle(int position) {
            return mListDataHeader.get(position).getCategoryName();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
