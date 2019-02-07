package com.ghostFood.payment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.PaymentResponseAPI;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebviewOnlinePayment extends AppCompatActivity implements View.OnClickListener {

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
    @BindView(R.id.wvStripe)
    WebView wvPayment;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    private String url;

    JIFace iface = new JIFace();
    private CustomProgressDialog mCustomProgressDialog;
    private String order_number;
    private String loyalty_points;
    private String orderKey;

    AsyncTask<Void, Void, Void> mTask;
    String jsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_online_payment);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        url = b.getString("url");
        order_number = b.getString("order_number");
        loyalty_points = b.getString("loyalty_points");
        orderKey = b.getString("order_key");


        wvPayment.loadUrl(url);
        wvPayment.getSettings().setJavaScriptEnabled(true);
        wvPayment.getSettings().setDomStorageEnabled(true);
        wvPayment.clearCache(true);
        wvPayment.clearHistory();
        wvPayment.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvPayment.addJavascriptInterface(iface, "HTMLOUT");
        wvPayment.setWebChromeClient(new WebChromeClient());
        wvPayment.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*if (mCustomProgressDialog.isShowing()) {

                }*/
                if (request.getUrl().toString().contains("https://www.shahigrill.in/")) {
                    try {
                        String html = URLDecoder.decode(request.getUrl().toString(), "UTF-8").substring(9);
                        System.out.println("html" + html);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("example", "failed to decode source", e);
                    }
                }

                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("Start url : " + url);
/*
                if (url.contains("http://td-developer.ontabee.com/api/v1/payment/load-pay-form")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            wvPayment.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);

                        }
                    });
                }
*/

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("Finish url : " + url);
                if (url.contains("https://www.shahigrill.in/api/v1/payment/process-vpc-order")) {
                    wvPayment.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                    callApi(url);
//                    wvPayment.loadUrl(url);
                } else if (url.contains("https://www.shahigrill.in/api/v1/payment/load-pay-form")) {
                    wvPayment.loadUrl("javascript:window.HTMLOUT.showFailureHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                } else if (url.contains("https://migs.mastercard.com.au/vpcpay?")) {
                    wvPayment.setVisibility(View.VISIBLE);

                }else if (url.contains("https://www.shahigrill.in/api/v1/payment/success/vpc/")){
                    SuccessApi();
                }
//                wvPayment.loadUrl(url);
            }
        });


//        wvStripe.loadUrl(url);

        InitiView();
    }

    private void InitiView() {
        tlbarBack.setOnClickListener(this);
        tlbarText.setText(Constants.online_payment);
    }

    private String getResponseText(String stringUrl) throws IOException {
        StringBuilder response = new StringBuilder();

        URL url = new URL(stringUrl);
        HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
            String strLine = null;
            while ((strLine = input.readLine()) != null) {
                response.append(strLine);
            }
            input.close();
        }
        return response.toString();
    }

    class JIFace {
        @JavascriptInterface
        public void showHTML(String data) {


            final Document doc = Jsoup.parse(data);
            if (doc.getElementById("webkit-xml-viewer-source-xml").getElementsByTag("data").size() > 0) {
                final String dataUrl = URLDecoder.decode(doc.getElementById("webkit-xml-viewer-source-xml").getElementsByTag("data").get(0).toString().replace("<data>", "").replace("</data>", ""));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wvPayment.loadUrl(dataUrl.replace("&amp;", "&"));
                    }
                });

/*                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 2000);*/
            } else {

            }


//            Element elem = doc.getElementById("hidWnsLoginKey");
//            String sessionKey = elem.attr("value");

//            SuccessApi();

                /*String htmlString = data;
                System.out.println("HTML === >" + htmlString.replace("<head><head></head><body>", "").replace("</body></head>", ""));
                htmlString = htmlString.replace("<head><head></head><body>", "").replace("</body></head>", "")
                        .replace("<prestyle=\"word-wrap: break-word; white-space: pre-wrap;\">", "").replace("</pre>", "").toString();
                if (htmlString.toLowerCase().contains("success")) {*/
