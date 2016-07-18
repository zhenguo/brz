package com.brz.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;

import com.brz.basic.Basic;
import com.brz.mx5.R;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeDefine;
import com.brz.utils.FileUtil;
import com.brz.view.ImagePlayer;
import com.brz.view.MarqueeTextView;
import com.brz.listener.OnCompletionListener;
import com.brz.view.VideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by macro on 16/4/14.
 */
public class ProgrammePresenter implements ProgrammeContract.Presenter, OnCompletionListener {
    private static final String TAG = "ProgrammePresenter";
    private Logger mLogger = Logger.getLogger(TAG);
    private Context mContext;
    private ProgrammeContract.View mProgrammeView;
    private ProgrammeContext mProgrammeContext;
    private OnCompletionListener mListener;
    private boolean mHasVideo = false;
    private boolean mHasImg = false;
    private MarqueeTextView mMarqueeTextView;
    private ImagePlayer mImagePlayer;
    private VideoPlayer mVideoPlayer;

    private static final int MSG_WHAT_NOTIFY_COMPLETION = 0x1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_NOTIFY_COMPLETION:
                    destoryView();
                    if (mListener != null)
                        mListener.onCompletion(LEVEL_PROGRAMME);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    public ProgrammePresenter(Context context, @NonNull ProgrammeContext programmeContext,
                              @NonNull ProgrammeContract.View view) {
        mContext = context;
        mProgrammeView = view;
        mProgrammeView.setPresenter(this);
        mProgrammeContext = programmeContext;

        inflateScreen(mProgrammeContext);
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        mListener = listener;
    }

    private String getFileSuffix(String fileName) {
        return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
    }

    private String getFilePath(String resourceType, String fileSigna, String suffix) {
        String filePath = null;
        switch (resourceType) {
            case ProgrammeDefine.BACKGROUND_REGION:
                String builder = Basic.RESOURCE_PATH + "BG/" + fileSigna + "." + suffix;
                filePath = builder.trim();
                break;
            case ProgrammeDefine.VIDEO_REGION:
                filePath = Basic.RESOURCE_PATH + "VIDEO/" + fileSigna + "." + suffix;
                break;
            case ProgrammeDefine.PICTURE_REGION:
                filePath = Basic.RESOURCE_PATH + "IMAGE/" + fileSigna + "." + suffix;
                break;
            case ProgrammeDefine.TEXT_REGION:
                filePath = Basic.RESOURCE_PATH + "TEXT/" + fileSigna + "." + suffix;
            default:
                break;
        }

        return filePath;
    }

