package com.android_mobile.core.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences 封装对象
 */
public class SharedPrefManager {

    private static final boolean DEBUG = false;

    private static final String LOG_TAG = "SharedPrefManager:";
    //数据缓存目录
    private static final String DATA_CACHE = "data_cache";

    private static SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        if (mSharedPreferences == null) {
            synchronized (SharedPrefManager.class) {
                mSharedPreferences = context.getSharedPreferences(
                        DATA_CACHE, Context.MODE_PRIVATE);
            }
        }
    }

    static void isInitialize() {
        if (mSharedPreferences == null) {
            throw new NullPointerException(String.valueOf("SharePreferences is Null!"));
        }
    }

    private SharedPrefManager() {
    }

    /**
     * Retrieve all values from the preferences
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        isInitialize();
        return mSharedPreferences.getAll();
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public static String getString(String key, String defValue) {
        isInitialize();
        return mSharedPreferences.getString(key, defValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defValues) {
        isInitialize();
        return mSharedPreferences.getStringSet(key, defValues);
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public static int getInt(String key, int defValue) {
        isInitialize();
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * Retrieve a Long value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public static long getLong(String key, long defValue) {
        isInitialize();
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * Retrieve an Float value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public static float getFloat(String key, float defValue) {
        isInitialize();
        return mSharedPreferences.getFloat(key, defValue);
    }

    /**
     * Retrieve a Boolean value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public static boolean getBoolean(String key, boolean defValue) {
        isInitialize();
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    public static boolean contains(String key) {
        isInitialize();
        return mSharedPreferences.contains(key);
    }

    /**
     * Set a String value in the preferences editor
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public static boolean putString(String key, String value) {
        isInitialize();
        return mSharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * Set a set String value in the preferences editor
     *
     * @param key    The name of the preference to modify.
     * @param values The set of new values for the preference.
     */
    public static boolean putStringSet(String key, Set<String> values) {
        isInitialize();
        return mSharedPreferences.edit().putStringSet(key, values).commit();
    }

    /**
     * Set an int value in the preferences editor
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public static boolean putInt(String key, int value) {
        isInitialize();
        return mSharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * Set a long value in the preferences editor
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public static boolean putLong(String key, long value) {
        isInitialize();
        return mSharedPreferences.edit().putLong(key, value).commit();
    }

    /**
     * Set a float value in the preferences editor
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public static boolean putFloat(String key, float value) {
        isInitialize();
        return mSharedPreferences.edit().putFloat(key, value).commit();
    }

    /**
     * Set a boolean value in the preferences editor
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public static boolean putBoolean(String key, boolean value) {
        isInitialize();
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * Remove the value from the perferences
     *
     * @param key The name of the preference to remove.
     */
    public static boolean remove(String key) {
        isInitialize();
        return mSharedPreferences.edit().remove(key).commit();
    }

    public static SharedPreferences.Editor editor() {
        isInitialize();
        return mSharedPreferences.edit();
    }

    public void saveObject(String key, Object obj) {
        saveObject(key, obj, DATA_CACHE);
    }

    /**
     * 保存对象缓存
     *
     * @param key      建
     * @param obj      被保存的对象
     * @param fileName 缓存路径
     */
    public void saveObject(String key, Object obj, String fileName) {
        CacheBody body = new CacheBody();
        body.d = new Date();
        body.obj = obj;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(body);
            String strList = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            putString(key, strList);
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

    public Object getObject(String key) {
        return getObject(key, DATA_CACHE);
    }

    /**
     * 获取对象缓存
     *
     * @param key      查找建
     * @param fileName 文件路径
     * @return obj
     */
    public Object getObject(String key, String fileName) {
        Object result = null;
        String message = getString(key, "");
        if (message.equals("")) {
            return null;
        }
        byte[] buffer = Base64.decode(message.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bais);
            result = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 改变缓存路径
     *
     * @param context  全局
     * @param fileName 要切换的路径
     * @return SP
     */
    public SharedPreferences changeSharePrefPath(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    class CacheBody implements Serializable {
        private static final long serialVersionUID = 4653948707335338906L;
        public Date d;
        public Object obj;
    }
}
