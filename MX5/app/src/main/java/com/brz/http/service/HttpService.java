package com.brz.http.service;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macro on 16/5/20.
 */
public class HttpService {
    public final static String HuantengsmartUrl = "http://huantengsmart.com:80/api/";
    protected Retrofit mRetrofit;

    public HttpService(String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpUrl.parse(url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
