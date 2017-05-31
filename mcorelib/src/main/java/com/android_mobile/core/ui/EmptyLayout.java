package com.android_mobile.core.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android_mobile.core.R;
import com.android_mobile.core.ui.loading.Loading;
import com.android_mobile.core.utiles.Utiles;

/**
 * Created by mxh on 2016/11/21.
 * Describe：app提示页面
 */
public class EmptyLayout extends LinearLayout {

    private ImageView mIvError;
    private TextView mTvErrorMsg;
    private Loading mLoading;
    private boolean clickEnable = true;
    private OnClickListener mListener;
    //状态码
    private int mErrorState;

    /**
     * 网络错误
     */
    public static final int STATE_NETWORK_ERROR = 1;
    /**
     * 网络加载中
     */
    public static final int STATE_NETWORK_LOADING = 2;
    /**
     * 没有数据
     */
    public static final int STATE_NODATA = 3;
    /**
     * 隐藏布局
     */
    public static final int STATE_HIDE_LAYOUT = 4;
    /**
     * 没有数据，可点击重新获取
     */
    public static final int STATE_NODATA_ENABLE_CLICK = 5;
    /**
     * 未登录
     */
    public static final int STATE_NO_LOGIN = 6;
    private String strNoDataContent = "";

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_error_layout, null);
        mIvError = (ImageView) view.findViewById(R.id.iv_error_layout);
        mTvErrorMsg = (TextView) view.findViewById(R.id.tv_error_layout);
        mLoading = (Loading) view.findViewById(R.id.animProgress);
        setBackgroundColor(-1);
        mIvError.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    // setErrorType(NETWORK_LOADING);
                    if (mListener != null)
                        mListener.onClick(v);
                }
            }
        });
        addView(view);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = STATE_HIDE_LAYOUT;
        super.setVisibility(visibility);
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case STATE_NETWORK_ERROR:
                mErrorState = STATE_NETWORK_ERROR;
                if (Utiles.isNetworkAvailable(getContext())) {
                    mTvErrorMsg.setText(R.string.error_view_load_error_click_to_refresh);
                    mIvError.setBackgroundResource(R.mipmap.ic_loading_error);
                } else {
                    mTvErrorMsg.setText(R.string.error_view_network_error_click_to_refresh);
                    mIvError.setBackgroundResource(R.mipmap.ic_network_error);
                }
                mIvError.setVisibility(View.VISIBLE);
                mLoading.stop();
                mLoading.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case STATE_NETWORK_LOADING:
                mErrorState = STATE_NETWORK_LOADING;
                mLoading.setVisibility(View.VISIBLE);
                mLoading.start();
                mIvError.setVisibility(View.GONE);
                mTvErrorMsg.setText(R.string.error_view_loading);
                clickEnable = false;
                break;
            case STATE_NODATA:
                mErrorState = STATE_NODATA;
                mIvError.setBackgroundResource(R.mipmap.ic_empty_error);
                mIvError.setVisibility(View.VISIBLE);
                mLoading.stop();
                mLoading.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case STATE_HIDE_LAYOUT:
                if (mLoading.isRunning()) {
                    mLoading.stop();
                }
                if (getVisibility() == VISIBLE) {
                    setVisibility(View.GONE);
                }
                break;
            case STATE_NODATA_ENABLE_CLICK:
                mErrorState = STATE_NODATA_ENABLE_CLICK;
                mIvError.setBackgroundResource(R.mipmap.ic_empty_error);
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                mIvError.setVisibility(View.VISIBLE);
                mLoading.stop();
                mLoading.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            mTvErrorMsg.setText(strNoDataContent);
        else
            mTvErrorMsg.setText(R.string.error_view_no_data);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == STATE_NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == STATE_NETWORK_LOADING;
    }

    public void setErrorMsg(String msg) {
        mTvErrorMsg.setText(msg);
    }

    public void setErrorImg(int imgId) {
        try {
            mIvError.setImageResource(imgId);
        } catch (Exception e) {
            //not handle
        }
    }

}
