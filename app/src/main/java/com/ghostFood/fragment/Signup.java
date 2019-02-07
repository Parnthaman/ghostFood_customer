package com.ghostFood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ghostFood.R;
import com.ghostFood.acitivity.OtpActivity;
import com.ghostFood.api.OtpApi;
import com.ghostFood.api.RegistraionUserApi;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signup extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int mPosition;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_emailId)
    EditText edtEmailId;
    @BindView(R.id.edt_mobilenumber)
    EditText edtMobilenumber;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirmpassword)
    EditText edtConfirmpassword;
    @BindView(R.id.btn_signup)
    LinearLayout btnSignup;
    Unbinder unbinder;
    @BindView(R.id.txt_signup)
    TextView txtSignup;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.txtEmailid)
    TextView txtEmailid;
    @BindView(R.id.txtMobile)
    TextView txtMobile;
    @BindView(R.id.txtPassword)
    TextView txtPassword;
    @BindView(R.id.txt_CPassword)
    TextView txtCPassword;
    @BindView(R.id.txtFirstname)
    TextView txtFirstname;
    @BindView(R.id.edt_Firstname)
    EditText edtFirstname;
    @BindView(R.id.txtLastname)
    TextView txtLastname;
    @BindView(R.id.edt_Lastname)
    EditText edtLastname;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ConstantsInternal.RegistrationType registrationType = ConstantsInternal.RegistrationType.Normal;

    @SuppressLint("ValidFragment")
    public Signup() {

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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }

    private void initview() {

        txtUsername.setText(Constants.Username);
        txtEmailid.setText(Constants.Email);
        txtPassword.setText(Constants.Password);
        txtCPassword.setText(Constants.ConfirmPassword);
        txtMobile.setText(Constants.MobileNumber);
        txtSignup.setText(Constants.Signup);

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

    @OnClick({R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                CallRegisterApi();
                break;
        }
    }

    private void CallRegisterApi() {

//        if (edtUsername.getText().toString().isEmpty()) {
//            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.Username);
//            return;
//        }
        if (edtFirstname.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.FirstName);
            return;
        }
        if (edtLastname.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.LastName);
            return;
        }
        if (edtEmailId.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.Email);
            return;
        }
        if (!CommonFunctions.getInstance().isValidEmail(edtEmailId.getText().toString())) {
            CommonFunctions.getInstance().ValidField(getActivity(), Constants.Email);
            return;
        }
        if (edtMobilenumber.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.MobileNumber);
            return;
        }

        if (edtMobilenumber.getText().toString().length() < 7 && edtMobilenumber.getText().toString().length() > 16) {
            CommonFunctions.getInstance().ValidField(getActivity(), Constants.MobileNumber);
            return;
        }

        if (ConstantsInternal.RegistrationType.Normal.getValue() == registrationType.getValue()) {
            if (edtPassword.getText().toString().isEmpty()) {
                CommonFunctions.getInstance().EmptyField(getActivity(), Constants.Password);
                return;
            }

            if (edtPassword.getText().toString().length() < 6) {
                CommonFunctions.getInstance().ValidField(getActivity(), Constants.Password);
                return;
            }

            if (edtConfirmpassword.getText().toString().isEmpty()) {
                CommonFunctions.getInstance().EmptyField(getActivity(), Constants.ConfirmPassword);
                return;
            }
            if (edtConfirmpassword.getText().toString().length() < 6) {
                CommonFunctions.getInstance().ValidField(getActivity(), Constants.ConfirmPassword);
                return;
            }

            if (!edtPassword.getText().toString().equals(edtConfirmpassword.getText().toString())) {
                CommonFunctions.getInstance().PasswordMatch(getActivity(), Constants.Password, Constants.ConfirmPassword);
                return;
            }
        }


        CustomProgressDialog.getInstance().show(getActivity());
        RegistraionUserApi mRegistraionUserApi = ApiConfiguration.getInstance2().getApiBuilder().create(RegistraionUserApi.class);
        final Call<RegistraionUserApi.ResponseRegistraionUser> call = mRegistraionUserApi.Register(
                registrationType.getValue(),
                /*this.edtUsername.getText().toString()*/this.edtMobilenumber.getText().toString(),
                this.edtFirstname.getText().toString(),
                this.edtLastname.getText().toString(),
                this.edtEmailId.getText().toString(),
                this.edtPassword.getText().toString(),
                this.edtConfirmpassword.getText().toString(),
                this.edtMobilenumber.getText().toString(),
                this.edtFirstname.getText().toString(),
                this.edtLastname.getText().toString(),
                "",
                ConstantsInternal.DeviceTypeId,
                SessionManager.User.getInstance().getDeviceID(),
                "1",
                ConstantsInternal.DeviceTypeId
        );

        call.enqueue(new Callback<RegistraionUserApi.ResponseRegistraionUser>() {
            @Override
            public void onResponse(Call<RegistraionUserApi.ResponseRegistraionUser> call, Response<RegistraionUserApi.ResponseRegistraionUser> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();

//                        Intent i = new Intent(getActivity(), OtpActivity.class);
//                        i.putExtra("mobileNumber", edtMobilenumber.getText().toString());
//                        startActivity(i);

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
//
//                    Intent i = new Intent(getActivity(), OtpActivity.class);
//                    i.putExtra("mobileNumber", edtMobilenumber.getText().toString());
//                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<RegistraionUserApi.ResponseRegistraionUser> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.toString());
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
