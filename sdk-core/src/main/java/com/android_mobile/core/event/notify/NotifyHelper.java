package com.android_mobile.core.event.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by picher on 2018/3/22.
 * Describe：具体的通知管理方法
 */

public class NotifyHelper implements NotifyStrategy {

    private static final String DEFAULT_NOTIFY_TAG = "default";
    private static final int DEFAULT_NOTIFY_ID = 1;
    private NotificationManager mNotifyManager;

    @Override
    public void notifySimple(Context context, String title, String label, int smallIcon) {
        notify(context, title, label, smallIcon, null, DEFAULT_NOTIFY_TAG, DEFAULT_NOTIFY_ID);
    }

    @Override
    public void notify(Context context, String title, String content, int smallIcon, PendingIntent action, String tag, int id) {
        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotifyManager == null) {
            throw new RuntimeException("发送通知失败，notifyManager 为空！");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setContentIntent(action)
                //需要自定义则注释该行
                .setDefaults(Notification.DEFAULT_ALL)
                //点击通知后自动清除
                .setAutoCancel(true);
                //调用系统多媒体裤内的铃声
                //.setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2")).
                //自定义震动效果 需要权限 android.permission.VIBRATE
                //.setVibrate(vibrate).
                //ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间
                //.setLights(0xFF0000, 3000, 3000);

        mNotifyManager.notify(tag, id, builder.build());
    }

    @Override
    public void cancel(String tag, int id) {
        mNotifyManager.cancel(tag,id);
    }

}
