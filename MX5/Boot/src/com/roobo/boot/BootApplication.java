package com.roobo.boot;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;
import android.text.TextUtils;

import com.roobo.boot.service.BootCheckService;
import com.roobo.boot.utils.ProcessNameUtil;
import com.roobo.boot.utils.Utils;

/**
 * Created by henan on 2/24/16.
 */
public class BootApplication extends Application {
    public static BootApplication mApp;
    public static final String APP_NAME = "Boot";
    public static BootApplication getApp() {
        return mApp;
    }

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        super.onCreate();
        mApp = this;

        String processName = ProcessNameUtil.getCurrentProcessName();
        Utils.doLog("Boot", "processName is " + processName);
        if (!TextUtils.isEmpty(processName) && processName.equalsIgnoreCase(getPackageName())) {
            //启动coreserver
            Utils.startPackage(mApp, "com.brz.mx5");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100000);
                        //启动 boot check 服务
                        Intent intent = new Intent(mApp, BootCheckService.class);
                        mApp.startService(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}