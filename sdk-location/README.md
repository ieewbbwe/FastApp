# 使用说明
1. 请在module工程build.gradle中添加
```
   compile project(':sdk-location')
```
2. 在manifest中配置key
```
 <application>

        <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:value="your key" />

    </application>
```
3. APP初始化时请调用LocationHelper.init()，否则无法使用位置相关API，相关方法见ILocationCore.java
4. 地图相关功能请使用MMapView，相关方法见IMapCore.java
5. 可使用工具检测Key是否正确
```
    SDKReceiver sdkReceiver = new SDKReceiver();
    LocateUtils.checkoutKey(this, sdkReceiver);
```

百度地图API集成手册：http://developer.baidu.com/map/loc_refer/index.html

注意：可以根据业务需要，自行增减lib包内容来控制apk大小