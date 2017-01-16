package com.brz.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.brz.imageloader.ImageResizer2;
import com.brz.imageloader.ImageWorker2;
import com.brz.listener.OnCompletionListener;
import com.brz.mx5.FullscreenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

/**
 * Created by macro on 16/5/26.
 */
public class ImagePlayer extends SurfaceView {

    private static final String TAG = "ImagePlayer";
    private Logger mLogger = Logger.getLogger(TAG);
    private ImageResizer2 mImageResizer2;
    private Bitmap mCurrentBitmap;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                if (mImageOptionsBlockingDeque.isEmpty()) {
                    if (mOnCompletionListener != null) {
                        if (!mOnCompletionListener.onCompletion(OnCompletionListener.LEVEL_IMAGE)) {
                            inflateQueue();
                        } else {
                            mLogger.info("time to switch to next programme.");
                            return;
                        }
                    }
                }

                final ImageOptions options = mImageOptionsBlockingDeque.take();

                mImageResizer2.loadImage(options.filePath, new ImageWorker2.OnImageLoadedListener() {
                    @Override
                    public void onImageLoaded(boolean success, BitmapDrawable drawable) {
                        mCurrentBitmap = drawable.getBitmap();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Log.d(TAG, "mCurrentBitmap: " + mCurrentBitmap.getAllocationByteCount() / 1024);
                        } else {
                            Log.d(TAG, "mCurrentBitmap: " + mCurrentBitmap.getByteCount() / 1024);
                        }

                        showPicture(Integer.parseInt(options.getTransmode()));
//                    mLogger.info("sleep: " + options.getDuration());
                        int duration = options.getDuration();
                        if (duration == 0) {
                            duration = 10;
                        }
                        mHandler.sendEmptyMessageDelayed(0, duration * 1000);
                    }
                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static class ImageOptions {
        private String filePath;
        private String transmode;
        private double transittime;
        private int duration;

        public String getFilePath() {
            return filePath;
        }

        public String getTransmode() {
            return transmode;
        }

        public void setTransmode(String transmode) {
            this.transmode = transmode;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public double getTransittime() {
            return transittime;
        }

        public void setTransittime(double transittime) {
            this.transittime = transittime;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    private List<ImageOptions> mImages = new ArrayList<>();
    private BlockingDeque<ImageOptions> mImageOptionsBlockingDeque = new LinkedBlockingDeque<>();
    private ImageAnimator mImageAnimator = new ImageAnimator();
    private OnCompletionListener mOnCompletionListener;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mImageResizer2.setImageSize(width, height);
            mHandler.sendEmptyMessage(0);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mLogger.info("surfaceDestroyed");
        }
    };

    public void setOnCompletionListener(OnCompletionListener listener) {
        mOnCompletionListener = listener;
    }

    public ImagePlayer(Context context) {
        super(context);
        init();
    }

    public ImagePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImagePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImagePlayer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void inflateQueue() {
        for (int i = 0; i < mImages.size(); i++) {
            mImageOptionsBlockingDeque.add(mImages.get(i));
        }
    }

    public void setImages(List<ImageOptions> images) {
        mImages = images;
        inflateQueue();
    }

    private void init() {
        mImageResizer2 = FullscreenActivity.getIntance().getImageLoader();

        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        mSurfaceHolder.addCallback(mCallback);
    }

    public void quit() {
        mHandler.removeCallbacksAndMessages(null);
        mCurrentBitmap = null;
    }

    private void showPicture(int mode) {

        mLogger.info("mode: " + mode);

        RectF rectF = new RectF(getLeft(), getTop(), getRight(), getBottom());

        switch (mode) {
            case 0:
                mImageAnimator.nominal(mSurfaceHolder, mCurrentBitmap, rectF);
                break;
            case 1:
                mImageAnimator.window_left(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 2:
                mImageAnimator.window_right(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 3:
                mImageAnimator.window_down(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 4:
                mImageAnimator.window_up(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 5:
                mImageAnimator.black_away(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 6:
                mImageAnimator.black_in(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 7:
                mImageAnimator.clean_left(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            case 8:
                mImageAnimator.clean_right(mSurfaceHolder, mCurrentBitmap, getWidth(), getHeight());
                break;
            default:
                break;

        }
    }
}
