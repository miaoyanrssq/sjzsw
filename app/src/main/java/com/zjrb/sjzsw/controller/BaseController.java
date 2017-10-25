package com.zjrb.sjzsw.controller;

import android.content.Context;

import com.zjrb.sjzsw.listener.LifeCycle;

/**
 * Created by jinzifu on 2017/9/1.
 * 业务逻辑类基类
 * 这种类只做业务逻辑处理，只要不传view进来就可以完成的事情都可以在这里做
 * 这是我没用mvp的原因：https://juejin.im/post/58b25e588d6d810057ed3659
 */

public class BaseController implements LifeCycle {
    protected Context context;

    public BaseController(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
