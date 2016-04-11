package com.brz.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by macro on 16/4/11.
 */
public class JsonUtil {

    private final static Gson GSON = new GsonBuilder().serializeNulls().create();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }
}
