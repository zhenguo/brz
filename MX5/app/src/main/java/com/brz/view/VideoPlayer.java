package com.brz.view;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by macro on 16/3/28.
 */
public class VideoPlayer extends VideoView {

    private List<String> mVideoList = new ArrayList<>();
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
            if (mVideoListQueue.isEmpty())
                inflateQueue();
            setVideoPath(mVideoListQueue.take());
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // 防止弹出播放错误对话框
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

        startPlayback();
    }
}
