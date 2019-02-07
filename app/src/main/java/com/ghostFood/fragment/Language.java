package com.ghostFood.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.ghostFood.R;
import com.ghostFood.api.LanguagesAPI;

import java.util.ArrayList;
import java.util.List;

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
 * Use the {@link Language#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Language extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_loginLogo)
    ImageView ivLoginLogo;


    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.tv_Language)
    TextView tvLanguage;
    @BindView(R.id.tvLanguageBox)
    LinearLayout tvLanguageBox;
    @BindView(R.id.tvSelectedLanguage)
    TextView tvSelectedLanguage;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Language() {
        // Required empty public constructor
    }

    public static Language newInstance(String param1, String param2) {
        Language fragment = new Language();
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
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        unbinder = ButterKnife.bind(this, view);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
        ivAddNewAddress.setVisibility(View.INVISIBLE);

        tvLanguage.setText(Constants.Language);
        btnSubmit.setText(Constants.Submit);
        FontFunctions.getInstance().FontBalooBhaijaan(getActivity(), tvLanguage);
        FontFunctions.getInstance().FontSketchBold(getActivity(), btnSubmit);


        callLanguagesApi();

        return view;
    }

    private void callLanguagesApi() {
        CustomProgressDialog.getInstance().show(getActivity());
        LanguagesAPI mLanguagesAPI = ApiConfiguration.getInstance2().getApiBuilder().create(LanguagesAPI.class);
        final Call<LanguagesAPI.ResponseLanguages> call = mLanguagesAPI.Get();
        call.enqueue(new Callback<LanguagesAPI.ResponseLanguages>() {
            @Override
            public void onResponse(Call<LanguagesAPI.ResponseLanguages> call, Response<LanguagesAPI.ResponseLanguages> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        final String[] languageNames = new String[response.body().getData().size()];
                        final List<LanguagesAPI.Data> mLanguages = new ArrayList<LanguagesAPI.Data>();
                        int count = 0;
                        for (LanguagesAPI.Data lang : response.body().getData()) {
                            mLanguages.add(lang);
                            languageNames[count] = lang.getLanguageName();

                            if(SessionManager.AppProperties.getInstance().getLanguageCode().equalsIgnoreCase(lang.getLanguageCode())) {
                                tvSelectedLanguage.setText(lang.getLanguageName());
                            }
                            count++;
                        }

                        tvLanguageBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setItems(languageNames, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tvSelectedLanguage.setText(mLanguages.get(which).getLanguageName());
                                        SessionManager.AppProperties.getInstance().setLanguageCode(mLanguages.get(which).getLanguageCode());
                                        SessionManager.AppProperties.getInstance().setAppDirection(ConstantsInternal.AppDirection.fromInteger(mLanguages.get(which).getIsRtl()));
                                        if (mLanguages.get(which).getLanguageCode().equals("en")){
                                            Constants.languageCode = "en";
                                        }
                                        else {
                                            Constants.languageCode = "es";
                                        }

                                        Constants.initViews(getActivity());
                                        dialog.dismiss();
                                        getActivity().finish();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("from", "language");
                                        MyActivity.getInstance().Home(getActivity());
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();


                            }
                        });
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
            public void onFailure(Call<LanguagesAPI.ResponseLanguages> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.toString());
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
