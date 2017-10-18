package com.zjrb.sjzsw.listener;

import android.view.View;

/**
 * Created by jinzifu on 2017/6/10.
 */
public interface OnChildViewClickListener {

    /**
     * view 内子控件点击事件监听回调
     *
     * @param childView 子控件
     * @param action    活动类型——一般写为view的ID
     * @param obj       额外数据
     */
    void onChildViewClick(View childView, String action, Object obj);
}
