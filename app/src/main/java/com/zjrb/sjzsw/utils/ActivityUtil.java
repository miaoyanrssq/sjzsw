package com.zjrb.sjzsw.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * Created by jinzifu on 2017/7/7.
 * 一个用于多数activity跳转的管理类
 */

public class ActivityUtil {

    public static void next(Context context, Intent intent) {
        if (context != null && intent != null){
            context.startActivity(intent);
        }
    }

    public static void next(Context context, Class<? extends FragmentActivity> actClas) {
        Intent intent = new Intent(context, actClas);
        next(context, intent);
    }

}
