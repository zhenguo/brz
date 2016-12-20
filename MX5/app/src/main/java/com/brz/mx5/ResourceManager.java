package com.brz.mx5;

import com.brz.basic.Basic;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeManager;
import com.brz.programme.Theme;
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
  private TerminalConfigManager mTerminalConfigManager;
  private TerminalConfig mTerminalConfig;

  private ProgrammeManager mProgrammeManager;
  private Theme mTheme;

  public static ResourceManager getInstance() {
    return SingletonHolder.instance;
  }

  public ResourceManager() {

  }

  public void init() {
    // 读取系统配置
    mLogger.info("read terminal configurations...");
    mTerminalConfigManager = TerminalConfigManager.getInstance();
    mTerminalConfig = mTerminalConfigManager.getTerminalConfig();
    mLogger.info(mTerminalConfig.toString());

    // 读取节目单信息
    mLogger.info("read programmes info...");
    mProgrammeManager = ProgrammeManager.getInstance();
    mTheme = mProgrammeManager.getTheme(Basic.THEME_PATH);
    mLogger.info("theme: " + mTheme.toString());

    for (int i = 0; i < mTheme.getDefaults().size(); i++) {
      mLogger.info("programme: " + mTheme.getDefaults().get(i).toString());
    }
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
