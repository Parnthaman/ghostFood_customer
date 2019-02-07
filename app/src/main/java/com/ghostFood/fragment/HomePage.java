package com.ghostFood.fragment;

import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fujiyuu75.sequent.Sequent;
import com.ghostFood.R;
import com.ghostFood.adapter.AddressAdapter;
import com.ghostFood.adapter.CategoryGridAdapter;
import com.ghostFood.adapter.ViewPagerAdapter;
import com.ghostFood.api.CategoryAPI;
import com.ghostFood.api.OfferAPI;
import com.ghostFood.api.Spin2WinAPI;
import com.ghostFood.api.UserAddressListAPI;
import com.ghostFood.events.EBLogin;
import com.ghostFood.model.AddressList;
import com.ghostFood.model.Category;
import com.ghostFood.model.HomeOffer;
import com.ghostFood.model.Item;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.ghyeok.stickyswitch.widget.StickySwitch;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePage extends Fragment {
    EventBus myEventBus = new EventBus();

    @BindView(R.id.vp_offer)
    ViewPager vpOffer;
    Unbinder unbinder;
    @BindView(R.id.fl_offer)
    FrameLayout flOffer;
    @BindView(R.id.tvAddNew)
    TextView tvAddNew;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.btn_Coupon)
    Button btnCoupon;

    @BindView(R.id.btn_Locations)
    Button btnLocations;
    @BindView(R.id.ivLeftArrow)
    ImageView ivLeftArrow;
    @BindView(R.id.ivRightArrow)
    ImageView ivRightArrow;
    @BindView(R.id.bt_SelectYourAddress)
    Button btSelectYourAddress;
    @BindView(R.id.btn_playToEat)
    Button btnPlayToEat;
    @BindView(R.id.tvHeading)
    TextView tvHeading;
    @BindView(R.id.txt_delivery)
    TextView txt_delivery;
    @BindView(R.id.txt_pickup)
    TextView txt_pickup;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 6000;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.stickySwitch)
    StickySwitch stickySwitch;
    @BindView(R.id.card_view1)
    CardView cardView1;
    @BindView(R.id.card_view2)
    CardView cardView2;
    @BindView(R.id.tvDeliveryType)
    TextView tvDeliveryType;
    @BindView(R.id.llDeliveryType)
    LinearLayout llDeliveryType;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.gvCategory)
    GridView gvCategory;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<HomeOffer> mOfferList;
    ArrayList<AddressList> mAddressList = new ArrayList<>();
    HomeOffer mHomeOffer;
    ViewPagerAdapter adapter;
    private OnFragmentInteractionListener mListener;

    ArrayList<Category> listDataHeader;
    HashMap<Category, List<Item>> listDataChild;
    private CategoryGridAdapter mCategoryGridAdapter;
    private IntentIntegrator qrScan;

    public HomePage() {
        // Required empty public constructor
    }

    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEventBus.register(this);
        if (getArguments() != null) {
        }
    }

    private AnimatorSet showFrontAnim = new AnimatorSet();
    private AnimatorSet showBackAnim = new AnimatorSet();
    private boolean isShowingBack = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
        ivAddNewAddress.setVisibility(View.GONE);
        ImageView ivQR = (ImageView) toolbar.findViewById(R.id.ivQR);
        ivQR.setVisibility(View.VISIBLE);


        qrScan = new IntentIntegrator(getActivity());


        ivQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callQR();
            }
        });

        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                if (direction == StickySwitch.Direction.RIGHT) {

                    /*Animation out = AnimationUtils.makeOutAnimation(getActivity(), true);
                    cardView1.startAnimation(out);*/
                    cardView1.setVisibility(View.INVISIBLE);

                    Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
                    cardView2.startAnimation(in);
                    cardView2.setVisibility(View.VISIBLE);

                } else {
                    /*Animation out = AnimationUtils.makeOutAnimation(getActivity(), true);
                    cardView2.startAnimation(out);*/
                    cardView2.setVisibility(View.INVISIBLE);

                    Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
                    cardView1.startAnimation(in);
                    cardView1.setVisibility(View.VISIBLE);

                }
            }
        });
        initString();
        callCategoryApi();

        //callHomeOfferApi();


       /* if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            ivRightArrow.setImageResource(R.drawable.ic_vpleft);
            ivLeftArrow.setImageResource(R.drawable.ic_vpright);
        } else {
            ivLeftArrow.setImageResource(R.drawable.ic_vpleft);
            ivRightArrow.setImageResource(R.drawable.ic_vpright);
        }*/


        return view;
    }

    private void callQR() {
        qrScan.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("item_key", result.getContents());
                MyActivity.getInstance().ItemDetails(getActivity(), bundle);
//                Toast.makeText(getActivity(), "testing", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanning Failed", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void callCategoryApi() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        CustomProgressDialog.getInstance().show(getActivity());
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
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
                    CustomProgressDialog.getInstance().dismiss();
//                    CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
                }

            }

            @Override
            public void onFailure(Call<CategoryAPI.ResponseCategory> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.NoInternetConnection);
