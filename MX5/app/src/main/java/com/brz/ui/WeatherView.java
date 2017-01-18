package com.brz.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.brz.http.bean.WeatherInfo;
import com.brz.mx5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by macro on 16/6/8.
 */
public class WeatherView extends RelativeLayout{
    private ImageView mImageView;
    private TextView mCity, mWeather, mAdvise, mTemperature, mTemperature_range;

    private String url = "http://apis.baidu.com/heweather/pro/weather";
    private String cityName;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            refreshWeather(cityName);
            mHandler.sendEmptyMessageDelayed(0, 10 * 1000);
            return false;
        }
    });

    public WeatherView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_weather,this);

        InitView();
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_weather,this);

        InitView();
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_weather,this);

        InitView();
    }

    private void InitView() {
        mCity = (TextView)findViewById(R.id.city);
        mWeather = (TextView)findViewById(R.id.weather);
        mAdvise = (TextView)findViewById(R.id.advise);
        mTemperature = (TextView)findViewById(R.id.temperature);
        mTemperature_range = (TextView)findViewById(R.id.temperature_range);
    }

    private void ChangeWeatherIcon() {
        /*
        *   根据天气情况设置 天气图标
        *   需要找一组icon
        * */
    }

    public void setCityName(String name) {
        cityName = name;
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mHandler.removeCallbacksAndMessages(null);
    }

    public void refreshWeather(final String cityName) {   //调用时传入城市名
        this.cityName = cityName;
        final Parameters mParameters = new Parameters();

        ApiStoreSDK.execute(url, ApiStoreSDK.GET, mParameters, new ApiCallBack() {

            @Override
            public void onSuccess(int i, String s) {
                JSONObject mJsonObject, mJsonResult;
                JSONArray mJsonArray, mJsonArrayResult;

                WeatherInfo weatherInfo = new WeatherInfo();

                mParameters.put("city", cityName);    //这里城市固定

                Log.i("ApiStoreSDK", "onSuccess");

                try {
                    mJsonObject = new JSONObject(s);
                    mJsonArray = mJsonObject.getJSONArray("HeWeather data service 3.0");
                    mJsonResult = mJsonArray.getJSONObject(0);

                    weatherInfo.setCity(mJsonResult.getJSONObject("basic").getString("city"));
                    weatherInfo.setWeather(mJsonResult.getJSONObject("now").getJSONObject("cond").getString("txt"));
                    weatherInfo.setTemperature(mJsonResult.getJSONObject("now").getString("tmp") + "°");
                    weatherInfo.setTemperatureRange(mJsonResult.getJSONObject("suggestion").getJSONObject("comf").getString("brf"));

                    mJsonArrayResult = mJsonResult.getJSONArray("daily_forecast");
                    mJsonResult = mJsonArrayResult.getJSONObject(0).getJSONObject("tmp");

                    weatherInfo.setTemperatureRange(mJsonResult.getString("max") + "°/" + mJsonResult.getString("min") + "°");

                    mTemperature_range.setText(weatherInfo.getTemperatureRange());
                    mCity.setText(weatherInfo.getCity());
                    mWeather.setText(weatherInfo.getWeather());
                    mTemperature.setText(weatherInfo.getTemperature());
                    mAdvise.setText(weatherInfo.getAdvise());

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
