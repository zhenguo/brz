package com.brz.mx5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;
import io.vov.vitamio.Vitamio;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends PermissionsActivity {
  private static final String TAG = "FullscreenActivity";
  private static final int REQUEST_PERMISSION_RWS = 0x1001;
  private DisplayManager mDisplayManager;
  private ResourceManager mResourceManager;
  private final Handler mHideHandler = new Handler();
  private View mContentView;
  private final Runnable mHidePart2Runnable = new Runnable() {
    @SuppressLint("InlinedApi") @Override public void run() {
      // Delayed removal of status and navigation bar

      // Note that some of these constants are new as of API 16 (Jelly Bean)
      // and API 19 (KitKat). It is safe to use them, as they are inlined
      // at compile-time and do nothing on earlier devices.
      mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fullscreen);

    mContentView = findViewById(R.id.fullscreen_content);
    hide();

    if (Build.VERSION.SDK_INT < 23) {
      mResourceManager = ResourceManager.getInstance();
      mResourceManager.init();
      mDisplayManager = new DisplayManager(this);
    }
  }

  @Override protected void onStart() {
    super.onStart();

    if (Build.VERSION.SDK_INT >= 23) {
      requestPermission(new String[] {
          Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
      }, REQUEST_PERMISSION_RWS);
    }
  }

  @Override public void permissionSuccess(int requestCode) {
    super.permissionSuccess(requestCode);
    switch (requestCode) {
      case REQUEST_PERMISSION_RWS:
        mResourceManager = ResourceManager.getInstance();
        mResourceManager.init();

        mDisplayManager = new DisplayManager(this);
        break;
      default:
        break;
    }
  }

  @Override protected void onResume() {
    super.onResume();

    mDisplayManager.display();
  }

  @Override protected void onStop() {
    super.onStop();

    mDisplayManager.finish();
  }

  private void hide() {
    // Hide UI first
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }

    mHideHandler.post(mHidePart2Runnable);
  }
}
