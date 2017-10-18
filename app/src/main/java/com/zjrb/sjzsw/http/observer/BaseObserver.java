package com.zjrb.sjzsw.http.observer;

import com.zjrb.sjzsw.http.callback.OnResultCallBack;
import com.zjrb.sjzsw.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class BaseObserver<T> implements Observer<T> {
    private OnResultCallBack mOnResultListener;
    private Disposable mDisposable;

    public BaseObserver(OnResultCallBack listener) {
        this.mOnResultListener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {
        if (mOnResultListener != null) {
            mOnResultListener.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException.ResponeThrowable) {
            mOnResultListener.onError((ApiException.ResponeThrowable) e);
        } else {
            mOnResultListener.onError(new ApiException.ResponeThrowable(e, ApiException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

    public void unSubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
