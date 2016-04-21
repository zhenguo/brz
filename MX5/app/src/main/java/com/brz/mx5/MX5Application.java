package com.brz.mx5;

import com.brz.basic.Basic;
import com.brz.programme.ProgrammeManager;
import com.brz.programme.Theme;
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

        // 获取屏幕大小
        Basic.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Basic.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;

        // 读取系统配置
        mLogger.info("获取系统配置信息...");
        mTerminalConfigManager = TerminalConfigManager.getInstance();
        mTerminalConfig = mTerminalConfigManager.getTerminalConfig();
        mLogger.info(mTerminalConfig.toString());

        // 读取节目单信息
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

    public Theme getTheme2() {
        return mTheme;
    }
}
