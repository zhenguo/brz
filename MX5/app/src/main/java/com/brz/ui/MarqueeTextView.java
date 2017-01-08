package com.brz.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.brz.mx5.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by macro on 16/6/7.
 */
public class MarqueeTextView extends View {

    public static class TextViewOptions {
        private String bgcolor;
        private String bgmix;
        private String color;
        private String direction;
        private String face;
        private String font_size;
        private String size;
        private String speed;
        private String content;

        public String getBgcolor() {
            return bgcolor;
        }

        public String getBgmix() {
            return bgmix;
        }

        public String getColor() {
            return color;
        }

        public String getDirection() {
            return direction;
        }

        public String getFace() {
            return face;
        }

        public String getFont_size() {
            return font_size;
        }

        public String getSize() {
            return size;
        }

        public String getSpeed() {
            return speed;
        }

        public void setBgcolor(String bgcolor) {
            this.bgcolor = bgcolor;
        }

        public void setBgmix(String bgmix) {
            this.bgmix = bgmix;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public void setFont_size(String font_size) {
            this.font_size = font_size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    private static final String TAG = "MarqueeTextView";
    private Logger mLogger = Logger.getLogger(TAG);
    private Paint mPaint;
    private String mText;
    private float mTextWidth;
    private float mSpeed;
    private float mStep;
    private Timer mTimer;

    public MarqueeTextView(Context context) {
        super(context);

        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_HARDWARE, mPaint);
        mStep = 0f;
        setBackgroundColor(getResources().getColor(R.color.black_overlay));
    }

    public void setTypeface(Typeface typeface) {
        mPaint.setTypeface(typeface);
    }

    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
    }

    public final void setTextColor(String color) {
        mPaint.setColor(Color.parseColor(color));
    }

    public final void setText(String text) {
        mText = text;
        mTextWidth = mPaint.measureText(mText);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStep = getRight();
    }

    public void startMarquee() {
        mTimer = new Timer("marquee_text_timer");
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 0, 100);
    }

    public void stopMarquee() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "mStep: " + mStep + " mTextWidth: " + mTextWidth);
        canvas.drawText(mText, 0, mText.length(), mStep,
                getTop() + getHeight() / 2 + mPaint.getTextSize() / 2, mPaint);
        mStep -= mSpeed;

        if (mTextWidth > getWidth()) {
            if (Math.abs(mStep) > mTextWidth)
                mStep = getRight();
        } else {
            if (Math.abs(mStep) > getWidth())
                mStep = getRight();
        }
    }
}
