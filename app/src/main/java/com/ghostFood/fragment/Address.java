package com.ghostFood.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.adapter.AddressAdapter;
import com.ghostFood.api.UserAddressListAPI;
import com.ghostFood.model.AddressList;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

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
 * {@link Address.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Address#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Address extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.lvAddress)
    ListView lvAddress;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.txt_no_data_found)
    TextView txt_no_data_found;
    Unbinder unbinder;
    public static int needToRefresh = 0;

    private OnFragmentInteractionListener mListener;

    ArrayList<AddressList> mAddressList;
    AddressList mAddress;
    AddressAdapter mAddressAdapter;
    private String from = "";

    public Address() {
        // Required empty public constructor
    }


    public static Address newInstance(String param1, String param2) {
        Address fragment = new Address();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().getString("from") != null) {
            from = getArguments().getString("from");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        unbinder = ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);


        TextView tlbarText = (TextView) toolbar.findViewById(R.id.tlbar_text);
        ImageView tlbarLogo = (ImageView) toolbar.findViewById(R.id.tlbar_logo);
        txt_no_data_found.setText(Constants.no_data_found);



        if (from.trim().equalsIgnoreCase("addressact")) {
            tlbarText.setText(Constants.SelectAddress);
        } else {
            ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
            ivAddNewAddress.setVisibility(View.VISIBLE);

            ivAddNewAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Bundle bundle = new Bundle();
                    MyActivity.getInstance().AddAddressMap(getActivity(), bundle);*/
                }
            });


        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                MyActivity.getInstance().AddAddressMap(getActivity(), bundle);
            }
        });

        if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
            tlbarText.setText(Constants.Login);
            tlbarText.setVisibility(View.GONE);
            tlbarLogo.setVisibility(View.GONE);
        } else {
            callAddressApi();
        }

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (from.trim().equalsIgnoreCase("addressact")) {
                    AddressList addressList = mAddressList.get(position);
                    String mAddress = addressList.getAddressContactName() + ", " +
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

                    getActivity().finish();
                }


            }
        });


        return view;
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
                            mAddress = new AddressList();
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
                            mAddress.setLatitude(data.getLatitude());
                            mAddress.setLongitude(data.getLongitude());
                            mAddressList.add(mAddress);
                        }

                        if (from.trim().equalsIgnoreCase("addressact")) {
                            mAddressAdapter = new AddressAdapter(getActivity(), mAddressList, ConstantsInternal.AddressAdapterType.Checkout);
                        } else {
                            mAddressAdapter = new AddressAdapter(getActivity(), mAddressList, ConstantsInternal.AddressAdapterType.AddressBook);
                        }


                        if (response.body().getData().size() > 0) {
                            txt_no_data_found.setVisibility(View.GONE);
                            lvAddress.setVisibility(View.VISIBLE);
                        } else {
                            txt_no_data_found.setVisibility(View.VISIBLE);
                            lvAddress.setVisibility(View.GONE);
                        }
                        lvAddress.setAdapter(mAddressAdapter);
                        lvAddress.setSmoothScrollbarEnabled(true);

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
    public void onResume() {
        super.onResume();
        if (needToRefresh == 1) {
            callAddressApi();
            needToRefresh = 0;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
