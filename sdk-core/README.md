## 库文件申明
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

### OrmLite

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

## 包结构说明
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
9. 网络加载页面

### utils

