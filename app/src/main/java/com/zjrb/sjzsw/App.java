package com.zjrb.sjzsw;

import android.app.Application;

import com.jzf.net.api.HttpClient;


/**
 * Created by shiwei on 2017/3/21.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpClient.init(this);
    }
}
