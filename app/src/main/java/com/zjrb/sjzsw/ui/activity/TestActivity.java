package com.zjrb.sjzsw.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zjrb.sjzsw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinzifu on 2017/10/23.
 * Email:jinzifu123@163.com
 * 类描述:
 */

public class TestActivity extends BaseControllerActivity {
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.result)
    TextView result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn, R.id.result})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                break;
            case R.id.result:
                break;
        }
    }
}
