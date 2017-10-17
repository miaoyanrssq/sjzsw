package com.zjrb.sjzsw.http.callback;

import com.zjrb.sjzsw.http.exception.ApiException;

public interface OnResultCallBack<T> {

    void onSuccess(T t);

    void onError(ApiException.ResponeThrowable e);
}
