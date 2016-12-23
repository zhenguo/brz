package com.brz.mx5;

import com.brz.system.TerminalConfig;
import com.brz.system.TerminalConfigManager;

import java.util.logging.Logger;

/**
 * Created by macro on 16/11/21.
 */

public class ResourceManager {
  private static final String TAG = "ResourceManager";

  private static class SingletonHolder {
    static ResourceManager instance = new ResourceManager();
  }

  private Logger mLogger = Logger.getLogger(TAG);
  private TerminalConfig mTerminalConfig;

  public static ResourceManager getInstance() {
    return SingletonHolder.instance;
  }

  public ResourceManager() {

  }

  public boolean init() {
    // 读取系统配置
    mLogger.info("read terminal configurations...");
    mTerminalConfig = TerminalConfigManager.getInstance().getTerminalConfig();
    if (mTerminalConfig == null) {
      return false;
    }

    mLogger.info(mTerminalConfig.toString());
    return true;
  }

  public TerminalConfig getTerminalConfig() {
    return mTerminalConfig;
  }
}
