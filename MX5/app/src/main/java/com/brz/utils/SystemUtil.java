package com.brz.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by macro on 16/7/20.
 */
public class SystemUtil {

    private static final String TAG = "SystemUtil";

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * translate the MAC address in {@code XX:XX:XX:XX:XX:XX} form to {@code xxxxxxxxxxxx}
     * @return MAC address in form {@code xxxxxxxxxxxx}
     */
    private static String macAddress2Hex(String address) {
        if (address == null)
            return null;

        return address.replaceAll(":", "");
    }

    private static String byte2hex(byte[] b) {
        if (b == null)
            return null;

        StringBuffer buffer = new StringBuffer(b.length);
        int len = b.length;

        for (int i = 0; i < len; i++)
            buffer = buffer.append(String.format("%02x", b[i]));

        return String.valueOf(buffer);
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected();
    }

    public static String getHardwareAddress(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return macAddress2Hex(wifiInfo.getMacAddress());
        } else {
            try {
                for (Enumeration<NetworkInterface> nif = NetworkInterface.getNetworkInterfaces();
                     nif.hasMoreElements(); ) {
                    NetworkInterface cur = nif.nextElement();
                    if (cur.getName().equals("eth0")) {
                        return byte2hex(cur.getHardwareAddress());
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
