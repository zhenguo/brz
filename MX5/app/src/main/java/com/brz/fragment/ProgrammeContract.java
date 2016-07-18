package com.brz.fragment;

import com.brz.BasePresenter;
import com.brz.BaseView;
import com.brz.programme.ProgrammeContext;

/**
 * Created by macro on 16/4/14.
 */
public class ProgrammeContract {

    interface View extends BaseView<Presenter> {
        void addView(android.view.View view, ProgrammeContext.Coordinate coordinate);
        void removeView(android.view.View view);
        void setBackground(String url);
    }

    interface Presenter extends BasePresenter {

    }
}
