package com.zjrb.sjzsw.controller;

import android.content.Context;

import com.zjrb.sjzsw.http.api.ApiService;
import com.zjrb.sjzsw.http.api.HttpClient;
import com.zjrb.sjzsw.http.observer.CommonObserver;

/**
 * Created by jinzifu on 2017/10/18.
 * Email:jinzifu123@163.com
 * 类描述:
 */

public class MainController extends BaseController {
    public MainController(Context context) {
        super(context);
    }

    /**
     * 获取美女列表
     *
     * @param s              userkey
     * @param i              每页个数
     * @param commonObserver
     */
    public void getGrils(String s, int i, CommonObserver commonObserver) {
        ApiService customApi = HttpClient.getInstance().apiServiceCreate();
        HttpClient.getInstance().execute(customApi.getGirls(s, i), commonObserver);
    }
}
