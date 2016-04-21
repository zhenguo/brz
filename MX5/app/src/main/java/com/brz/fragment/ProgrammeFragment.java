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

/**
 * Created by macro on 16/3/29.
 */
public class ProgrammeFragment extends BasicFragment implements ProgrammeContract.View {

    private FrameLayout mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = (FrameLayout) inflater.inflate(R.layout.fragment_programme, container);
        return mContainer;
    }

    @Override
    public void setPresenter(ProgrammeContract.Presenter presenter) {

    }

    @Override
    public void addView(View view, ProgrammeContext.Coordinate coordinate) {
        if (mContainer != null) {
            mContainer.addView(view);
        }
    }
}
