package com.brz.service;

import android.util.Log;

import com.brz.basic.Basic;
import com.brz.download.FileDownloadManager;
import com.brz.http.bean.OTAInfo;
import com.brz.system.TerminalConfigManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by macro on 2017/1/16.
 */

public class OTAUpdaterRunnable implements Runnable {
    private static final String TAG = "OTAUpdaterRunnable";
    private OTAInfo mOtaInfo;
    private String mBaseUrl =
            TerminalConfigManager.getInstance().getTerminalConfig().getFileServer() + File.separator;

    public OTAUpdaterRunnable(OTAInfo info) {
        mOtaInfo = info;
    }

    @Override
    public void run() {
        FileDownloadManager.getInstance().performRequest(mBaseUrl + mOtaInfo.getUrl(), Basic.RESOURCE_PATH + File.separator + "update.apk", new FileDownloadManager.Response() {
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

    private void updateAPK(String apkPath) {

        Log.v(TAG, "Start the update progress");
        try {
            String[] cmd = {"/system/bin/sh", "-c", "sync "};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            process.destroy();
            Log.v(TAG, "sync done");

            String[] cmd1 = {"/system/bin/sh", "-c",
                    "pm install -r " + apkPath.trim()};
            Log.d(TAG, "cmd: " + Arrays.toString(cmd1));
            Process process1 = Runtime.getRuntime().exec(cmd1);
            Log.d(TAG, "waitFor()");
            process1.waitFor();
            Log.d(TAG, "destroy()");
            process1.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
