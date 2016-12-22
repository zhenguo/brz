package com.brz.service;

import android.util.Log;
import com.brz.http.bean.Cmd;
import com.brz.programme.Programme;
import com.brz.programme.Theme;
import com.brz.system.TerminalConfigManager;
import java.io.File;
import java.util.List;

/**
 * Created by macro on 16/12/8.
 */

public class CmdProcessor {
  private static final String TAG = "CmdProcessor";

  private String fileServ =
      TerminalConfigManager.getInstance().getTerminalConfig().getFileServer() + File.separator;

  private static class SingletonHolder {
    private static CmdProcessor instance = new CmdProcessor();
  }

  public static CmdProcessor getInstance() {
    return SingletonHolder.instance;
  }

  public void process(Cmd cmd) {
    if (cmd == null) return;
    Log.d(TAG, "cmd: " + cmd.getCmdType());

    String type = cmd.getCmdType();
    switch (type) {
      case CmdType.UPDATE_PROGRAMME:
        Theme theme = cmd.getCmdData().getTheme();
        List<Programme> programmeList = cmd.getCmdData().getPrograms();

        new UpdateProgrammeProcessor(theme.getUrl(), programmeList);

        break;
      default:
        break;
    }
  }
}
