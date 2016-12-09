package com.brz.service;

import android.util.Log;
import com.brz.basic.Basic;
import com.brz.programme.Programme;
import com.brz.system.TerminalConfigManager;
import com.brz.utils.FileUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by macro on 16/12/9.
 */

public class UpdateProgrammeProcessor {
  private static final String TAG = "UpdateProgrammeProcesso";
  private String mBaseUrl =
      TerminalConfigManager.getInstance().getTerminalConfig().getFileServer() + File.separator;
  private List<BaseDownloadTask> mTasks = new ArrayList<>();
  private boolean mThemeSuccess;
  private boolean mProgrammeSuccess;
  private boolean mResouceSuccess;

  private final FileDownloadListener mQueueTarget = new FileDownloadListener() {
    @Override protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      Log.d(TAG, "pending: " + task.getTargetFilePath());
    }

    @Override protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      Log.d(TAG, "progress: " + task.getTargetFilePath());
    }

    @Override protected void completed(BaseDownloadTask task) {
      Log.d(TAG, "completed: " + task.getTargetFilePath());
    }

    @Override protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      Log.d(TAG, "paused: " + task.getTargetFilePath());
    }

    @Override protected void error(BaseDownloadTask task, Throwable e) {
      Log.d(TAG, "error: " + task.getTargetFilePath());
    }

    @Override protected void warn(BaseDownloadTask task) {
      Log.d(TAG, "warn: " + task.getTargetFilePath());
    }
  };

  private final FileDownloadQueueSet mQueueSet = new FileDownloadQueueSet(mQueueTarget);

  public UpdateProgrammeProcessor(String themeUrl, List<Programme> programmesUrl) {
    mTasks.add(FileDownloader.getImpl()
        .create(mBaseUrl + themeUrl)
        .setPath(Basic.RESOURCE_PATH + File.separator + "theme.temp"));

    for (int i = 0; i < programmesUrl.size(); i++) {
      Programme programme = programmesUrl.get(i);
      mTasks.add(FileDownloader.getImpl()
          .create(mBaseUrl + programme.getUrl())
          .setPath(Basic.RESOURCE_PATH + File.separator + FileUtil.getFileName(programme.getUrl())));
    }
    mQueueSet.setAutoRetryTimes(3);
    mQueueSet.downloadTogether(mTasks);
    mQueueSet.start();
    Log.d(TAG, "start download");
  }
}
