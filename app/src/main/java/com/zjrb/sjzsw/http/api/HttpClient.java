package com.zjrb.sjzsw.http.api;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zjrb.sjzsw.App;
import com.zjrb.sjzsw.Constant;
import com.zjrb.sjzsw.http.cookie.CookieManger;
import com.zjrb.sjzsw.http.interceptor.HeaderInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    public static final String TAG = HttpClient.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit mRetrofit;
    private volatile static HttpClient instance;
//    private GsonTSpeaker gsonTSpeaker = null;

    private HttpClient() {
        HashMap<String, String> headerMap = new HashMap<String, String>();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieManger(App.getContext()))
                .addInterceptor(new HeaderInterceptor(headerMap))
                .addInterceptor(loggingInterceptor);

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient.build())
                .build();
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public <T> T createService(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

//    public CacheProvider createCache() {
//        if (gsonTSpeaker == null) {
//            gsonTSpeaker = new GsonTSpeaker();
//        }
//        return new RxCache.Builder().persistence(mContext.getFilesDir(), gsonTSpeaker).using(CacheProvider.class);
//    }

//    public static void init(Context context) {
//        mContext = context;
//    }

//        public void getDatasWithCache(Observer<TestBean> subscriber, int pno, int ps, String dtype, boolean update) {
//        commonSubscribe(createCache().getNoInterceptorData(mApiService.getNoInterceptorData(pno, ps,dtype),new EvictProvider(update)), subscriber);
//    }

}
