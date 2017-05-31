package com.android_mobile.core.utiles;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by mxh on 2017/5/30.
 * Describe:工具类
 */

public class Utiles {

    /**
     * dip转换为px
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转换为dip
     */
    public static int px2dip(Context context,float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
