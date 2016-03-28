package com.brz.mx5;

import android.app.Application;

import org.xutils.x;

/**
 * Created by macro on 16/3/28.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
