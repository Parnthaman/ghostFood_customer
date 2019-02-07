package com.ghostFood.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostFood.model.OrderItems;
import com.ghostFood.model.OrdersList;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.SessionManager;
import com.ghostFood.R;
import com.ghostFood.adapter.OrdersAdapter;
import com.ghostFood.api.OrdersAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Orders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Orders extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lvOrders)
    ListView lvOrders;
    @BindView(R.id.txt_no_data_found)
    TextView txt_no_data_found;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<OrdersList> mOrdersList;
    OrdersList mOrders;
    ArrayList<OrderItems> orderItems;
    OrdersAdapter mOrderAdapter;

    public Orders() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Orders newInstance(String param1, String param2) {
        Orders fragment = new Orders();
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
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
        ivAddNewAddress.setVisibility(View.INVISIBLE);


        callOrdersApi();


        return view;
    }

    private void callOrdersApi() {
        mOrdersList = new ArrayList<>();
        CustomProgressDialog.getInstance().show(getActivity());
        OrdersAPI mOrdersAPI = ApiConfiguration.getInstance2().getApiBuilder().create(OrdersAPI.class);
        final Call<OrdersAPI.ResponseOrders> call = mOrdersAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<OrdersAPI.ResponseOrders>() {
            @Override
            public void onResponse(Call<OrdersAPI.ResponseOrders> call, Response<OrdersAPI.ResponseOrders> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        for (OrdersAPI.Data data : response.body().getData()) {
                            mOrders = new OrdersList();
                            mOrders.setOrderKey(data.getOrderKey());
                            mOrders.setOrderNumber(data.getOrderNumber());
                            mOrders.setStatus(ConstantsInternal.OrderStatus.fromInteger(data.getOrderStatus()).toString());
                            mOrders.setOrderDate(data.getOrderDateTime());
                            mOrders.setOrderAmount(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getOrderTotal()));
                            //mOrders.setDeliveryFee("SAR 5" + count);
                            //mOrders.setOrderTime("08:30 AM");
                            mOrders.setSubTotal(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getOrderTotal()));
                            mOrders.setTotal(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(data.getOrderTotal()));
                            orderItems = new ArrayList<>();
                            mOrders.setOrderItems(orderItems);
                            mOrdersList.add(mOrders);
                        }

                        if (mOrdersList.size() > 0){
                            txt_no_data_found.setVisibility(View.GONE);
                            lvOrders.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_no_data_found.setVisibility(View.VISIBLE);
                            lvOrders.setVisibility(View.GONE);
                        }
                        mOrderAdapter=new OrdersAdapter(getActivity(),mOrdersList);
                        lvOrders.setAdapter(mOrderAdapter);
//                        lvOrders.setSmoothScrollbarEnabled(true);

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
            public void onFailure(Call<OrdersAPI.ResponseOrders> call, Throwable t) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
