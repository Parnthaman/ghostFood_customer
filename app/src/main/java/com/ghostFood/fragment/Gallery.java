package com.ghostFood.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.R;
import com.ghostFood.adapter.GalleryAdapter;
import com.ghostFood.api.GalleryApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gallery extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.gvGallery)
    GridView gvGallery;
    @BindView(R.id.txtview_nodata)
    TextView txtviewNodata;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Gallery() {
        // Required empty public constructor
    }

    public static Gallery newInstance(String param1, String param2) {
        Gallery fragment = new Gallery();
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
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        unbinder = ButterKnife.bind(this, view);

        GalleryApi();
        return view;
    }


    private void GalleryApi() {

        CustomProgressDialog.getInstance().show(getActivity());

        GalleryApi galleryApi = ApiConfiguration.getInstance2().getApiBuilder().create(GalleryApi.class);
        final Call<GalleryApi.GalleryApiResponse> call = galleryApi.Get();
        call.enqueue(new Callback<GalleryApi.GalleryApiResponse>() {
            @Override
            public void onResponse(Call<GalleryApi.GalleryApiResponse> call, Response<GalleryApi.GalleryApiResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();

                        List<GalleryApi.GalleryApiResponse.Datum> gallery_data = response.body().getData();

                        if (gallery_data.size() > 0){
                            txtviewNodata.setVisibility(View.GONE);
                            gvGallery.setVisibility(View.VISIBLE);

                            GalleryAdapter galleryAdapter = new GalleryAdapter(getActivity(),gallery_data);
                            gvGallery.setAdapter(galleryAdapter);
                        }
                        else {
                            txtviewNodata.setText(Constants.no_data_found);
                            FontFunctions.getInstance().FontArialNovaLight(getActivity(),txtviewNodata);

                            txtviewNodata.setVisibility(View.VISIBLE);
                            gvGallery.setVisibility(View.GONE);
                        }


                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
                }

            }

            @Override
            public void onFailure(Call<GalleryApi.GalleryApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.NoInternetConnection);
                CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
