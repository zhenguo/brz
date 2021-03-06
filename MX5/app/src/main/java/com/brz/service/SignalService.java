package com.brz.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.brz.basic.Basic;
import com.brz.http.bean.Cmd;
import com.brz.http.bean.CmdData;
import com.brz.http.bean.Hardware;
import com.brz.http.bean.RequestBody;
import com.brz.http.bean.Status;
import com.brz.http.bean.SystemState;
import com.brz.http.service.TerminalService;
import com.brz.system.TerminalConfig;
import com.brz.system.TerminalConfigManager;
import com.brz.utils.SystemUtil;

import rx.Subscriber;

/**
 * Created by macro on 16/7/18.
 */
public class SignalService extends Service {

    private static final int NOTIFICATION_ID = 1000;
    private TerminalService mService;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private static final String TAG = "SignalService";
    private static final int MSG_HEARTBEAT = 0x1000;
    private static final int MSG_GET_TERMINAL_ID = 0x1001;
    private static final int MSG_UPDATE_CONFIG = 0x1002;
    private static final int MSG_GET_CONFIG = 0x1003;

    private static final int DEFAULT_HEARTBEAT_INTERVAL = 3000;
    private static final String EXTRA_PARAM_TERM_ID = "extra_param_term_id";
    private TerminalConfig mConfig;
    private ActivityManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        mHandlerThread = new HandlerThread("signal_thread");
        mHandlerThread.start();

        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_HEARTBEAT:
                        doHeartBeat();
                        break;
                    case MSG_GET_TERMINAL_ID:
                        getTermId();
                        break;
                    case MSG_GET_CONFIG:
                        mConfig = TerminalConfigManager.getInstance().getTerminalConfig();
                        if (mConfig != null) {
                            Basic.HTTP_SERVER = mConfig.getHttpServer();
                            mService = TerminalService.getInstance();

                            Log.d(TAG, "http: " + Basic.HTTP_SERVER);

                            if (TextUtils.isEmpty(mConfig.getTermId())) {
                                mHandler.sendEmptyMessage(MSG_GET_TERMINAL_ID);
                            } else {
                                startHeartbeat();
                            }
                        }

                        break;
                    case MSG_UPDATE_CONFIG:
                        TerminalConfigManager.getInstance().updateTerminalConfig();
                        break;
                    default:
                        break;
                }
            }
        };

        // 设置成前台服务
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, SignalService.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(android.support.v7.appcompat.R.drawable.notification_template_icon_bg);
        builder.setTicker("Foreground SignalService Start");
        builder.setContentTitle("Foreground SignalService");
        builder.setContentText("Make this service run in the foreground.");
        Notification notification = builder.build();
        startForeground(NOTIFICATION_ID, notification);

        mHandler.sendEmptyMessage(MSG_GET_CONFIG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mHandlerThread != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mHandlerThread.quitSafely();
            } else {
                mHandlerThread.quit();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();

            if (TextUtils.isEmpty(action)) {
                return START_STICKY;
            }

            switch (action) {
                case TerminalConstants.ACTION_GET_TERMINAL_ID:
                    getTermId();
                default:
                    break;
            }
        }

        return START_STICKY;
    }

    private void sendMsg(int what, Object o) {
        Message message = mHandler.obtainMessage();
        message.what = what;
        message.obj = o;
        message.sendToTarget();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getTermId() {
        Log.d(TAG, "getTermId");
        Status status = new Status();
        status.setSystemState(SystemState.ID_NOT_ASSIGN.getValue());
        Hardware hardware = new Hardware();
        hardware.setMac(SystemUtil.getHardwareAddress(SignalService.this));
        status.setHardware(hardware);

        Cmd cmd = new Cmd();
        cmd.setCmdType(CmdType.GET_TERM_ID);

        RequestBody body = new RequestBody("", status, cmd);
        mService.getTermId(body, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext termId: " + s);
                mConfig.setTermId(s);

                startHeartbeat();
                mHandler.sendEmptyMessage(MSG_UPDATE_CONFIG);
            }
        });
    }

    public void doHeartBeat() {
        Log.d(TAG, "doHeartBeat: " + mManager.getLargeMemoryClass());
        Status status = new Status();
        status.setSystemState(SystemState.ONLINE.getValue());

        Cmd cmd = new Cmd();
        cmd.setCmdType(CmdType.HEARTBEAT);

        CmdData cmdData = new CmdData();
        String v = SystemUtil.getVersionName(this);
        cmdData.setVersion(v);
        cmd.setCmdData(cmdData);

        Log.d(TAG, "TermID: " + mConfig.getTermId() + " ver: " + v);

        RequestBody requestBody = new RequestBody.Builder().setCmd(cmd)
                .setStatus(status)
                .setTermId(mConfig.getTermId())
                .build();

        mService.heartBeat(requestBody, new Subscriber<Cmd>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Cmd cmd) {
                CmdProcessor.getInstance().process(SignalService.this, cmd);
            }
        });
    }

    private void startHeartbeat() {
        mHandler.post(mHeartbeatRunnable);
    }

    private Runnable mHeartbeatRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(MSG_HEARTBEAT);

            mHandler.postDelayed(mHeartbeatRunnable, DEFAULT_HEARTBEAT_INTERVAL);
        }
    };
}
