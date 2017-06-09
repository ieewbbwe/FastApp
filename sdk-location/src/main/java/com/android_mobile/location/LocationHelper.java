package com.android_mobile.location;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android_mobile.location.listener.LocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.DistanceUtil;

/**
 * Created by mxh on 2017/6/8.
 * Describe：位置相关帮助类
 */

public class LocationHelper implements ILocationCore {

    private final String TAG = getClass().getSimpleName();
    private static LocationHelper mInstance;
    private LocationClient mLocationClient;
    private Context mContext;
    private LocationListener mLocationListener;

    private static int MAX_REPEAT_COUNT = 2;
    private int mLocateRepeatCount = 0;
    private int poiLoadIndex = 0;
    private PoiSearch mPoiSearch;

    private LocationHelper() {
    }

    private LocationHelper(Context context) {
        this.mContext = context;
        mLocationClient = new LocationClient(context);
        initLocation();
        LocateListener mLocateListener = new LocateListener();
        mLocationClient.registerLocationListener(mLocateListener);
    }

    public static void init(Context context) {
        synchronized (LocationHelper.class) {
            if (mInstance == null) {
                mInstance = new LocationHelper(context);
            }
        }
    }

    public static LocationHelper getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("LocationHelper is not initialize!");
        }
        return mInstance;
    }

    private void initLocation() {
        // 初始化地图
        SDKInitializer.initialize(mContext);
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        //option.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //设置地址信息类型
        //option.setAddrType("all");
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //option.setLocationNotify(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationDescribe(false);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        //option.setIsNeedLocationPoiList(false);
        //可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        //option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        //option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        //option.setEnableSimulateGps(false);

        //设置定位超时 brucend
//        option.setTimeOut(3000);

        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到


        mLocationClient.setLocOption(option);
    }

    /**
     * 开始定位
     *
     * @param locationListener 定位回调
     */
    @Override
    public void startLocation(LocationListener locationListener) {
        if (locationListener == null) {
            throw new IllegalStateException("locationListener is not allow null!!");
        }
        this.mLocationListener = locationListener;

        synchronized (LocationHelper.class) {
            if (mLocationClient != null && !mLocationClient.isStarted()) {
                mLocationListener.onStartLocation();
                mLocationClient.start();
            }
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void stopLocation() {
        Log.d(TAG, "停止定位");
        synchronized (LocationHelper.class) {
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    /**
     * 计算两点间的距离
     *
     * @param pos1 坐标1
     * @param pos2 坐标2
     * @return 两点直线距离
     */
    @Override
    public Double calcDistance(LatLng pos1, LatLng pos2) {
        return DistanceUtil.getDistance(pos1, pos2);
    }

    /**
     * 查找附近信息
     *
     * @param radarSearchListener 结果回调
     */
    @Override
    public void startRadarNearby(RadarSearchListener radarSearchListener) {

    }

    /**
     * 按照关键字查找周边信息
     *
     * @param keyWord     关键字
     * @param poiSortType 排序类型
     * @param centerPos   中心坐标
     * @param radius      查找范围
     * @param listener    结果回调
     */
    @Override
    public void startPoiNearBySearch(String keyWord, PoiSortType poiSortType, LatLng centerPos, int radius, OnGetPoiSearchResultListener listener) {
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyWord)
                .sortType(poiSortType).location(centerPos)
                .radius(radius).pageNum(poiLoadIndex);
        startPoiNearBySearch(nearbySearchOption, listener);
    }

    /**
     * 按关键字查找周边信息
     *
     * @param searchOption 查找参数
     * @param listener
     */
    @Override
    public void startPoiNearBySearch(PoiNearbySearchOption searchOption, OnGetPoiSearchResultListener listener) {
        if (mPoiSearch == null) {
            mPoiSearch = PoiSearch.newInstance();
        }
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
        mPoiSearch.searchNearby(searchOption);
    }

    /**
     * 返回值：
     * 61 ： GPS定位结果，GPS定位成功。
     * 62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
     * 63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     * 65 ： 定位缓存的结果。
     * 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     * 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     * 161： 网络定位结果，网络定位定位成功。
     * 162： 请求串密文解析失败。
     * 167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     * 502： key参数错误，请按照说明文档重新申请KEY。
     * 505： key不存在或者非法，请按照说明文档重新申请KEY。
     * 601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
     * 602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
     * 501～700：key验证失败，请按照说明文档重新申请KEY。
     */
    private class LocateListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            try {
                String cityName = bdLocation.getCity();
                Log.d(TAG, "定位回调");
                if (!TextUtils.isEmpty(cityName)) {
                    onSuccess(bdLocation, false);
                } else {
                    Throwable t = new Throwable();
                    String errorMsg = "定位失败!";
                    if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                        errorMsg = "定位失败，一般是由于手机的原因，可以试着重启手机";
                    }
                    onError(t, errorMsg);
                }
            } catch (Exception e) {
                onError(e, e.getLocalizedMessage());
            }
        }
    }

    /**
     * 成功 回调
     */
    private void onSuccess(BDLocation location, boolean isCache) {
        mLocateRepeatCount = 0;
        mLocationClient.stop();
        if (mLocationListener != null) {
            mLocationListener.onReceiveLocation(location, isCache);
        }
    }

    /**
     * 定位出错,实现重试机制
     */
    private synchronized void onError(Throwable throwable, String msg) {
        Log.d(TAG, "重定位...");
        if (mLocationListener != null) {
            if (mLocateRepeatCount < MAX_REPEAT_COUNT) {
                requestLocation();
                mLocateRepeatCount++;
            } else {
                //不需要重复提示失败操作,只在失败时提示
                mLocationListener.onLocationFailure(throwable, msg);
                mLocationClient.stop();
            }
        }
    }

    private void requestLocation() {
        mLocationClient.requestLocation();
    }
}
