package com.brz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brz.basic.BasicFragment;

/**
 * Created by macro on 16/3/29.
 */
public class ProgrammeFragment extends BasicFragment implements ProgrammeContract.View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setPresenter(ProgrammeContract.Presenter presenter) {

    }
}
