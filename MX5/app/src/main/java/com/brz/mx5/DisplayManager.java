package com.brz.mx5;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.brz.basic.Basic;
import com.brz.fragment.ProgrammeFragment;
import com.brz.fragment.ProgrammePresenter;
import com.brz.listener.OnCompletionListener;
import com.brz.programme.Programme;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeManager;
import com.brz.programme.Theme;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by macro on 16/7/13.
 */
public class DisplayManager implements OnCompletionListener {
  private static final String TAG = "DisplayManager";
  private Logger mLogger = Logger.getLogger(TAG);
  private AppCompatActivity mActivity;
  private ProgrammeContext mProgrammeContext;
  private ProgrammeFragment mFragment;
  private ProgrammePresenter mPresenter;
  private List<Programme> mProgrammeList;
  private int mPosition = 0;
  private static final int MSG_WHAT_SWITCH_PROGRAMME = 0x1000;
  private Handler mHandler = new Handler(new Handler.Callback() {
    @Override public boolean handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_WHAT_SWITCH_PROGRAMME:
          next();
          display();
          break;
      }

      return false;
    }
  });

  public DisplayManager(AppCompatActivity compatActivity) {
    mActivity = compatActivity;

    initProgramme();
  }

  public void updateProgramme() {
    mPosition = 0;
    initProgramme();
    display();
  }

  private void initProgramme() {
    Theme theme = ProgrammeManager.getInstance().getTheme(Basic.THEME_PATH);
    if (theme == null) return;
    mProgrammeList = theme.getDefaults();

    if (mProgrammeList != null && mProgrammeList.size() > 0) {
      mProgrammeContext = ProgrammeManager.getInstance()
          .getContext(mProgrammeList.get(mPosition++).getFileSigna() + ".json");
      mFragment = new ProgrammeFragment();
      mActivity.getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fullscreen_content, mFragment)
//          .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
          .commit();
      mActivity.getSupportFragmentManager().executePendingTransactions();
    }
  }

  public void display() {
    mLogger.info("display");

    if (mProgrammeContext != null) {
      mPresenter = new ProgrammePresenter(mActivity, mProgrammeContext, mFragment);
      mPresenter.setOnCompletionListener(this);
    }
  }

  private void next() {
    mLogger.info("mPosition: " + mPosition);

    if (mPosition > mProgrammeList.size() - 1) {
      mPosition = 0;
    }

    mProgrammeContext = ProgrammeManager.getInstance()
        .getContext(mProgrammeList.get(mPosition++).getFileSigna() + ".json");

    mLogger.info("replace new programme");
    mFragment = new ProgrammeFragment();
    mActivity.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fullscreen_content, mFragment)
//        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit();
    mActivity.getSupportFragmentManager().executePendingTransactions();
  }

  @Override public boolean onCompletion(int level) {
    if (level == LEVEL_PROGRAMME) {
      // 节目单播放完成,切换到下一个
      mHandler.sendEmptyMessage(MSG_WHAT_SWITCH_PROGRAMME);
      return true;
    }

    return false;
  }

  public void finish() {
    mHandler.removeMessages(MSG_WHAT_SWITCH_PROGRAMME);
  }
}
