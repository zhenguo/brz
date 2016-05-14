package com.brz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhenhua on 16-4-11.
 */
public class LeftToRightTextView extends TextView {
    public boolean mIsFloating = false; //是否开始滚动
    private float mSpeed = 0.5f;
    private float mStep = 0f;
    private String mStr = ""; //文本内容
    private float mTextLength = 0f; //文本长度
    private float mViewWidth = 0f;
    private float mY = 200f; //文字的纵坐标
    private Paint mPaint = new Paint();

    public LeftToRightTextView(Context context) {
        super(context);

    }

    public LeftToRightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LeftToRightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setPaintInit(int c, float s) {
        if (mPaint != null) {
            mPaint.setColor(c);
            mPaint.setTextSize(s);
        }

    }

    public void init(String str, float speed, float width) {
        setText(str);
        mSpeed = speed;
        mStr = getText().toString();
        mTextLength = getPaint().measureText(mStr);
        mViewWidth = width;
        mStep = mTextLength + mViewWidth;
        mY = getTextSize() + getPaddingTop();
        // getPaint().setColor(0xff0000ff);
    }

    /**
     * mstep是字符串第一个字符的足迹
     *
     * @param canvas
     */

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(mStr, 0, mStr.length(), mStep - (mViewWidth + mTextLength), mY, mPaint);
        if (!mIsFloating)
            return;
        mStep += mSpeed;

        if (mStep > mTextLength + mViewWidth * 2)   //当字符串的第一个字符移动到：（mTextLength + mViewWidth * 2）- （mViewWidth + mTextLength）= mViewWidth距离时
            mStep = mViewWidth;         //让字符串从-mTextLenght处开始移动
        invalidate();
    }

    /**
     * 从保存的当前的状态中恢复
     * @param state
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        mStep = savedState.mStep;
        mIsFloating = savedState.mIsFloating;
    }

    /**
     * 保存当前的状态
     * @return
     */
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mStep = mStep;
        savedState.mIsFloating = mIsFloating;
        return savedState;
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
    }

    public void startFloating() {
        mIsFloating = mTextLength > mViewWidth;
        mIsFloating = true;
        invalidate();
    }

    public void stopFloating() {
        mIsFloating = false;
        invalidate();
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

        };
        public boolean mIsFloating = false;
        public float mStep = 0.0f;

        private SavedState(Parcel in) {
            super(in);
            boolean[] b = new boolean[1];
            in.readBooleanArray(b);
            mIsFloating = b[0];
            mStep = in.readFloat();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBooleanArray(new boolean[] { mIsFloating });
            out.writeFloat(mStep);
        }
    }

}