//                CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
            }
        });

    }

    private void LoadGrid() {

        mCategoryGridAdapter = new CategoryGridAdapter(getActivity(), listDataHeader, listDataChild);
        gvCategory.setAdapter(mCategoryGridAdapter);
    }

    private void initString() {

        PushDownAnim.setPushDownAnimTo(btnOrder, btnLocations)
                .setDurationPush(40)
                .setDurationRelease(100)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view == btnOrder) {
                            SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Delivery.getValue());
//                            SessionManager.Cart.getInstance().setAddress("");
                            Bundle bundle = new Bundle();
//                            MyActivity.getInstance().ItemListing(getActivity(), bundle);
                            MyActivity.getInstance().CategoryListing(getActivity(), bundle);
                        } else if (view == btnLocations) {
                            SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Pickup.getValue());
                            SessionManager.Cart.getInstance().setBranchKey("");
                            SessionManager.Cart.getInstance().setAddressKey("");

                            MyActivity.getInstance().PickupLocations(getActivity());
                        }

                    }

                });


        FontFunctions.getInstance().FontLatoFont(getActivity(), btSelectYourAddress);


        Sequent.origin(llDeliveryType).duration(500).anim(getContext(), R.anim.overshoot).start();


        btSelectYourAddress.setText(Constants.PleaseSelectYourAddress);
        btnOrder.setText(Constants.HomeOrder);
        btnCoupon.setText(Constants.Coupons);
        btnLocations.setText(Constants.PickUp);
        btnPlayToEat.setText(Constants.PlayToEat);
        txt_delivery.setText(Constants.delivery);
        txt_pickup.setText(Constants.pickup);
        tvDescription.setText(Constants.AppDescription);
        tvDeliveryType.setText(Constants.DeliveryTypeDescription);
        // tvAddNew.setText(Constants.AddNew);
        tvHeading.setText(Constants.WhatsHot);
