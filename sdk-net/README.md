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

