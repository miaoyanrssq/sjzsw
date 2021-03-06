package com.jzf.net.observer;

import com.jzf.net.callback.OnResultCallBack;
import com.jzf.net.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class CommonObserver<T> implements Observer<T> {
    private OnResultCallBack mOnResultListener;
    private Disposable mDisposable;

    public CommonObserver(OnResultCallBack listener) {
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
        if (mOnResultListener != null) {
            mOnResultListener.onComplete();
        }
    }

    public void unSubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
