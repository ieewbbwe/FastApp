package com.android_mobile.core.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.android_mobile.core.utiles.TimerUtils;


/**
 * Created by mxh on 2016/7/26.
 * 自定義倒計時控件
 */
public class CountDownTextView extends AppCompatTextView {

    private MyCountDownTimer timer;
    private long time;
    private boolean isStart;

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void toCancel() {
        if (timer != null) {
            isStart = false;
            timer.cancel();
        }
    }

    public void toStart() {
        isStart = true;
        if(time < 0){
            setText("已结束");
            return;
        }
        if (timer != null) {
            timer.start();
        } else {
            timer = new MyCountDownTimer(time, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    setText(TimerUtils.transformToTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    setText("已结束");
                }
            }.start();
        }
    }

    public boolean isStart() {
        return isStart;
    }

    /**
     * 設置計時器 要在toStart之前調用
     */
    public void setCountDownTimer(MyCountDownTimer timer) {
        this.timer = timer;
    }
}
