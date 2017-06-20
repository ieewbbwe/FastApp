package com.android_mobile.location;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.PlanNode;

import java.util.List;

/**
 * Created by mxh on 2017/6/8.
 * Describe：地图相关的核心方法
 */

public interface IMapCore {

    /**
     * 移动到地图上某个点
     *
     * @param latLng 点位坐标
     */
    void moveToPoint(LatLng latLng, boolean isAnim);

    /**
     * 在地图上标出坐标点
     *
     * @param latLng 坐标
     */
    void tagPointInMap(LatLng latLng);

    /**
     * 在地图上标出坐标点
     *
     * @param latLngs 坐标集合
     */
    void tagPontInMap(List<LatLng> latLngs);

    /**
     * 在地图上标出当前位置
     */
    void tagCurrentPosInMap();

    /**
     * 设置标注物图标
     *
     * @param resId 资源id
     */
    void setMarkIcon(int resId);

    /**
     * 路线规划
     *
     * @param stNode   开始点
     * @param enNode   结束点
     * @param nodeEnum 行进类型
     */
    void routePlan(PlanNode stNode, PlanNode enNode, PlanNodeEnum nodeEnum);

}
