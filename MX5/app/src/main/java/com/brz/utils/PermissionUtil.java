package com.brz.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static io.vov.vitamio.utils.Log.TAG;

/**
 * Created by macro on 16/11/19.
 */

public class PermissionUtil {

    public static void grantePermission(Activity activity) {
        boolean readExternalStorageDenied = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
        boolean writeExternalStorageDenied = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;

        Log.d(TAG, "readExternalStorageDenied: " + readExternalStorageDenied);
        Log.d(TAG, "writeExternalStorageDenied: " + writeExternalStorageDenied);

        // Here, thisActivity is the current activity
        if (readExternalStorageDenied || writeExternalStorageDenied) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
