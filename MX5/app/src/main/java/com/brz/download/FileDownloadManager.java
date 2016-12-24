package com.brz.download;

import android.text.TextUtils;
import android.util.Log;

import com.brz.http.bean.Transmission;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by macro on 2016/12/22.
 */

public class FileDownloadManager {
    private static final String TAG = "FileDownloadManager";
    private Executor mExecutor;
    private Transmission mTransmission = new Transmission();
    private String mPublishid;
    private List<Transmission.Item> mItems = new ArrayList<>();

    public interface Response {
        void onSuccess(File result);

        void onFailure(Throwable e);
    }

    private static class SingletonHolder {
        static FileDownloadManager instance = new FileDownloadManager();
    }

    private FileDownloadManager() {
        mExecutor = new PriorityExecutor(2, true);
        mTransmission.setFiles(mItems);
    }

    public static FileDownloadManager getInstance() {
        return SingletonHolder.instance;
    }

    public void setPublishId(String id) {
        mItems.clear();
        mPublishid = id;
    }

    public String getPublishId() {
        return mPublishid;
    }

    public Transmission getTransmission() {
        long totalSize = 0;
        long completeSize = 0;

        for (int i = 0; i < mItems.size(); i++) {
            Transmission.Item item = mItems.get(i);
            totalSize += Integer.parseInt(item.getFileSize());
            completeSize += Integer.parseInt(item.getFileCompleted());
        }

        mTransmission.setTotalSize(String.valueOf(totalSize));
        mTransmission.setCompletedSize(String.valueOf(completeSize));

        Log.d(TAG, mTransmission.toString());
        return mTransmission;
    }

    public Callback.Cancelable performRequest(String srcUrl, String targetPath,
                                              final Response response) {
        if (TextUtils.isEmpty(srcUrl) || TextUtils.isEmpty(targetPath)) return null;

        RequestParams params = new RequestParams(srcUrl);
        params.setAutoResume(true);
        params.setAutoRename(true);
        params.setSaveFilePath(targetPath);
        params.setExecutor(mExecutor);
        params.setCancelFast(true);
        params.setMaxRetryCount(3);

        String fileName = targetPath.substring(targetPath.lastIndexOf('/') + 1);

        Log.d(TAG, "fileName: " + fileName);
        final Transmission.Item item = new Transmission.Item();
        try {
            item.setFileName(fileName);
            item.setFileSigna(fileName.split("\\.")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mItems.add(item);

        return x.http().get(params, new DownloadCallback() {
            @Override
            public void call(Object result) {

            }

            @Override
            public void onWaiting() {
                item.setStatue(Transmission.Item.STATE_WAITING);
            }

            @Override
            public void onStarted() {
                item.setStatue(Transmission.Item.STATE_DOWNLOADING);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                item.setFileSize(String.valueOf(total));
                item.setFileCompleted(String.valueOf(current));
//                Log.d(TAG, "total: " + total + " current: " + current);
            }

            @Override
            public void onSuccess(File result) {
                item.setStatue(Transmission.Item.STATE_COMPLETE);
                if (response != null) {
                    response.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                item.setStatue(Transmission.Item.STATE_FAILURE);
                if (response != null) {
                    response.onFailure(ex);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
