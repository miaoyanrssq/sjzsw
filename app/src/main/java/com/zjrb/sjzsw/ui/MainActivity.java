package com.zjrb.sjzsw.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zjrb.sjzsw.R;
import com.zjrb.sjzsw.entity.TestBean;
import com.zjrb.sjzsw.http.api.ApiManager;
import com.zjrb.sjzsw.http.callback.OnResultCallBack;
import com.zjrb.sjzsw.http.observer.CommonObserver;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private CommonObserver mHttpObserver;
    private TextView resultTv;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = (TextView) findViewById(R.id.result);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "";
                resultTv.setText("");

                ApiManager.commonSubscribe(ApiManager.getApiService().getDatas(1, 10, "json1"),mHttpObserver);
            }
        });

        mHttpObserver = new CommonObserver(new OnResultCallBack<TestBean>() {
            @Override
            public void onSuccess(TestBean tb) {
                for (TestBean.ListBean bean : tb.getList()) {
                    result += bean.toString();
                }
                resultTv.setText(result);
            }

            @Override
            public void onError(int code, String errorMsg) {
                resultTv.setText("onError: code:" + code + "  errorMsg:" + errorMsg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHttpObserver.unSubscribe();
    }
}

