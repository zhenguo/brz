package com.brz.download;

import android.util.Log;

/**
 * Created by macro on 16/12/8.
 */

public class FileDownloadManager {

  private static final String TAG = "FileDownloadManager";

  private static class SingletonHolder {
    private static FileDownloadManager instance = new FileDownloadManager();
  }

  private FileDownloadManager() {

  }

  public static FileDownloadManager getInstance() {
    return SingletonHolder.instance;
  }

  public void addDownload(String url, String targetFileName, ProgressListener listener) {
    Log.d(TAG, "addDownload: " + url + " targetFileName: " + targetFileName);
    FileDownloader downloader = new FileDownloader(url, targetFileName, listener);
    DiskIOExecutor.getInstance().execute(downloader);
  }
}
