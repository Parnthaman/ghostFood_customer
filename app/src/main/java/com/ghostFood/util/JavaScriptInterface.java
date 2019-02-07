package com.ghostFood.util;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by android1 on 8/2/2017.
 */

public class JavaScriptInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public JavaScriptInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getAccessToken() {
        return SessionManager.User.getInstance().getAccessToken();
    }

    @JavascriptInterface
    public String getSpin2WinJson() {
        return SessionManager.Spin2Win.getInstance().getJson();
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void saveSpin2WinResult(String type, String winStatus, String userDataType) {
        //SaveResult(type, winStatus, userDataType);
    }

    /*private void SaveResult(String type, String winStatus, String userDataType) {

        String typeIs = "0";
        try {
            typeIs = new JSONObject(userDataType).get("type").toString();
        }
        catch (Exception e) {}
        CustomProgressDialog.getInstance().show(mContext);
        Spin2WinResultAPI mSpin2WinResultAPI = ApiConfiguration.getInstance2().getApiBuilder().create(Spin2WinResultAPI.class);
        final Call<Spin2WinResultAPI.ResponseSpin2Win> call = mSpin2WinResultAPI.Update(SessionManager.User.getInstance().getKey(), type, winStatus, typeIs);
        call.enqueue(new Callback<Spin2WinResultAPI.ResponseSpin2Win>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<Spin2WinResultAPI.ResponseSpin2Win> call, Response<Spin2WinResultAPI.ResponseSpin2Win> response) {

                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CommonFunctions.getInstance().ShowSnackBar(mContext, response.body().getMessage());
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(mContext, response.body().getMessage());
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(mContext, response.message());
                }
            }

            @Override
            public void onFailure(Call<Spin2WinResultAPI.ResponseSpin2Win> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(mContext, t.toString());
            }
        });

    }*/
}
