package com.android_mobile.core.net;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.android_mobile.core.enums.CacheType;
import com.android_mobile.core.enums.RequestType;
import com.android_mobile.core.enums.ResponseChartset;
import com.android_mobile.core.net.http.Service;
import com.android_mobile.core.net.http.ServiceManager;
import com.android_mobile.core.net.http.ServiceRequest;
import com.android_mobile.core.net.http.ServiceResponse;
import com.android_mobile.core.utiles.CacheUtil;
import com.android_mobile.core.utiles.Lg;
import com.google.gson.Gson;

/**
 * http 请求封装 支持 直接 传递 URL 加参数 支持 Request Response 封装两种格式
 *
 * @author fyygw
 */
@SuppressLint("HandlerLeak")
public class BasicAsyncTask extends AsyncTask<Object, Integer, Object> {
    private static final String TAG = BasicAsyncTask.class.getSimpleName();
    public static boolean asyncWithProgress = false;
    private String url;
    protected IBasicAsyncTask callback;
    private Gson g = new Gson();

    public RequestType REQUEST_TYPE = RequestType.GET;
    public ResponseChartset CHARSET = ResponseChartset.UTF_8;
    private CacheType cacheType = CacheType.DEFAULT_NET;
    private IBasicAsyncTaskFinish listener = null;

    private ServiceRequest sRequest;
    private Service sService;
    private ServiceResponse sResponse;

    protected Handler h = null;
    protected Object result = null;

    public BasicAsyncTask(ServiceRequest request, Service service,
                          CacheType cache, IBasicAsyncTask callback) {
        this.sRequest = request;
        this.sService = service;
        this.callback = callback;
        this.cacheType = cache;
    }

    public BasicAsyncTask(ServiceResponse response, Service service,
                          CacheType cache) {
        this.sService = service;
        this.cacheType = cache;
        this.sResponse = response;
    }

    public BasicAsyncTask(IBasicAsyncTask callback, String url,
                          CacheType cacheType) {
        this.url = url;
        this.callback = callback;
        this.cacheType = cacheType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 100:
                        callback.callback(result);
                        break;

                    default:
                        break;
                }
            }

        };
    }

    @Override
    protected Object doInBackground(Object... params) {
        Object obj = null;
        Lg.print(cacheType);
        if (cacheType == CacheType.DEFAULT_NET) {
            obj = _doInBackGround_net(params);
        } else if (cacheType == CacheType.DEFAULT_CACHE_NET) {
            obj = _doInBackGround_cache_net(params);
        } else if (cacheType == CacheType.DEFAULT_CACHE) {
            obj = _doInBackGround_cache(params);
        } else if (cacheType == CacheType.BASIC_NET) {
            //obj = _doInBackGround_basic_net(params);
        } else if (cacheType == CacheType.BASIC_CACHE_NET) {
            obj = _doInBackGround_basic_cache_net(params);
        } else if (cacheType == CacheType.BASIC_CACHE) {
            obj = _doInBackGround_basic_cache(params);
        }
        return obj;
    }

    // 从缓存返回
    private Object _doInBackGround_basic_cache(Object[] params) {
        if (params != null && params.length % 2 != 0) {
            Lg.print("params error ...");
            return null;
        }
        String key = url + g.toJson(params);
        result = CacheUtil.getInterfaceObject(key);
        // Lg.print("从缓存获取");
        h.sendEmptyMessage(100);
        return result;
    }

    // 先从缓存 获取， 然后返回网络数据 ，返回两次
    private Object _doInBackGround_basic_cache_net(Object[] params) {
        if (params != null && params.length % 2 != 0) {
            Lg.print("params error ...");
            return null;
        }
        String key = sService.getClass().getName() + g.toJson(sRequest)
                + CacheUtil.cacheKey;
        result = CacheUtil.getInterfaceObject(key);
        if (result != null) {
            Lg.print(TAG, "return from cache first");
            h.sendEmptyMessage(100); // return from cache first
        }
        BasicParams p = new BasicParams();
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                p.addParam(params[i].toString(), params[i + 1].toString());
            }
        }
        sResponse = ServiceManager.getServiceResponse(sRequest, sService);
        result = sResponse;

        CacheUtil.saveInterfaceObject(key, result);

        if (listener != null) {
            listener.asyncTaskFinish(this);
        }
        return result;
    }

    private Object _doInBackGround_cache(Object[] params) {
        String key = sService.getClass().getName() + g.toJson(sRequest)
                + CacheUtil.cacheKey;
        result = CacheUtil.getInterfaceObject(key);
        if (result != null) {
            Lg.print("从缓存获取");
            h.sendEmptyMessage(100);
        } else {
            sResponse = ServiceManager.getServiceResponse(sRequest, sService);
            result = sResponse;
            CacheUtil.saveInterfaceObject(key, result);
            Lg.print("从网络获取");
            h.sendEmptyMessage(100);
        }
        if (listener != null) {
            listener.asyncTaskFinish(this);
        }
        return result;
    }

    private Object _doInBackGround_cache_net(Object[] params) {
        String key = sService.getClass().getName() + g.toJson(sRequest)
                + CacheUtil.cacheKey;
        Lg.print("_doInBackGround_cache_net    " + key);
        result = CacheUtil.getInterfaceObject(key);
        if (result != null) {
            Lg.print("从缓存获取");
            h.sendEmptyMessage(100);
        } else {
            sResponse = ServiceManager.getServiceResponse(sRequest, sService);
            result = sResponse;
            CacheUtil.saveInterfaceObject(key, result);
            Lg.print("从网络获取");
            h.sendEmptyMessage(100);
        }
        if (listener != null) {
            listener.asyncTaskFinish(this);
        }
        return result;
    }

    private Object _doInBackGround_net(Object[] params) {
        String key = sService.getClass().getName() + g.toJson(sRequest)
                + CacheUtil.cacheKey;
        sResponse = ServiceManager.getServiceResponse(sRequest, sService);
        result = sResponse;
        CacheUtil.saveInterfaceObject(key, result);
        if (listener != null) {
            listener.asyncTaskFinish(this);
        }
        h.sendEmptyMessage(100);
        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        // callback.callback(result);
    }

    @Override
    protected void onCancelled() {
        if (sService != null) {
            sService.cancel();
        }
        super.onCancelled();
    }

    public void addFinishListener(IBasicAsyncTaskFinish fl) {
        this.listener = fl;
    }

    @Override
    protected void onCancelled(Object result) {
        if (sService != null) {
            sService.cancel();
        }
        super.onCancelled();
    }
}
