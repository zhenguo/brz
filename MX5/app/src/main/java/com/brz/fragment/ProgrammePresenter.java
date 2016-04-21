package com.brz.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.brz.mx5.R;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeDefine;
import com.brz.view.VideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macro on 16/4/14.
 */
public class ProgrammePresenter implements ProgrammeContract.Presenter {
    private Context mContext;
    private ProgrammeContract.View mProgrammeView;
    private ProgrammeContext mProgrammeContext;

    public ProgrammePresenter(Context context, @NonNull ProgrammeContext programmeContext, @NonNull ProgrammeContract.View view) {
        mContext = context;
        mProgrammeView = view;
        mProgrammeView.setPresenter(this);
        mProgrammeContext = programmeContext;

        inflateScreen(mProgrammeContext);
    }

    private void inflateScreen(ProgrammeContext programmeContext) {
        List<ProgrammeContext.ContentItem> list = programmeContext.getContent();
        ProgrammeContext.ContentItem item = null;

        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            ProgrammeContext.Region region = item.getRegion();
            ProgrammeContext.Seq seq = item.getSeq();
            switch (region.getType()) {
                case ProgrammeDefine.BACKGROUND_REGION:

                    break;
                case ProgrammeDefine.VIDEO_REGION:
                    List<ProgrammeContext.Item> items = seq.getItem();
                    List<String> fileNames = new ArrayList<>();
                    for (ProgrammeContext.Item item1 : items) {
                        fileNames.add(item1.getSrc());
                    }
                    ProgrammeContext.Coordinate coordinate = new ProgrammeContext.Coordinate(Float.parseFloat(region.getLeft()),
                            Float.parseFloat(region.getHeight()),
                            Float.parseFloat(region.getTop()),
                            Float.parseFloat(region.getHeight()));
                    inflateVideo(coordinate, fileNames);

                    break;
                case ProgrammeDefine.PICTURE_REGION:
                    break;
                case ProgrammeDefine.TEXT_REGION:
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

    private void inflateVideo(ProgrammeContext.Coordinate coordinate, List<String> videoFiles) {
        VideoPlayer player = (VideoPlayer) View.inflate(mContext, R.layout.view_videoview, null);
        player.setX(coordinate.getLeft());
        mProgrammeView.addView(player, coordinate);
        player.setVideoList(videoFiles);
    }
}
