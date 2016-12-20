package com.brz.basic;

import android.os.Environment;

/**
 * Created by macro on 16/4/11.
 */
public class Basic {
    public final static String RESOURCE_PATH = Environment.getExternalStorageDirectory().getPath() + "/resource/";
    public final static String TERMINAL_CONFIG_PATH = RESOURCE_PATH + "system.json";
    public final static String THEME_PATH = RESOURCE_PATH + "theme.json";
    public final static String THEME_TEMP_PATH = RESOURCE_PATH + "theme.temp";
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
}
