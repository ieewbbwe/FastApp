package com.android_mobile.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android_mobile.capture.CaptureActivity;
import com.android_mobile.core.enums.CacheType;
import com.android_mobile.core.enums.ModalDirection;
import com.android_mobile.core.event.BasicEvent;
import com.android_mobile.core.net.BasicAsyncTask;
import com.android_mobile.core.net.IBasicAsyncTask;
import com.android_mobile.core.net.IBasicAsyncTaskFinish;
import com.android_mobile.core.net.ThreadPool;
import com.android_mobile.core.net.http.Service;
import com.android_mobile.core.net.http.ServiceRequest;
import com.android_mobile.core.ui.EmptyLayout;
import com.android_mobile.core.ui.NavigationBar;
import com.android_mobile.core.ui.listener.IMediaImageListener;
import com.android_mobile.core.ui.listener.IMediaPicturesListener;
import com.android_mobile.core.ui.listener.IMediaScannerListener;
import com.android_mobile.core.ui.listener.IMediaSoundRecordListener;
import com.android_mobile.core.ui.listener.IMediaVideoListener;
import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.core.utiles.CacheUtil;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.core.utiles.StringUtils;
import com.android_mobile.core.utiles.Utils;
import com.android_mobile.core.utiles.ViewServer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public abstract class BasicActivity extends RxAppCompatActivity
        implements IBasicCoreMethod, IBasicAsyncTaskFinish {

    public final String TAG = this.getClass().getSimpleName();
    //管理类
    private DisplayMetrics metrics;
    private InputMethodManager imm;
    private ActivityManager activityManager;
    private BasicApplication application;
    private Handler h;
    private FragmentManager fm;
    public LayoutInflater inflater;

    //控件
    public NavigationBar navigationBar;
    private LinearLayout progressBar;
    private TextView progressBarLabel;
    private RelativeLayout modalViewGroup;
    private RelativeLayout helpView;
    private RelativeLayout modalViewGroupBg;
    private ViewStub bodyStub;
    private View bodyView;
    private RelativeLayout rootView;
    private Toast toast;
    private ProgressDialog progressDialog;
    public EmptyLayout mEmptyLl;

    //变量
    private ArrayList<BasicAsyncTask> tasks = new ArrayList<BasicAsyncTask>();
    public static int skinColor;
    private Stack<Integer> helpImgs = new Stack<Integer>();
    public Stack<Integer> helpFragmentImgs = new Stack<Integer>();
    Bitmap _tmpBtmp = null;
    public static String backActivityTitle = ""; //跳船activity 前保存当前activity 的title
    public float SCREEN_WIDTH;
    public float SCREEN_HEIGHT;
    public int STATUS_BAR_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        printLog();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;
        STATUS_BAR_HEIGHT = getStatusBarHeightForReflect();
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        activityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        application = (BasicApplication) this.getApplication();
        h = new Handler();
        BasicApplication.activityStack.add(this);
        fm = getSupportFragmentManager();
        inflater = getLayoutInflater();
        super.onCreate(savedInstanceState);
        if (Lg.DEBUG) {
            ViewServer.get(this).addWindow(this);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        printLog();
        super.setContentView(R.layout.activity_frame);
        navigationBar = new NavigationBar(this,
                (ViewStub) findViewById(R.id.title_stub));
        progressBar = (LinearLayout) findViewById(R.id.frame_progress);
        mEmptyLl = (EmptyLayout) findViewById(R.id.error_layout);

        progressBarLabel = (TextView) findViewById(R.id.frame_progress_label);
        modalViewGroup = (RelativeLayout) findViewById(R.id.frame_modal_view_root);
        helpView = (RelativeLayout) findViewById(R.id.frame_help_relativelayout);
        modalViewGroupBg = (RelativeLayout) findViewById(R.id.frame_modal_view_bg);
        modalViewGroupBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popModalFragment();
            }
        });

        helpView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (_tmpBtmp != null && !_tmpBtmp.isRecycled()) {
                    _tmpBtmp.recycle();
                }
                if (helpImgs.size() > 0) {
                    _tmpBtmp = displayHelpView(helpImgs.pop());
                } else if (helpFragmentImgs.size() > 0) {
                    _tmpBtmp = displayHelpView(helpFragmentImgs.pop());
                } else {
                    helpView.setVisibility(View.GONE);
                }
            }
        });
        bodyStub = (ViewStub) findViewById(R.id.body_stub);
        bodyStub.setLayoutResource(layoutResID);
        bodyView = bodyStub.inflate();
        rootView = (RelativeLayout) findViewById(R.id.main_root);
        //网络数据加载的时候出现的页面

        // 换肤功能
        skinColor = CacheUtil.getInteger("SKIN_COLOR");
        if (skinColor != -1) {
            navigationBar.layout.setBackgroundColor(skinColor);
        }
        initDefault();
        init();
    }

    protected void init() {
        printLog();
        initComp();
        initListener();
        initData();
    }

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();

    protected void initDefault() {
        if (backActivityTitle.equals("")) {
            navigationBar.leftBtn.setVisibility(View.GONE);
        } else {
            navigationBar.leftBtn.setVisibility(View.VISIBLE);
            navigationBar.leftBtn.setText(" " + backActivityTitle);
            navigationBar.leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popFragment();
                }
            });
        }
        navigationBar.rightBtn.setVisibility(View.GONE);
    }

    @Override
    public void async(IBasicAsyncTask callback, ServiceRequest req,
                      Service service) {
        async(callback, req, service, CacheType.DEFAULT_NET);
    }

    @Override
    public void async(IBasicAsyncTask callback, ServiceRequest req,
                      Service service, CacheType cacheType) {
        closeSoftInput();
        closeViewInteraction();
        if (BasicAsyncTask.asyncWithProgress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayProgressBar();
                }
            });
        }
        BasicAsyncTask task = new BasicAsyncTask(req, service, cacheType,
                callback);
        tasks.add(task);
        task.addFinishListener(this);
        task.execute();
    }

    @Override
    public void asyncTaskFinish(BasicAsyncTask task) {
        tasks.remove(task);
        openViewInteraction();
        if (BasicAsyncTask.asyncWithProgress && tasks.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hiddenProgressBar();
                }
            });
        }
    }

    public void exitAppWithToast() {
        CacheUtil.saveInteger("app_help_view", 100);
        ThreadPool.basicPool.shutdown();
        ThreadPool.fixedPool.shutdown();
        while (BasicApplication.activityStack.size() > 0) {
            BasicApplication.activityStack.pop().finish();
        }
        finish();
        System.gc();
        System.exit(0);
    }

    /**
     * 关闭软键盘
     */
    public void closeSoftInput() {
        printLog();
        if (imm != null && imm.isActive()) {
            View focusView = getCurrentFocus();
            if (focusView != null) {
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param et
     */
    public void showSoftInput(EditText et) {
        imm.showSoftInput(et, 0);
    }

    /**
     * 获取当前程序的版本号
     */
    public int getVersionCode() {
        // 获取packagemanager的实例
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 取消异步调用
     */
    public void cancelAsyncTask(BasicAsyncTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    public void cancelAllTask() {
        printLog();
        if (tasks == null || tasks.size() == 0)
            return;
        for (int i = 0; i < tasks.size(); i++) {
            cancelAsyncTask(tasks.get(i));
        }
        tasks.clear();
    }

    /**
     * 显示Toast
     */
    public void toast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        } else {
            // toast.cancel();
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void toast(String text) {
        if (toast == null) {
            toast = Toast.makeText(this.getApplicationContext(), text,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    @Override
    public void displayProgressBar(String s) {
        progressBar.bringToFront();
        progressBar.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(s)) {
            progressBarLabel.setVisibility(View.VISIBLE);
            progressBarLabel.setText(s);
        }
    }

    public void displayProgressBar() {
        displayProgressBar(null);
    }

    public void hiddenProgressBar() {
        progressBar.setVisibility(View.GONE);
        progressBarLabel.setVisibility(View.GONE);
        progressBarLabel.setText("");
    }

    /**
     * 获取该activity所有view
     */
    public List<View> getAllChildViews() {
        View view = this.getWindow().getDecorView();
        return getAllChildViews(view);
    }

    public List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    private void printLog() {
        Lg.print(TAG, Thread.currentThread().getStackTrace()[3].getMethodName());
    }

    public void setEditViewClearButton(final EditText et, final View clear) {
        setEditViewClearButton(et, clear, true);
    }

    public void setEditViewClearButton(final EditText et, final View clear,
                                       final boolean clearOnce) {

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (et.getText().toString().trim().length() == 0) {
                    clear.setVisibility(View.GONE);
                } else {
                    clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (clearOnce) {
                    et.setText("");
                } else {
                    String regPhoneNum = et.getText().toString().trim();
                    if (regPhoneNum.length() > 0) {
                        regPhoneNum = regPhoneNum.substring(0,
                                regPhoneNum.length() - 1);
                        et.setText(regPhoneNum);
                        // 设置edittext编辑在末尾
                        CharSequence text = et.getText();
                        if (text != null) {
                            Spannable spanText = (Spannable) text;
                            Selection.setSelection(spanText, text.length());
                        }
                    }
                }
            }
        });
    }

    private Bitmap displayHelpView(int resId) {
        helpView.removeAllViews();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(this);
        Bitmap b = BitmapUtils.obtainBitmap(this, resId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(b);
        helpView.addView(imageView, lp);
        helpView.bringToFront();
        helpView.setVisibility(View.VISIBLE);
        return b;
    }

    public void setTitle(String title) {
        navigationBar.titleText.setText(title);
    }

    protected void openViewInteraction() {

    }

    protected void closeViewInteraction() {

    }

    /*---------应用内跳转----------*/
    private String backText;

    public BasicActivity getActivity() {
        return this;
    }

    public void setBackText(String s) {
        backText = s;
    }

    public void pushActivity(Class<?> a) {
        if (StringUtils.isEmpty(backText)) {
            backActivityTitle = navigationBar.titleText.getText().toString()
                    .trim();
        } else {
            backActivityTitle = backText;
        }
        startActivity(new Intent(this, a));
    }

    public void pushActivity(Intent i) {
        if (StringUtils.isEmpty(backText)) {
            backActivityTitle = navigationBar.titleText.getText().toString()
                    .trim();
        } else {
            backActivityTitle = backText;
        }
        startActivity(i);
    }

    public void pushActivity(Intent i, boolean finishSelf) {
        startActivity(i);
        if (finishSelf) {
            backActivityTitle = "";
            this.finish();
        }
    }

    public void pushActivityForResult(Class<?> a, int requestCode) {
        if (StringUtils.isEmpty(backText)) {
            backActivityTitle = navigationBar.titleText.getText().toString()
                    .trim();
        } else {
            backActivityTitle = backText;
        }
        startActivityForResult(new Intent(this, a), requestCode);
    }

    public void pushActivityForResult(Intent a, int requestCode) {
        if (StringUtils.isEmpty(backText)) {
            backActivityTitle = navigationBar.titleText.getText().toString()
                    .trim();
        } else {
            backActivityTitle = backText;
        }
        startActivityForResult(a, requestCode);
    }

    public void pushActivity(Class<?> a, boolean finishSelf) {
        pushActivity(a);
        if (finishSelf) {
            backActivityTitle = "";
            this.finish();
        }
    }

    public void popActivity() {
        BasicApplication.activityStack.pop().finish();
    }

    /*---------fragment相关----------*/
    public BasicFragment currentFragment;
    protected int fragmentBodyLayoutRes = -1;
    public boolean fragmentChangeEffect = false;

    @Override
    public void setPushFragmentLayout(int layoutId) {
        fragmentBodyLayoutRes = layoutId;
    }

    public void pushFragment(BasicFragment f) {
        pushFragment(f, false);
    }

    public void pushFragment(int layoutId, BasicFragment f) {
        pushFragment(layoutId, f, false);
    }

    /**
     * 配置 是否在Fragment 切换的时候 需要默认动画
     *
     * @param flag
     */
    public void isDisplayFragmentEffect(boolean flag) {
        fragmentChangeEffect = flag;
    }

    public void isDisplayProgressByHttpRequest(boolean b) {
        BasicAsyncTask.asyncWithProgress = b;
    }

    @Override
    public void pushFragment(BasicFragment f, boolean flag) {
        if (currentFragment != null && f != null
                && f.name.equals(currentFragment.name))
            return;
        FragmentTransaction ft = this.fm.beginTransaction();
        if (this.fragmentChangeEffect) {
            ft.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_out,
                    android.R.anim.fade_in);
        }
        ft.replace(fragmentBodyLayoutRes, f);
        if (flag) {
            ft.addToBackStack(f.name);
        } else {
        }
        ft.commit();
    }

    @Override
    public void pushFragment(int layoutId, BasicFragment f, boolean flag) {
        if (currentFragment != null && f != null
                && f.name.equals(currentFragment.name))
            return;
        FragmentTransaction ft = this.fm.beginTransaction();
        if (this.fragmentChangeEffect) {
            ft.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_out,
                    android.R.anim.fade_in);
        }
        ft.replace(layoutId, f);
        if (flag) {
            ft.addToBackStack(f.name);
        }
        ft.commit();
    }

    public void popFragment() {
        Lg.print("popFragment");
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            popActivity();
        }
    }

    /*---------Module相关----------*/
    private int modalAnimTime = 300;
    private boolean isModalFragmentDisplay;
    private float widthDip = 0;
    private ModalDirection direction = ModalDirection.RIGHT;

    @Override
    public void displayViewAnimation(View v, int fx, float depth) {
        // right
        if (fx == 1) {
            ObjectAnimator.ofFloat(v, "translationX", SCREEN_WIDTH,
                    SCREEN_WIDTH - depth).start();
        }
    }

    @Override
    public void pushModalFragment(BasicFragment f) {
        this.pushModalFragment(ModalDirection.RIGHT, (int) (SCREEN_WIDTH / 2),
                modalAnimTime, f);
    }

    @Override
    public void pushModalFragment(int widthDip, BasicFragment f) {
        this.pushModalFragment(ModalDirection.RIGHT, widthDip, modalAnimTime, f);
    }

    @Override
    public void pushModalFragment(ModalDirection d, int widthDip,
                                  BasicFragment f) {
        this.pushModalFragment(d, widthDip, modalAnimTime, f);
    }

    public void initModalFragment(BasicFragment f) {
        setModalFragment(f);
    }

    public void setModalFragment(BasicFragment f) {
        pushFragment(R.id.frame_modal_view_root, f, false);
    }

    @Override
    public void pushModalFragment(ModalDirection d, int widthDip, int time,
                                  BasicFragment f) {
        pushModalFragment(d, widthDip, time, f, true);
    }

    @Override
    public void pushModalFragment(ModalDirection d, int widthDip, int time,
                                  BasicFragment f, boolean hasBackground) {
        initModalFragment(f);
        isModalFragmentDisplay = true;
        this.widthDip = widthDip;
        this.modalAnimTime = time;
        this.direction = d;
        ValueAnimator colorAnim;
        if (hasBackground) {
            colorAnim = ObjectAnimator.ofInt(modalViewGroupBg,
                    "backgroundColor", /* Red */0x00000000, /* Blue */
                    0x55000000);
        } else {
            colorAnim = ObjectAnimator.ofInt(modalViewGroupBg,
                    "backgroundColor", /* Red */0x00000000, /* Blue */
                    0x00000000);
        }
        modalViewGroupBg.setVisibility(View.VISIBLE);
        modalViewGroup.setVisibility(View.VISIBLE);
        colorAnim.setDuration(modalAnimTime);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
        RelativeLayout.LayoutParams imagebtn_params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (ModalDirection.RIGHT == d) {
            // Lg.print("modal  right");
            imagebtn_params.height = (int) SCREEN_HEIGHT;
            imagebtn_params.width = widthDip;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator
                    .ofFloat(modalViewGroup, "translationX", SCREEN_WIDTH,
                            SCREEN_WIDTH - widthDip).setDuration(modalAnimTime)
                    .start();

        } else if (ModalDirection.LEFT == d) {
            // Lg.print("modal  left");
            imagebtn_params.height = (int) SCREEN_HEIGHT;
            imagebtn_params.width = widthDip;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator
                    .ofFloat(modalViewGroup, "translationX", -widthDip, 0)
                    .setDuration(modalAnimTime).start();
        } else if (ModalDirection.BOTTOM == d) {
            // Lg.print("modal  bottom");
            imagebtn_params.height = widthDip;
            imagebtn_params.width = (int) SCREEN_WIDTH;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator
                    .ofFloat(modalViewGroup, "y",
                            SCREEN_HEIGHT - STATUS_BAR_HEIGHT,
                            SCREEN_HEIGHT - STATUS_BAR_HEIGHT - widthDip)
                    .setDuration(modalAnimTime).start();
        } else if (ModalDirection.TOP == d) {
            // Lg.print("modal  top");
            imagebtn_params.height = widthDip;
            imagebtn_params.width = (int) SCREEN_WIDTH;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator.ofFloat(modalViewGroup, "translationY", 0, widthDip)
                    .setDuration(modalAnimTime).start();
        }
        // pushFragment(R.id.frame_modal_view_root, f,false);
    }

    @Override
    public void pushModalView(View v, ModalDirection d, int widthDip, int time) {
        modalViewGroup.removeAllViews();
        RelativeLayout.LayoutParams vLp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        modalViewGroup.addView(v, vLp);
        isModalFragmentDisplay = true;
        this.widthDip = widthDip;
        this.modalAnimTime = time;
        this.direction = d;
        modalViewGroupBg.setVisibility(View.VISIBLE);
        modalViewGroup.setVisibility(View.VISIBLE);
        ValueAnimator colorAnim = ObjectAnimator.ofInt(modalViewGroupBg,
                "backgroundColor", /* Red */0x00000000, /* Blue */0x65000000);
        colorAnim.setDuration(modalAnimTime);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
        RelativeLayout.LayoutParams imagebtn_params = (android.widget.RelativeLayout.LayoutParams) modalViewGroup
                .getLayoutParams();
        if (ModalDirection.RIGHT == d) {
            // Lg.print("modal  right");
            imagebtn_params.height = (int) SCREEN_HEIGHT;
            imagebtn_params.width = widthDip;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator
                    .ofFloat(modalViewGroup, "translationX", SCREEN_WIDTH,
                            SCREEN_WIDTH - widthDip).setDuration(modalAnimTime)
                    .start();

        } else if (ModalDirection.LEFT == d) {
            // Lg.print("modal  left");
            imagebtn_params.height = (int) SCREEN_HEIGHT;
            imagebtn_params.width = widthDip;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator
                    .ofFloat(modalViewGroup, "translationX", -widthDip, 0)
                    .setDuration(modalAnimTime).start();
        } else if (ModalDirection.BOTTOM == d) {
            // Lg.print("modal  bottom: STATUS BAR HEIGHT:" +
            imagebtn_params.height = widthDip;
            imagebtn_params.width = (int) SCREEN_WIDTH;
            modalViewGroup.setLayoutParams(imagebtn_params);
            if (isInScreen()) {
                ObjectAnimator
                        .ofFloat(modalViewGroup, "y",
                                SCREEN_HEIGHT,
                                SCREEN_HEIGHT - widthDip)
                        .setDuration(modalAnimTime).start();
            } else {
                ObjectAnimator
                        .ofFloat(modalViewGroup, "y",
                                SCREEN_HEIGHT - STATUS_BAR_HEIGHT,
                                SCREEN_HEIGHT - STATUS_BAR_HEIGHT - widthDip)
                        .setDuration(modalAnimTime).start();
            }
        } else if (ModalDirection.TOP == d) {
            // Lg.print("modal  top");
            imagebtn_params.height = widthDip;
            imagebtn_params.width = (int) SCREEN_WIDTH;
            modalViewGroup.setLayoutParams(imagebtn_params);
            ObjectAnimator.ofFloat(modalViewGroup, "y", -widthDip, STATUS_BAR_HEIGHT * 2)
                    .setDuration(modalAnimTime).start();
        }
    }

    //判断是否处于沉浸式状态
    private boolean isInScreen() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;
    }

    @Override
    public void pushModalView(View v, ModalDirection d, int widthDip) {
        pushModalView(v, d, widthDip, modalAnimTime);
    }

    @Override
    public void pushModalView(View v, int widthDip) {
        pushModalView(v, ModalDirection.BOTTOM, widthDip, modalAnimTime);
    }

    @Override
    public void popModalView() {
        popModalFragment();
    }

    @Override
    public void popModalFragment() {
        isModalFragmentDisplay = false;
        if (direction == ModalDirection.RIGHT) {
            ObjectAnimator
                    .ofFloat(modalViewGroup, "translationX",
                            SCREEN_WIDTH - widthDip, SCREEN_WIDTH)
                    .setDuration(modalAnimTime).start();
        } else if (direction == ModalDirection.LEFT) {
            ObjectAnimator
                    .ofFloat(modalViewGroup, "translationX", 0, -widthDip)
                    .setDuration(modalAnimTime).start();
        } else if (direction == ModalDirection.TOP) {
            ObjectAnimator.ofFloat(modalViewGroup, "y", 0, -widthDip)
                    .setDuration(modalAnimTime).start();
        } else if (direction == ModalDirection.BOTTOM) {
            if (isInScreen()) {
                ObjectAnimator
                        .ofFloat(modalViewGroup, "y",
                                SCREEN_HEIGHT - widthDip,
                                SCREEN_HEIGHT)
                        .setDuration(modalAnimTime).start();
            } else {
                ObjectAnimator
                        .ofFloat(modalViewGroup, "y",
                                SCREEN_HEIGHT - widthDip - STATUS_BAR_HEIGHT,
                                SCREEN_HEIGHT - STATUS_BAR_HEIGHT)
                        .setDuration(modalAnimTime).start();
            }
        }
        ValueAnimator colorAnim = ObjectAnimator.ofInt(modalViewGroupBg,
                "backgroundColor", /* Red */
                0x65000000, /* Blue */0x00000000);
        colorAnim.setDuration(modalAnimTime);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                modalViewGroupBg.setVisibility(View.GONE);
                modalViewGroup.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        colorAnim.start();
    }

    /*----------常用方法----------*/
    private static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    private static final int REQUEST_CODE_TAKE_VIDEO = 2;// 摄像的照相的requestCode
    private static final int RESULT_CAPTURE_RECORDER_SOUND = 3;// 录音的requestCode
    private static final int RESULT_CAPTURE_PICTURES = 4;// 本地相冊
    private static final int RESULT_SCANNER = 5;// 条码扫描
    private String strImgPath = "";// 照片文件绝对路径
    private String strVideoPath = "";// 视频文件的绝对路径
    private String strRecorderPath = "";// 录音文件的绝对路径
    private IMediaImageListener iMediaImageListener = null;
    private IMediaVideoListener iMediaVideoListener = null;
    private IMediaSoundRecordListener iMediaSoundRecordListener = null;
    private IMediaPicturesListener iMediaPicturesListener = null;
    private IMediaScannerListener iMediaScannerListener = null;

    @Override
    public void setMediaImageListener(IMediaImageListener listener) {
        this.iMediaImageListener = listener;
    }

    @Override
    public void setMediaPictureListener(IMediaPicturesListener listener) {
        this.iMediaPicturesListener = listener;
    }

    @Override
    public void setMediaScannerListener(IMediaScannerListener listener) {
        this.iMediaScannerListener = listener;
    }

    @Override
    public void setMediaVideoListener(IMediaVideoListener listener) {
        this.iMediaVideoListener = listener;
    }

    @Override
    public void setMediaSoundRecordListener(IMediaSoundRecordListener listener) {
        this.iMediaSoundRecordListener = listener;
    }

    /**
     * 弹出 选择 图片方式组件
     *
     * @return
     */
    public View showPictureComponent() {
        View v = inflater.inflate(R.layout.dialog_headportrait, null);
        TextView photograph = (TextView) v.findViewById(R.id.tv_take_photo);// 拍照
        TextView albums = (TextView) v.findViewById(R.id.tv_pick_photo);// 相册
        TextView cancel = (TextView) v.findViewById(R.id.tv_cancel);// 取消
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popModalView();
                startCamera();
            }
        });
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popModalView();
                startPictures();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popModalView();
            }
        });
        return v;

    }

    /**
     * 本地相册
     */
    public void startPictures() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, RESULT_CAPTURE_PICTURES);
    }

    /**
     * 照相功能
     */
    public void startCamera() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        strImgPath = Environment.getExternalStorageDirectory().toString()
                + "/CONSDCGMPIC/";// 存放照片的文件夹
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()) + ".jpg";// 照片命名
        File out = new File(strImgPath);
        if (!out.exists()) {
            out.mkdirs();
        }
        out = new File(strImgPath, fileName);
        strImgPath = strImgPath + fileName;// 该照片的绝对路径
        Uri uri = Uri.fromFile(out);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);

    }

    /**
     * 拍摄视频
     */
    public void startVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
    }

    public void startScan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, RESULT_SCANNER);
    }

    public void startScan(Intent i) {
        startActivityForResult(i, RESULT_SCANNER);
    }

    /**
     * 录音功能
     */
    public void startSoundRecorder() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/amr");
        startActivityForResult(intent, RESULT_CAPTURE_RECORDER_SOUND);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        printLog();
        super.onActivityResult(requestCode, resultCode, data);
        String picturePath = "";
        switch (requestCode) {
            case BasicActivity.RESULT_CAPTURE_PICTURES:// 相册
                if (resultCode == RESULT_OK) {
                    if (iMediaPicturesListener != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            String uriStr = selectedImage.toString();
                            //部分机器获取的url不同 例如小米
                            if (uriStr.startsWith("file://")) {
                                picturePath = uriStr.substring(7, uriStr.length());
                                if (iMediaPicturesListener != null) {
                                    iMediaPicturesListener.mediaPicturePath(picturePath);
                                }
                                return;
                            }
                            String path = uriStr.substring(10, uriStr.length());
                            if (path.startsWith("com.sec.android.gallery3d")) {
                                return;
                            }
                        }
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (filePathColumn != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);
                                cursor.close();
                            }
                        }
                        Lg.print(TAG, "select_pic_path:" + picturePath);
                        if (iMediaPicturesListener != null) {
                            iMediaPicturesListener.mediaPicturePath(picturePath);
                        }
                    }
                }
                break;
            case BasicActivity.RESULT_CAPTURE_IMAGE:// 拍照
                if (resultCode == RESULT_OK) {
                    Lg.print(TAG, "select_capture_path:" + strImgPath);
                    if (iMediaImageListener != null) {
                        iMediaImageListener.mediaImagePath(strImgPath);
                    }
                }
                break;
            case BasicActivity.REQUEST_CODE_TAKE_VIDEO:// 拍摄视频
                if (resultCode == RESULT_OK) {
                    Uri uriVideo = data.getData();
                    Cursor cursor = getContentResolver().query(uriVideo, null,
                            null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            /** _data：文件的绝对路径 ，_display_name：文件名 */
                            strVideoPath = cursor.getString(cursor
                                    .getColumnIndex("_data"));
                        }
                    }
                    Lg.print(TAG, "select_video_path:" + strVideoPath);
                    if (iMediaVideoListener != null) {
                        iMediaVideoListener.mediaVideoPath(strVideoPath);
                    }
                }
                break;
            case BasicActivity.RESULT_CAPTURE_RECORDER_SOUND:// 录音
                if (resultCode == RESULT_OK) {
                    Uri uriRecorder = data.getData();
                    Cursor cursor = getContentResolver().query(uriRecorder, null,
                            null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            /** _data：文件的绝对路径 ，_display_name：文件名 */
                            strRecorderPath = cursor.getString(cursor
                                    .getColumnIndex("_data"));
                        }
                    }
                    if (iMediaSoundRecordListener != null) {
                        iMediaSoundRecordListener.mediaSoundRecordPath(strRecorderPath);
                    }
                }
                break;
            case BasicActivity.RESULT_SCANNER:// 拍照
                if (resultCode == RESULT_OK) {
                    if (iMediaScannerListener != null) {
                        iMediaScannerListener.mediaScannerResult(data
                                .getStringExtra("result"));
                    }
                }
                break;
        }
    }

    @Override
    public void setTextViewIcon(TextView tv, int l, int t, int r, int b) {
        Drawable left = null;
        Drawable right = null;
        Drawable top = null;
        Drawable bottom = null;
        if (l > 0) {
            left = getResources().getDrawable(l);
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
        }
        if (r > 0) {
            right = getResources().getDrawable(r);
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());
        }
        if (t > 0) {
            top = getResources().getDrawable(t);
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        }
        if (b > 0) {
            bottom = getResources().getDrawable(b);
            bottom.setBounds(0, 0, bottom.getMinimumWidth(),
                    bottom.getMinimumHeight());
        }
        tv.setCompoundDrawables(left, top, right, bottom);
    }

    /**
     * 模拟键盘的 view 推移 键盘出现时 挡住输入窗口， view 整体向上推移 包括 导航栏和 bodyview
     */
    public void pushBody(int dept) {
        ObjectAnimator.ofFloat(bodyView, "y", dept).setDuration(300).start();
        ObjectAnimator.ofFloat(navigationBar.layout, "y", dept - Utils.dip2px(this, 55))
                .setDuration(300).start();
    }

    public void pushBackBody() {
        ObjectAnimator.ofFloat(bodyView, "y", Utils.dip2px(this, 55)).setDuration(300)
                .start();
        ObjectAnimator.ofFloat(navigationBar.layout, "y", 0).setDuration(300)
                .start();
    }

    /**
     * 显示进度条
     */
    public void showProgress() {
        showProgress(R.string.loading_net, true);
    }

    public void showProgress(boolean canBack) {
        showProgress(R.string.loading_net, canBack);
    }

    /**
     * 显示进度条
     *
     * @param resID
     * @param canBack
     */
    public void showProgress(int resID, boolean canBack) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
        progressDialog = ProgressDialog.show(this, "", getResources()
                .getString(resID));
        progressDialog.setCancelable(canBack);
    }

    /**
     * 显示进度条
     *
     * @param res
     * @param canBack
     */
    public void showProgress(String res, boolean canBack) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
        progressDialog = ProgressDialog.show(this, "", res);
        progressDialog.setCancelable(canBack);
    }

    /**
     * 取消进度条
     */
    public void cancelProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }

    /**
     * 獲取狀態欄高度 反射
     */
    public int getStatusBarHeightForReflect() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取运行中任务
     */
    public ArrayList<BasicAsyncTask> getTasks() {
        return tasks;
    }

    public int dip2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int px2dip(float pxValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void updateSkin(int skinColor) {
        if (skinColor != -1) {
            navigationBar.layout.setBackgroundColor(skinColor);
            // bodyView.setBackgroundColor(skinColor);
            BasicEvent e = new BasicEvent(BasicEvent.UPDATE_SKIN);
            e.setData(skinColor);
            CacheUtil.saveInteger("SKIN_COLOR", skinColor);
        }
    }

    public void setSkin(String color) {
        skinColor = Color.parseColor(color);
        updateSkin(skinColor);
    }

    @Override
    public boolean hasSkinColor() {
        if (skinColor == -1) {
            return false;
        }
        return true;
    }

    public void setSkin(int colorRes) {
        skinColor = colorRes;
        updateSkin(skinColor);
    }

    @Override
    protected void onPause() {
        printLog();
        super.onPause();
    }

    @Override
    protected void onResume() {
        printLog();
        super.onResume();
    }

    @Override
    public void focus(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
    }

    @Override
    public void finish() {
        printLog();
        cancelAllTask();
        BasicApplication.activityStack.remove(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        printLog();
        super.onDestroy();
        if (Lg.DEBUG) {
            ViewServer.get(this).removeWindow(this);
        }
    }

}
