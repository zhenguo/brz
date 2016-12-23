package com.brz.download;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by macro on 2016/12/22.
 */

public abstract class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>, Callback.Callable {
}
