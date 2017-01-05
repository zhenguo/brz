package com.brz.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * Created by zhenhua on 16-4-12.
 */
public class ImageAnimator {

    private int duration = 800;// 动画持续时间
    private final int leafNum = 20;// 百叶窗均分屏幕条数
    public boolean isRunning = true;

    public void nominal(SurfaceHolder holder, Bitmap bitmap) {

        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        Canvas canvas = new Canvas();
        try {
            canvas = holder.lockCanvas(null);

            if ((canvas == null))
                return;

            canvas.setDrawFilter(pdf);
            canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 清除画布
            canvas.save();
            if (bitmap == null) {
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                return;
            }
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != canvas) {
                try {
                    holder.unlockCanvasAndPost(canvas);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void window_left(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        // int dx = (1366-768)/2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        // int perHeight=maxHeight/leafNum;
        int perWidth = maxWidth / leafNum;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        Rect dst = new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 清除画布

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < leafNum; j++) {
                    // src.set(0,
                    // (int)((j+1)*perHeight-((float)runMills/(float)duration)*perHeight),
                    // maxWidth, (j+1)*perHeight);
                    // canvas.drawBitmap(bitmap[0], src, src, null);
                    src.set(j * perWidth,
                            0,
                            (int) (j * perWidth + ((float) runMills / (float) duration) * perWidth),
                            maxHeight);
                    dst.set(j * perWidth,
                            0,
                            (int) (j * perWidth + ((float) runMills / (float) duration) * perWidth),
                            maxHeight);
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, dst, null);
                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }
                if (!isRunning) {
                    break;
                }
            }
        }
    }

    public void window_down(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perHeight = maxHeight / leafNum;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        // Rect dst=new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 清除画布

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < leafNum; j++) {
                    src.set(0, (int) ((j + 1) * perHeight - ((float) runMills / (float) duration)
                            * perHeight), maxWidth, (j + 1) * perHeight);
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, src, null);

                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    public void window_right(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perWidth = maxWidth / leafNum;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 娓呴櫎鐢诲竷

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < leafNum; j++) {
                    src.set((int) ((j + 1) * perWidth - ((float) runMills / (float) duration)
                            * perWidth), 0, (j + 1) * perWidth, maxHeight);
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, src, null);
                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    public void window_up(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perHeight = maxHeight / leafNum;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 娓呴櫎鐢诲竷

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < leafNum; j++) {
                    src.set(0, j * perHeight, maxWidth, j * perHeight
                            + (int) (((float) runMills / (float) duration) * perHeight));
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, src, null);
                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    // 向外小黑块
    public void black_away(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perWidth = maxWidth / leafNum;
        if (perWidth < 1)
            perWidth = 1;
        int row = maxHeight / perWidth;
        if (row < 1)
            row = 1;
        int perHeight = maxHeight / row;

        Rect[][] array = new Rect[row][leafNum];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = new Rect();
                array[i][j].set(j * perWidth, i * perHeight, (j + 1) * perWidth, (i + 1)
                        * perHeight);

                if (j == array[i].length - 1) {
                    array[i][j].set(maxWidth - perWidth, array[i][j].top, maxWidth,
                            array[i][j].bottom);
                }

                if (i == array.length - 1) {
                    array[i][j].set(array[i][j].left, maxHeight - perHeight, array[i][j].right,
                            maxHeight);
                }
            }
        }

        long start = System.currentTimeMillis();
        long runMills = 0;

        Paint mRectPaint = new Paint();
        mRectPaint.setColor(Color.BLACK);
        Rect drawRect = new Rect();

        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 娓呴櫎鐢诲竷

                if (!isRunning) {
                    if (bitmap == null) {
                        if (null != canvas) {
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, dx, dy, null);
                } else {
                    canvas.save();
                    canvas.translate(dx, dy);
                    for (int i = 0; i < array.length; i++) {
                        for (int j = 0; j < array[i].length; j++) {
                            drawRect.set(array[i][j]);
                            int _dx = drawRect.width()
                                    / 2
                                    - (int) (((float) runMills / (float) duration)
                                    * drawRect.width() / 2);
                            int _dy = drawRect.height()
                                    / 2
                                    - (int) (((float) runMills / (float) duration)
                                    * drawRect.height() / 2);

                            drawRect.inset(_dx, _dy);
                            if (bitmap == null) {
                                if (null != canvas) {
                                    canvas.restore();
                                    holder.unlockCanvasAndPost(canvas);
                                }
                                return;
                            }
                            canvas.drawBitmap(bitmap, drawRect, drawRect, null);
                        }
                    }
                    canvas.restore();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    // 向内小黑块
    public void black_in(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perWidth = maxWidth / leafNum;
        if (perWidth < 1)
            perWidth = 1;
        int row = maxHeight / perWidth;
        if (row < 1)
            row = 1;
        int perHeight = maxHeight / row;

        Rect[][] array = new Rect[row][leafNum];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = new Rect();
                array[i][j].set(j * perWidth, i * perHeight, (j + 1) * perWidth, (i + 1)
                        * perHeight);

                if (j == array[i].length - 1) {
                    array[i][j].set(maxWidth - perWidth, array[i][j].top, maxWidth,
                            array[i][j].bottom);
                }

                if (i == array.length - 1) {
                    array[i][j].set(array[i][j].left, maxHeight - perHeight, array[i][j].right,
                            maxHeight);
                }
            }
        }

        long start = System.currentTimeMillis();
        long runMills = 0;

        Paint mRectPaint = new Paint();
        mRectPaint.setColor(Color.BLACK);
        Rect drawRect = new Rect();

        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 娓呴櫎鐢诲竷
                if (bitmap == null) {
                    if (null != canvas) {
                        canvas.restore();
                        holder.unlockCanvasAndPost(canvas);
                    }
                    return;
                }
                canvas.drawBitmap(bitmap, dx, dy, null);

                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        drawRect.set(array[i][j]);
                        int _dx = (int) (((float) runMills / (float) duration) * drawRect.width() / 2);
                        int _dy = (int) (((float) runMills / (float) duration) * drawRect.height() / 2);

                        drawRect.inset(_dx, _dy);
                        canvas.drawRect(drawRect, mRectPaint);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    // 从左向右擦除
    public void clean_left(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int Num = 1;
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        // int perHeight=maxHeight/leafNum;
        int perWidth = maxWidth / Num;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        Rect dst = new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 清除画布

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < Num; j++) {
                    // src.set(0,
                    // (int)((j+1)*perHeight-((float)runMills/(float)duration)*perHeight),
                    // maxWidth, (j+1)*perHeight);
                    // canvas.drawBitmap(bitmap[0], src, src, null);
                    src.set(j * perWidth,
                            0,
                            (int) (j * perWidth + ((float) runMills / (float) duration) * perWidth),
                            maxHeight);
                    dst.set(j * perWidth,
                            0,
                            (int) (j * perWidth + ((float) runMills / (float) duration) * perWidth),
                            maxHeight);
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, dst, null);
                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }
                if (!isRunning) {
                    break;
                }
            }
        }
    }

    // 从右向左擦除
    public void clean_right(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int Num = 1;
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perWidth = maxWidth / Num;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 娓呴櫎鐢诲竷

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < leafNum; j++) {
                    src.set((int) ((j + 1) * perWidth - ((float) runMills / (float) duration)
                            * perWidth), 0, (j + 1) * perWidth, maxHeight);
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, src, null);
                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    public void show_bitmap(SurfaceHolder holder, Bitmap bitmap, int maxWidth, int maxHeight) {
        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        int Num = 1;
        int dx = (maxWidth - bitmap.getWidth()) / 2;
        int dy = (maxHeight - bitmap.getHeight()) / 2;
        int perWidth = maxWidth / Num;

        long start = System.currentTimeMillis();
        long runMills = 0;

        Rect src = new Rect();
        Canvas canvas = new Canvas();
        isRunning = true;
        while (isRunning) {
            isRunning = ((runMills = (System.currentTimeMillis() - start)) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                if ((canvas == null) || (pdf == null))
                    return;

                canvas.setDrawFilter(pdf);
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 娓呴櫎鐢诲竷

                canvas.save();
                canvas.translate(dx, dy);
                for (int j = 0; j < leafNum; j++) {
                    src.set((int) ((j + 1) * perWidth - ((float) runMills / (float) duration)
                            * perWidth), 0, (j + 1) * perWidth, maxHeight);
                    if (bitmap == null) {
                        if (null != canvas) {
                            canvas.restore();
                            holder.unlockCanvasAndPost(canvas);
                        }
                        return;
                    }
                    canvas.drawBitmap(bitmap, src, src, null);
                }
                canvas.restore();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }
}
