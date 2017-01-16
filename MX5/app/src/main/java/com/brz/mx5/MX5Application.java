package com.brz.mx5;

import android.content.Intent;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.brz.basic.Basic;
import com.brz.service.SignalService;
import com.squareup.leakcanary.LeakCanary;

import java.util.logging.Logger;

/**
 * Created by macro on 16/3/28.
 */
public class MX5Application extends BaseApplication {
    private static final String TAG = "MX5Application";
    private Logger mLogger = Logger.getLogger(TAG);

    public static MX5Application mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        ApiStoreSDK.init(this, "e6b642ccfe83be58357c450c5c5622a2"); //百度apikey

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        // 获取屏幕大小
        mLogger.info("get screen configurations...");
        Basic.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Basic.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;

        // 启动SignalService, 获取终端ID
        mLogger.info("start signal service...");
        Intent intent = new Intent();
        intent.setClass(this, SignalService.class);
        startService(intent);
    }
}
