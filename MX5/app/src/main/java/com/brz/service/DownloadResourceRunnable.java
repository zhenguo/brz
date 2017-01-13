package com.brz.service;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.brz.basic.Basic;
import com.brz.download.FileDownloadManager;
import com.brz.http.bean.Cmd;
import com.brz.http.bean.RequestBody;
import com.brz.http.bean.Status;
import com.brz.http.bean.SystemState;
import com.brz.http.service.TerminalService;
import com.brz.mx5.ResourceManager;
import com.brz.programme.Programme;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeDefine;
import com.brz.programme.ProgrammeManager;
import com.brz.programme.Theme;
import com.brz.system.TerminalConfigManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * Created by macro on 16/12/9.
 */

public class DownloadResourceRunnable implements Runnable {
    private static final String TAG = "DownloadResourceRunnabl";
    private String mBaseUrl =
            TerminalConfigManager.getInstance().getTerminalConfig().getFileServer() + File.separator;

    private String mPushlibId;
    private String mThemeUrl;
    private List<Programme> mProgrammeUrl;
    private DownloadResourceCallback mCallback;

    public DownloadResourceRunnable(String publishId, String themeUrl, List<Programme> programmesUrl,
                                    DownloadResourceCallback callback) {

        mPushlibId = publishId;
        mThemeUrl = themeUrl;
        mProgrammeUrl = programmesUrl;
        mCallback = callback;
    }

    interface DownloadResourceCallback {
        void onSuccess();

        void onFailure();
    }

    @Override
    public void run() {
        startDownload(mThemeUrl, mProgrammeUrl);
    }

    private static class DownloadInfo {
        String mediaType;
        String url;
        String fileSigna;
        String fileName;
    }

    private List<DownloadInfo> checkMedia() {
        List<DownloadInfo> result = new ArrayList<>();

        String preFileSigna = null;

        Theme theme = ProgrammeManager.getInstance().getTheme(Basic.THEME_TEMP_PATH);
        for (int i = 0; i < theme.getDefaults().size(); i++) {
            Programme programme = theme.getDefaults().get(i);
            ProgrammeContext context =
                    ProgrammeManager.getInstance().getContext(programme.getFileSigna() + ".json");
            List<ProgrammeContext.ContentItem> items = context.getContent();
            for (int j = 0; j < items.size(); j++) {
                ProgrammeContext.ContentItem item = items.get(j);
                List<ProgrammeContext.Item> itemList = item.getRegion().getItem();
                if (itemList == null) // 有些region里面不包含item.
                    continue;
                for (int k = 0; k < itemList.size(); k++) {

                    DownloadInfo info = new DownloadInfo();
                    info.mediaType = item.getRegion().getType();
                    info.url = itemList.get(k).getUrl();
                    info.fileSigna = itemList.get(k).getFileSigna();
                    info.fileName = itemList.get(k).getSrc();

//                    Log.d(TAG, "fileSigna: " + info.fileSigna);
                    if (!TextUtils.equals(info.fileSigna, preFileSigna)) {
                        result.add(info);
                        preFileSigna = info.fileSigna;
                    }
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

    public boolean startDownload(String themeUrl, List<Programme> programmesUrl) {
        final CountDownLatch latch = new CountDownLatch(programmesUrl.size() + 1);
        FileDownloadManager.getInstance()
                .performRequest(mBaseUrl + themeUrl, Basic.RESOURCE_PATH + File.separator + "theme.temp",
                        new FileDownloadManager.Response() {
                            @Override
                            public void onSuccess(File result) {
                                latch.countDown();
                            }

                            @Override
                            public void onFailure(Throwable e) {
                                e.printStackTrace();
                            }
                        });

        for (int i = 0; i < programmesUrl.size(); i++) {
            Programme programme = programmesUrl.get(i);
            FileDownloadManager.getInstance()
                    .performRequest(mBaseUrl + programme.getUrl(),
                            Basic.RESOURCE_PATH + File.separator + programme.getSigna() + ".json",
                            new FileDownloadManager.Response() {
                                @Override
                                public void onSuccess(File result) {
                                    latch.countDown();
                                }

                                @Override
                                public void onFailure(Throwable e) {
                                    e.printStackTrace();
                                }
                            });
        }

        try {
            Log.d(TAG, "tid: " + Process.myTid());

            latch.await();
            downloadMedia();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private int preProcessing(List<DownloadInfo> list) {
        int latch = list.size();

        for (int i = 0; i < list.size(); i++) {
            DownloadInfo info = list.get(i);

            String src = mBaseUrl + info.url;
            String target =
                    getFilePath(ProgrammeDefine.VIDEO_REGION, info.fileSigna, getFileSuffix(info.fileName));

            if (new File(target).exists()) {
                latch--;
                continue;
            }
        }

        return latch;
    }

    private void uploadProgress() {
        Status status = new Status();
        status.setSystemState(SystemState.DOWNLOADING.getValue());
        status.setTransmission(FileDownloadManager.getInstance().getTransmission());

        Cmd cmd1 = new Cmd();
        cmd1.setCmdType(CmdType.TRANSMISSIONS);

        RequestBody body = new RequestBody(ResourceManager.getInstance().getTerminalConfig().getTermId(), status, cmd1);
        body.setPublishid(FileDownloadManager.getInstance().getPublishId());

        TerminalService.getInstance().postTransmission(body);
    }

    private void downloadMedia() {
        List<DownloadInfo> downloadInfos = checkMedia();

        final CountDownLatch latch = new CountDownLatch(downloadInfos.size());

        FileDownloadManager.getInstance().setPublishId(mPushlibId);

        for (int i = 0; i < downloadInfos.size(); i++) {
            DownloadInfo info = downloadInfos.get(i);

            String src = mBaseUrl + info.url;
            String target =
                    getFilePath(info.mediaType, info.fileSigna, getFileSuffix(info.fileName));

//            if (new File(target).exists()) {
//                Log.d(TAG, "skip file: " + target);
//                latch.countDown();
//                continue;
//            }

            FileDownloadManager.getInstance()
                    .performRequest(src, target, new FileDownloadManager.Response() {
                        @Override
                        public void onSuccess(File result) {
                            latch.countDown();
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }

        Timer timer = new Timer("post_progress_thread");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                uploadProgress();
            }
        }, 15 * 1000, 15 * 1000);

        Log.d(TAG, "start download media");
        try {
            Log.d(TAG, "tid: " + Process.myTid());
            latch.await();

            uploadProgress(); // Download complete, upload 100%

            timer.cancel();
            timer.purge();

            new File(Basic.RESOURCE_PATH + File.separator + "theme.temp").renameTo(
                    new File(Basic.RESOURCE_PATH + File.separator + "theme.json"));
            Log.d(TAG, "it's time to change programme");
            if (mCallback != null) {
                mCallback.onSuccess();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
