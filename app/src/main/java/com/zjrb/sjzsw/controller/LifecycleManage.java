package com.zjrb.sjzsw.controller;

import com.zjrb.sjzsw.listener.LifeCycle;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by jinzifu on 2017/9/1.
 * 生命周期管理器
 * 类描述：此模式并不能避免内存泄漏，只是提供了一种业务层和视图层解耦的思路，并支持绑定业务层与视图层的生命周期
 */

public class LifecycleManage implements LifeCycle {
    private Map<String, LifeCycle> lifeCycleMap;

    public LifecycleManage() {
        //WeakHashMap当系统内存不足时，垃圾收集器会自动的清除没有在任何其他地方被引用的键值对。
        lifeCycleMap = new WeakHashMap<String, LifeCycle>();
    }

    public void register(String key, LifeCycle lifeCycle) {
        //只有put、get、size()方法来可以触发WeakHashMap来进行处理ReferenceQueue。
        lifeCycleMap.put(key, lifeCycle);
    }

    public void unregister(String key) {
        lifeCycleMap.remove(key);
    }


    @Override
    public void onStart() {
        for (Map.Entry<String, LifeCycle> entry : lifeCycleMap.entrySet()) {
            entry.getValue().onStart();
        }
    }

    @Override
    public void onResume() {
        for (Map.Entry<String, LifeCycle> entry : lifeCycleMap.entrySet()) {
            entry.getValue().onResume();
        }
    }

    @Override
    public void onPause() {
        for (Map.Entry<String, LifeCycle> entry : lifeCycleMap.entrySet()) {
            entry.getValue().onPause();
        }
    }

    @Override
    public void onStop() {
        for (Map.Entry<String, LifeCycle> entry : lifeCycleMap.entrySet()) {
            entry.getValue().onStop();
        }
    }

    @Override
    public void onDestroy() {
        for (Map.Entry<String, LifeCycle> entry : lifeCycleMap.entrySet()) {
            entry.getValue().onDestroy();
        }
        lifeCycleMap.clear();//移除所有ctrl元素，但并未置空对象引用与回收对象内存

    }

    public LifeCycle get(String key) {
        return lifeCycleMap.get(key);
    }
}
