package com.brz.download;

/**
 * Created by macro on 16/12/8.
 */
public interface ProgressListener {
  void update(long bytesRead, long contentLength, boolean done);
}
