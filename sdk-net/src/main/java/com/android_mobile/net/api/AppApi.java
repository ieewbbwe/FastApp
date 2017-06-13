package com.android_mobile.net.api;

/**
 * Created by mxh on 2017/2/24.
 * Describe：应用通用请求
 *  * Example:
 * http://127.0.0.1:8080/springmvc_users/user
 *
 * @GET("springmvc_users/user") Observable<Response<User>> getProjectDetail();
 * <p>
 * http://127.0.0.1:8080/springmvc_users/user?username="张三"&password="123"
 * @GET("springmvc_users/user") Observable<Response<User>> getProjectDetail(@Query("username" String username,@Query("password" String password)));
 * <p>
 * http://127.0.0.1:8080/springmvc_users/user/"001"
 * @GET("springmvc_users/user/{userid}") Observable<Response<User>> getProjectDetail(@Path("userid" String userid));
 * study url:http://square.github.io/retrofit/
 */

public interface AppApi {

}
