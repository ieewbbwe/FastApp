package com.android_mobile.core.ui;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android_mobile.core.BasicActivity;
import com.android_mobile.core.R;
import com.android_mobile.core.utiles.Utiles;

public class NavigationBar implements View.OnClickListener {
    private Button leftBtn;
    private Button rightBtn;
    private TextView titleText;
    public RelativeLayout layout;
    private RelativeLayout titleView;

    private boolean isSearchModel = false;

    private boolean isHidden = false;
    private ViewStub vs;
    protected View view;
    private BasicActivity act;
    private RelativeLayout searchLayout;
    private EditText searchEditText;
    private ImageView searchClear;

    public NavigationBar(BasicActivity act, ViewStub vs) {
        this.act = act;
        this.vs = vs;
        navigationView = initWithlayoutResID(R.layout.activity_frame_navigationbar);
        layout = (RelativeLayout) navigationView
                .findViewById(R.id.activity_frame_title_layout);
        titleView = (RelativeLayout) navigationView
                .findViewById(R.id.activity_frame_titleview_layout);
        leftBtn = (Button) navigationView.findViewById(R.id.activity_frame_title_btn_left);
        // leftBtn.getPaint().setFakeBoldText(true);
        rightBtn = (Button) navigationView.findViewById(R.id.activity_frame_title_btn_right);
        // rightBtn.getPaint().setFakeBoldText(true);
        titleText = (TextView) navigationView.findViewById(R.id.activity_frame_title_text);
        // titleText.getPaint().setFakeBoldText(true);
        searchLayout = (RelativeLayout) navigationView.findViewById(R.id.activity_frame_search_layout);
        searchEditText = (EditText) navigationView
                .findViewById(R.id.activity_frame_search_et);
        searchClear = (ImageView) navigationView
                .findViewById(R.id.activity_frame_search_et_clear);
        //act.setEditViewClearButton(searchEditText, searchClear);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    public View initWithlayoutResID(int resId) {
        vs.setLayoutResource(resId);
        navigationView = vs.inflate();
        return navigationView;
    }

    public View findViewById(int id) {
        return navigationView.findViewById(id);
    }

    public void hidden() {
        isHidden = true;
        vs.setVisibility(View.GONE);
    }

    public void display() {
        isHidden = false;
        vs.setVisibility(View.VISIBLE);
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setBackground(int resid) {
        layout.setBackgroundResource(resid);
    }

    public void setBackground(String color) {
        layout.setBackgroundColor(Color.parseColor(color));
    }


    @Override
    public void onClick(View v) {

    }

    public void hiddenButtons() {
        leftBtn.setVisibility(View.GONE);
        rightBtn.setVisibility(View.GONE);
    }

    public void displayButtons() {
        leftBtn.setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.VISIBLE);
    }

    public void hiddenLeftButton() {
        leftBtn.setVisibility(View.GONE);
    }

    public void hiddenRightButton() {
        rightBtn.setVisibility(View.GONE);
    }

    public void displayLeftButton() {
        leftBtn.setVisibility(View.VISIBLE);
    }

    public void displayRightButton() {
        rightBtn.setVisibility(View.VISIBLE);
    }

    public void displayTitle() {
        titleView.setVisibility(View.VISIBLE);
    }

    public void hiddenTitle() {
        titleView.setVisibility(View.GONE);
    }

    public void setTitle(String s) {
        titleText.setText(s);
    }

    public void setTitle(int strId) {
        titleText.setText(strId);
    }

    public void setTextLeftButton(String s) {
        leftBtn.setText(s);
        displayLeftButton();
    }

    public void setTextRightButton(String s) {
        rightBtn.setText(s);
        displayRightButton();
    }

    public void setOnLeftButtonClickListener(View.OnClickListener listener) {
        leftBtn.setOnClickListener(listener);
    }

    public void setOnRightButtonClickListener(View.OnClickListener listener) {
        rightBtn.setOnClickListener(listener);
    }

