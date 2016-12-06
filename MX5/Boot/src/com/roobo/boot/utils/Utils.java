package com.roobo.boot.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.roobo.boot.BuildConfig;
import java.util.List;

/**
 * Created by henan on 2/24/16.
 */
public class Utils {
  static final String TAG = "BootCheckThread";
  static final boolean DEBUG = BuildConfig.DEBUG;

  public static final String CHECKING_PKG_NAME = "com.brz.mx5";
  public static final String CHECKING_PKG_SERVICE = "com.roobo.coreserver.CoreService";

  /**
   * 根据pkg name 启动某个package
   */
  public static void startPackage(Context c, String pkgNmae) {
    PackageManager packageManager = c.getPackageManager();
    Intent intent = packageManager.getLaunchIntentForPackage(pkgNmae);
    try {
      c.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 重启机器
   */
  public static int rebootDevice(Context c) {
    int iRet = 0;
    Intent intent = new Intent(Intent.ACTION_REBOOT);
    intent.putExtra("nowait", 1);
    intent.putExtra("interval", 1);
    intent.putExtra("window", 0);
    c.sendBroadcast(intent);

    return iRet;
  }

  public static void doLog(String TAG, String msg) {
    if (DEBUG) {
      Log.d(TAG, msg);
    }
  }

  /**
   * 重启 CoreServer
   */
  public static void restartCoreServer(Context context) {
    ComponentName comp = new ComponentName(CHECKING_PKG_NAME, CHECKING_PKG_SERVICE);
    Intent intent = new Intent();
    intent.setComponent(comp);
    context.startService(intent);
  }

  /**
   * 关闭coreservice service
   */
  public static void stopCoreService(Context context) {
    ComponentName comp = new ComponentName(CHECKING_PKG_NAME, CHECKING_PKG_SERVICE);
    Intent intent = new Intent();
    intent.setComponent(comp);
    context.stopService(intent);
  }

  public static void killProcessViaPkgname(Context c, String pkgName) {
    int pid = -1;
    ActivityManager activityManager =
        (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> appProcesses =
        activityManager.getRunningAppProcesses();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.processName.equals(pkgName)) {
        pid = appProcess.pid;
        Utils.doLog(TAG, "get pkg " + pkgName + " pid is " + pid);
        break;
      }
    }
    if (pid > 0) {
      Utils.doLog(TAG, "boot do kill " + pkgName + "; pid is " + pid);
      android.os.Process.killProcess(pid);
    } else {
      Utils.doLog(TAG, "boot kill error for pid:" + pid);
    }
  }
}
