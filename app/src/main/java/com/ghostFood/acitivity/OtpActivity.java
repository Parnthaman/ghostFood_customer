package com.ghostFood.acitivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ghostFood.R;
import com.ghostFood.api.OtpApi;
import com.ghostFood.api.ResentOtpApi;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    @BindView(R.id.tvFirst)
    EditText tvFirst;
    @BindView(R.id.tvSecond)
    EditText tvSecond;
    @BindView(R.id.tvThird)
    EditText tvThird;
    @BindView(R.id.tvFourth)
    EditText tvFourth;

    String mobileNumber = "";
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvResent)
    TextView tvResent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);

//        tvFirst.setPaintFlags(tvFirst.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        tvSecond.setPaintFlags(tvSecond.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        tvFourth.setPaintFlags(tvFourth.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        tvThird.setPaintFlags(tvThird.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("mobileNumber") != null) {
            mobileNumber = bundle.getString("mobileNumber");
        } else {

        }

        tvFirst.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvFirst.getText().toString().length() == 1)     //size as per your requirement
                {
                    tvSecond.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        tvSecond.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvSecond.getText().toString().length() == 1)     //size as per your requirement
                {
                    tvThird.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        tvThird.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvThird.getText().toString().length() == 1)     //size as per your requirement
                {
                    tvFourth.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        tvFourth.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvFirst.getText().toString().length() == 1 && tvSecond.getText().toString().length() == 1 &&
                        tvThird.getText().toString().length() == 1 && tvFourth.getText().toString().length() == 1)     //size as per your requirement
                {
                    String otp = tvFirst.getText().toString() + tvSecond.getText().toString() + tvThird.getText().toString() + tvFourth.getText().toString();
                    CustomProgressDialog.getInstance().show(OtpActivity.this);
                    OtpApi otpApi = ApiConfiguration.getInstance2().getApiBuilder().create(OtpApi.class);
                    Call<OtpApi.OtpApiResponse> otpApiResponse = otpApi.getOtp(mobileNumber, otp);

                    otpApiResponse.enqueue(new Callback<OtpApi.OtpApiResponse>() {
                        @Override
                        public void onResponse(Call<OtpApi.OtpApiResponse> call, Response<OtpApi.OtpApiResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    CustomProgressDialog.getInstance().dismiss();
                                    if (response.body().getData() != null) {
                                        finish();
                                        Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.body().getMessage());
                                    CustomProgressDialog.getInstance().dismiss();
                                }
                            } else {
                                CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.message());
                                CustomProgressDialog.getInstance().dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<OtpApi.OtpApiResponse> call, Throwable t) {
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, t.toString());
                        }
                    });
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressDialog.getInstance().show(OtpActivity.this);
                if (tvFirst.getText().toString().length() == 1 && tvSecond.getText().toString().length() == 1 &&
                        tvThird.getText().toString().length() == 1 && tvFourth.getText().toString().length() == 1)     //size as per your requirement
                {
                    String otp = tvFirst.getText().toString() + tvSecond.getText().toString() + tvThird.getText().toString() + tvFourth.getText().toString();
                    CustomProgressDialog.getInstance().show(OtpActivity.this);
                    OtpApi otpApi = ApiConfiguration.getInstance2().getApiBuilder().create(OtpApi.class);
                    Call<OtpApi.OtpApiResponse> otpApiResponse = otpApi.getOtp(mobileNumber, otp);

                    otpApiResponse.enqueue(new Callback<OtpApi.OtpApiResponse>() {
                        @Override
                        public void onResponse(Call<OtpApi.OtpApiResponse> call, Response<OtpApi.OtpApiResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    finish();
                                    CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.body().getMessage());
                                    CustomProgressDialog.getInstance().dismiss();

                                } else {
                                    CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.body().getMessage());
                                    CustomProgressDialog.getInstance().dismiss();
                                }
                            } else {
                                CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.message());
                                CustomProgressDialog.getInstance().dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<OtpApi.OtpApiResponse> call, Throwable t) {
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, t.toString());
                        }
                    });
                }
            }
        });

        tvResent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = tvFirst.getText().toString() + tvSecond.getText().toString() + tvThird.getText().toString() + tvFourth.getText().toString();
                CustomProgressDialog.getInstance().show(OtpActivity.this);
                ResentOtpApi otpApi = ApiConfiguration.getInstance2().getApiBuilder().create(ResentOtpApi.class);
                Call<ResentOtpApi.ResentOtpApiResponse> otpApiResponse = otpApi.getOtp(mobileNumber);

                otpApiResponse.enqueue(new Callback<ResentOtpApi.ResentOtpApiResponse>() {
                    @Override
                    public void onResponse(Call<ResentOtpApi.ResentOtpApiResponse> call, Response<ResentOtpApi.ResentOtpApiResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                CustomProgressDialog.getInstance().dismiss();
                                if (response.body().getData() != null) {
                                    CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.body().getMessage());
                                    if (CustomProgressDialog.getInstance().isShowing()) {
                                        CustomProgressDialog.getInstance().dismiss();
                                    }
                                    tvFirst.setText("");
                                    tvSecond.setText("");
                                    tvThird.setText("");
                                    tvFourth.setText("");
                                }

                            } else {
                                CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.body().getMessage());
                                CustomProgressDialog.getInstance().dismiss();
                            }
                        } else {
                            CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, response.message());
                            CustomProgressDialog.getInstance().dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResentOtpApi.ResentOtpApiResponse> call, Throwable t) {
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().ShowSnackBar(OtpActivity.this, t.toString());
                    }
                });

            }
        });

    }
}
