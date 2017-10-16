package com.zjrb.sjzsw.http.cache;


import com.zjrb.sjzsw.entity.TestBean;
import com.zjrb.sjzsw.http.api.BaseResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<TestBean>> getDatas(Observable<BaseResponse<TestBean>> oRepos, EvictProvider evictDynamicKey);

}