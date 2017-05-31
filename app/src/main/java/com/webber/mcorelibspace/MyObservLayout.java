package com.webber.mcorelibspace;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android_mobile.core.utiles.Lg;

import static com.android_mobile.core.utiles.CacheUtil.context;

/**
 * Created by mxh on 2017/5/27.
 * Describe：
 */

public class MyObservLayout extends RelativeLayout implements View.OnClickListener {

    private ImageView addDisTv;
    private ImageView mimusDisTv;
    private EditText numberDisEt;
    private TextView errorMsgTv;
    private View rootV;
    private int purchaseNumber;

    public MyObservLayout(Context context) {
        this(context, null);
    }

    public MyObservLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyObservLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;

        LayoutInflater li = (LayoutInflater) context
                .getSystemService(infService);
        li.inflate(R.layout.view_addminus, this);

        addDisTv = (ImageView) this.findViewById(R.id.add_dis_tv);
        mimusDisTv = (ImageView) this.findViewById(R.id.mimus_dis_tv);
        numberDisEt = (EditText) this.findViewById(R.id.number_dis_tv);
        errorMsgTv = (TextView) this.findViewById(R.id.error_msg_tv);
        numberDisEt.addTextChangedListener(watcher);
        mimusDisTv.setOnClickListener(this);
        addDisTv.setOnClickListener(this);

        rootV = getRootView();
        Lg.print("picher", "address:" + rootV.toString() + "-->rootVID:" + rootV.getId());
        rootV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                rootV.getWindowVisibleDisplayFrame(r);
                int heightDiff = rootV.getRootView().getHeight() - (r.bottom - r.top);
                Lg.print("picher", "rootH:" + rootV.getHeight() + "onGlobalLayout:" + rootV.getRootView().getHeight() + "rootVRoot:" + rootV.getRootView().toString());
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Lg.print("picher", "beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Lg.print("picher", "onTextChanged");
        }

        @Override
        public void afterTextChanged(Editable s) {
            Lg.print("picher", "afterTextChanged");
        }
    };

    @Override
    public void onClick(View v) {
        String num = numberDisEt.getText().toString().trim();
        switch (v.getId()) {
            case R.id.mimus_dis_tv:
                if ("".equals(num)) {
                    numberDisEt.setText(Integer.parseInt(num) - 1 + "");
                }
                break;
            case R.id.add_dis_tv:
                if ("".equals(num)) {
                    numberDisEt.setText(Integer.parseInt(num) + 1 + "");
                }
                break;
        }
    }
}