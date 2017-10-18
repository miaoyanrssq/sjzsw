package com.zjrb.sjzsw.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by jinzifu on 2017/8/23.
 */

public abstract class BaseActivity extends FragmentActivity {
    private ProgressDialog progressDialog;
    protected Context context;


    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = this;
    }

    public void showWaiteDialog() {
        progressDialog = ProgressDialog.show(context, "Loading...", "加载中，请稍后……");
        progressDialog.setCancelable(true);
    }

    public void hideWaiteDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != progressDialog) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 通用toast展示
     *
     * @param string
     */
    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
