package com.brz.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.brz.basic.BasicFragment;
import com.brz.imageloader.ImageWorker2;
import com.brz.mx5.FullscreenActivity;
import com.brz.mx5.R;
import com.brz.programme.ProgrammeContext;

import java.util.logging.Logger;

/**
 * Created by macro on 16/3/29.
 */
public class ProgrammeFragment extends BasicFragment implements ProgrammeContract.View {

    private Logger mLogger = Logger.getLogger(ProgrammeFragment.class.getSimpleName());
    private FrameLayout mContainer;
    private ProgrammeContract.Presenter mProgrammePresenter;
    private BitmapDrawable mBackground;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLogger.info("onCreateView");
        mContainer = (FrameLayout) inflater.inflate(R.layout.fragment_programme, null);
        return mContainer;
    }

    @Override
    public void setPresenter(ProgrammeContract.Presenter presenter) {
        mProgrammePresenter = presenter;
    }

    @Override
    public void addView(View view, ProgrammeContext.Coordinate coordinate) {
        mLogger.info("addView");
        if (mContainer != null) {
            view.setX(coordinate.getLeft());
            view.setY(coordinate.getTop());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    (int) coordinate.getRight(), (int) coordinate.getBottom());
            mContainer.addView(view, params);
        }
    }

    @Override
    public void removeView(View view) {
        if (mContainer != null) {
            mContainer.removeView(view);
        }
    }

    @Override
    public void setBackground(String url) {
        mLogger.info("setBackground");
        if (mContainer != null) {
            if (TextUtils.isEmpty(url)) {
                mContainer.setBackground(null);
            } else {
                FullscreenActivity.getIntance().getImageLoader().loadImage(url, new ImageWorker2.OnImageLoadedListener() {
                    @Override
                    public void onImageLoaded(boolean success, BitmapDrawable bitmap) {
                        mBackground = bitmap;
                        mContainer.setBackground(mBackground);
                    }
                });
            }
        }
    }

    @Override
    public void removeAllViews() {
        if (mContainer != null) {
            mContainer.setBackground(null);
            mContainer.removeAllViews();
            mContainer = null;
        }
    }
}
