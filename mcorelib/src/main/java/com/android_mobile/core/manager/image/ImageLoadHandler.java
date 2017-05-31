package com.android_mobile.core.manager.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

/**
 * Create by mxh on 2016/11/12
 * Describe:
 * 图片加载框架的 接口 ,如果以后需要更换图片加载框架,
 * 只需要让新的图片加载框架实现该接口,然后 修改
 */
public interface ImageLoadHandler {

    String SCHEMA_HTTP = "http://";

    String SCHEMA_FILE = "file://";

    String SCHEMA_CONTENT = "content://";

    String SCHEMA_ASSET = "asset://";

    String SCHEMA_RES = "res://";

    void displayImage(String url, ImageView iv);

    void displayImage(String url, ImageView iv, int errorImg, int placeHolderImg);

    void displayImage(File file, ImageView iv);

    void displayImage(File file, ImageView iv, int errorImg, int placeHolderImg);

    void displayImage(Uri uri, ImageView iv);

    void displayImage(Uri uri, ImageView iv, int errorImg, int placeHolderImg);

    void displayImage(int imgSource, ImageView iv);

    void displayImage(int imgSource, ImageView iv, int errorImg, int placeHolderImg);

    void cancelAllTasks(Context context);

    void resumeAllTasks(Context context);

    void clearDiskCache(Context context);

    void cleanAll(Context context);

    void displayRoundImage(String imagePath, ImageView mAdd);
}
