package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.ForgotPasswordApi;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {


    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_loginLogo)
    ImageView ivLoginLogo;
    @BindView(R.id.tvForgetPasswordTxt)
    TextView tvForgetPasswordTxt;
    @BindView(R.id.txt_Emailid)
    TextView txtEmailid;
    @BindView(R.id.edt_emailId)
    EditText edtEmailId;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.rly_forgetPassword)
    LinearLayout rlyForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        CommonFunctions.getInstance().ChangeDirection(ForgotPassword.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(ForgotPassword.this, toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initViews();


    }

    public void initViews() {

        tvForgetPasswordTxt.setHint(Constants.ForgotPassword);
        tlbarText.setText(Constants.ForgotPassword);
        tlbarText.setVisibility(View.GONE);
        tvForgetPassword.setText(Constants.ForgotPassword);
        txtEmailid.setText(Constants.Email);
        //    btnSubmit.setText(Constants.Submit);

        FontFunctions.getInstance().FontBalooBhaijaan(ForgotPassword.this, tlbarText);
        FontFunctions.getInstance().FontSketchBold(ForgotPassword.this, tvForgetPassword);
        FontFunctions.getInstance().FontKGSecondSketch(ForgotPassword.this, tvForgetPassword);


    }

    private void callForgotPasswordAPI() {

        if (edtEmailId.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(ForgotPassword.this, Constants.Email);
            return;
        } else {

            CustomProgressDialog.getInstance().show(ForgotPassword.this);
            ForgotPasswordApi mForgotPasswordApi = ApiConfiguration.getInstance().getApiBuilder().create(ForgotPasswordApi.class);
            final Call<ForgotPasswordApi.ResponseForgotPassword> call = mForgotPasswordApi.Get(edtEmailId.getText().toString());
            call.enqueue(new Callback<ForgotPasswordApi.ResponseForgotPassword>() {
                @Override
                public void onResponse(Call<ForgotPasswordApi.ResponseForgotPassword> call, Response<ForgotPasswordApi.ResponseForgotPassword> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            CommonFunctions.getInstance().ShowSnackBar(ForgotPassword.this, response.body().getMessage());
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().FinishActivityWithDelay(ForgotPassword.this);
                        } else {
                            CommonFunctions.getInstance().ShowSnackBar(ForgotPassword.this, response.body().getMessage());
                            CustomProgressDialog.getInstance().dismiss();
                        }
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ForgotPassword.this, response.message());
                        CustomProgressDialog.getInstance().dismiss();
                    }

                }

                @Override
                public void onFailure(Call<ForgotPasswordApi.ResponseForgotPassword> call, Throwable t) {
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().ShowSnackBar(ForgotPassword.this, Constants.NoInternetConnection);
                }
            });
        }
    }

    @OnClick({R.id.rly_forgetPassword, R.id.tlbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rly_forgetPassword:
                callForgotPasswordAPI();
                break;
            case R.id.tlbar_back:
                finish();
                break;
        }
    }

    @OnClick(R.id.rly_forgetPassword)
    public void onViewClicked() {
    }
}
