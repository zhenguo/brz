package com.brz.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhenhua on 16-4-14.
 */
public class ImageAnimatorTest extends SurfaceView {
    private boolean isPause = true;
    private SurfaceHolder holder = null;
    private ImageAnimator imageAnimator = null;
    private Bitmap bitmap = null;
    private int mWidth;
    private int mHeight;
    private File flower_file = null;
    private BitmapInfo params = null;
    private BitmapInfo info = null;
    private boolean isRunning = false;
    public boolean isChangePro = false;
    public mOneThread mImgThread = null;
    public int mode = 0;

    public ImageAnimatorTest(Context context) {
        super(context);
    }

    public ImageAnimatorTest(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public ImageAnimatorTest(Context context, AttributeSet attr, int def_style) {
        super(context, attr, def_style);
    }

    public void Init() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.holder = this.getHolder();
        this.holder.addCallback(callback);
        this.holder.setFormat(PixelFormat.TRANSLUCENT);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);

        flower_file = new File(Environment.getExternalStorageDirectory(), "Download/flower.jpg");
        params = new BitmapInfo();
    }

    public void InitParams(String file, int width, int height) {
        params.mFileName = file;
        params.mWidth = width;
        params.mHeight = height;
    }

    private Callback callback = new Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (imageAnimator == null) {
                imageAnimator = new ImageAnimator();
            }
            new BitmapAsyncWork().execute(params);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    };

    private class BitmapInfo {
        int mWidth;
        int mHeight;
        String mFileName;
    }

    public void setMainSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    private Bitmap decodeSampledBitmapFromFile(String fileName, int reqWidth, int reqHeight) {

        if (fileName == null)
            return null;

        Bitmap resizedBitmap, BitmapOrg;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        resizedBitmap = null;
        BitmapOrg = null;

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is;

        try {
            File f = new File(fileName);
            is = new FileInputStream(f);
            BitmapOrg = BitmapFactory.decodeStream(is, null, opt);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        if (BitmapOrg == null)
            return null;
        // BitmapOrg = BitmapFactory.decodeFile(fileName);
        if ((options.outWidth != reqWidth) || (options.outHeight != reqHeight)) {
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;
            if (outWidth != 0 && outHeight != 0 && reqWidth != 0
                    && reqHeight != 0) {
                float scaleWidth = ((float) reqWidth) / outWidth;
                float scaleHeight = ((float) reqHeight) / outHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // if you want to rotate the Bitmap
                // matrix.postRotate(45);
                resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, outWidth,
                        outHeight, matrix, true);
            }
        }
        if (resizedBitmap != null) {
            BitmapOrg.recycle();
            return resizedBitmap;
        } else
            return BitmapOrg;
    }

    private class BitmapAsyncWork extends
            AsyncTask<BitmapInfo, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(BitmapInfo... params) {
            info = params[0];
            if (info.mFileName == null)
                return null;

            bitmap = decodeSampledBitmapFromFile(info.mFileName, info.mWidth, info.mHeight);

            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            isRunning = true;
            InitImageThread(mode);
            mImgThread.start();
        }
    }

    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
            isPause = (visibility == GONE || visibility == INVISIBLE);
        }
    };

    protected void onVisibilityChanged(View changedView,
                                       int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        isPause = (visibility == GONE || visibility == INVISIBLE);
    };

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        isPause = (visibility == GONE || visibility == INVISIBLE);
    }

    private class mOneThread extends Thread {
        public mOneThread(Runnable runnable) {
            super(runnable);
        }

        public void quit() {
            isRunning = false;
            interrupt();
        }
    }

    public void InitImageThread(final int mode) {
        mImgThread = new mOneThread(new Runnable() {
            @Override
            public void run() {
                if (!flower_file.exists()) {
                    Toast.makeText(getContext(), "图片文件不存在..", Toast.LENGTH_SHORT).show();
                }

                bitmap = decodeSampledBitmapFromFile(flower_file.getAbsolutePath(), mWidth, mHeight);

                if (bitmap != null) {

                    switch (mode) {
                        case 0:
                            imageAnimator.nominal(holder, bitmap, mWidth, mHeight);
                            break;
                        case 1:
                            imageAnimator.window_left(holder, bitmap, mWidth, mHeight);
                            break;
                        case 2:
                            imageAnimator.window_right(holder, bitmap, mWidth, mHeight);
                            break;
                        case 3:
                            imageAnimator.window_down(holder, bitmap, mWidth, mHeight);
                            break;
                        case 4:
                            imageAnimator.window_up(holder, bitmap, mWidth, mHeight);
                            break;
                        case 5:
                            imageAnimator.black_away(holder, bitmap, mWidth, mHeight);
                            break;
                        case 6:
                            imageAnimator.black_in(holder, bitmap, mWidth, mHeight);
                            break;
                        case 7:
                            imageAnimator.clean_left(holder, bitmap, mWidth, mHeight);
                            break;
                        case 8:
                            imageAnimator.clean_right(holder, bitmap, mWidth, mHeight);
                            break;
                        default:
                            break;

                    }


                }


            }
        });
    }

    public void clearDraw() {
        // Canvas canvas = holder.lockCanvas(null);
        // canvas.drawColor(Color.BLACK);
        // holder.unlockCanvasAndPost(canvas);
        imageAnimator.isRunning = false;
        if (imageAnimator != null) {
            //imageAnimator.quit();
            imageAnimator = null;
        }
    }


}
