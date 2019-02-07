package com.ghostFood.acitivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.JavaScriptInterface;
import com.ghostFood.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayToEat extends AppCompatActivity {

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
    @BindView(R.id.wvPlayToEat)
    WebView wvPlayToEat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_to_eat);
        ButterKnife.bind(this);

        tlbarText.setText(Constants.PlayToEat);
        FontFunctions.getInstance().FontBabeNeueBold(PlayToEat.this, tlbarText);

        String BASE_URL = ApiConfiguration.BASE_URL_STAGING;
        if(SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Dev) {
            BASE_URL = ApiConfiguration.BASE_URL_DEV;
        } else if(SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Prod) {
            BASE_URL = ApiConfiguration.BASE_URL_PROD;
        }


        wvPlayToEat.clearCache(true);
        wvPlayToEat.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wvPlayToEat.getSettings().setJavaScriptEnabled(true);
        String postData = "user_key=" + SessionManager.User.getInstance().getKey();
        wvPlayToEat.postUrl(BASE_URL + "api/spin-2-win", postData.getBytes());
        wvPlayToEat.setBackgroundColor(Color.TRANSPARENT);
        wvPlayToEat.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        //wvPlayToEat.loadUrl(BASE_URL + "api/spin-2-win");
        wvPlayToEat.addJavascriptInterface(new JavaScriptInterface(PlayToEat.this), "Android");
        /*swipeContainer.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        wvPlayToEat.reload();
                        swipeContainer.setRefreshing(false);
                    }
                }
        );*/

    }

    @OnClick({R.id.tlbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
        }
    }

    /*private void LoadSpin2WinJson() {
        CustomProgressDialog.getInstance().show(PlayToEat.this);
        Spin2WinAPI mSpin2WinAPI = ApiConfiguration.getInstance2().getApiBuilder().create(Spin2WinAPI.class);
        final Call<Spin2WinAPI.ResponseSpin2Win> call = mSpin2WinAPI.Get(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<Spin2WinAPI.ResponseSpin2Win>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<Spin2WinAPI.ResponseSpin2Win> call, Response<Spin2WinAPI.ResponseSpin2Win> response) {

                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        Gson gson = new GsonBuilder().create();
                        SessionManager.Spin2Win.getInstance().setJson(gson.toJson( response.body().getData()));

                        wvPlayToEat.clearCache(true);
                        wvPlayToEat.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
                        wvPlayToEat.getSettings().setJavaScriptEnabled(true);
                        wvPlayToEat.loadUrl(ApiConfiguration.Domain + "api/spin-2-win");
                        wvPlayToEat.addJavascriptInterface(new JavaScriptInterface(PlayToEat.this), "Android");
                        swipeContainer.setOnRefreshListener(
                                new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        wvPlayToEat.reload();
                                        swipeContainer.setRefreshing(false);
                                    }
                                }
                        );

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(PlayToEat.this, response.body().getMessage());
                        CommonFunctions.getInstance().FinishActivityWithDelay(PlayToEat.this);
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(PlayToEat.this, response.message());
                    CommonFunctions.getInstance().FinishActivityWithDelay(PlayToEat.this);
                }
            }

            @Override
            public void onFailure(Call<Spin2WinAPI.ResponseSpin2Win> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(PlayToEat.this, t.toString());
            }
        });

    }*/
}
