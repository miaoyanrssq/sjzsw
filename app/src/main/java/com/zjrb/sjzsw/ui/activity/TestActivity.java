package com.zjrb.sjzsw.ui.activity;

import android.os.Bundle;

import com.zjrb.sjzsw.R;

import butterknife.ButterKnife;

/**
 * Created by jinzifu on 2017/10/23.
 * Email:jinzifu123@163.com
 * 类描述:
 */

public class TestActivity extends BaseControllerActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
