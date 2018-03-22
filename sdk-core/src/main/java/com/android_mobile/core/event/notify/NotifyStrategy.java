package com.android_mobile.core.event.notify;

import android.app.PendingIntent;
import android.content.Context;

/**
 * Created by picher on 2018/3/22.
 * Describe：通知策略
 */

public interface NotifyStrategy {


    void notifySimple(Context context, String title, String label,int smallIcon);

    /**
     * 通知
     * @param title 通知标题
     * @param content 通知内容
     * @param smallIcon 通知图标
     * @param action 通知点击后的动作
     */
    void notify(Context context, String title, String content, int smallIcon, PendingIntent action, String tag, int id);

    void cancel(String tag, int id);
}
