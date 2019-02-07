package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.TermsAndConditionsAPI;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TermsAndConditionAct extends AppCompatActivity {


    @BindView(R.id.tvCMSValue)
    TextView tvCMSValue;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_cond);
        ButterKnife.bind(this);

        tlbarText.setText(Constants.TermsAndCondition);
        callConfigApi();
    }

    private void callConfigApi() {
        CustomProgressDialog.getInstance().show(TermsAndConditionAct.this);
        TermsAndConditionsAPI mTermsAndConditionsAPI = ApiConfiguration.getInstance2().getApiBuilder().create(TermsAndConditionsAPI.class);
        final Call<TermsAndConditionsAPI.ResponseTAC> call = mTermsAndConditionsAPI.Get();
        call.enqueue(new Callback<TermsAndConditionsAPI.ResponseTAC>() {
            @Override
            public void onResponse(Call<TermsAndConditionsAPI.ResponseTAC> call, Response<TermsAndConditionsAPI.ResponseTAC> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        tvCMSValue.setText(CommonFunctions.getInstance().fromHtml(response.body().getData().getContent()));

                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().ShowSnackBar(TermsAndConditionAct.this, response.body().getMessage());
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(TermsAndConditionAct.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<TermsAndConditionsAPI.ResponseTAC> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(TermsAndConditionAct.this, Constants.NoInternetConnection);
            }
        });

    }

    @OnClick({R.id.tlbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
        }
    }
}
