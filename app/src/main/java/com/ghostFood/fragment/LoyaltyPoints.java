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
import android.widget.TextView;

import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.SessionManager;
import com.ghostFood.R;
import com.ghostFood.api.LoyaltyPointsAPI;

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
 * Use the {@link LoyaltyPoints#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoyaltyPoints extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tvYourLoyalityPoints)
    TextView tvYourLoyalityPoints;
    @BindView(R.id.tvPoints)
    TextView tvPoints;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoyaltyPoints() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoyaltyPoints.
     */
    // TODO: Rename and change types and number of parameters
    public static LoyaltyPoints newInstance(String param1, String param2) {
        LoyaltyPoints fragment = new LoyaltyPoints();
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
        View view = inflater.inflate(R.layout.fragment_loyality_points, container, false);
        unbinder = ButterKnife.bind(this, view);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
        ivAddNewAddress.setVisibility(View.INVISIBLE);



        tvYourLoyalityPoints.setText(Constants.YourLoyaltyPoints);
        FontFunctions.getInstance().FontCMMidNight(getActivity(),tvYourLoyalityPoints);
        FontFunctions.getInstance().FontBabeNeueBold(getActivity(),tvPoints);
        tvPoints.setText("---");

        callLoyalityPointsApi();

        return view;
    }

    private void callLoyalityPointsApi() {
        CustomProgressDialog.getInstance().show(getActivity());
        LoyaltyPointsAPI mLoyaltyPointsAPI = ApiConfiguration.getInstance2().getApiBuilder().create(LoyaltyPointsAPI.class);
        final Call<LoyaltyPointsAPI.ResponseLoyalityPoints> call = mLoyaltyPointsAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<LoyaltyPointsAPI.ResponseLoyalityPoints>() {
            @Override
            public void onResponse(Call<LoyaltyPointsAPI.ResponseLoyalityPoints> call, Response<LoyaltyPointsAPI.ResponseLoyalityPoints> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        tvPoints.setText(response.body().getData().getLoyaltyPoints());

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
            public void onFailure(Call<LoyaltyPointsAPI.ResponseLoyalityPoints> call, Throwable t) {
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
