package com.zjrb.sjzsw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jzf.net.Constant;


/**
 * Created by jzf on 2015/10/22.
 */
public class SpUtil {

    /** SharedPreferences input */
    public static void inputSP(Context mContext, String key, Object object) {
        if (null == object) {
            return;
        }
        @SuppressWarnings("static-access")
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /** SharedPreferences get */
    public static Object getSP(Context mContext, String key, Object defaultObject) {
        @SuppressWarnings("static-access")
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return defaultObject;
    }

    /**
     * 根据key删除数据
     * @param mContext
     * @param key
     */
    public static void deleteByKey(Context mContext,String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