//        SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Delivery.getValue());


    }

    private void callHomeOfferApi() {
        mOfferList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(getActivity());
        OfferAPI mOfferAPI = ApiConfiguration.getInstance2().getApiBuilder().create(OfferAPI.class);
        final Call<OfferAPI.ResponseOffer> call = mOfferAPI.Get(ConstantsInternal.OfferType.SpecialOffer.getValue());
        call.enqueue(new Callback<OfferAPI.ResponseOffer>() {
            @Override
            public void onResponse(Call<OfferAPI.ResponseOffer> call, Response<OfferAPI.ResponseOffer> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        mOfferList = new ArrayList<>();
                        for (OfferAPI.Data data : response.body().getData()) {
                            mHomeOffer = new HomeOffer();
                            mHomeOffer.setOfferType(ConstantsInternal.OfferType.SpecialOffer);
                            mHomeOffer.setKey(data.getOfferKey());
                            mHomeOffer.setHeading(data.getOfferTitle());
                            mHomeOffer.setItemImage(data.getOfferItemImage());
                            mHomeOffer.setItemPrice(data.getOfferPrice());
                            mHomeOffer.setOfferText(data.getOfferText1());
                            mHomeOffer.setOfferImage(data.getOfferImage());
                            mHomeOffer.setItemName(data.getOfferDescription());
                            mOfferList.add(mHomeOffer);
                        }

                        if (mOfferList.size() > 0) {
                            adapter = new ViewPagerAdapter(getActivity(), mOfferList);
                            vpOffer.setAdapter(adapter);
                        } else {
                            vpOffer.setAdapter(null);
                        }
                        indicator.setViewPager(vpOffer);
                        CustomProgressDialog.getInstance().dismiss();

                        if (mOfferList.size() > 1) {
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (vpOffer != null) {
                                        if (currentPage == mOfferList.size()) {
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

                        if (mOfferList.size() > 0) {

                            ivLeftArrow.setVisibility(View.INVISIBLE);
                            ivRightArrow.setVisibility(View.INVISIBLE);
                            if (mOfferList.size() > 1) {
                                ivRightArrow.setVisibility(View.VISIBLE);
                            }

                            vpOffer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    System.out.println("One");
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    System.out.println("One");
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                    ivLeftArrow.setVisibility(View.VISIBLE);
                                    ivRightArrow.setVisibility(View.VISIBLE);
                                    if (vpOffer.getCurrentItem() == 0) {
                                        ivLeftArrow.setVisibility(View.INVISIBLE);
                                    }
                                    if (vpOffer.getCurrentItem() == mOfferList.size() - 1) {
                                        ivRightArrow.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                            ivLeftArrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int currentPosition = vpOffer.getCurrentItem();
                                    if (currentPosition > 0) {
                                        currentPosition--;
                                        vpOffer.setCurrentItem(currentPosition);
                                    } else if (currentPosition == 0) {
                                        vpOffer.setCurrentItem(currentPosition);
                                    }
                                }
                            });

                            // Images right navigatin
                            ivRightArrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int currentPosition = vpOffer.getCurrentItem();
                                    currentPosition++;
                                    vpOffer.setCurrentItem(currentPosition);
                                }
                            });


                        }
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<OfferAPI.ResponseOffer> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.getMessage());
            }
        });

    }

    private void ShowMAddressDialog() {

        if (mAddressList.size() <= 0) {
            CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.PleaseAddAddress);
            return;
        }
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_address);
        dialog.setCancelable(false);
        ListView lvAddress = (ListView) dialog.findViewById(R.id.lvAddress);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        AddressAdapter mAddressDialogAdapter = new AddressAdapter(getActivity(), mAddressList, ConstantsInternal.AddressAdapterType.ChooseAddressDialog);
        lvAddress.setAdapter(mAddressDialogAdapter);

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btSelectYourAddress.setText(mAddressList.get(position).getAddressLine1() + "," + mAddressList.get(position).getAddressLine2() + "," + mAddressList.get(position).getAddressArea());
                btSelectYourAddress.setTag(mAddressList.get(position));

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void callAddressApi() {
        mAddressList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(getActivity());
        UserAddressListAPI mUserAddressListAPI = ApiConfiguration.getInstance2().getApiBuilder().create(UserAddressListAPI.class);
        final Call<UserAddressListAPI.ResponseAddress> call = mUserAddressListAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<UserAddressListAPI.ResponseAddress>() {
            @Override
            public void onResponse(Call<UserAddressListAPI.ResponseAddress> call, Response<UserAddressListAPI.ResponseAddress> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        for (UserAddressListAPI.Data data : response.body().getData()) {
                            AddressList mAddress = new AddressList();

                            mAddress.setLatitude(data.getLatitude());
                            mAddress.setLongitude(data.getLongitude());
                            mAddress.setAddressKey(data.getAddressKey());
                            mAddress.setAddressContactName(data.getAddressContactName());
                            mAddress.setBuildingName(data.getBuildingName());
                            mAddress.setFlatNo(data.getFlatNo());
                            mAddress.setAddressLine1(data.getAddressLine1());
                            mAddress.setAddressLine2(data.getAddressLine2());
                            mAddress.setAddressArea(data.getAddressArea());
                            mAddress.setAddressCity(data.getAddressCity());
                            mAddress.setAddressCountry(data.getAddressCountry());
                            mAddress.setAddressZipCode(data.getAddressZipCode());
                            mAddressList.add(mAddress);
                        }

                        ShowMAddressDialog();

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserAddressListAPI.ResponseAddress> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.NoInternetConnection);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_order, R.id.btn_Coupon, R.id.btn_playToEat, R.id.btn_Locations, R.id.bt_SelectYourAddress, R.id.tvAddNew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_playToEat:

                if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                    MyActivity.getInstance().LoginAct(getActivity());
                } else {
                    LoadSpin2WinJson();
                }
                break;
            case R.id.btn_order:
                /*if (this.btSelectYourAddress.getTag() == null) {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.PleaseSelectAnAddressToContinue);
                    return;
                }

                AddressList addressList = (AddressList) btSelectYourAddress.getTag();
                String mAddress = addressList.getAddressContactName() + ", " +
                        addressList.getBuildingName() + ", " +
                        addressList.getFlatNo() + ", " +
                        addressList.getAddressLine1() + ", " +
                        addressList.getAddressLine2() + ", " +
                        addressList.getAddressArea() + ", " +
                        addressList.getAddressCity() + ", " +
                        addressList.getAddressCountry() + " - " + addressList.getAddressZipCode() + " ";

                SessionManager.Cart.getInstance().setAddress(mAddress);
                SessionManager.Cart.getInstance().setBranchKey("");
                SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Delivery.getValue());
                SessionManager.Cart.getInstance().setAddressKey(addressList.getAddressKey());

                SessionManager.Cart.getInstance().setLatitude(addressList.getLatitude() != null ? addressList.getLatitude().toString() : "0.0");
                SessionManager.Cart.getInstance().setLongitude(addressList.getLongitude() != null ? addressList.getLongitude().toString() : "0.0");
*/
                SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Delivery.getValue());
