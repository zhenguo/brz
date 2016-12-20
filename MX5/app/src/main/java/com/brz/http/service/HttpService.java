package com.brz.http.service;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macro on 16/5/20.
 */
public class HttpService {
  protected Retrofit mRetrofit;
  public static final String BSQ_URL = "http://139.129.98.83:8080/tmms/tmms/";

  public HttpService(String url) {
    mRetrofit = new Retrofit.Builder().baseUrl(HttpUrl.parse(url))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }
}