//                Gson gson = new Gson();
//                PaymentResponse paymentResponse = gson.fromJson(htmlString,PaymentResponse.class);


//            bundle.putString("order_number", orderKey);
//            bundle.putString("orderAmount", orderValue);
            // bundle.putString("loyalty_points", loyalty_points);
//            MyActivity.DisplayPaymentOrderConfirmed(PayUMoneyActivity.this, orderKey, orderValue, "", "");
            // CommonFunctions.getInstance().newIntent(PaymentActivity.this, OrderConformationActivity.class, bundle, true);
//            finish();
                /*} else {
                    Bundle bundle = new Bundle();
                    bundle.putString("order_number", orderKey);
                    bundle.putString("orderAmount", orderValue);
                    // bundle.putString("loyalty_points", loyalty_points);
                    MyActivity.DisplayPaymentOrderFailure(PayUMoneyActivity.this);
                   // CommonFunctions.getInstance().newIntent(PaymentActivity.this, OrderFailureActivity.class, bundle, true);
                    finish();
                }*/

            /*ConstantsInternal.DeliveryTypeValue = "";
            ConstantsInternal.DeliveryDate = "";
            ConstantsInternal.DeliveryTime = "";
            ConstantsInternal.d_opt = "";
            ConstantsInternal.PROMOCODE = "";
            String response = htmlString.replace("<head><head></head><body>", "").replace("</body></head>", "");
            Gson gson = new Gson();
            PaymentResponse paymentResponse = gson.fromJson(response, PaymentResponse.class);
            Toast.makeText(PaymentWebViewActivity.this, paymentResponse.getMessage(), Toast.LENGTH_SHORT).show();

            if (paymentResponse.getHttpcode().equals("200")) {

                String orderKey = paymentResponse.getData().getOrder_key();
                MyActivity.getInstance().order_placed(PaymentWebViewActivity.this, orderKey);
                finish();
            } else if (paymentResponse.getHttpcode().equals("406")) {
                MyActivity.getInstance().order_placed(PaymentWebViewActivity.this, null);
                finish();

            }*/
        }

        @JavascriptInterface
        public void showFailureHTML(String data) {
            Bundle bundle = new Bundle();
//            bundle.putString("order_number", orderKey);
//            bundle.putString("orderAmount", orderValue);
//            // bundle.putString("loyalty_points", loyalty_points);
//            MyActivity.DisplayPaymentOrderFailure(PayUMoneyActivity.this);
            // CommonFunctions.getInstance().newIntent(PaymentActivity.this, OrderFailureActivity.class, bundle, true);
//            finish();
        }
    }

    private void SuccessApi() {
        CustomProgressDialog.getInstance().show(WebviewOnlinePayment.this);
        PaymentResponseAPI stripeResponseAPI = ApiConfiguration.getInstance2().getApiBuilder().create(PaymentResponseAPI.class);
        final Call<PaymentResponseAPI.paymentResponse> call = stripeResponseAPI.Get(orderKey);
        call.enqueue(new Callback<PaymentResponseAPI.paymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponseAPI.paymentResponse> call, Response<PaymentResponseAPI.paymentResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("order_number", order_number);
                        bundle.putString("loyalty_points", loyalty_points);
                        MyActivity.getInstance().OrderConfirmation(WebviewOnlinePayment.this, bundle);
                        CommonFunctions.getInstance().ShowSnackBar(WebviewOnlinePayment.this, response.body().getMessage());
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(WebviewOnlinePayment.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().FinishActivityWithDelay(WebviewOnlinePayment.this);
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(WebviewOnlinePayment.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().FinishActivityWithDelay(WebviewOnlinePayment.this);
                }

            }

            @Override
            public void onFailure(Call<PaymentResponseAPI.paymentResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(WebviewOnlinePayment.this, Constants.NoInternetConnection);
                CommonFunctions.getInstance().FinishActivityWithDelay(WebviewOnlinePayment.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == tlbarBack) {
            finish();
        }

    }
}

