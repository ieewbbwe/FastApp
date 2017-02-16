package com.android_mobile.core;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android_mobile.core.enums.CacheType;
import com.android_mobile.core.enums.ModalDirection;
import com.android_mobile.core.net.BasicAsyncTask;
import com.android_mobile.core.net.IBasicAsyncTask;
import com.android_mobile.core.net.IBasicAsyncTaskFinish;
import com.android_mobile.core.net.http.Service;
import com.android_mobile.core.net.http.ServiceRequest;
import com.android_mobile.core.ui.NavigationBar;
import com.android_mobile.core.ui.listener.IMediaImageListener;
import com.android_mobile.core.ui.listener.IMediaPicturesListener;
import com.android_mobile.core.ui.listener.IMediaScannerListener;
import com.android_mobile.core.ui.listener.IMediaSoundRecordListener;
import com.android_mobile.core.ui.listener.IMediaVideoListener;
import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.core.utiles.Lg;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;
import java.util.Stack;

@SuppressLint("NewApi")
public abstract class BasicFragment extends RxFragment implements
        IBasicAsyncTaskFinish, IBasicCoreMethod {
    public View v;
    public BasicActivity activity;
    public BasicApplication application;
    protected boolean asyncWithProgress = true;
    protected NavigationBar navigationBar = null;
    private Stack<BasicAsyncTask> tasks = new Stack<BasicAsyncTask>();
    public String name = this.getClass().getName();
    public final String TAG = this.getClass().getSimpleName();

    private void printLog() {
        Lg.print(TAG, Thread.currentThread().getStackTrace()[3]
                .getMethodName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        printLog();
        v = inflater.inflate(create(), container, false);
        activity = (BasicActivity) getActivity();
        application = (BasicApplication) activity.getApplication();
        navigationBar = activity.navigationBar;
        activity.updateSkin(BasicActivity.skinColor);
        init();
        return v;
    }

    @Override
    public void async(IBasicAsyncTask callback, ServiceRequest req, Service service) {
        activity.async(callback, req, service);
    }

    @Override
    public void async(IBasicAsyncTask callback, ServiceRequest req, Service service, CacheType cacheType) {
        activity.async(callback, req, service, cacheType);
    }

    @Override
    public void asyncTaskFinish(BasicAsyncTask task) {
        tasks.remove(task);
        if (tasks.size() == 0 && asyncWithProgress) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hiddenProgressBar();
                }
            });
        }
    }

    protected abstract int create();

    protected void init() {
        printLog();
        initComp();
        initListener();
        initData();
    }

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();


    public void pushFragment(BasicFragment f) {
        activity.pushFragment(f);
    }

    public void popBackStack() {
        activity.popFragment();
    }

    public void pushActivity(Class<?> a) {
        activity.pushActivity(a);
    }

    public void pushActivity(Intent i) {
        activity.pushActivity(i);
    }

    public void pushActivity(Class<?> a, boolean finishSelf) {
        activity.pushActivity(a, finishSelf);
    }

    public void popActivity() {
        activity.popActivity();
    }

    @Override
    public void onDestroy() {
        printLog();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        printLog();
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        printLog();
        if (activity != null)
            activity.currentFragment = this;
        super.onStart();
    }

    @Override
    public void onStop() {
        printLog();
        while (tasks.size() > 0) {
            cancelAsyncTask(tasks.pop());
        }
//		hiddenProgressBar();
//		cancelProgress();
        super.onStop();
    }

    public View findViewById(int id) {
        return v.findViewById(id);
    }

    // 取消异步调用
    public void cancelAsyncTask(BasicAsyncTask task) {
        activity.cancelAsyncTask(task);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        printLog();
        super.onViewCreated(view, savedInstanceState);
    }

    public void displayProgressBar() {
        activity.displayProgressBar();
    }

    public void hiddenProgressBar() {
        activity.hiddenProgressBar();
    }

    /**
     * 显示进度条
     */
    public void showProgress() {
        activity.showProgress(R.string.loading_net, true);
    }

    public void showProgress(boolean canBack) {
        activity.showProgress(R.string.loading_net, canBack);
    }

    /**
     * 取消进度条
     */
    public void cancelProgress() {
        activity.cancelProgress();
    }

    /**
     * 退出应用
     */
    public void exitAppWithToast() {
        activity.exitAppWithToast();
    }

    // 关闭软键盘
    public void closeSoftInput() {
        activity.closeSoftInput();
    }

    /**
     * 显示Toast
     */
    public void toast(int resId) {
        activity.toast(resId);
    }

    public void toast(String text) {
        activity.toast(text);
    }

    public void changeFragment(int layoutId, BasicFragment f) {
        activity.pushFragment(layoutId, f);
    }

    public void changeFragment(BasicFragment f) {
        if (activity.fragmentBodyLayoutRes != -1)
            activity.pushFragment(activity.fragmentBodyLayoutRes, f);
    }


    public void setTitle(String title) {
        activity.setTitle(title);
    }

    public void setTitle(int strRes) {
        activity.setTitle(strRes);
    }

    @Override
    public void isDisplayFragmentEffect(boolean flag) {
        activity.isDisplayFragmentEffect(flag);
    }

    @Override
    public void setEditViewClearButton(EditText et, View clear,
                                       boolean clearOnce) {
        activity.setEditViewClearButton(et, clear, clearOnce);
    }

    @Override
    public void showProgress(int resID, boolean canBack) {
        activity.showProgress(resID, canBack);
    }

    @Override
    public void showProgress(String res, boolean canBack) {
        activity.showProgress(res, canBack);
    }

    @Override
    public void showSoftInput(EditText et) {
        activity.showSoftInput(et);
    }

    @Override
    public int getVersionCode() {
        return activity.getVersionCode();
    }

    @Override
    public List<View> getAllChildViews() {
        return activity.getAllChildViews();
    }

    @Override
    public List<View> getAllChildViews(View view) {
        return activity.getAllChildViews(view);
    }

    @Override
    public void setMediaImageListener(IMediaImageListener listener) {
        activity.setMediaImageListener(listener);
    }

    @Override
    public void setMediaPictureListener(IMediaPicturesListener listener) {
        activity.setMediaPictureListener(listener);
    }

    @Override
    public void setMediaVideoListener(IMediaVideoListener listener) {
        activity.setMediaVideoListener(listener);
    }

    @Override
    public void setMediaSoundRecordListener(IMediaSoundRecordListener listener) {
        activity.setMediaSoundRecordListener(listener);
    }

    @Override
    public void setMediaScannerListener(IMediaScannerListener listener) {
        activity.setMediaScannerListener(listener);
    }

    @Override
    public void startPictures() {
        activity.startPictures();
    }

    @Override
    public void startCamera() {
        activity.startCamera();
    }

    @Override
    public void startVideo() {
        activity.startVideo();
    }

    @Override
    public void startSoundRecorder() {
        activity.startSoundRecorder();
    }

    @Override
    public void setPushFragmentLayout(int layoutId) {
        activity.setPushFragmentLayout(layoutId);
    }

    @Override
    public void pushFragment(int layoutId, BasicFragment f) {
        activity.pushFragment(layoutId, f);
    }

    @Override
    public void popFragment() {
        activity.popFragment();
    }

    @Override
    public void focus(View v) {
        activity.focus(v);
    }

    @Override
    public void displayViewAnimation(View v, int fx, float depth) {
        activity.displayViewAnimation(v, fx, depth);
    }

    @Override
    public void startScan() {
        activity.startScan();
    }

    @Override
    public void startScan(Intent i) {
        activity.startScan(i);
    }

    @Override
    public void pushFragment(BasicFragment f, boolean flag) {
        activity.pushFragment(f, flag);
    }

    @Override
    public void pushFragment(int layoutId, BasicFragment f, boolean flag) {
        activity.pushFragment(layoutId, f, flag);
    }

    @Override
    public void initModalFragment(BasicFragment f) {
        activity.initModalFragment(f);
    }

    @Override
    public void pushModalFragment(ModalDirection d, int widthDip, int time, BasicFragment f, boolean hasBackground) {
        activity.pushModalFragment(d, widthDip, time, f, true);
    }

    @Override
    public void pushModalFragment(ModalDirection d, int widthDip, int time,
                                  BasicFragment f) {
        activity.pushModalFragment(d, widthDip, time, f);

    }

    @Override
    public void pushModalFragment(ModalDirection d,
                                  int widthDip, BasicFragment f) {
        activity.pushModalFragment(d, widthDip, f);
    }

    @Override
    public void pushModalFragment(int widthDip, BasicFragment f) {
        activity.pushModalFragment(widthDip, f);
    }

    @Override
    public void pushModalFragment(BasicFragment f) {
        activity.pushModalFragment(f);
    }

    @Override
    public void pushActivityForResult(Class<?> a, int requestCode) {
        activity.pushActivityForResult(a, requestCode);
    }

    @Override
    public void popModalFragment() {
        activity.popModalFragment();
    }

    @Override
    public void displayProgressBar(String s) {
        activity.displayProgressBar(s);
    }

    @Override
    public void onPause() {
        printLog();
        super.onPause();
    }


    @Override
    public void onResume() {
        printLog();
        super.onResume();
    }

    @Override
    public boolean hasSkinColor() {
        return activity.hasSkinColor();
    }

    @Override
    public void pushModalView(View v, ModalDirection d, int widthDip, int time) {
        activity.pushModalView(v, d, widthDip, time);
    }

    @Override
    public void pushModalView(View v, ModalDirection d, int widthDip) {
        activity.pushModalView(v, d, widthDip);
    }

    @Override
    public void pushModalView(View v, int widthDip) {
        activity.pushModalView(v, widthDip);
    }

    @Override
    public void popModalView() {
        activity.popModalView();
    }

    public int getStatusBarHeight() {
        return activity.STATUS_BAR_HEIGHT;
    }

    public Bitmap readBitmap(int resId) {
        return BitmapUtils.obtainBitmap(activity, resId);
    }

    @Override
    public void setTextViewIcon(TextView tv, int l, int t, int r, int b) {
        activity.setTextViewIcon(tv, l, t, r, b);
    }
}
