package com.android_mobile.core.manager;

import android.app.PendingIntent;
import android.content.Context;

import com.android_mobile.core.event.notify.NotifyHelper;
import com.android_mobile.core.event.notify.NotifyStrategy;

/**
 * Created by picher on 2018/3/22.
 * Describe：应用通知管理器
 */

public class NotifyManager {

    private static NotifyManager mInstance = null;
    private NotifyStrategy notifyStrategy = null;

    private NotifyManager(){
        notifyStrategy = new NotifyHelper();
    }

    public static NotifyManager getInstance(){
        if(mInstance == null){
            synchronized (NotifyManager.class){
                mInstance = new NotifyManager();
            }
        }
        return mInstance;
    }

    private void notify(Context context, String title, String content, int smallIcon, PendingIntent action, String tag, int id){
        if(notifyStrategy != null){
            notifyStrategy.notify(context,title,content,smallIcon,action, tag, id);
        }
    }

    private void notifySimple(Context context, String title, String label,int smallIcon){
        if(notifyStrategy != null){
            notifyStrategy.notifySimple(context,title,label,smallIcon);
        }
    }

    private void cancel(String tag, int id){
        if(notifyStrategy != null){
            notifyStrategy.cancel(tag,id);
        }
    }
}
