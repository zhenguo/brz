package com.brz.mx5;

import android.content.Intent;

import com.brz.basic.Basic;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeManager;
import com.brz.programme.Theme;
import com.brz.service.SignalService;
import com.brz.service.TerminalConstants;
import com.brz.system.TerminalConfig;
import com.brz.system.TerminalConfigManager;

import java.util.logging.Logger;

/**
 * Created by macro on 16/3/28.
 */
public class MX5Application extends BaseApplication {
    private static final String TAG = "MX5Application";
    private Logger mLogger = Logger.getLogger(TAG);

    public static MX5Application mInstance = null;
    private TerminalConfigManager mTerminalConfigManager;
    private TerminalConfig mTerminalConfig;

    private ProgrammeManager mProgrammeManager;
    private Theme mTheme;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        // 启动SignalService, 获取终端ID
        mLogger.info("start signal service...");
        Intent intent = new Intent(TerminalConstants.ACTION_GET_TERMINAL_ID);
        intent.setClass(this, SignalService.class);
        startService(intent);

        // 获取屏幕大小
        mLogger.info("get screen configurations...");
        Basic.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Basic.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;

        // 读取系统配置
        mLogger.info("read terminal configurations...");
        mTerminalConfigManager = TerminalConfigManager.getInstance();
        mTerminalConfig = mTerminalConfigManager.getTerminalConfig();
        mLogger.info(mTerminalConfig.toString());

        // 读取节目单信息
        mLogger.info("read programmes info...");
        mProgrammeManager = ProgrammeManager.getInstance();
        mTheme = mProgrammeManager.getTheme();
        mLogger.info("theme: " + mTheme.toString());

        for (int i = 0; i < mTheme.getDefaults().size(); i++) {
            mLogger.info("programme: " + mTheme.getDefaults().get(i).toString());
        }
    }

    public static MX5Application getInstance() {
        return mInstance;
    }

    public TerminalConfig getTerminalConfig() {
        return mTerminalConfig;
    }

    public ProgrammeContext getProgrammeContext(String fileName) {
        return mProgrammeManager.getContext(fileName);
    }

    public Theme getTheme2() {
        return mTheme;
    }
}
