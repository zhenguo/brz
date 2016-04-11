package com.brz.system;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by macro on 16/4/11.
 */
public class TerminalConfigManager {
    private TerminalConfig mTerminalConfig;
    private static volatile TerminalConfigManager mInstance;

    public static TerminalConfigManager getInstance(Context context) {
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

    public TerminalConfig getTerminalConfig(String jsonFilePath) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(readFile(jsonFilePath), TerminalConfig.class);
    }

    private String readFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
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
