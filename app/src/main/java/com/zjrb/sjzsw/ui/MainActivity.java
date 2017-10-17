package com.zjrb.sjzsw.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zjrb.sjzsw.R;
import com.zjrb.sjzsw.entity.GirlList;
import com.zjrb.sjzsw.http.api.CustomApi;
import com.zjrb.sjzsw.http.api.HttpClient;
import com.zjrb.sjzsw.http.callback.OnResultCallBack;
import com.zjrb.sjzsw.http.exception.ApiException;
import com.zjrb.sjzsw.http.observer.CommonObserver;

public class MainActivity extends AppCompatActivity {
    private CommonObserver commonObserver;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = (TextView) findViewById(R.id.result);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTv.setText("");

                CustomApi customApi = HttpClient.getInstance().customApiService();
                HttpClient.getInstance().customApiExecute(customApi.getGirls("9ea08bbe593c23393780a4d5a7fa35cd",50), commonObserver);
            }
        });

        commonObserver = new CommonObserver(new OnResultCallBack<GirlList>() {
            @Override
            public void onSuccess(GirlList tb) {
                if (tb == null) return;
                resultTv.setText(""+tb.getNewslist().size());
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
        commonObserver.unSubscribe();
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

