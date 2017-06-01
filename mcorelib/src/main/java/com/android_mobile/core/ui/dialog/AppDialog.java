package com.android_mobile.core.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android_mobile.core.BasicActivity;
import com.android_mobile.core.BasicDialog;
import com.android_mobile.core.R;
import com.android_mobile.core.utiles.StringUtils;

public class AppDialog extends BasicDialog {
    private TextView title;
    private EditText contentEt;
    private TextView descri;
    private Button button1;
    private Button button2;
    private View dividerView;
    private boolean isForced;
    private boolean isVisibleEt;//显示输入框的标志
    private String etHintMsg;
    private Context ctx;
    private AppDialogButtonOnClickListener listener;

    public interface AppDialogButtonOnClickListener {
        void onButton1Click(Dialog dialog, View v);

        void onButton2Click(Dialog dialog, View v);
    }

    @Override
    public int onCreate() {
        return R.layout.dialog_app;
    }

    @Override
    public void onViewCreated(View v) {
        init();
    }

    public AppDialog(Context context) {
        super(context);
        this.ctx = context;
    }

    public AppDialog(Context context, String etHintMsg) {
        this(context);
        this.isVisibleEt = true;
        this.etHintMsg = etHintMsg;
    }


    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        title = (TextView) findViewById(R.id.app_dialog_title);
        contentEt = (EditText) findViewById(R.id.dialog_content_et);
        if (isVisibleEt) {
            contentEt.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(etHintMsg)) {
                contentEt.setHint(etHintMsg);
            }
        }
        descri = (TextView) findViewById(R.id.app_dialog_descri);
        button1 = (Button) findViewById(R.id.app_dialog_button1);
        button2 = (Button) findViewById(R.id.app_dialog_button2);
        dividerView = findViewById(R.id.app_dialog_view);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (listener != null) {
                    listener.onButton1Click(getDialog(), isVisibleEt ? contentEt : button1);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (listener != null) {
                    listener.onButton2Click(getDialog(), isVisibleEt ? contentEt : button2);
                }
            }
        });

        setCanceledOnTouchOutside(true);// 设置点击dialog外面的界面 关闭dialog
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        dialogWindow.setWindowAnimations(R.style.dialog_anim);
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        lp.width = (int) (0.7 * dm.widthPixels);
        //lp.height = (int) (0.21 * dm.heightPixels);
        dialogWindow.setAttributes(lp);
    }

    public void setDialogTitle(String str) {
        if (title != null) {
            title.setText(str);
        }
    }

    public final void setDialogTitle(@StringRes int resId) {
        if (title != null) {
            title.setText(ctx.getResources().getString(resId));
        }
    }

    public void setDialogDescri(String str) {
        if (descri != null) {
            descri.setText(str);
        }
    }

    public void setDialogDescri(@StringRes int str) {
        if (descri != null) {
            descri.setText(ctx.getResources().getString(str));
        }
    }

    public void hideDialogDescri() {
        if (descri != null) {
            descri.setVisibility(View.GONE);
        }
    }

    public void setButtonText(String str1, String str2) {
        if (button1 != null) {
            button1.setText(str1);
        }
        if (button2 != null) {
            button2.setText(str2);
        }
    }

    public void setButtonText(int str1, int str2) {
        if (button1 != null) {
            button1.setText(ctx.getResources().getString(str1));
        }
        if (button2 != null) {
            button2.setText(ctx.getResources().getString(str2));
        }
    }

    public void hideLeftButton() {
        if (button1 != null) {
            button1.setVisibility(View.GONE);
        }
        if (dividerView != null) {
            dividerView.setVisibility(View.GONE);
        }
    }

    public void setButtonClickListener(AppDialogButtonOnClickListener l) {
        this.listener = l;
    }

    public void showTitleView() {
        if (title != null) {
            title.setVisibility(View.VISIBLE);
        }
    }

    public void hiddenTitilView() {
        if (title != null) {
            title.setVisibility(View.GONE);
        }
    }

    public AppDialog getDialog() {
        return this;
    }

    /**
     * 当程序需要强制更新的时候 调用此设置
     * 调用此方法能够让当前的 dialog 只进行一个 按钮的操作
     * 点击dialog之外的地方不会关闭当前的 dialog
     * 点击返回键的时候会直接退出程序
     */
    public void setForceDialog() {
        if (button1 != null) {
            button1.setVisibility(View.GONE);
            dividerView.setVisibility(View.GONE);
        }
        isForced = true;
        setCanceledOnTouchOutside(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isForced) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                ((BasicActivity) ctx).exitAppWithToast();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 建造者
     */
    public static class Builder {
        AppDialog dialog;

        public Builder(Context ctx) {
            dialog = new AppDialog(ctx);
        }

        public Builder setTitle(int strSrc) {
            dialog.setDialogTitle(strSrc);
            return this;
        }

        public Builder setDescript(int desSrc) {
            dialog.setDialogDescri(desSrc);
            return this;
        }

        public Builder setClickListener(AppDialogButtonOnClickListener clickListener) {
            dialog.setButtonClickListener(clickListener);
            return this;
        }

        public Builder hideLeftButton() {
            dialog.hideLeftButton();
            return this;
        }

        public Builder setButtonText(int bt1, int bt2) {
            dialog.setButtonText(bt1, bt2);
            return this;
        }

        public Builder hideDescribe() {
            dialog.hideDialogDescri();
            return this;
        }

        public Builder show() {
            dialog.show();
            return this;
        }

        public Builder hideTitle() {
            dialog.hiddenTitilView();
            return this;
        }
    }

}
