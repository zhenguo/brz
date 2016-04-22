package com.brz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.brz.basic.BasicFragment;
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
            view.setLeft((int) coordinate.getLeft());
            view.setTop((int) coordinate.getTop());
            view.setRight((int) coordinate.getRight());
            view.setBottom((int) coordinate.getButtom());
            mContainer.addView(view);
        }
    }
}
