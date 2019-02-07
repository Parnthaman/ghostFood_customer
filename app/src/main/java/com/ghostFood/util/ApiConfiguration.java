package com.ghostFood.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ghostFood.acitivity.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfiguration {

    public static String VERSION = "api/v1/";

    //    public static String BASE_URL_PROD = "https://td-developer.ontabee.com/";
//    public static String BASE_URL_STAGING = "https://td-developer.ontabee.com/";
//    public static String BASE_URL_DEV = "https://td-developer.ontabee.com/";
//
    public static String BASE_URL_PROD = "https://ghostfood.ontabee.com/";
    public static String BASE_URL_STAGING = "https://ghostfood.ontabee.com/";
    public static String BASE_URL_DEV = "https://ghostfood.ontabee.com/";


    public String ProdOrDev = "?env";
    private static ApiConfiguration ourInstance = new ApiConfiguration();
    Retrofit mRetrofit;

    public static ApiConfiguration getInstance() {
        if (ourInstance == null) {
            synchronized (ApiConfiguration.class) {
                if (ourInstance == null)
                    ourInstance = new ApiConfiguration();
            }
        }
        ourInstance.config();
        return ourInstance;
    }

    public static ApiConfiguration getInstance2() {
        if (ourInstance == null) {
            synchronized (ApiConfiguration.class) {
                if (ourInstance == null)
                    ourInstance = new ApiConfiguration();
            }
        }
        ourInstance.config2();
        return ourInstance;
    }

    private void config2() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String BASE_URL2 = BASE_URL_STAGING + VERSION;
        if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Dev) {
            BASE_URL2 = BASE_URL_DEV + VERSION;
        } else if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Prod) {
            BASE_URL2 = BASE_URL_PROD + VERSION;
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .client(getRequestHeader(SessionManager.User.getInstance().getAccessToken()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private ApiConfiguration() {
    }

    private void config() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String BASE_URL = BASE_URL_STAGING + VERSION;
        if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Dev) {
            BASE_URL = BASE_URL_DEV + VERSION;
        } else if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Prod) {
            BASE_URL = BASE_URL_PROD + VERSION;
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getRequestHeader(SessionManager.User.getInstance().getAccessToken()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OkHttpClient getRequestHeader(final String token) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuild = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
//                .addInterceptor(new ChuckInterceptor(MyApplication.context))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .addHeader("Accept-Language", SessionManager.AppProperties.getInstance().getLanguageCode())
                                .build();
                        if (!CheckInternetConnection()) {
                            CustomProgressDialog.getInstance().dismiss();
                            throw new NoConnectivityException();
                        } else {
                            Response response = chain.proceed(newRequest);
                            return response;
                        }
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        if (ProdOrDev != "") {
            okHttpClientBuild.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("env", "dev").build();
                    request = request.newBuilder().url(url).build();

                    if (!CheckInternetConnection()) {
                        CustomProgressDialog.getInstance().dismiss();
                        throw new NoConnectivityException();
                    } else {
                        Response response = chain.proceed(request);
                        return response;
                    }
                }
            });
        }
        OkHttpClient okHttpClient = okHttpClientBuild.build();
        return okHttpClient;
    }

    public Retrofit getApiBuilder() {

        return mRetrofit;
    }

    public class NoConnectivityException extends IOException {
        @Override
        public String getMessage() {
            return Constants.NoInternetConnection;
        }
    }

    public boolean CheckInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}