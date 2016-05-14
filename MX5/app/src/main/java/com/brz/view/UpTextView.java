package com.brz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenhua on 16-4-11.
 */
public class UpTextView extends TextView{
    // 速度变量
    private float speed = 0.9f;
    private float step = 0.0f;
    private Paint mPaint = new Paint();
    private String text;
    private float width;
    private int lineNum = 1;
    private List<String> textList = new ArrayList<String>();

    private boolean isFloated = false;

    public UpTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UpTextView(Context context) {
        super(context);
    }

    public void setPaintInit(int c, float s) {
        if (mPaint != null) {
            mPaint.setColor(c);
            mPaint.setTextSize(s);
            lineNum = (int) s;
        }

    }

    public void init(String str, float sp, float width) {
        setText(str);
        speed = sp;

        Log.v("cook", "===>  "+speed);

        if(lineNum>0)
            lineNum = (int) width / lineNum;
        else
            lineNum = 1;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(
                    "ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }

        text = getText().toString();
        if (text == null | text.length() == 0) {

            return;
        }

        textList.clear();
        StringBuilder builder = null;
        for (int i = 0; i < text.length(); i++) {
            if (i % lineNum == 0) {
                builder = new StringBuilder();
            }
            if (i % lineNum <= lineNum - 1) {
                builder.append(text.charAt(i));
            }
            if (i % lineNum == lineNum - 1) {
                textList.add(builder.toString());
            }else if(i == text.length()-1){
                textList.add(builder.toString());
            }

        }
        Log.e("textviewscroll", "" + textList.size());

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (textList.size() == 0)
            return;

        for (int i = 0; i < textList.size(); i++) {
            canvas.drawText(textList.get(i), 500, this.getHeight() + (i + 1) * mPaint.getTextSize() - step + 30, mPaint);
            //Log.d("textList: ", textList.get(i));
        }

        invalidate();

        if (!isFloated) {
            return;
        }

        step = step + speed;
        if (step >= this.getHeight() + textList.size() * mPaint.getTextSize()) {
            step = 0;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SaveState saveState = new SaveState(superState);

        saveState.isFloating = isFloated;
        return saveState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SaveState) {
            super.onRestoreInstanceState(state);
            return;
        }

        SaveState saveState = (SaveState) state;
        super.onRestoreInstanceState(saveState.getSuperState());

        isFloated = saveState.isFloating;
    }

    public void StartFloating() {
        isFloated = true;
        invalidate();
    }

    public void StopFloating() {
        isFloated = false;
        Toast.makeText(getContext(), "stoped", Toast.LENGTH_SHORT).show();
        invalidate();
    }

    private static class SaveState extends BaseSavedState {
        public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {

            @Override
            public SaveState createFromParcel(Parcel in) {
                return new SaveState(in);
            }

            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }

        };

        public boolean isFloating = true;

        private SaveState(Parcel in) {
            super(in);
            boolean[] b = new boolean[1];
            in.readBooleanArray(b);
            isFloating = b[0];
        }

        SaveState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBooleanArray(new boolean[] { isFloating });
        }
    }

}
