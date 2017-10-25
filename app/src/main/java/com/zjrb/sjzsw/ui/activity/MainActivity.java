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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zjrb.sjzsw.R;
import com.zjrb.sjzsw.controller.MainController;
import com.zjrb.sjzsw.entity.GirlList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseControllerActivity {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CommonObserver commonObserver;
    private MainController mainController;
    private List<GirlList.NewslistBean> beanList = new ArrayList<>();
    private CommonAdapter commonAdapter = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        registerController(mainController = new MainController(this));
        initView();
    }

    private void initView() {
        refreshLayout.autoRefresh();
        refreshLayout.autoLoadmore();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getGirls();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getGirls();
            }
        });

        recycleView.setLayoutManager(new GridLayoutManager(this,2));
        // TODO: 2017/10/25 动态设置style
//        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recycleView.setAdapter(commonAdapter = new CommonAdapter<GirlList.NewslistBean>(this, R.layout.item_main_list, beanList) {
            @Override
            protected void convert(ViewHolder holder, GirlList.NewslistBean newslistBean, int position) {
                TextView tv = holder.getView(R.id.item_title);
                ImageView itemImg = holder.getView(R.id.item_img);

                tv.setText(newslistBean.getTitle());
                Glide.with(context).load(newslistBean.getPicUrl()).placeholder(R.mipmap.img_defult).into(itemImg);
            }
        });
    }

    private void getGirls() {
        mainController.getGrils("9ea08bbe593c23393780a4d5a7fa35cd", 20,
                commonObserver = new CommonObserver(new OnResultCallBack<GirlList>() {
                    @Override
                    public void onSuccess(GirlList tb) {
                        beanList.addAll(tb.getNewslist());
                        commonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadmore();
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