package com.zjrb.sjzsw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zjrb.sjzsw.Constant;


/**
 * Created by jzf on 2015/10/22.
 */
public class SpUtil {

    /** SharedPreferences input */
    public static void inputSP(Context mContext, String Key, Object object) {
        if (null == object)return;
        @SuppressWarnings("static-access")
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SP_FILE_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (object instanceof String) {
            editor.putString(Key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(Key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(Key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(Key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(Key, (Long) object);
        } else {
            editor.putString(Key, object.toString());
        }
        editor.commit();
    }

    /** SharedPreferences get */
    public static Object getSP(Context mContext, String Key, Object defaultObject) {
        @SuppressWarnings("static-access")
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SP_FILE_NAME, mContext.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(Key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(Key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(Key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(Key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(Key, (Long) defaultObject);
        }
        return defaultObject;
    }

    /**
     * 根据key删除数据
     * @param mContext
     * @param Key
     */
    public static void deleteByKey(Context mContext,String Key) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SP_FILE_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Key);
        editor.commit();
    }
}
