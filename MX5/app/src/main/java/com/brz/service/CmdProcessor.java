package com.brz.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.brz.basic.IntentActions;
import com.brz.download.DiskIOExecutor;
import com.brz.http.bean.Cmd;
import com.brz.http.bean.OTAInfo;
import com.brz.programme.Programme;
import com.brz.programme.Theme;

import java.util.List;

/**
 * Created by macro on 16/12/8.
 */

public class CmdProcessor {
    private static final String TAG = "CmdProcessor";
    private Context mContext;

    private CmdProcessor() {

    }

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
                        new DownloadResourceRunnable(theme.getPublishid(), theme.getUrl(), programmeList,
                                new DownloadResourceRunnable.DownloadResourceCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Intent intent = new Intent(IntentActions.ACTION_UPDATE_PROGRAMME);
                                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                DiskIOExecutor.getInstance().execute(runnable);
                break;
            case CmdType.SET_OTA:
                OTAInfo otaInfo = cmd.getCmdData().getUpdate();
                OTAUpdaterRunnable updaterRunnable = new OTAUpdaterRunnable(otaInfo);
                DiskIOExecutor.getInstance().execute(updaterRunnable);
                break;
            case CmdType.SHUTDOWN:
                Intent intent = new Intent("android.intent.action.pubds_sleep");
                mContext.sendBroadcast(intent);
                break;
            case CmdType.REBOOT:
                mContext.sendBroadcast(new Intent().setAction("android.intent.action.pubds_reboot "));
                break;
            case CmdType.PLAY:
                mContext.sendBroadcast(new Intent().setAction("android.intent.action.pubds_reboot "));
                break;
            case CmdType.SET_FILE_SERVER:
                String value = new String(cmd.getCmdData().getData());
                UpdateConfigRunnable ur = new UpdateConfigRunnable(value, UpdateConfigRunnable.FILE);
                DiskIOExecutor.getInstance().execute(ur);
                break;
            case CmdType.SET_HTTP_SERVER:
                value = new String(cmd.getCmdData().getData());
                ur = new UpdateConfigRunnable(value, UpdateConfigRunnable.HTTP);
                DiskIOExecutor.getInstance().execute(ur);
                break;
            default:
                break;
        }
    }


}
