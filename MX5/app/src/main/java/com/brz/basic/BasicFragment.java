package com.brz.basic;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by macro on 16/3/29.
 */
public class BasicFragment extends Fragment {

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPause(getContext());
    }
}
