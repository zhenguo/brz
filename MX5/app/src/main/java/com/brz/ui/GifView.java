package com.brz.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.brz.mx5.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by zhenhua on 16-4-12.
 */
@SuppressLint("NewApi")
public class GifView extends View {

    private static final int DEFAULT_MOVIE_DURATION = 1000;
    private Movie mMovie = null;
    private long mMovieStart = 0;
    private int mCurrentAnimationTime = 0;
    private boolean mVisible = true;
    public String gif_file = "";
    float scaleH = 1f;
    float scaleW = 1f;

    public GifView(Context context) {
        this(context, null);
        this.setWillNotDraw(false);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.CustomTheme_gifViewStyle);
        this.setWillNotDraw(false);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setWillNotDraw(false);
        // setViewAttributes(context, attrs, defStyle);
    }

    public void setGifFile(String name) {
        this.gif_file = name;
        invalidateView();
    }

    @SuppressLint("NewApi")
    public void setViewAttributes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        {
            File file = new File(gif_file);

            if (file.exists()) {
                Log.v("cook", "file path " + file.getPath() + "  gif_file:" + gif_file);
                InputStream is = null;
                try {
                    is = new BufferedInputStream(new FileInputStream(file),
                            16 * 1024);
                    is.mark(16 * 1024);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mMovie = Movie.decodeStream(is);

                Log.d("mMovie", (mMovie == null) + "");
                invalidateView();
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("onDraw", "开始绘图");
        if (mMovie != null) {

            updateAnimationTime();
            drawMovieFrame(canvas);
            invalidateView();
        } else {
            Toast.makeText(getContext(), "gif 文件不存在！", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @SuppressLint("NewApi")
    private void invalidateView() {
        if (mVisible) {
            Log.d("invalidateView", "更新视图");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                postInvalidateOnAnimation();
            } else {
                invalidate();
            }
        }
    }

    private void updateAnimationTime() {
        long now = android.os.SystemClock.uptimeMillis();  //得到当前时间
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int dur = mMovie.duration();
        if (dur == 0) {
            dur = DEFAULT_MOVIE_DURATION;
        }
        mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);  //当前动画时间
    }

    @SuppressLint("NewApi")
    private void drawMovieFrame(Canvas canvas) {
        mMovie.setTime(mCurrentAnimationTime);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        // canvas.scale(mScale, mScale);
        // mMovie.draw(canvas, mLeft / mScale, mTop / mScale);
        // canvas.scale(0.5f, 0.5f);

        if (scaleW == 1f) {
            Log.d("drawMovieFrame", (scaleW == 1f) + "");
            if (getWidth() > 0 && getHeight() > 0) {
                Log.v("cook", "===>" + mMovie.width() + " " + getWidth());
                scaleW = (float) getWidth() / mMovie.width();
                scaleH = (float) getHeight() / mMovie.height();
            }
        }
        canvas.scale(scaleW, scaleH);
        mMovie.draw(canvas, 0, 0);
        canvas.restore();
    }

    @SuppressLint("NewApi")
    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        mVisible = screenState == SCREEN_STATE_ON;
        invalidateView();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mVisible = visibility == View.VISIBLE;
        invalidateView();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == View.VISIBLE;
        invalidateView();
    }


}
