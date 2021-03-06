package com.zjrb.sjzsw.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzf.net.callback.OnResultCallBack;
import com.jzf.net.exception.ApiException;
import com.jzf.net.observer.CommonObserver;
import com.zjrb.sjzsw.R;
import com.zjrb.sjzsw.controller.MainController;
import com.zjrb.sjzsw.entity.GirlList;
import com.zjrb.sjzsw.widget.recyclerview.CommonAdapter;
import com.zjrb.sjzsw.widget.recyclerview.DividerGridItemDecoration;
import com.zjrb.sjzsw.widget.recyclerview.base.MyViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseControllerActivity {
    private CommonObserver commonObserver;
    private MainController mainController;
    private List<GirlList.NewslistBean> beanList = new ArrayList<>();
    private CommonAdapter commonAdapter = null;


    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerController(mainController = new MainController(this));
        initView();
        getGirls();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // TODO: 2017/10/26 要支持动态设置分割线粗细和颜色

        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerView.setAdapter(commonAdapter = new CommonAdapter<GirlList.NewslistBean>(this, R.layout.item_main_list, beanList) {
            @Override
            protected void convert(MyViewHolder holder, GirlList.NewslistBean newslistBean, int position) {
                TextView tv = holder.getView(R.id.item_title);
                ImageView itemImg = holder.getView(R.id.item_img);

                tv.setText(newslistBean.getTitle());
                Glide.with(context).load(newslistBean.getPicUrl()).centerCrop().placeholder(R.mipmap.img_defult).into(itemImg);
            }
        });
    }

    private void getGirls() {
        mainController.getGrils("9ea08bbe593c23393780a4d5a7fa35cd", 50,
                commonObserver = new CommonObserver(new OnResultCallBack<GirlList>() {
                    @Override
                    public void onSuccess(GirlList tb) {
                        beanList.addAll(tb.getNewslist());
                        commonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(ApiException.ResponeThrowable e) {
                        Log.e("onError", "" + e.getMessage());
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != commonObserver) {
            commonObserver.unSubscribe();
        }
    }
}