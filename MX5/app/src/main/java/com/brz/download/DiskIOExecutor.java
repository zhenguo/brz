package com.brz.download;

import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by macro on 16/8/26.
 */
public class DiskIOExecutor {

    private static class SingletoneHolder {
        public static DiskIOExecutor instance = new DiskIOExecutor();
    }

    private ExecutorService mExecutorService;

    public static DiskIOExecutor getInstance() {
        return SingletoneHolder.instance;
    }

    private DiskIOExecutor() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        mExecutorService.execute(runnable);
    }

    @NonNull public <v> Future<v> execute(Callable<v> callable) {
        return mExecutorService.submit(callable);
    }

    public void closeExecutor() {
        mExecutorService.shutdown();
    }
}
