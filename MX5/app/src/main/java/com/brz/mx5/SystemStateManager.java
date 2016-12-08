package com.brz.mx5;

import com.brz.http.bean.SystemState;

/**
 * Created by macro on 16/12/8.
 */

public class SystemStateManager {
  private static class SingletonHolder {
    private static SystemStateManager instance = new SystemStateManager();
  }

  private SystemStateManager() {
    mState = SystemState.ONLINE;
  }

  private SystemState mState;

  public static SystemStateManager getInstance() {
    return SingletonHolder.instance;
  }

  public void setSystemState(SystemState state) {
    mState = state;
  }

  public SystemState getSystemState() {
    return mState;
  }
}
