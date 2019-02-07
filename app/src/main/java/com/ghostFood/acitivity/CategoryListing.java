package com.ghostFood.acitivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.adapter.CategoryGridAdapter;
import com.ghostFood.adapter.ItemListAdapter;
import com.ghostFood.api.CategoryAPI;
import com.ghostFood.model.Category;
import com.ghostFood.model.Item;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListing extends AppCompatActivity {


    ArrayList<Category> listDataHeader;
    HashMap<Category, List<Item>> listDataChild;
    ItemListAdapter listAdapter;
    CategoryGridAdapter mCategoryGridAdapter;
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
    @BindView(R.id.ex_item_listing)
    ExpandableListView exItemListing;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    public static Activity mItemListingActivity;
    @BindView(R.id.gvCategory)
    GridView gvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_listing);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(CategoryListing.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(CategoryListing.this, toolbar);

        tlbarText.setText(Constants.Category);
        flCart.setVisibility(View.VISIBLE);
        tlbarCart.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);

        mItemListingActivity = this;

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            tlbarCart.setImageResource(R.drawable.ic_cart_ar);
        } else {
            tlbarCart.setImageResource(R.drawable.ic_cart);
        }

        FontFunctions.getInstance().FontBalooBhaijaan(CategoryListing.this, tlbarText);

       /* LayoutInflater myinflater = getLayoutInflater();
        ViewGroup cartHeader = (ViewGroup) myinflater.inflate(R.layout.header_menu, gvCategory, false);
        gvCategory.addHeaderView(cartHeader, null, false);*/

        callCategoryApi();
    }

    @Override
    protected void onResume() {
        super.onResume();

        CommonFunctions.getInstance().ShowCartCount(tvCartCount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        MyActivity.getInstance().LoadHome(CategoryListing.this);
        finish();
    }

    private void drawExpandableList() {

        exItemListing.setGroupIndicator(null);
        exItemListing.setChildIndicator(null);
        exItemListing.setChildDivider(getResources().getDrawable(android.R.color.transparent));
        exItemListing.setDivider(getResources().getDrawable(android.R.color.transparent));
        exItemListing.setDividerHeight(0);

        listAdapter = new ItemListAdapter(this, listDataHeader, listDataChild, exItemListing);
        exItemListing.setAdapter(listAdapter);

        exItemListing.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });

        exItemListing.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {


            }
        });

        exItemListing.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        exItemListing.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                ItemListAdapter.ItemHolder holder = ((ItemListAdapter.ItemHolder) v.getTag());
                Bundle bundle = new Bundle();
                bundle.putString("item_key", holder.tvItemName.getTag() != null ? holder.tvItemName.getTag().toString() : "");
                MyActivity.getInstance().ItemDetails(CategoryListing.this, bundle);
                return false;
            }
        });
    }

    private void callCategoryApi() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        CustomProgressDialog.getInstance().show(CategoryListing.this);
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
                        CustomProgressDialog.getInstance().dismiss();

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

                        //drawExpandableList();

                        LoadGrid();


                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(CategoryListing.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().FinishActivityWithDelay(CategoryListing.this);
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(CategoryListing.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().FinishActivityWithDelay(CategoryListing.this);
                }

            }

            @Override
            public void onFailure(Call<CategoryAPI.ResponseCategory> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(CategoryListing.this, Constants.NoInternetConnection);
                CommonFunctions.getInstance().FinishActivityWithDelay(CategoryListing.this);
            }
        });

    }

    private void LoadGrid() {

        mCategoryGridAdapter = new CategoryGridAdapter(CategoryListing.this, listDataHeader, listDataChild);
        gvCategory.setAdapter(mCategoryGridAdapter);
        if (CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().dismiss();
        }

    }

    @OnClick({R.id.tlbar_back, R.id.tlbar_cart, R.id.flCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
//                finish();
//                MyActivity.getInstance().LoadHome(CategoryListing.this);
                finish();
                break;
            case R.id.tlbar_cart:
                Bundle mBundle = new Bundle();
                MyActivity.getInstance().Cart(CategoryListing.this, mBundle);
                break;
            case R.id.flCart:
                Bundle mBundle1 = new Bundle();
                MyActivity.getInstance().Cart(CategoryListing.this, mBundle1);
                break;
        }
    }
}
