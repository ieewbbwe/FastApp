package com.android_mobile.core.utiles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Date;

public class CacheUtil {
    public static String cacheKey = "";
    public static final long CACHE_SECOND = 60 * 60 * 6/*缓存6小时*/;
    /**
     * 一般数据缓存
     */
    public static final String DATA_CACHE = "data_cache_" + CacheUtil.class.getName();
    /**
     * 接口数据缓存
     */
    public static final String INTERFACE_CACHE = "interface_cache_" + CacheUtil.class.getName();
    /**
     * 不可清理数据缓存
     */
    public static final String FOREVER_CACHE = "forever_cache_" + CacheUtil.class.getName();
    public static Context context = null;
    public static Gson g = new Gson();
    private static boolean isTimeCache = true;

    public static void isTimeCache(boolean b) {
        isTimeCache = b;
    }

    /**
     * 缓存Integer
     *
     * @param key
     * @param b
     */
    public static void saveInteger(String key, int b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DATA_CACHE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key + cacheKey, b);
        editor.commit();
    }

    public static int getInteger(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DATA_CACHE, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key + cacheKey, -1);
    }

    /**
     * 缓存   boolean
     *
     * @param key
     * @param b
     */
    public static void saveBoolean(String key, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DATA_CACHE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key + cacheKey, b);
        editor.commit();
    }

    public static boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DATA_CACHE, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key + cacheKey, false);
    }

    public static void saveObject(String key, Object object) {
        saveObject(key, object, DATA_CACHE);
    }

    public static void saveInterfaceObject(String key, Object object) {
        saveObject(key, object, INTERFACE_CACHE);
    }

    private static void saveObject(String key, Object object, String filename) {
        CacheBody body = new CacheBody();
        body.d = new Date();
        body.obj = object;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(body);
            String strList = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(key + cacheKey, strList);
            editor.commit();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getObject(String key) {
        return getObject(key, DATA_CACHE);
    }

    public static Object getInterfaceObject(String key) {
        return getObject(key, INTERFACE_CACHE);
    }

    private static Object getObject(String key, String filename) {
        CacheBody body = null;
        Object result = null;
        Date d1 = new Date();
        Date d0 = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Activity.MODE_PRIVATE);
        String message = sharedPreferences.getString(key + cacheKey, "");
        if (message.equals(""))
            return null;
        byte[] buffer = Base64.decode(message.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        try {
            if (isTimeCache) {
                ObjectInputStream ois = new ObjectInputStream(bais);
                body = (CacheBody) ois.readObject();
                ois.close();
                d0 = body.d;
                int hours = (int) ((d1.getTime() - d0.getTime()) / 3600000);
                int minutes = (int) (((d1.getTime() - d0.getTime()) / 1000 - hours * 3600) / 60);
                int second = (int) ((d1.getTime() - d0.getTime()) / 1000
                        - hours * 3600 - minutes * 60);
                if (Math.abs(second) <= CACHE_SECOND) {
                    result = body.obj;
                } else {
                    result = null;
                }
            } else {
                ObjectInputStream ois = new ObjectInputStream(bais);
                body = (CacheBody) ois.readObject();
                ois.close();
                result = body.obj;
            }
            return result;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void clearInterfaceCache() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                INTERFACE_CACHE, Activity.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void clearDataCache() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DATA_CACHE, Activity.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void clear(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                name + cacheKey, Activity.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void saveForeverObject(String key, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FOREVER_CACHE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String strList = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(key + cacheKey, strList);
            editor.commit();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getForeverObject(String key) {
        Object result = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FOREVER_CACHE, Activity.MODE_PRIVATE);
        if (sharedPreferences == null)
            return null;
        String message = sharedPreferences.getString(key + cacheKey, "");
        if (message.equals(""))
            return null;
        byte[] buffer = Base64.decode(message.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = ois.readObject();
            ois.close();
            return result;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

class CacheBody implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4653948707335338906L;
    public Date d;
    public Object obj;
}
