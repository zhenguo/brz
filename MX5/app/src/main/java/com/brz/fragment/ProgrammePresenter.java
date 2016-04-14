package com.brz.fragment;

import android.support.annotation.NonNull;

/**
 * Created by macro on 16/4/14.
 */
public class ProgrammePresenter implements ProgrammeContract.Presenter {
    private ProgrammeContract.View mProgrammeView;

    public ProgrammePresenter(@NonNull ProgrammeContract.View view) {
        mProgrammeView = view;
        mProgrammeView.setPresenter(this);
    }
}
