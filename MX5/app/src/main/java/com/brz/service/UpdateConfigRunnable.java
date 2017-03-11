package com.brz.service;

import android.text.TextUtils;
import android.util.Log;

import com.brz.system.TerminalConfigManager;

/**
 * Created by macro on 2017/3/11.
 */

public class UpdateConfigRunnable implements Runnable {
    private static final String TAG = "UpdateConfigRunnable";
    public final static int HTTP = 1;
    public final static int FILE = 2;
    public final static int WORKID = 3;

    private String mValue;
    private int mType;

    public UpdateConfigRunnable(String str, int type) {
        mValue = str;
        mType = type;
    }

    @Override
    public void run() {
        switch (mType) {
            case HTTP:
                Log.d(TAG, "set http server: " + mValue);
                TerminalConfigManager.getInstance().getTerminalConfig().setHttpServer(mValue);
                if (TextUtils.isEmpty(TerminalConfigManager.getInstance().getTerminalConfig().getFileServer())) {
                    TerminalConfigManager.getInstance().getTerminalConfig().setFileServer(mValue);
                }
                break;
            case FILE:
                Log.d(TAG, "set file server: " + mValue);
                TerminalConfigManager.getInstance().getTerminalConfig().setFileServer(mValue);
                break;
            case WORKID:
                Log.d(TAG, "workId: " + mValue);
                TerminalConfigManager.getInstance().getTerminalConfig().setWorkId(mValue);
            default:break;
        }

        TerminalConfigManager.getInstance().updateTerminalConfig();
    }
}
