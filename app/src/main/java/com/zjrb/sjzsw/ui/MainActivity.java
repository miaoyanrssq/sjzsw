package com.zjrb.sjzsw.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zjrb.sjzsw.R;
import com.zjrb.sjzsw.entity.GirlList;
import com.zjrb.sjzsw.http.api.ApiService;
import com.zjrb.sjzsw.http.api.HttpClient;
import com.zjrb.sjzsw.http.callback.OnResultCallBack;
import com.zjrb.sjzsw.http.exception.ApiException;
import com.zjrb.sjzsw.http.observer.BaseObserver;

public class MainActivity extends AppCompatActivity {
    private BaseObserver baseObserver;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = (TextView) findViewById(R.id.result);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService customApi = HttpClient.getInstance().apiServiceCreate();
                HttpClient.getInstance().execute(customApi.getGirls("9ea08bbe593c23393780a4d5a7fa35cd",50), baseObserver);
            }
        });

        baseObserver = new BaseObserver(new OnResultCallBack<GirlList>() {
            @Override
            public void onSuccess(GirlList tb) {
                StringBuilder stringBuilder = new StringBuilder();
                for (GirlList.NewslistBean bean:tb.getNewslist()) {
                    stringBuilder.append(bean.getTitle()+"\n");
                }
                resultTv.setText(stringBuilder.toString());
            }

            @Override
            public void onError(ApiException.ResponeThrowable e) {
                resultTv.setText("onError: errorMsg:" + e.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseObserver.unSubscribe();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            resultTv.setText("");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

