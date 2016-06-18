package com.brz.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.brz.mx5.R;

import java.util.logging.Logger;

/**
 * Created by macro on 16/6/7.
 */
public class MarqueeTextView extends View {
	private static final String TAG = "MarqueeTextView";
	private Logger mLogger = Logger.getLogger(TAG);
	private Paint mPaint;
	private String mText;
	private float mTextWidth;
	private float mSpeed;
	private float mStep;

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
		mPaint.setColor(Color.WHITE);
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

	public final void setText(String text) {
		mText = text;
		mTextWidth = mPaint.measureText(mText);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mStep = getRight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawText(mText, 0, mText.length(), mStep,
				getTop() + getHeight() / 2 + mPaint.getTextSize() / 2, mPaint);
		mStep -= mSpeed;

		if (Math.abs(mStep) > getWidth())
			mStep = getRight();

		postInvalidateDelayed(20);
	}
}
