package com.roobo.boot.utils;

import android.os.Process;
import android.util.Log;

import java.io.FileInputStream;
import java.util.Locale;

/**
 * 从common中移植过来，去掉对common的依赖
 */
public class ProcessNameUtil {
    private static final String CMDLINE_FORMAT_STRING = "/proc/%d/cmdline";
    private static final int MAX_PKGNAME_LEN = 128;
    private static final String TAG = "ProcessNameUtil";

    public static String getCurrentProcessName() {
        return getProcessName(Process.myPid());
    }

    public static String getProcessNameSuffix() {
        String processName = getCurrentProcessName();
        int index = processName.indexOf(":");
        if (index == -1 || ((index + 1) >= processName.length())) {
            return "";
        } else {
            return processName.substring(index + 1);
        }
    }

    public static String getProcessName(int pid) {
        String cmdlinePath = String.format(Locale.getDefault(), CMDLINE_FORMAT_STRING, pid);
        FileInputStream fis = null;
        String pkgname = "";
        try {
            fis = new FileInputStream(cmdlinePath);

            byte[] buffer = new byte[MAX_PKGNAME_LEN];

            int len = 0;
            int b;
            while ((b = fis.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                pkgname = new String(buffer, 0, len, "UTF-8");
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
        }
        return pkgname;
    }

}
