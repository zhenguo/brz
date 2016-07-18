package com.brz.view;

import android.content.Context;
import android.util.AttributeSet;

import com.brz.listener.OnCompletionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by macro on 16/3/28.
 */
public class VideoPlayer extends VideoView {
	private static final String TAG = "VideoPlayer";
	private Logger mLogger = Logger.getLogger(TAG);
	private List<String> mVideoList = new ArrayList<>();
	private OnCompletionListener mOnCompletionListener;
	private BlockingQueue<String> mVideoListQueue = new LinkedBlockingDeque<>();

	public VideoPlayer(Context context) {
		super(context);
	}

	public VideoPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VideoPlayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnCompletionListener(OnCompletionListener listener) {
		mOnCompletionListener = listener;
	}

	public void setVideoList(List<String> videoList) {
		mVideoList = videoList;
		init();
	}

	private void inflateQueue() {
		for (int i = 0; i < mVideoList.size(); i++)
			mVideoListQueue.add(mVideoList.get(i));
	}

	private void startPlayback() {
		try {
			if (mVideoListQueue.isEmpty()) { // playlist has been through
				if (mOnCompletionListener != null) {
					if (mOnCompletionListener.onCompletion(OnCompletionListener.LEVEL_VIDEO)) {
						mLogger.info("stopPlayback video.");
						stopPlayback();
						return;
					} else {
						inflateQueue(); // not processed, continue play
					}
				}
			}

			setVideoPath(mVideoListQueue.take());
			start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		setHardwareDecoder(true);
		// 防止弹出播放错误对话框
		setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				setVideoLayout(VIDEO_LAYOUT_ORIGIN, 0);
			}
		});
		setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				return true;
			}
		});

		setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				stopPlayback();
				startPlayback();
			}
		});

		inflateQueue();

		startPlayback();
	}
}
