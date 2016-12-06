package com.roobo.boot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.roobo.boot.service.BootCheckThread;
import com.roobo.boot.utils.Utils;

/**
 * Created by henan on 2/24/16.
 * 开机/关机的广播接收
 */
public class BootReceiver extends BroadcastReceiver {

  @Override public void onReceive(final Context context, Intent intent) {
    try {
      String action = intent.getAction();
      if (Intent.ACTION_SHUTDOWN.equals(action)) {//关机的广播
        Utils.doLog("BOOT", "boot RECEIVE android.intent.action.ACTION_SHUTDOWN");
        //关闭对coreserver的监控
        BootCheckThread.doStop();
        //关闭coreserver，不再对voice进行监控
        Utils.killProcessViaPkgname(context, Utils.CHECKING_PKG_NAME);

        //                VoiceLedUtil.playByMsgKey(context, MsgConstants.CONDITION_VOICE_SHUT_DOWN,
        //                        VoiceLedUtil.STATE_SHUTDOWN, LEDConstants.ACTION_SHUTDOWN, 0, true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
