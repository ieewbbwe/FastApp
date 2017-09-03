# FastApp
## app
模块下主要是个功能的demo，目前还在完善中

## sdk-core
该库是开发框架的核心库，封装了Activity基类，提供一些常用的utils，常用的控件如轮播图dialog、popwindow等

库文件申明
### OkHttp
网络请求库 https://github.com/square/okhttps
Copyright (C) 2012 Square, Inc.
Apache License, Version 2.0

### RefreshLayout
List刷新库 https://github.com/bingoogolapple/BGARefreshLayout-Android
Copyright 2015 bingoogolapple
Apache License, Version 2.0

### Glide
图片加载库 https://github.com/bumptech/glide
Copyright 2011-2013 Sergey Tarasevich
Author Sam Judd - @sjudd on GitHub, @samajudd on Twitter
License BSD, part MIT and Apache 2.0. See the LICENSE file for details.

### AndPermission
6.0以上的权限申请库 https://github.com/yanzhenjie/AndPermission
Copyright 2016 Yan Zhenjie
Apache License, Version 2.0

```
AndPermission.with(this)
    .requestCode(101)
    .permission(Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_SMS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    .send();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    // 成功回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionYes(100)
    private void getLocationYes() {
        // 申请权限成功，可以去做点什么了。
        Toast.makeText(this, "获取定位权限成功", Toast.LENGTH_SHORT).show();
    }

    // 失败回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionNo(100)
    private void getLocationNo() {
        // 申请权限失败，可以提醒一下用户。
        Toast.makeText(this, "获取定位权限失败", Toast.LENGTH_SHORT).show();
    }
```

### RxJava
响应式库 https://github.com/ReactiveX/RxJava
Copyright 2013 Netflix, Inc.
Apache License, Version 2.0

### RxLifecycle
RxJava生命周期管理库 https://github.com/trello/RxLifecycle
防止RxJava滥用造成的内存泄漏，在需要时同步解除订阅
.compose(RxLifecycle.bindUntilEvent(lifecycle, ActivityEvent.DESTROY))
Copyright (C) 2016 Trello
Apache License, Version 2.0

## 工具类介绍（欢迎补充）
### Utils 基础工具类
1. 判断系统网络情况
2. dip2px 互换
3. 判断SD卡搭载状态

### StringUtils 字符串处理工具类
1. 常见的正则表达式
2. 判断传入的字符串是否有值
3. 邮箱、密码、银行卡、电话、身份证正则校验
4. 特殊字符、纯数字正则校验
5. 字符串编码转换

### TimerUtils 时间处理工具类
1. 常见的日期格式format
2. 转换时间格式,long与string时间互换
3. 获取与当前时间的时间差
4. 获取与当前时间的关系
5. UTC时间转换

### BitmapUtils 图片处理工具类
1. 根据路径获取图片
2. Drawable转Bitmap
3. Bitmap图片模糊处理
4. 按比例或者宽高压缩图片
5. 获取图片角度，旋转图片

### SystemInfo 系统信息工具类
1. 获取运营商、CPU、磁盘、网络、显示屏等信息
2. 获取运行中服务、任务列表

### IntentUtils 意图工具类
1. 进行各类意图跳转操作

### Log工具类
1. 打印各级log，上线后屏蔽所有log

包结构说明
### app

### base
对Basic的封装，用于拓展，自己的Activity继承该Base类

### listener
用到的接口

### manager
图片加载管理器，Sp管理器

### net
网络访问框架的封装

### ui
一些常用的自定义控件和组件
1. dialog 基于AppCompat包的对话框，继承BasicDialog可自定义界面
2. popupWindows 底部弹出框，联级菜单弹出框，列表弹出框
3. banner 支持无限轮播，触摸停止，点击放大
4. zxing 二维码扫描
5. 筛选的自定义控件
6. tab的自定义控件
7. 嵌套滑动NestScroll
8. 动画相关
9. 分享
10. 支付
11. 定位
12. 计时器

## sdk-location
该库对百度地图api进行初步的封装，提供了一些地图相关的常用方法
### 使用说明
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

## sdk-pay
封装了支付宝支付、微信支付

### 使用文档
库中引入了支付宝支付，微信支付两种支付方式。
1. 在PayConstants 中配置好WX_APP_ID，申请地址https://open.weixin.qq.com/
2. 与公司后端协商，定义生成orderInfo的接口地址，在PayUrlMgr中配置
3. 可以在PayApi中修改订单接口的请求参数，RewardAliResp和RewardWxResp分别是支付宝订单信息，微信订单信息的响应参数
4. WXPayEntryActivity这里做了修改，可以同时处理支付宝和微信的回调，并根据支付code做出响应
5. 注意请按照需要自行添加 notifyService() 通知后端支付结果
6. 使用方法new PayHelper().aliPrePay()

还有问题的，请看官网接入文档：
支付宝支付：https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.CoadnK&treeId=204&articleId=105296&docType=1
微信支付：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5

## sdk-share

###使用文档
1. 修改assets/ShareSDK.XML 中各平对应的key
2. 修改Manifest 中<data android:scheme="tencent100371282" /> 为腾讯平台的id
3. 使用SocialShareHelper，对ShareSdk的简单封装
```
   socialComponent= new SocialShareHelper.Builder(this)
                 .setContent("应用描述信息")
                 .setTitle("推荐给您一款好用的APP，快来试试吧！")
                 .setTitleUrl("应用下载地址")
                 .setImageUrl("图片地址").create();
   socialComponent.show();
```

其余还有问题的，可以去ShareSdk官网上查阅集成手册。
http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/

注意：使用时请参照需要去掉一些无用lib,减少包大小

## sdk-net
#库文件申明
## Retrofit
网络请求库 https://github.com/square/retrofit
Copyright 2013 Square, Inc.
Apache License, Version 2.0

#使用文档
1. 继承BaseFactory，生成相关api，对Retrofit进行二次封装，使用统一的client配置
2. 响应参数继承BaseResponse，请求参数继承BaseRequest
For example:
Get
    URL：http://127.0.0.1:8080/springmvc_users/user?id=001
    API：
```
    @GET("springmvc_users/user")
    Observable<Response<User>> getUserInfo(@Query("id") String userId);

    BaseFactory.getUserApi().getUserInfo("001")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new OnSimpleRequestCallback<Response<User>>(NetDemoActivity.this) {
                             @Override
                             public void onResponse(Response<User> response) {

                             }

                             @Override
                             public void onFinish() {
                                 complete();
                             }
                         });
```
Post:

#变更记录

2017.06.29 增加Https支持
如果需要支持Https的链接，需要：
1. 将证书放在assets目录下
2. 在Application中 ```OkHttpFactory.init(getAssets().open("srca.cer"));```初始化


