package com.ghostFood.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/27/2017.
 */

public interface BankListAPI {
    @GET("bank")
    Call<ResponseBankList> GetBankList(@Query("page") String mPageNumber
    );

    public class ResponseBankList {

        private Integer status;
        private List<Datum> data = null;
        private Integer time;
        private Integer response_code;
        private String message;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Integer getResponse_code() {
            return response_code;
        }

        public void setResponse_code(Integer response_code) {
            this.response_code = response_code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Datum {

        private Integer id;
        private String bank_name;
        private Integer bank_status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public Integer getBank_status() {
            return bank_status;
        }

        public void setBank_status(Integer bank_status) {
            this.bank_status = bank_status;
        }

    }
}
