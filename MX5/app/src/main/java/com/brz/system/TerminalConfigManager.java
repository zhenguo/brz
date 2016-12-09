package com.brz.system;

import com.brz.basic.Basic;
import com.brz.utils.JsonUtil;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by macro on 16/4/11.
 */
public class TerminalConfigManager {

  private static final String TAG = "TerminalConfigManager";
  private TerminalConfig mConfig;

  private static class SingletonHolder {
    private static TerminalConfigManager instance = new TerminalConfigManager();
  }

  private TerminalConfigManager() {

  }

  public static TerminalConfigManager getInstance() {
    return SingletonHolder.instance;
  }

  public TerminalConfig getTerminalConfig() {
    if (mConfig == null) {
      mConfig = JsonUtil.fromJson(readFile(Basic.TERMINAL_CONFIG_PATH), TerminalConfig.class);
    }

    return mConfig;
  }

  public void updateTerminalConfig() {
    writeFile(Basic.TERMINAL_CONFIG_PATH, JsonUtil.toJson(mConfig));
  }

  private void writeFile(String path, String json) {
    JsonWriter writer = null;

    try {
      writer = new JsonWriter(new FileWriter(path));
      writer.jsonValue(json);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String readFile(String path) {
    File file = new File(path);
    BufferedReader reader = null;
    StringBuilder builder = new StringBuilder();

    try {
      reader = new BufferedReader(new FileReader(file));
      String tempString;
      while ((tempString = reader.readLine()) != null) {
        builder.append(tempString);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return builder.toString();
  }
}
