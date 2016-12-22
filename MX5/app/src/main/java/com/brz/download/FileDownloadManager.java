package com.brz.download;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.concurrent.Executor;

/**
 * Created by macro on 2016/12/22.
 */

public class FileDownloadManager {
    private Executor mExecutor;

    private static class SingletonHolder {
        static FileDownloadManager instance = new FileDownloadManager();
    }

    private FileDownloadManager() {
        mExecutor = new PriorityExecutor(2, true);
    }

    public static FileDownloadManager getInstance() {
        return SingletonHolder.instance;
    }

    public Callback.Cancelable performRequest(String srcUrl, String targetPath, DownloadCallback callback) {
        RequestParams params = new RequestParams(srcUrl);
        params.setAutoResume(true);
        params.setAutoRename(true);
        params.setSaveFilePath(targetPath);
        params.setExecutor(mExecutor);
        params.setCancelFast(true);
        params.setMaxRetryCount(3);

        return x.http().get(params, callback);
    }
}
