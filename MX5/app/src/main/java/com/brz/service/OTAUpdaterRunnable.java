package com.brz.service;

import android.util.Log;

import com.brz.basic.Basic;
import com.brz.download.FileDownloadManager;
import com.brz.http.bean.OTAInfo;

import java.io.File;
import java.io.IOException;

/**
 * Created by macro on 2017/1/16.
 */

public class OTAUpdaterRunnable implements Runnable {
    private static final String TAG = "OTAUpdaterRunnable";
    private OTAInfo mOtaInfo;

    public OTAUpdaterRunnable(OTAInfo info) {
        mOtaInfo = info;
    }

    @Override
    public void run() {
        FileDownloadManager.getInstance().performRequest(mOtaInfo.getUrl(), Basic.RESOURCE_PATH + File.separator + "update.apk", new FileDownloadManager.Response() {
            @Override
            public void onSuccess(File result) {
                updateAPK(result.getPath());
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void updateAPK(String path) {
        final String apkPath = path;
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.v(TAG, "Start the update progress");
                try {
                    String[] cmd = {"/system/bin/sh", "-c", "sync "};
                    Process process = Runtime.getRuntime().exec(cmd);
                    process.waitFor();
                    process.destroy();
                    Log.v(TAG, "sync done");

                    String[] cmd1 = {"/system/bin/sh", "-c",
                            "pm install -r " + apkPath.trim()};
                    Process process1 = Runtime.getRuntime().exec(cmd1);
                    process1.waitFor();
                    process1.destroy();
                } catch (IOException e) {
                    Log.v(TAG, "updateAPK: " + e.getLocalizedMessage());
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Log.v(TAG, "updateAPK: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
