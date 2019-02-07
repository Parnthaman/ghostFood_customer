package com.ghostFood.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.model.Coupons;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.ghostFood.R;
import com.ghostFood.adapter.CouponsAdapter;
import com.ghostFood.api.CouponsAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Coupon extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.gvCoupons)
    GridView gvCoupons;
    Unbinder unbinder;
    @BindView(R.id.tvLoginCouponMessage)
    TextView tvLoginCouponMessage;
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    CouponsAdapter mCouponAdapter;
    ArrayList<Coupons> mCouponList;

    public Coupon() {
        // Required empty public constructor
    }

    public static Coupon newInstance(String param1, String param2) {
        Coupon fragment = new Coupon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        unbinder = ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
        ivAddNewAddress.setVisibility(View.INVISIBLE);

        tv_nodata.setText(Constants.no_data_found);
        tvLoginCouponMessage.setText(Constants.login_to_view_more_coupons);

        tvLoginCouponMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.getInstance().LoginAct(getActivity());
            }
        });

        return view;
    }

    private void callCouponApi() {
        mCouponList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(getActivity());
        CouponsAPI mCouponsAPI = ApiConfiguration.getInstance2().getApiBuilder().create(CouponsAPI.class);
        final Call<CouponsAPI.ResponseCoupons> call = mCouponsAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<CouponsAPI.ResponseCoupons>() {
            @Override
            public void onResponse(Call<CouponsAPI.ResponseCoupons> call, Response<CouponsAPI.ResponseCoupons> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getData().size()  > 0) {
                            tv_nodata.setVisibility(View.GONE);
                            gvCoupons.setVisibility(View.VISIBLE);
                            for (CouponsAPI.Data data : response.body().getData()) {
                                Coupons coupon = new Coupons();
                                coupon.setCouponKey(data.getCouponKey());
                                coupon.setCouponCode(data.getCouponCode());
                                coupon.setCouponType(data.getCouponType());
                                coupon.setCouponImage(data.getCouponImage());
                                coupon.setCouponName(data.getCouponName());
                                coupon.setCouponDescription(data.getCouponDescription());
                                coupon.setCouponPrice(data.getCouponValue());
                                coupon.setUserScope(data.getCouponScope());
                                coupon.setBranches(data.getBranches());
                                coupon.setCouponStartDate(data.getCouponStartsAt());
                                coupon.setCouponEndDate(data.getCouponEndsAt());
                                mCouponList.add(coupon);
                            }

                            mCouponAdapter = new CouponsAdapter(getActivity(), mCouponList);
                            gvCoupons.setAdapter(mCouponAdapter);
                            gvCoupons.setSmoothScrollbarEnabled(false);
                            CustomProgressDialog.getInstance().dismiss();

                        }
                        else {
                            CustomProgressDialog.getInstance().dismiss();

                            tv_nodata.setVisibility(View.VISIBLE);
                            gvCoupons.setVisibility(View.GONE);
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
            public void onFailure(Call<CouponsAPI.ResponseCoupons> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.toString());
            }
        });

    }

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

    @Override
    public void onResume() {
        super.onResume();

        if(SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            tvLoginCouponMessage.setVisibility(View.VISIBLE);
        }
        callCouponApi();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
