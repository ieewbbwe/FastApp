package com.android_mobile.core.manager.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by mxh on 2016/11/18.
 * Describe：Glide 属性 申明在manifest
 */

public class GlideModelConfig implements GlideModule {

    private int diskSize = 1024 * 1024 * 100;
    private int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));  //内存中
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); //sd卡中

        // 自定义内存和图片池大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片格式
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
