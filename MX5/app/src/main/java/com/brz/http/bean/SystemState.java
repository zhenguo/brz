package com.brz.http.bean;

/**
 * Created by macro on 16/7/16.
 */
public enum  SystemState {

    BOOT_COMPLETE("1000"),
    WORKING("1001"),
    SHUTDOWN("1002"),
    SLEEPING("1003"),
    STORAGE_NOT_FIND("1004"),
    ID_NOT_ASSIGN("1005"),
    ONLINE("1006"),
    OFFLINE("1007"),
    DOWNLOADING("1008"),
    PREDOWNLOAD("1009"),
    DOWNLOADPAUSED("1010");

    private String mValue;

    SystemState(String code) {
        mValue = code;
    }

    public static String getValue(SystemState state) {
        return state.mValue;
    }
}
