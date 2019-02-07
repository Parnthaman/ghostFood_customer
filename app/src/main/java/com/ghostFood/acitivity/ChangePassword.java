package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.ChangePasswordAPI;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {


    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_loginLogo)
    ImageView ivLoginLogo;
    @BindView(R.id.tvChangePassword)
    TextView tvChangePassword;
    @BindView(R.id.txtOldPassword)
    TextView txtOldPassword;
    @BindView(R.id.edOldPassword)
    EditText edOldPassword;
    @BindView(R.id.llOldPassword)
    LinearLayout llOldPassword;
    @BindView(R.id.txtNewPassword)
    TextView txtNewPassword;
    @BindView(R.id.edNewPassword)
    EditText edNewPassword;
    @BindView(R.id.llNewPassword)
    LinearLayout llNewPassword;
    @BindView(R.id.txtConfirmPassword)
    TextView txtConfirmPassword;
    @BindView(R.id.edConfirmPassword)
    EditText edConfirmPassword;
    @BindView(R.id.llConfirmPassword)
    LinearLayout llConfirmPassword;
    @BindView(R.id.tvUpdate)
    TextView tvUpdate;
    @BindView(R.id.rly_UpdatePassword)
    LinearLayout rlyUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        //      CommonFunctions.getInstance().ChangeDirection(ChangePassword.this);
//        CommonFunctions.getInstance().ChangeFontActivity(ChangePassword.this);
        //    CommonFunctions.getInstance().ChangeToolbarLanguage(ChangePassword.this, toolbar);
        initViews();

    }

    public void initViews() {

        tvChangePassword.setText(Constants.ChangePassword);
        tlbarText.setText("");
        tlbarText.setVisibility(View.VISIBLE);
        tlbarSave.setVisibility(View.GONE);
        tvUpdate.setText(Constants.Update);

        txtOldPassword.setText(Constants.OldPassword);
        txtNewPassword.setText(Constants.NewPassword);
        txtConfirmPassword.setText(Constants.ConfirmPassword);


        FontFunctions.getInstance().FontSketchBold(ChangePassword.this, tvChangePassword);
        FontFunctions.getInstance().FontLatoFont(ChangePassword.this, edOldPassword);
        FontFunctions.getInstance().FontLatoFont(ChangePassword.this, edNewPassword);
        FontFunctions.getInstance().FontLatoFont(ChangePassword.this, edConfirmPassword);

        FontFunctions.getInstance().FontKGSecondSketch(ChangePassword.this, tvUpdate);
    }


    private void ChangePassword() {
        if (edOldPassword.getText().toString().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, Constants.OldPasswordShouldNotBeEmpty);
        } else if (edNewPassword.getText().toString().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, Constants.NewPasswordShouldNotBeEmpty);
        } else if (edConfirmPassword.getText().toString().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, Constants.ConfirmPasswordShouldNotBeEmpty);
        } else if (edNewPassword.getText().toString().trim().equals(edConfirmPassword.getText().toString().trim())) {
            CustomProgressDialog.getInstance().show(ChangePassword.this);
            ChangePasswordAPI mChangePasswordAPI = ApiConfiguration.getInstance().getApiBuilder().create(ChangePasswordAPI.class);
            final Call<ChangePasswordAPI.ResponseChangePassword> call = mChangePasswordAPI.Update(
                    SessionManager.User.getInstance().getKey(),
                    edOldPassword.getText().toString().trim(),
                    edNewPassword.getText().toString().trim(),
                    edConfirmPassword.getText().toString().trim()
            );
            call.enqueue(new Callback<ChangePasswordAPI.ResponseChangePassword>() {
                @Override
                public void onResponse(Call<ChangePasswordAPI.ResponseChangePassword> call, Response<ChangePasswordAPI.ResponseChangePassword> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, response.body().getMessage());
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().FinishActivityWithDelay(ChangePassword.this);
                        } else {
                            CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, response.body().getMessage());
                            CustomProgressDialog.getInstance().dismiss();
                        }
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, response.message());
                        CustomProgressDialog.getInstance().dismiss();
                    }

                }

                @Override
                public void onFailure(Call<ChangePasswordAPI.ResponseChangePassword> call, Throwable t) {
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, Constants.NoInternetConnection);
                }
            });
        } else {
            CommonFunctions.getInstance().ShowSnackBar(ChangePassword.this, Constants.PasswordNotMatching);
        }
    }


    @OnClick({R.id.tlbar_back, R.id.tlbar_save, R.id.rly_UpdatePassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.rly_UpdatePassword:
                ChangePassword();
                break;
        }
    }


}
