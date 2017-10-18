package com.zjrb.sjzsw.listener;

/**
 * Created by jinzifu on 2017/9/1.
 */

public interface LifeCycle {
    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
