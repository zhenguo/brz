package com.brz.mx5;

import com.brz.system.TerminalConfig;
import com.brz.system.TerminalConfigManager;

import java.util.logging.Logger;

/**
 * Created by macro on 16/3/28.
 */
public class MX5Application extends BaseApplication {
    private static final String TAG = "MX5Application";
    private Logger mLogger = Logger.getLogger(TAG);

    private TerminalConfigManager mTerminalConfigManager;
    private TerminalConfig mTerminalConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        // 读取系统配置
        mLogger.info("获取系统配置信息...");
        mTerminalConfigManager = TerminalConfigManager.getInstance();
        mTerminalConfig = mTerminalConfigManager.getTerminalConfig();
        mLogger.info(mTerminalConfig.toString());
    }
}
