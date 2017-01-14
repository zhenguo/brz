package com.brz.ui;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.brz.mx5.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by macro on 16/6/8.
 */
public class DateView extends TextView {

    private final static DateFormat DATE_FORMATE = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    private final static DateFormat TIME_FORMATE = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private int mType;
    private static final String TAG = "DateView";
    private TimeChangedReceiver mReceiver;
    private StyleParams mStyleParams;

    public static class StyleParams {

        public StyleParams() {
            textColor = Color.WHITE;
            textSize = 25;
            typeface = Typeface.MONOSPACE;
        }

        public int textColor;
        public int textSize;
        public Typeface typeface;
    }

    public DateView(Context context) {
        super(context);

        initUI();
    }

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DateView);
        mType = typedArray.getInt(R.styleable.DateView_type, 0);
        typedArray.recycle();

        initUI();
    }

    public DateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initUI();
    }

    private void setDisplay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String result = "";

        switch (mType) {
            case 1:
                result = DATE_FORMATE.format(date);
                break;
            case 2:
                result = TIME_FORMATE.format(date);
                break;
            default:
                break;
        }

        setText(result);
    }

    private void initUI() {
        setGravity(Gravity.CENTER);

        mReceiver = new TimeChangedReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        getContext().registerReceiver(mReceiver, filter);

        setDisplay();
    }

    public void setStyleParams(StyleParams params) {
        mStyleParams = params;

        setTextColor(mStyleParams.textColor);
        setTextSize(mStyleParams.textSize);
        setTypeface(mStyleParams.typeface);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getContext().unregisterReceiver(mReceiver);
    }

    public class TimeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + "TimeChangedReceiver");
            setDisplay();
        }
    }
}
