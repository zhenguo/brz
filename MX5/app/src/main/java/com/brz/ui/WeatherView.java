package com.brz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.brz.mx5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by macro on 16/6/8.
 */
public class WeatherView extends RelativeLayout{
    private ImageView imageView;
    private TextView city, weather, advise, temperature, temperature_range;
    private Parameters parameters;
    private JSONObject jsonObject, jsonResult;
    private JSONArray jsonArray, jsonArrayResult;

    private String url = "http://apis.baidu.com/heweather/pro/weather";
    private String cityName;

    public WeatherView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_weather,this);

        InitView();
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_weather,this);

        InitView();
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_weather,this);

        InitView();
    }

    private void InitView() {
        city = (TextView)findViewById(R.id.city);
        weather = (TextView)findViewById(R.id.weather);
        advise = (TextView)findViewById(R.id.advise);
        temperature = (TextView)findViewById(R.id.temperature);
        temperature_range = (TextView)findViewById(R.id.temperature_range);
    }

    private void ChangeWeatherIcon() {
        /*
        *   根据天气情况设置 天气图标
        *   需要找一组icon
        * */
    }

    public void RefreshWeather(String cityName) {   //调用时传入城市名
        this.cityName = cityName;

        parameters = new Parameters();
        parameters.put("city", cityName);    //这里城市固定

        ApiStoreSDK.execute(url, ApiStoreSDK.GET, parameters, new ApiCallBack() {

            @Override
            public void onSuccess(int i, String s) {
                Log.i("ApiStoreSDK", "onSuccess");

                try {
                    jsonObject = new JSONObject(s);
                    jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
                    jsonResult = jsonArray.getJSONObject(0);

                    city.setText(jsonResult.getJSONObject("basic").getString("city"));
                    weather.setText(jsonResult.getJSONObject("now").getJSONObject("cond").getString("txt"));
                    temperature.setText(jsonResult.getJSONObject("now").getString("tmp") + "°");
                    advise.setText(jsonResult.getJSONObject("suggestion").getJSONObject("comf").getString("brf"));

                    jsonArrayResult = jsonResult.getJSONArray("daily_forecast");
                    jsonResult = jsonArrayResult.getJSONObject(0).getJSONObject("tmp");

                    temperature_range.setText(jsonResult.getString("max") + "°/" + jsonResult.getString("min") + "°");


                }catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onComplete() {
                Log.i("ApiStoreSDK", "onComplete");
            }

            @Override
            public void onError(int i, String s, Exception e) {
                Log.i("ApiStoreSDK", "onError, status: " + i);
                Log.i("ApiStoreSDK", "errMsg: " + (e == null ? "" : e.getMessage()));
            }
        });
    }
}
