package com.roobo.boot.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.roobo.boot.utils.Utils;

public class BootCheckService extends Service {
    private BootCheckThread mBootCheckThread;

    @Override
    public void onCreate() {
        super.onCreate();

        mBootCheckThread = BootCheckThread.getInstance(this);
        mBootCheckThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Utils.doLog("BOOT", "BOOT do BootCheckService onDestroy");
//        if (mBootCheckThread != null){
//            mBootCheckThread.doStop();
//        }
        BootCheckThread.doStop();
        super.onDestroy();
    }

}
