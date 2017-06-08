package com.android_mobile.location;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.PlanNode;

/**
 * Created by mxh on 2017/6/8.
 * Describe：地图相关的核心方法
 */

public interface IMapCore {

    /**
     * 在地图上标出坐标点
     *
     * @param latLng 坐标
     */
    void tagPointInMap(LatLng latLng);

    /**
     * 设置标注物图标
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
