package com.android_mobile.core;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android_mobile.core.enums.CacheType;
import com.android_mobile.core.enums.ModalDirection;
import com.android_mobile.core.net.BasicAsyncTask;
import com.android_mobile.core.net.IBasicAsyncTask;
import com.android_mobile.core.net.http.Service;
import com.android_mobile.core.net.http.ServiceRequest;
import com.android_mobile.core.ui.listener.IMediaImageListener;
import com.android_mobile.core.ui.listener.IMediaPicturesListener;
import com.android_mobile.core.ui.listener.IMediaScannerListener;
import com.android_mobile.core.ui.listener.IMediaSoundRecordListener;
import com.android_mobile.core.ui.listener.IMediaVideoListener;

import java.util.List;

public interface IBasicCoreMethod {

    void isDisplayFragmentEffect(boolean flag);

    void async(IBasicAsyncTask callback, ServiceRequest req,
               Service service, CacheType cacheType);

    void async(IBasicAsyncTask callback, ServiceRequest req,
               Service service);

    void setEditViewClearButton(final EditText et, final View clear,
                                final boolean clearOnce);

    void displayProgressBar();

    void displayProgressBar(String s);

    void hiddenProgressBar();

    void showProgress();

    void showProgress(boolean canBack);

    void showProgress(int resID, boolean canBack);

    void showProgress(String res, boolean canBack);

    void cancelProgress();

    void exitAppWithToast();

    void closeSoftInput();

    void showSoftInput(EditText et);

    int getVersionCode();

    void cancelAsyncTask(BasicAsyncTask task);

    void toast(int resId);

    void toast(String text);

    List<View> getAllChildViews();

    List<View> getAllChildViews(View view);

    void setMediaImageListener(IMediaImageListener listener);

    void setMediaPictureListener(IMediaPicturesListener listener);

    void setMediaVideoListener(IMediaVideoListener listener);

    void setMediaSoundRecordListener(IMediaSoundRecordListener listener);

    void setMediaScannerListener(IMediaScannerListener listener);

    void startScan();

    void startScan(Intent i);

    void startPictures();

    void startCamera();

    void startVideo();

    void startSoundRecorder();

    void setTitle(String title);

    void pushActivityForResult(Class<?> a, int requestCode);

    void pushActivity(Class<?> a);

    void pushActivity(Intent i);

    void pushActivity(Class<?> a, boolean finishSelf);

    void initModalFragment(BasicFragment f);

    void pushModalFragment(ModalDirection d, int widthDip, int time, BasicFragment f,
                           boolean hasBackground);

    void pushModalFragment(ModalDirection d, int widthDip,
                           BasicFragment f);

    void pushModalFragment(int widthDip, BasicFragment f);

    void pushModalFragment(BasicFragment f);

    void popModalFragment();

    void setPushFragmentLayout(int layoutId);

    void pushFragment(BasicFragment f);

    void pushFragment(BasicFragment f, boolean flag);

    void pushFragment(int layoutId, BasicFragment f);

    void pushFragment(int layoutId, BasicFragment f, boolean flag);

    void popFragment();

    void focus(View v);

    void displayViewAnimation(View v, int fx, float depth);

    void pushModalView(View v, ModalDirection d, int widthDip, int time);

    void pushModalView(View v, ModalDirection d, int widthDip);

    void pushModalView(View v, int widthDip);

    void popModalView();

    void pushModalFragment(ModalDirection d, int widthDip, int time,
                           BasicFragment f);

    void setTextViewIcon(TextView tv, int l, int t, int r, int b);

    boolean hasSkinColor();
}
