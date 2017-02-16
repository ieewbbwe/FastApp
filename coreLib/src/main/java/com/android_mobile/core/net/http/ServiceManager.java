package com.android_mobile.core.net.http;


/**
 * 统一服务管理者
 */
public class ServiceManager {

    public static ServiceResponse getServiceResponse(ServiceRequest request, Class<? extends Service> class1) {
        try {
            Service _service = class1.newInstance();
            return _service.execute(request);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ServiceResponse getServiceResponse(ServiceRequest request, Service s) {
        return s.execute(request);
    }
}
