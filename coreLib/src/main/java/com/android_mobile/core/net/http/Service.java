package com.android_mobile.core.net.http;

import com.android_mobile.core.enums.RequestType;
import com.android_mobile.core.enums.ResponseChartset;
import com.android_mobile.core.utiles.Lg;
import com.google.gson.Gson;

import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Service {
    public RequestType REQUEST_TYPE = RequestType.POST;
    public ResponseChartset CHARSET = ResponseChartset.UTF_8;
    private static final int SET_CONNECTION_TIMEOUT = 60 * 1000;
    private static final int SET_SOCKET_TIMEOUT = 60 * 1000;

    protected abstract ServiceResponse execute(ServiceRequest request);

    protected Gson g = new Gson();

    private Http http = null;

    public void cancel() {
        if (http != null) {
            http.cancel();
        }
    }

    protected List<BasicNameValuePair> getParams(ServiceRequest req) {
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair param = null;
        Class<? extends ServiceRequest> c = req.getClass();
        Class supreClazz = c.getSuperclass();
        Field[] fields = c.getDeclaredFields();
        Field[] parentFields = supreClazz.getDeclaredFields();

        Field[] result = new Field[fields.length + parentFields.length];
        System.arraycopy(fields, 0, result, 0, fields.length);
        System.arraycopy(parentFields, 0, result, fields.length,
                parentFields.length);

        for (Field f : result) {
            f.setAccessible(true);
            try {
                String s = null;
                if (f.get(req) instanceof Integer) {
                    s = String.valueOf((Integer) f.get(req));
                }
                if (f.get(req) instanceof String) {
                    s = (String) f.get(req);
                }
                if (f.get(req) instanceof Double) {
                    s = String.valueOf((Double) f.get(req));
                }
                if (f.get(req) instanceof Float) {
                    s = String.valueOf((Float) f.get(req));
                }
                if (f.get(req) instanceof Boolean) {
                    s = String.valueOf((Boolean) f.get(req));
                }
                param = new BasicNameValuePair(f.getName(), s);
                if (s != null) {
                    params.add(param);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return params;
    }

    protected String request(RequestType requestType, String actionUrl,
                             ServiceRequest req) {
        http = new Http(actionUrl, requestType.getContext());
        List<BasicNameValuePair> valueList = getParams(req);
        Lg.print("network", "URL:" + actionUrl);
        Lg.print("network", "params:" + g.toJson(valueList));
        if (valueList.size() > 0) {
            for (BasicNameValuePair value : valueList) {
                if (value.getValue().toLowerCase().contains("file://")) {
                    String[] split = value.getValue().split(";");
                    for (String s : split) {
                        if (!s.equals("")) {
                            http.addFile(s.replaceAll("file://", ""),
                                    value.getName());
                        }
                    }
                } else {
                    http.addParam(value.getName(), value.getValue());
                }
            }
        }
        String str = http.execute();
        // Lg.print("network_escape", "response:" + Escape.escape(str));
        Lg.print("network", "response:" + str);
        return str;
    }
}