//                SessionManager.Cart.getInstance().setAddress("");
                Bundle bundle = new Bundle();
//                MyActivity.getInstance().ItemListing(getActivity(), bundle);
                MyActivity.getInstance().CategoryListing(getActivity(), bundle);
                break;
            case R.id.btn_Coupon:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Coupon()).commit();
                break;
            case R.id.btn_Locations: {
                SessionManager.Cart.getInstance().setOrderType(ConstantsInternal.OrderType.Pickup.getValue());
                SessionManager.Cart.getInstance().setBranchKey("");
                SessionManager.Cart.getInstance().setAddressKey("");

                MyActivity.getInstance().PickupLocations(getActivity());
            }
            break;
            case R.id.bt_SelectYourAddress:
                if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                    MyActivity.getInstance().LoginAct(getActivity());
                } else {
                    //MyActivity.getInstance().AddressAct(getActivity());
                    callAddressApi();
                }
                break;
            case R.id.tvAddNew:
                if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                    MyActivity.getInstance().LoginAct(getActivity());
                } else {
                    Bundle myBundle = new Bundle();
                    MyActivity.getInstance().AddAddressMap(getActivity(), myBundle);
                }
                break;
        }
    }

    private void LoadSpin2WinJson() {

        //Bundle bundlePlay = new Bundle();
        //MyActivity.getInstance().PlayToEat(getActivity(), bundlePlay);

        CustomProgressDialog.getInstance().show(getActivity());
        Spin2WinAPI mSpin2WinAPI = ApiConfiguration.getInstance2().getApiBuilder().create(Spin2WinAPI.class);
        final Call<Spin2WinAPI.ResponseSpin2Win> call = mSpin2WinAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<Spin2WinAPI.ResponseSpin2Win>() {
            //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<Spin2WinAPI.ResponseSpin2Win> call, Response<Spin2WinAPI.ResponseSpin2Win> response) {

                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        //Gson gson = new GsonBuilder().create();
                        //SessionManager.Spin2Win.getInstance().setJson(gson.toJson(response.body().getData()));

                        Bundle bundlePlay = new Bundle();
                        MyActivity.getInstance().PlayToEat(getActivity(), bundlePlay);

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
                }
            }

            @Override
            public void onFailure(Call<Spin2WinAPI.ResponseSpin2Win> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.toString());
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myEventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBLogin event) {
        callAddressApi();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
