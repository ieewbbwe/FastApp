##使用文档
1. 继承BaseFactory，生成相关api，对Retrofit进行二次封装，使用统一的client配置
2. 响应参数继承BaseResponse，请求参数继承BaseRequest
For example:
Get
    URL：http://127.0.0.1:8080/springmvc_users/user?id=001
    API：@GET("springmvc_users/user")
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
Post:

