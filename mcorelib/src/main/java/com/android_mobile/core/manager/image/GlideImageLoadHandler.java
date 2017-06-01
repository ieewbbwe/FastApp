package com.android_mobile.core.manager.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.android_mobile.core.R;
import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by mxh on 2016/11/21.
 * Describe：图片加载框架 Glide 的实现类
 */

public class GlideImageLoadHandler implements ImageLoadHandler {

    //默认占位图
    private static final int DEFAULT_PLACEHOLDER = R.mipmap.ic_img_item_default;
    //默认加载失败图片
    private static final int DEFAULT_ERROR = R.mipmap.ic_img_item_default;
    private Context ctx;

    public GlideImageLoadHandler(Context context) {
        //初始化操作
        this.ctx = context;
    }

    @Override
    public void displayImage(String url, ImageView iv) {
        Glide.with(ctx)
                .load(url)
                .crossFade()
                .error(DEFAULT_ERROR)
                .placeholder(DEFAULT_PLACEHOLDER)
                .into(iv);
    }

    @Override
    public void displayImage(String url, ImageView iv, int errorImg, int placeHolderImg) {
        Glide.with(ctx)
                .load(url)
                .crossFade()
                .error(errorImg)
                .placeholder(placeHolderImg)
                .into(iv);
    }

    @Override
    public void displayImage(File file, ImageView iv) {
        Glide.with(ctx)
                .load(file)
                .crossFade()
                .error(DEFAULT_ERROR)
                .placeholder(DEFAULT_PLACEHOLDER)
                .into(iv);
    }

    @Override
    public void displayImage(File file, ImageView iv, int errorImg, int placeHolderImg) {
        Glide.with(ctx)
                .load(file)
                .crossFade()
                .error(errorImg)
                .placeholder(placeHolderImg)
                .into(iv);
    }

    @Override
    public void displayImage(Uri uri, ImageView iv) {
        Glide.with(ctx)
                .load(uri)
                .crossFade()
                .error(DEFAULT_ERROR)
                .placeholder(DEFAULT_PLACEHOLDER)
                .into(iv);
    }

    @Override
    public void displayImage(Uri uri, ImageView iv, int errorImg, int placeHolderImg) {
        Glide.with(ctx)
                .load(uri)
                .crossFade()
                .error(errorImg)
                .placeholder(placeHolderImg)
                .into(iv);
    }

    @Override
    public void displayImage(int imgSource, ImageView iv) {
        Glide.with(ctx)
                .load(imgSource)
                .crossFade()
                .error(DEFAULT_ERROR)
                .placeholder(DEFAULT_PLACEHOLDER)
                .into(iv);
    }

    @Override
    public void displayImage(int imgSource, ImageView iv, int errorImg, int placeHolderImg) {
        Glide.with(ctx)
                .load(imgSource)
                .crossFade()
                .error(errorImg)
                .placeholder(placeHolderImg)
                .into(iv);
    }

    @Override
    public void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    @Override
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    @Override
    public void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }

    @Override
    public void displayRoundImage(String url, ImageView iv) {
        Glide.with(ctx)
                .load(url)
                .transform(new GlideCircleTransform(ctx))
                .crossFade()
                .error(DEFAULT_ERROR)
                .placeholder(DEFAULT_PLACEHOLDER)
                .into(iv);
    }

}
