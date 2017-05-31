package com.android_mobile.core.manager.image;

import android.content.Context;

/**
 * 图片加载框架的构造器
 */
public class ImageLoadFactory {

    private static ImageLoadFactory mImageLoadFactory;

    private Context mContext;

    private ImageLoadHandler mImageLoadHandler;

    /**
     * 初始化
     */
    public static void init(Context context) {
        if (mImageLoadFactory == null) {
            synchronized (ImageLoadFactory.class) {
                if (mImageLoadFactory == null) {
                    mImageLoadFactory = new ImageLoadFactory(context);
                }
            }
        }
    }

    /**
     * 获取实例
     */
    public static ImageLoadFactory getInstance() {
        if (mImageLoadFactory == null) {
            throw new IllegalStateException("ImageLoadFactory not initialized!");
        }
        return mImageLoadFactory;
    }

    /**
     * 获取图片加载管理器
     */
    public ImageLoadFactory(Context context) {
        this.mContext = context;
        //mImageLoadHandler = new UniversalImageLoadHandler(mContext);
        mImageLoadHandler = new GlideImageLoadHandler(mContext);
    }

    public ImageLoadHandler getImageLoadHandler() {
        return mImageLoadHandler;
    }
}
