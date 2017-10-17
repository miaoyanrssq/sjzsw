package com.zjrb.sjzsw.http.api;


import com.zjrb.sjzsw.entity.GirlList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jinzifu on 2017/10/16.
 * Email:jinzifu123@163.com
 * 类描述:自定义API接口类型
 */
public interface CustomApi {

    @GET("meinv/")
    Observable<BaseResponse<GirlList>> getGirls(@Query("key") String key, @Query("num") int num);

}
