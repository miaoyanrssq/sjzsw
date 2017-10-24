package com.zjrb.sjzsw.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jzf.net.api.HttpClient;
import com.jzf.net.observer.CommonObserver;
import com.zjrb.sjzsw.api.ApiManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

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
        HttpClient.getInstance().execute(ApiManager.getApiService().getGirls(s, i), commonObserver);
    }


    /**
     * 测试内存泄漏的示例
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("handler", "执行handler了");
        }
    };

    /**
     * 静态内部类handler，防止内存泄漏
     */
    private static class MyHandler extends Handler {
        private WeakReference<Activity> reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = reference.get();
            if (activity != null && !activity.isFinishing()) {
                switch (msg.what) {
                    //todo 处理消息

                    default:
                        break;
                }
            }
        }
    }

    /**
     * WeakHashMap当系统内存不足时，
     * 垃圾收集器会自动的清除没有在任何其他地方被引用的键值对，
     * 因此可以作为简单缓存表的解决方案。
     * 而HashMap就没有上述功能。
     * 但如果WeakHashMap的key在系统内持有强引用，
     * 那么WeakHashMap就退化为了HashMap，
     * 所有的表项都不会被垃圾回收器回收。
     * @param index
     */
    public void testWeakHashMap(int index) {
        int code = 100000;
        switch (index) {
            case 1:
                Map<Integer, byte[]> hashMap = new HashMap<Integer, byte[]>(16);
                for (int i = 0; i < code; i++) {
                    hashMap.put(i, new byte[i]);
                }
                Log.d("index == 1","已经执行完成了 "+hashMap.size());
                break;
            case 2:
                Map<Integer, byte[]> weakHashMap = new WeakHashMap<Integer, byte[]>(16);
                for (int i = 0; i < code; i++) {
                    weakHashMap.put(i, new byte[i]);
                }
                Log.d("index == 2","已经执行完成了 "+weakHashMap.size());
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.sendEmptyMessageDelayed(1, 8000);
//        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e("MainController", "已经执行回收了");
    }
}
