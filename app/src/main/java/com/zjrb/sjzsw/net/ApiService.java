package com.zjrb.sjzsw.net;


import com.zjrb.sjzsw.entity.TestBean;
import com.zjrb.sjzsw.http.api.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    @FormUrlEncoded
    @POST("query?key=7c2d1da3b8634a2b9fe8848c3a9edcba")
    Observable<ApiResponse<TestBean>> getDatas(@Field("pno") int pno, @Field("ps") int ps, @Field("dtype") String dtype);

}