package com.android_mobile.location;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android_mobile.location.listener.SimpleLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;

import java.util.List;

/**
 * Created by mxh on 2017/6/8.
 * Describe：封装mapView
 */

public class MMapView extends FrameLayout implements IMapCore {

    private MapView mMapView;
    private int markIconId = -1;
    private BaiduMap mBaiduMap;
    private RoutePlanSearch mSearch;

    public MMapView(Context context) {
        this(context, null);
    }

    public MMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mMapView = new MapView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        mBaiduMap = mMapView.getMap();
        mSearch = RoutePlanSearch.newInstance();
        addView(mMapView, params);
    }

    /**
     * 移动到地图上某个点
     *
     * @param latLng 点位坐标
     * @param isAnim 是否开启动画
     */
    @Override
    public void moveToPoint(LatLng latLng, boolean isAnim) {
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(12.0f)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        //改变地图状态
        if (isAnim) {
            mBaiduMap.animateMapStatus(mMapStatusUpdate);
        } else {
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }
    }

    /**
     * 在地图上标出坐标点
     *
     * @param latLng 坐标
     */
    @Override
    public void tagPointInMap(LatLng latLng) {
        if (markIconId == -1) {
            markIconId = R.drawable.icon_gcoding;
        }
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(markIconId);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 在地图上标出坐标点
     *
     * @param latLngs 坐标集合
     */
    @Override
    public void tagPontInMap(List<LatLng> latLngs) {

    }

    /**
     * 在地图上标出当前位置
     */
    @Override
    public void tagCurrentPosInMap() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        LocationHelper.getInstance().startLocation(new SimpleLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location, boolean isCache) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        //.direction()
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);

                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder()
                        .target(ll)
                        .zoom(18f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });
    }

    /**
     * 设置标注物图标
     *
     * @param resId 资源id
     */
    @Override
    public void setMarkIcon(int resId) {
        this.markIconId = resId;
    }

    /**
     * 路线规划
     *
     * @param stNode   开始点
     * @param enNode   结束点
     * @param nodeEnum 行进类型 CROSS_CITY,CART,BUS,WALK,CYCLING
     */
    @Override
    public void routePlan(PlanNode stNode, PlanNode enNode, PlanNodeEnum nodeEnum) {
        switch (nodeEnum) {
            case CROSS_CITY:
                mSearch.masstransitSearch(new MassTransitRoutePlanOption().from(stNode).to(enNode));
                break;
            case CART:
                mSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(stNode).to(enNode));
                break;
            case BUS:
                mSearch.transitSearch((new TransitRoutePlanOption())
                        .from(stNode).to(enNode));
                break;
            case WALK:
                mSearch.walkingSearch((new WalkingRoutePlanOption())
                        .from(stNode).to(enNode));
                break;
            case CYCLING:
                mSearch.bikingSearch((new BikingRoutePlanOption())
                        .from(stNode).to(enNode));
                break;
        }
    }
}
