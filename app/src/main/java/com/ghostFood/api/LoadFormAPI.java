package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface LoadFormAPI {

    @GET("payment/process-vpc-order")
    Call<ResponseLoadForm> Get();

    public class ResponseLoadForm {

    }


}
