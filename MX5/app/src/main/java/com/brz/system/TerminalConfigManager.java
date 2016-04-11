package com.brz.system;

import com.brz.basic.Basic;
import com.brz.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by macro on 16/4/11.
 */
public class TerminalConfigManager {
    private static volatile TerminalConfigManager mInstance;

    private TerminalConfigManager() {

    }

    public static TerminalConfigManager getInstance() {
        TerminalConfigManager manager = mInstance;
        if (manager == null) {
            synchronized (TerminalConfigManager.class) {
                manager = mInstance;
                if (manager == null) {
                    manager = new TerminalConfigManager();
                    mInstance = manager;
                }
            }
        }

        return mInstance;
    }

    public TerminalConfig getTerminalConfig() {
        return JsonUtil.fromJson(readFile(Basic.TERMINAL_CONFIG_PATH), TerminalConfig.class);
    }

    public void updateTerminalConfig(TerminalConfig config) {
        writeFile(Basic.TERMINAL_CONFIG_PATH, JsonUtil.toJson(config));
    }

    private void writeFile(String path, String json) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);
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
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
