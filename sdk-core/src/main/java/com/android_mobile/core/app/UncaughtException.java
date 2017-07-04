package com.android_mobile.core.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.android_mobile.core.utiles.Lg;
import com.android_mobile.core.utiles.TimerUtils;
import com.android_mobile.core.utiles.Utiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mxh on 2017/5/31.
 * Describe：注册未捕获异常的处理
 */

public class UncaughtException implements Thread.UncaughtExceptionHandler {

    private static final String LOG_FILE_NAME = "crash.log";
    private final String TAG = getClass().getSimpleName();
    private static UncaughtException mInstance;
    private final Context mContext;

    private File mCacheLogFile;
    private boolean isSend;

    private UncaughtException(Context context) {
        this.mContext = context;
        File path;
        if (Utiles.isSdCardExist()) {
            path = context.getExternalCacheDir();
        } else {
            path = context.getCacheDir();
        }
        if (path != null) {
            Lg.print(TAG, "unCaughtException Log path: " + path);
            mCacheLogFile = new File(path, LOG_FILE_NAME);
            if (!mCacheLogFile.exists()) {
                try {
                    mCacheLogFile.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized static UncaughtException getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UncaughtException(context);
        }
        return mInstance;
    }

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param thread the thread
     * @param ex     the exception
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Lg.e(TAG, "unCaughtException, thread: " + thread
                + " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
                + ex);
        if (mCacheLogFile != null && mCacheLogFile.exists()) {
            FileWriter writer = null;
            try {
                ex.printStackTrace();
                writer = new FileWriter(mCacheLogFile, true);
                writer.write("\r\n");
                writer.write("time-->" + TimerUtils.formatTime(System.currentTimeMillis(),
                        TimerUtils.FORMAT_YYYY_MM_DD$BLANK$HH$COLON$MM));
                writer.write("\r\n");
                writer.write("sdk-->" + Build.VERSION.SDK_INT);
                writer.write("\r\n");
                writer.write("device-->" + Build.DEVICE);
                writer.write("\r\n");
                writer.write("info-->");
                ex.printStackTrace(new PrintWriter(writer));
                writer.write("\r\n");
                writer.flush();

                // 自杀后关闭所有Activity
                SendRunnable sendRunnable = new SendRunnable(writer.toString());
                new Thread(sendRunnable).start();
            } catch (Exception e) {
                Lg.print(TAG, "unCaughtException:" + e.getMessage());
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class SendRunnable implements Runnable {

        String log;

        public SendRunnable(String log) {
            this.log = log;
        }

        @Override
        public void run() {
            try {
                killProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void killProgress() {
        if (!isSend) {
            isSend = true;
            //退出所有activity
            //BasicApplication.activityStack.pop().finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            // 自杀后自启 需要时打开
            //restartApplication();
        }
    }

    private void restartApplication() {
        final Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }
}
