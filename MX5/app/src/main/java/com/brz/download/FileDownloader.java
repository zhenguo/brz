package com.brz.download;

import android.util.Log;
import com.brz.basic.Basic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by macro on 16/12/8.
 */

public class FileDownloader implements Runnable {
  private static final String TAG = "FileDownloader";
  private OkHttpClient mClient;
  private Request mRequest;
  private String mTargetFileName;
  private ProgressListener mProgressListener;

  public FileDownloader(String url, String targetFileName, ProgressListener listener) {
    mRequest = new Request.Builder().url(url).build();
    mTargetFileName = targetFileName;
    mProgressListener = listener;

    mClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
            .body(new ProgressResponseBody(originalResponse.body(), mProgressListener))
            .build();
      }
    }).build();
  }

  private boolean writeResponseBodyToDisk(ResponseBody body, String targetFileName) {
    try {
      File futureStudioIconFile = new File(Basic.RESOURCE_PATH + File.separator + targetFileName);

      InputStream inputStream = null;
      OutputStream outputStream = null;

      try {
        byte[] fileReader = new byte[4096];

        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;

        inputStream = body.byteStream();
        outputStream = new FileOutputStream(futureStudioIconFile);

        while (true) {
          int read = inputStream.read(fileReader);

          if (read == -1) {
            break;
          }

          outputStream.write(fileReader, 0, read);

          fileSizeDownloaded += read;

          Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
        }

        outputStream.flush();

        return true;
      } catch (IOException e) {
        return false;
      } finally {
        if (inputStream != null) {
          inputStream.close();
        }

        if (outputStream != null) {
          outputStream.close();
        }
      }
    } catch (IOException e) {
      return false;
    }
  }

  private void startDownload() {

    try {
      Response response = mClient.newCall(mRequest).execute();
      if (response.isSuccessful()) {
        writeResponseBodyToDisk(response.body(), mTargetFileName);
      } else {

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public void run() {
    startDownload();
  }
}
