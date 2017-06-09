package com.android_mobile.location;

import com.android_mobile.location.listener.LocationListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSortType;

/**
 * Created by mxh on 2017/6/8.
 * Describe：地图相关核心方法
 */

public interface ILocationCore {

    /**
     * 开始定位
     *
     * @param locationListener 定位回调
     */
    void startLocation(LocationListener locationListener);

    /**
     * 停止定位
     */
    void stopLocation();

    /**
     * 计算两点间的距离
     *
     * @param pos1 坐标1
     * @param pos2 坐标2
     * @return 两点直线距离
     */
    Double calcDistance(LatLng pos1, LatLng pos2);

    /**
     * 查找附近信息
     *
     * @param radarSearchListener 结果回调
     */
    void startRadarNearby(RadarSearchListener radarSearchListener);

    /**
     * 按关键字查找周边信息
     *
     * @param searchOption 查找参数
     */
    void startPoiNearBySearch(PoiNearbySearchOption searchOption, OnGetPoiSearchResultListener listener);

    /**
     * 按照关键字查找周边信息
     *
     * @param keyWord     关键字
     * @param poiSortType 排序类型
     * @param centerPos   中心坐标
     * @param radius      查找范围
     * @param listener    结果回调
     */
    void startPoiNearBySearch(String keyWord, PoiSortType poiSortType, LatLng centerPos, int radius, OnGetPoiSearchResultListener listener);


}
