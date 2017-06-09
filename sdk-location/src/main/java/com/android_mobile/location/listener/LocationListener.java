package com.android_mobile.location.listener;

import com.baidu.location.BDLocation;

public interface LocationListener {

    /**
     * 开始定位
     */
    public void onStartLocation();

    /**
     * 定位成功
     * @param location 当前定位信息
     */
    public void onReceiveLocation(BDLocation location, boolean isCache);

    /**
     * 定位失败
     * @param error 异常对象
     * @param msg   描述信息
     */
    public void onLocationFailure(Throwable error, String msg);
}
