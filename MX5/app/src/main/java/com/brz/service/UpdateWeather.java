package com.brz.service;

import android.os.Handler;
import android.os.Message;

import com.brz.ui.WeatherView;

/**
 * Created by dahuahua on 2017/1/14.
 */

public class UpdateWeather implements Runnable {
    private WeatherView weatherView;
    private Message message;

    public UpdateWeather(WeatherView weatherView) { //启动线程时传入WeatherView的实例
        this.weatherView = weatherView;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            weatherView.RefreshWeather("北京");

            super.handleMessage(msg);
        }
    };

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3600000);            // 线程暂停1小时，单位毫秒
                message = new Message();
                message.what = 1;
                handler.sendMessage(message);   // 发送消息
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
