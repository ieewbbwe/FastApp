package com.android_mobile.location;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by mxh on 2017/6/9.
 * Describe：
 */

public class LocateUtils {

    public static void checkoutKey(Activity context, BroadcastReceiver receiver) {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        context.registerReceiver(receiver, iFilter);
    }
}
