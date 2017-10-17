package com.zjrb.sjzsw.http.api;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zjrb.sjzsw.App;
import com.zjrb.sjzsw.Constant;
import com.zjrb.sjzsw.http.cookie.CookieManger;
import com.zjrb.sjzsw.http.exception.ApiException;
import com.zjrb.sjzsw.http.interceptor.CaheInterceptor;
import com.zjrb.sjzsw.http.interceptor.HeaderInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit mRetrofit;
    private volatile static HttpClient instance;
    //    private GsonTSpeaker gsonTSpeaker = null;
    private Cache cache = null;
    private File httpCacheDirectory;
    private CommonApi apiService;
    private static ErrorTransformer transformer = new ErrorTransformer();

    private HttpClient() {
        //header追加
        HashMap<String, String> headerMap = new HashMap<String, String>();
        CaheInterceptor caheInterceptor = new CaheInterceptor();

        //缓存地址
        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(App.getContext().getCacheDir(), "zjrb_cache");
        }
        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("====HttpClient====", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieManger(App.getContext()))
                .addInterceptor(new HeaderInterceptor(headerMap))
                .addInterceptor(loggingInterceptor)
                .addInterceptor(caheInterceptor)
                .addNetworkInterceptor(caheInterceptor)
                .cache(cache);

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

    public void get(String url, Map parameters, Observer<?> observer) {
        apiService.executeGet(url, parameters)
                .compose(schedulersTransformer)
                .compose(transformer)
                .subscribe(observer);
    }

    public void post(String url, Map<String, String> parameters, Observer<?> observer) {
        apiService.executePost(url, parameters)
                .compose(schedulersTransformer)
                .compose(transformer)
                .subscribe(observer);
    }

    public void json(String url, RequestBody jsonStr, Observer<ResponseBody> observer) {

        apiService.json(url, jsonStr)
                .compose(schedulersTransformer)
                .compose(transformer)
                .subscribe(observer);
    }

    public void upload(String url, RequestBody requestBody, Observer<ResponseBody> observer) {
        apiService.upLoadFile(url, requestBody)
                .compose(schedulersTransformer)
                .compose(transformer)
                .subscribe(observer);
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public HttpClient createBaseApi() {
        apiService = create(CommonApi.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("CustomApi service is null!");
        }
        return mRetrofit.create(service);
    }

    //处理线程调度的变换
    ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return ((Observable) upstream).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    //处理错误的变换
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (Observable<T>) upstream.map(new HandleFunction<T>()).onErrorResumeNext(new HttpResponseFunction<T>());
        }
    }

    public static class HttpResponseFunction<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(ApiException.handleException(throwable));
        }
    }

    public static class HandleFunction<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) throws Exception {
            if (!response.isOk()) {
                throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg() : "");
            }
            return response.getData();
        }
    }

    /**
     * @param observable
     * @param observer
     * @param <T>        例如：
     *                   CustomApi customApi = HttpClient.getInstance().customApiService();
     *                   HttpClient.getInstance().customApiExecute(customApi.getGirls(1, 10, "json1"), commonObserver);
     */
    public <T> void customApiExecute(Observable<BaseResponse<T>> observable, Observer<?> observer) {
        observable.compose(schedulersTransformer)
                .compose(transformer)
                .subscribe(observer);
    }

    public CustomApi customApiService() {
        return mRetrofit.create(CustomApi.class);
    }
}