    public void setLeftIcon(int left, int top, int right, int bottom) {
        Drawable l = null;
        if (left > 0)
            l = act.getResources().getDrawable(left);
        Drawable t = null;
        if (top > 0)
            t = act.getResources().getDrawable(top);
        Drawable r = null;
        if (right > 0)
            r = act.getResources().getDrawable(right);
        Drawable b = null;
        if (bottom > 0)
            b = act.getResources().getDrawable(bottom);
        setLeftIcon(l, t, r, b);
    }

    public void setRightIcon(int left, int top, int right, int bottom) {
        Drawable l = null;
        if (left > 0)
            l = act.getResources().getDrawable(left);
        Drawable t = null;
        if (top > 0)
            t = act.getResources().getDrawable(top);
        Drawable r = null;
        if (right > 0)
            r = act.getResources().getDrawable(right);
        Drawable b = null;
        if (bottom > 0)
            b = act.getResources().getDrawable(bottom);
        setRightIcon(l, t, r, b);
    }

    public void setLeftIcon(Drawable left, Drawable top, Drawable right,
                            Drawable bottom) {
        if (left != null)
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
        if (right != null)
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());
        if (top != null)
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        if (bottom != null)
            bottom.setBounds(0, 0, bottom.getMinimumWidth(),
                    bottom.getMinimumHeight());
        leftBtn.setCompoundDrawables(left, top, right, bottom);
    }

    public void setLeftIcon(Drawable left, Drawable top, Drawable right,
                            Drawable bottom, int w, int h) {
        if (left != null) {
            left.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
            leftBtn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
        if (right != null) {
            right.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
            leftBtn.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
        if (top != null)
            top.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
        if (bottom != null)
            bottom.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
        leftBtn.setCompoundDrawables(left, top, right, bottom);
    }

    public void setRightIcon(Drawable left, Drawable top, Drawable right,
                             Drawable bottom) {
        if (left != null)
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
        if (right != null)
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());
        if (top != null)
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        if (bottom != null)
            bottom.setBounds(0, 0, bottom.getMinimumWidth(),
                    bottom.getMinimumHeight());
        rightBtn.setCompoundDrawables(left, top, right, bottom);
    }


    public void setRightIcon(Drawable left, Drawable top, Drawable right,
                             Drawable bottom, int w, int h) {
        if (left != null) {
            left.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
            rightBtn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
        if (right != null) {
            right.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
            rightBtn.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
        if (top != null) {
            top.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, Utiles.dip2px(act, w), Utiles.dip2px(act, h));
        }
        rightBtn.setCompoundDrawables(left, top, right, bottom);
    }

    public float getHeight() {
        return act.getResources().getDimension(R.dimen.navigation_height);
    }

    private int duration = 200;
    private View navigationView;

    public void switchToSearch() {
        ObjectAnimator.ofFloat(searchLayout, "alpha", 0.0f, 1.0f)
                .setDuration(duration).start();
        ObjectAnimator.ofFloat(titleView, "alpha", 1.0f, 0.0f)
                .setDuration(duration).start();
        ObjectAnimator.ofFloat(leftBtn, "alpha", 1.0f, 0.0f)
                .setDuration(duration).start();
        searchLayout.setVisibility(View.VISIBLE);

        leftBtn.setVisibility(View.GONE);
        rightBtn.setVisibility(View.VISIBLE);
        setSearchModel(true);
    }

    public void switchToDefault() {
        ObjectAnimator.ofFloat(searchLayout, "alpha", 1.0f, 0.0f)
                .setDuration(duration).start();
        ObjectAnimator.ofFloat(titleView, "alpha", 0.0f, 1.0f)
                .setDuration(duration).start();
        ObjectAnimator.ofFloat(leftBtn, "alpha", 0.0f, 1.0f)
                .setDuration(duration).start();
        searchLayout.setVisibility(View.GONE);
        titleView.setVisibility(View.VISIBLE);
        leftBtn.setVisibility(View.VISIBLE);
        setSearchModel(false);
    }

    public boolean isSearchModel() {
        return isSearchModel;
    }

    public void setSearchModel(boolean isSearchModel) {
        this.isSearchModel = isSearchModel;
    }


}
