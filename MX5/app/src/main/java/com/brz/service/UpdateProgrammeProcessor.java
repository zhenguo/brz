package com.brz.service;

import android.util.Log;
import com.brz.basic.Basic;
import com.brz.mx5.ResourceManager;
import com.brz.programme.Programme;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeDefine;
import com.brz.programme.ProgrammeManager;
import com.brz.programme.Theme;
import com.brz.system.TerminalConfigManager;
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
  private static int mQueueSize = 0;

  private List<BaseDownloadTask> mTasks = new ArrayList<>();

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
      e.printStackTrace();
      Log.d(TAG, "error: " + task.getTargetFilePath() );
    }

    @Override protected void warn(BaseDownloadTask task) {
      Log.d(TAG, "warn: " + task.getTargetFilePath());
    }
  };

  private static class DownloadInfo {
    String mediaType;
    String url;
    String fileSigna;
    String fileName;
  }

  private List<DownloadInfo> checkMedia() {
    List<DownloadInfo> result = new ArrayList<>();

    Theme theme = ProgrammeManager.getInstance().getTheme(Basic.THEME_TEMP_PATH);
    for (int i = 0; i < theme.getDefaults().size(); i++) {
      Programme programme = theme.getDefaults().get(i);
      ProgrammeContext context =
          ResourceManager.getInstance().getProgrammeContext(programme.getFileSigna() + ".json");
      List<ProgrammeContext.ContentItem> items = context.getContent();
      for (int j = 0; j < items.size(); j++) {
        ProgrammeContext.ContentItem item = items.get(i);
        List<ProgrammeContext.Item> itemList = item.getRegion().getItem();
        for (int k = 0; k < itemList.size(); k++) {

          DownloadInfo info = new DownloadInfo();
          info.mediaType = item.getRegion().getType();
          info.url = itemList.get(i).getUrl();
          info.fileSigna = itemList.get(i).getFileSigna();
          info.fileName = itemList.get(i).getSrc();
        }
      }
    }

    return result;
  }

  private String getFileSuffix(String fileName) {
    return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
  }

  private String getFilePath(String resourceType, String fileSigna, String suffix) {
    String filePath = null;
    switch (resourceType) {
      case ProgrammeDefine.BACKGROUND_REGION:
        String builder = Basic.RESOURCE_PATH + "BG/" + fileSigna + "." + suffix;
        filePath = builder.trim();
        break;
      case ProgrammeDefine.VIDEO_REGION:
        filePath = Basic.RESOURCE_PATH + "VIDEO/" + fileSigna + "." + suffix;
        break;
      case ProgrammeDefine.PICTURE_REGION:
        filePath = Basic.RESOURCE_PATH + "IMAGE/" + fileSigna + "." + suffix;
        break;
      case ProgrammeDefine.TEXT_REGION:
        filePath = Basic.RESOURCE_PATH + "TEXT/" + fileSigna + "." + suffix;
      default:
        break;
    }

    return filePath;
  }

  private final BaseDownloadTask.FinishListener mMediaFinishListener =
      new BaseDownloadTask.FinishListener() {
        @Override public void over(BaseDownloadTask task) {
          Log.d(TAG, "over: " + task.getTargetFilePath());
        }
      };

  private final BaseDownloadTask.FinishListener mFinishListener =
      new BaseDownloadTask.FinishListener() {
        @Override public void over(BaseDownloadTask task) {
          --mQueueSize;
          if (mQueueSize == 0) {
            // all file download finished, start parse and download media
            downloadMedia();
          }
        }
      };

  private final FileDownloadQueueSet mQueueSet = new FileDownloadQueueSet(mQueueTarget);

  public UpdateProgrammeProcessor(String themeUrl, List<Programme> programmesUrl) {
    mTasks.add(FileDownloader.getImpl()
        .create(mBaseUrl + themeUrl)
        .addFinishListener(mFinishListener)
        .setPath(Basic.RESOURCE_PATH + File.separator + "theme.temp"));

    mQueueSize = programmesUrl.size() + 1;

    for (int i = 0; i < programmesUrl.size(); i++) {
      Programme programme = programmesUrl.get(i);
      mTasks.add(FileDownloader.getImpl()
          .create(mBaseUrl + programme.getUrl())
          .addFinishListener(mFinishListener)
          .setPath(Basic.RESOURCE_PATH + File.separator + programme.getSigna() + ".json"));
    }
    mQueueSet.setAutoRetryTimes(3);
    mQueueSet.downloadTogether(mTasks);
    mQueueSet.start();
    Log.d(TAG, "start download: " + mQueueSize);
  }

  private void downloadMedia() {
    Log.d(TAG, "downloadMedia");
    List<DownloadInfo> downloadInfos = checkMedia();
    Log.d(TAG, "downloadInfos: " + downloadInfos.size());

    for (int i = 0; i < downloadInfos.size(); i++) {
      DownloadInfo info = downloadInfos.get(i);
      mTasks.add(FileDownloader.getImpl()
          .create(mBaseUrl + info.url)
          .addFinishListener(mMediaFinishListener)
          .setPath(getFilePath(ProgrammeDefine.VIDEO_REGION, info.fileSigna,
              getFileSuffix(info.fileName))));
    }

    mQueueSet.setAutoRetryTimes(3);
    mQueueSet.downloadTogether(mTasks);
    mQueueSet.start();
    Log.d(TAG, "start download media");
  }
}
