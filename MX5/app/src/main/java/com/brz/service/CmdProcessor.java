package com.brz.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.brz.basic.IntentActions;
import com.brz.download.DiskIOExecutor;
import com.brz.http.bean.Cmd;
import com.brz.programme.Programme;
import com.brz.programme.Theme;

import java.util.List;

/**
 * Created by macro on 16/12/8.
 */

public class CmdProcessor {
  private static final String TAG = "CmdProcessor";
  private Context mContext;

  private static class SingletonHolder {
    private static CmdProcessor instance = new CmdProcessor();
  }

  public static CmdProcessor getInstance() {
    return SingletonHolder.instance;
  }

  public void process(Context context, Cmd cmd) {
    if (cmd == null) return;
    Log.d(TAG, "cmd: " + cmd.getCmdType());
    mContext = context;

    String type = cmd.getCmdType();
    switch (type) {
      case CmdType.UPDATE_PROGRAMME:
        Theme theme = cmd.getCmdData().getTheme();
        List<Programme> programmeList = cmd.getCmdData().getPrograms();

        DownloadResourceRunnable runnable =
            new DownloadResourceRunnable(theme.getUrl(), programmeList,
                new DownloadResourceRunnable.DownloadResourceCallback() {
                  @Override public void onSuccess() {
                    Intent intent = new Intent(IntentActions.ACTION_UPDATE_PROGRAMME);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                  }

                  @Override public void onFailure() {

                  }
                });
        DiskIOExecutor.getInstance().execute(runnable);
        break;
      default:
        break;
    }
  }
}
