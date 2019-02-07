package com.ghostFood.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by developer on 27/7/16.
 */
public class ApiConfigurationPaymentGateway {

    public static String BASE_URL = "http://demo.hesabe.com/";

    private static ApiConfigurationPaymentGateway ourInstance = new ApiConfigurationPaymentGateway();
    Retrofit mRetrofit;

    public static ApiConfigurationPaymentGateway getInstance() {
        if (ourInstance == null) {
            synchronized (ApiConfigurationPaymentGateway.class) {
                if (ourInstance == null)
                    ourInstance = new ApiConfigurationPaymentGateway();
            }
        }
        ourInstance.config();
        return ourInstance;
    }

    private ApiConfigurationPaymentGateway() {
    }

    private void config() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OkHttpClient getRequestHeader() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public Retrofit getApiBuilder() {

        return mRetrofit;
    }
}