package com.zjrb.sjzsw.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jzf.net.callback.OnResultCallBack;
import com.jzf.net.exception.ApiException;
import com.jzf.net.observer.CommonObserver;
import com.zjrb.sjzsw.R;
import com.zjrb.sjzsw.controller.MainController;
import com.zjrb.sjzsw.entity.GirlList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseControllerActivity {
    @BindView(R.id.result)
    TextView result;
    private CommonObserver commonObserver;
    private MainController mainController;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        registerController(mainController = new MainController(this));
    }

    @OnClick({R.id.btn, R.id.result})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                mainController.getGrils("9ea08bbe593c23393780a4d5a7fa35cd", 50,
                        commonObserver = new CommonObserver(new OnResultCallBack<GirlList>() {
                            @Override
                            public void onSuccess(GirlList tb) {
                                result.setText("");
                                StringBuilder stringBuilder = new StringBuilder();
                                for (GirlList.NewslistBean bean : tb.getNewslist()) {
                                    stringBuilder.append(bean.getTitle() + "\n");
                                }
                                result.setText(stringBuilder.toString());
                            }

                            @Override
                            public void onError(ApiException.ResponeThrowable e) {
                                result.setText("onError: errorMsg:" + e.getMessage());
                            }
                        }));
                break;
            case R.id.result:
                startActivity(new Intent(MainActivity.this,TestActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            result.setText("");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != commonObserver){
            commonObserver.unSubscribe();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e("MainActivity","已经执行回收了");
    }
}

