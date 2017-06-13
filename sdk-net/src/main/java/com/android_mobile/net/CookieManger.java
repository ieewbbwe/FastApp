package com.android_mobile.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by mxh on 2017/2/25.
 * Describe：
 */

public class CookieManger implements CookieJar {

    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url, cookies);

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
