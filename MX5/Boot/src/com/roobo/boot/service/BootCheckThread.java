package com.roobo.boot.service;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.roobo.boot.utils.Utils;

import java.util.List;

/**
 * Created by henan on 2/24/16.
 */
public class BootCheckThread extends Thread {
  static final String TAG = "BootCheckThread";

  private static final boolean FOR_TEST = false;
  /** 需要重启的阀值 */
  private static final int REBOOT_TIME = FOR_TEST ? 0 : 3;
  private static final int DO_MONITOR = 1;
  private static final int TIME_TO_WAIT = 10 * 1000;
  private static boolean RUNNING = true;
  private Context mContext;
  private int mFailTime = 0;

  private static BootCheckThread sBootCheckThread;
  private BootCheckHandler mBootCheckHandler;

  public static synchronized BootCheckThread getInstance(Context context) {
    if (sBootCheckThread == null) {
      sBootCheckThread = new BootCheckThread(context);
    }

    return sBootCheckThread;
  }

  public BootCheckThread(Context context) {
    mContext = context;
    mBootCheckHandler = new BootCheckHandler();
  }

  @Override public void run() {
    super.run();

    while (RUNNING) {
      mBootCheckHandler.sendEmptyMessage(DO_MONITOR);
      synchronized (this) {
        //应该使用此时间，用于避免手动修改布丁的时间导致wait过长时间而不监控的问题(bug 2657 手动卸载coreserver后 进程没有启动)
        long start = SystemClock.uptimeMillis();
        long timeOut = TIME_TO_WAIT;
        while (timeOut > 0) {
          Utils.doLog(TAG, "timeOut is " + timeOut);
          try {
            wait(timeOut);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          timeOut = TIME_TO_WAIT - (SystemClock.uptimeMillis() - start);
        }
      }
    }
  }

  public static void doStop() {
    RUNNING = false;
  }

  final class BootCheckHandler extends Handler {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case DO_MONITOR:
          doCheck(mContext);
          break;
      }
    }
  }

  /** 检查CoreServer 进程是否存在，如果此进程不存在则重启此进程 */
  private void doCheck(Context c) {
    ActivityManager activityManager =
        (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> appProcesses =
        activityManager.getRunningAppProcesses();
    boolean good = false;

    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.processName.equals(Utils.CHECKING_PKG_NAME)) {
        good = true;
        Utils.doLog(TAG, "com.roobo.coreserver is in good condition ...");
        break;
      }
    }
    if (!good) {
      mFailTime++;
      Utils.doLog(TAG,
          "com.roobo.coreserver is not in good condition, restart it ...  mFailTime is "
              + mFailTime);
      //            Utils.startPackage(c,CHECKING_PKG_NAME);
      if (mFailTime > REBOOT_TIME) {
        Utils.doLog(TAG,
            "com.roobo.coreserver is always not good. reboot device! mFailTime is " + mFailTime);
        Utils.rebootDevice(mContext);
      } else {
        Utils.startPackage(mContext, "com.brz.mx5");
      }
    } else {
      mFailTime = 0;
    }
  }
}
