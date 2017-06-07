package com.android_mobile.share;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;


public class ShareDialog extends Dialog implements AdapterView.OnItemClickListener {

    private static final int DEFAULT_NUM_COLUMNS = 4;
    public static final String WE_CHART = "we_chart";
    public static final String WE_CIRCLE = "we_circle";
    public static final String TENCENT = "tencent";
    public static final String BROWSER = "browser";
    public static final String COPY_LINK = "copy_link";

    private GridView mSharePlatforms;
    private SharePlatformAdapter mSharePlatformAdapter;
    private OnSharePlatformListener mOnSharePlatformListener;
    private SharePlatform mSeleteItem;
    private TextView mCancelTv;

    public ShareDialog(Context context) {
        this(context, R.style.Theme_NoBorder_Dialog);
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
        initialize();
    }

    private void initialize() {
        setContentView(R.layout.dialog_share);
        Window window = this.getWindow(); // 得到对话框
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.socialize_dialog_animations);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = getScreenWidth();
        //attributes.height = (int) (getScreenHeight() * 0.4);
        attributes.dimAmount = 0.4f;
        window.setAttributes(attributes);
        mSharePlatforms = (GridView) findViewById(R.id.m_share_dialog_gv);
        mCancelTv = (TextView) findViewById(R.id.m_share_dialog_tv);
        mSharePlatformAdapter = new SharePlatformAdapter(getContext());
        mSharePlatforms.setAdapter(mSharePlatformAdapter);
        mSharePlatforms.setOnItemClickListener(this);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setCanceledOnTouchOutside(true);
        addSharePlatform();
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }


    private void addSharePlatform() {
        ArrayList<SharePlatform> platforms = new ArrayList<SharePlatform>();
        platforms.add(new SharePlatform(R.mipmap.ic_share_wx_chart, getString(R.string.umeng_socialize_wechat), WE_CHART));
        platforms.add(new SharePlatform(R.mipmap.ic_share_circle, getString(R.string.umeng_socialize_wxcircle), WE_CIRCLE));
        platforms.add(new SharePlatform(R.mipmap.ic_share_qq, getString(R.string.umeng_socialize_tencent), TENCENT));
        platforms.add(new SharePlatform(R.mipmap.ic_share_browse, getString(R.string.umeng_socialize_explorer), BROWSER));
        platforms.add(new SharePlatform(R.mipmap.ic_share_copy, getString(R.string.umeng_socialize_copylink), COPY_LINK));
        mSharePlatformAdapter.addAll(platforms);
    }

    private String getString(int string) {
        return getContext().getString(string);
    }

    public void add(SharePlatform platform) {
        mSharePlatformAdapter.add(platform);
    }

    public void addAll(Collection<? extends SharePlatform> platforms) {
        mSharePlatformAdapter.addAll(platforms);
    }

    public void remove(SharePlatform platform) {
        mSharePlatformAdapter.remove(platform);
    }

    public void remove(int index) {
        mSharePlatformAdapter.remove(index);
    }

    public int size() {
        return mSharePlatformAdapter.getCount();
    }

    public SharePlatform get(int position) {
        return mSharePlatformAdapter.getItem(position);
    }

    public void setOnSharePlatformListener(OnSharePlatformListener l) {
        this.mOnSharePlatformListener = l;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSeleteItem = mSharePlatformAdapter.getItem(position);
        if (mOnSharePlatformListener != null) {
            mOnSharePlatformListener.onShare(view, this, mSeleteItem, mSeleteItem.shareMedia, position);
        }
    }

    public interface OnSharePlatformListener {
        void onShare(View v, ShareDialog dialog, SharePlatform platform, String media, int index);
    }
}
