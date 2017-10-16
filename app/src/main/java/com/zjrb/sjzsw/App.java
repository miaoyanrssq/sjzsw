package com.zjrb.sjzsw;

import android.app.Application;
import android.content.Context;


/**
 * Created by shiwei on 2017/3/21.
 */

public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        HttpClient.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
