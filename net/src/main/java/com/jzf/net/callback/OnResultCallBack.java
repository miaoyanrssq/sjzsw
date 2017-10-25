package com.jzf.net.callback;

import com.jzf.net.exception.ApiException;

public interface OnResultCallBack<T> {

    void onSuccess(T t);

    void onComplete();

    void onError(ApiException.ResponeThrowable e);
}