    private void inflateScreen(ProgrammeContext programmeContext) {
        List<ProgrammeContext.ContentItem> list = programmeContext.getContent();
        ProgrammeContext.ContentItem item;

        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            ProgrammeContext.Region region = item.getRegion();
            List<ProgrammeContext.Item> items;
            List<String> fileNames = new ArrayList<>();

            switch (region.getType()) {
                case ProgrammeDefine.BACKGROUND_REGION:
                    mLogger.info("Add BACKGROUND_REGION");
                    items = region.getItem();
                    String suffix = getFileSuffix(items.get(0).getUrl());
                    inflateBg(getFilePath(ProgrammeDefine.BACKGROUND_REGION, items.get(0)
                            .getFileSigna(), suffix));
                    break;
                case ProgrammeDefine.VIDEO_REGION:
                    mLogger.info("Add VIDEO_REGION");
                    items = region.getItem();
                    fileNames.clear();
                    for (ProgrammeContext.Item item1 : items) {
                        fileNames.add(getFilePath(ProgrammeDefine.VIDEO_REGION, item1.getFileSigna(),
                                getFileSuffix(item1.getSrc())));
                    }
                    ProgrammeContext.Coordinate coordinate = new ProgrammeContext.Coordinate(
                            Float.parseFloat(region.getLeft()) * Basic.SCREEN_WIDTH / 100,
                            Float.parseFloat(region.getWidth()) * Basic.SCREEN_WIDTH / 100,
                            Float.parseFloat(region.getTop()) * Basic.SCREEN_HEIGHT / 100,
                            Float.parseFloat(region.getHeight()) * Basic.SCREEN_HEIGHT / 100);
                    inflateVideo(coordinate, fileNames);

                    break;
                case ProgrammeDefine.PICTURE_REGION:
                    mLogger.info("Add PICTURE_REGION");
                    items = region.getItem();
                    List<ImagePlayer.ImageOptions> imageOptionsList = new ArrayList<>();
                    for (ProgrammeContext.Item item1 : items) {
                        ImagePlayer.ImageOptions options = new ImagePlayer.ImageOptions();
                        options.setFilePath(getFilePath(ProgrammeDefine.PICTURE_REGION,
                                item1.getFileSigna(), getFileSuffix(item1.getSrc())));
                        options.setTransmode(item1.getTransmode());
                        options.setDuration(Integer.parseInt(item1.getDuration()));
                        options.setTransittime(Double.parseDouble(item1.getTransittime()));
                        imageOptionsList.add(options);
                    }
                    coordinate = new ProgrammeContext.Coordinate(Float.parseFloat(region.getLeft())
                            * Basic.SCREEN_WIDTH / 100, Float.parseFloat(region.getWidth())
                            * Basic.SCREEN_WIDTH / 100, Float.parseFloat(region.getTop())
                            * Basic.SCREEN_HEIGHT / 100, Float.parseFloat(region.getHeight())
                            * Basic.SCREEN_HEIGHT / 100);
                    inflatePicture(coordinate, imageOptionsList);
                    break;
                case ProgrammeDefine.TEXT_REGION:
                    mLogger.info("Add TEXT_REGION");
                    items = region.getItem();

                    List<MarqueeTextView.TextViewOptions> optionses = new ArrayList<>();
                    for (ProgrammeContext.Item item1 : items) {
                        MarqueeTextView.TextViewOptions options = new MarqueeTextView.TextViewOptions();
                        options.setBgcolor(item1.getBgcolor());
                        options.setBgmix(item1.getBgmix());
                        options.setColor(item1.getColor());
                        options.setDirection(item1.getDirection());
                        options.setFace(item1.getFace());
                        options.setFont_size(item1.getFont_size());
                        options.setSize(item1.getSize());
                        options.setSpeed(item1.getSpeed());
                        options.setContent(getFilePath(ProgrammeDefine.TEXT_REGION, item1.getFileSigna(), getFileSuffix(item1.getSrc())));
                        optionses.add(options);
                    }

                    coordinate = new ProgrammeContext.Coordinate(Float.parseFloat(region.getLeft())
                            * Basic.SCREEN_WIDTH / 100, Float.parseFloat(region.getWidth())
                            * Basic.SCREEN_WIDTH / 100, Float.parseFloat(region.getTop())
                            * Basic.SCREEN_HEIGHT / 100, Float.parseFloat(region.getHeight())
                            * Basic.SCREEN_HEIGHT / 100);
                    inflateText(coordinate, optionses);
                    break;
                case ProgrammeDefine.WEATHER_REGION:
                    break;
                case ProgrammeDefine.DATE_REGION:
                    break;
                case ProgrammeDefine.TIME_REGION:
                    break;
                default:
                    break;
            }
        }
    }

    private void inflateBg(String url) {
        mProgrammeView.setBackground(url);
    }

    private void inflateVideo(ProgrammeContext.Coordinate coordinate, List<String> videoFiles) {
        VideoPlayer player = (VideoPlayer) View.inflate(mContext, R.layout.view_videoview, null);
        player.setOnCompletionListener(this);
        mProgrammeView.addView(player, coordinate);
        player.setVideoList(videoFiles);
        mHasVideo = true;
    }

    private void inflatePicture(ProgrammeContext.Coordinate coordinate,
                                List<ImagePlayer.ImageOptions> pictures) {
        mImagePlayer = (ImagePlayer) View.inflate(mContext, R.layout.view_imageview, null);
        mImagePlayer.setOnCompletionListener(this);
        mImagePlayer.setImages(pictures);
        mProgrammeView.addView(mImagePlayer, coordinate);
        mHasImg = true;
    }

    private void inflateText(ProgrammeContext.Coordinate coordinate, List<MarqueeTextView.TextViewOptions> optionses) {
        mMarqueeTextView = (MarqueeTextView) View.inflate(mContext,
                R.layout.view_marquee_textview, null);
        mMarqueeTextView.setAlpha(Float.parseFloat(optionses.get(0).getBgmix()) / 100);
        mMarqueeTextView.setTextSize(Integer.parseInt(optionses.get(0).getFont_size()));
        mMarqueeTextView.setSpeed(Integer.parseInt(optionses.get(0).getSpeed()));
        mMarqueeTextView.setTypeface(Typeface.DEFAULT_BOLD);
        mMarqueeTextView.setTextColor(optionses.get(0).getColor());
        mMarqueeTextView.setText(FileUtil.readFile(optionses.get(0).getContent()));
        mMarqueeTextView.setBackgroundColor(Color.parseColor(optionses.get(0).getBgcolor()));
        mProgrammeView.addView(mMarqueeTextView, coordinate);
        mMarqueeTextView.startMarquee();
    }

    private void destoryView() {

        if (mVideoPlayer != null) {
            mVideoPlayer.stopPlayback();
            mProgrammeView.removeView(mVideoPlayer);
            mVideoPlayer = null;
        }

        if (mImagePlayer != null) {
            mImagePlayer.quit();
            mProgrammeView.removeView(mImagePlayer);
            mImagePlayer = null;
        }

        if (mMarqueeTextView != null) {
            mMarqueeTextView.stopMarquee();
            mProgrammeView.removeView(mMarqueeTextView);
            mMarqueeTextView = null;
        }
    }

    @Override
    public boolean onCompletion(int level) {
        if (level == LEVEL_VIDEO) {
            if (mListener != null) {
                mLogger.info("time to switch to next programme.");
                mHandler.sendEmptyMessage(MSG_WHAT_NOTIFY_COMPLETION);
                return true;
            }
        } else if (level == LEVEL_IMAGE) {
            if (mHasVideo) {
                // 视频优先级高
            } else {
                if (mListener != null) {
                    mLogger.info("time to switch to next programme, no video");
                    mHandler.sendEmptyMessage(MSG_WHAT_NOTIFY_COMPLETION);
                    return true;
                }
            }
        }

        return false;
    }
}
