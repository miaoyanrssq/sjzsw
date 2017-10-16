package com.zjrb.sjzsw.http.api;

import com.zjrb.sjzsw.Constant;
import com.zjrb.sjzsw.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinzifu on 2017/7/11.
 */

public class ApiManager {

    /**
     * 获取API接口类
     * 注：可以写多个API接口类，并在此配置
     * @return
     */
    public static Api getApiService() {
        return provideService(Api.class);
    }

    private static Map<Class, Object> map = new HashMap();

    private static <T> T provideService(Class cls) {
        Object object = map.get(cls);
        if (object == null) {
            synchronized (cls) {
                object = map.get(cls);
                if (object == null) {
                    object = HttpClient.getInstance().createService(cls);
                    map.put(cls, object);
                }
            }
        }
        return (T) object;
    }

    /**
     * 封装线程管理和订阅的过程
     * 注：如果要用到rxjava 2.0的链式调用特性，可以另写方法自定义配置
     * @param o
     * @param s
     * @param <T>
     */
    public static <T> void commonSubscribe(Observable<BaseResponse<T>> o, Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull BaseResponse<T> response) throws Exception {
                        int code = Integer.parseInt(response.getCode());
                        if (code != Constant.SUCCESS_CODE) {
                            throw new ApiException(code, response.getMsg());
                        } else {
                            return response.getDatas();
                        }
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
