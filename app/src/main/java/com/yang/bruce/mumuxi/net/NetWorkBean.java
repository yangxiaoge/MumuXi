package com.yang.bruce.mumuxi.net;

import com.yang.bruce.mumuxi.net.api.GankApi;
import com.yang.bruce.mumuxi.net.api.ZhuanLanApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 15:53
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class NetWorkBean {
    private static ZhuanLanApi zhuanLanApi;
    private static GankApi gankApi;
    private static OkHttpClient client;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static final String GANHUO_API = "http://gank.io/api/";
    private static final String ZhuanLanList_API = "https://zhuanlan.zhihu.com/api/columns/";
    private static final String ArticleList_API = "https://zhuanlan.zhihu.com/api/columns/";
    private static final String ARTICLEDETAIL_API = "https://zhuanlan.zhihu.com/api/posts/";
    public static final String ZhuanLanAvatar_BASE_API = "https://pic2.zhimg.com/";

    public static OkHttpClient initOkHttp(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        return client;
    }

    // Zhihu zhuanlan api
    public static ZhuanLanApi getZhuanLanApi() {
        if (zhuanLanApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(initOkHttp())
                    .baseUrl(ZhuanLanList_API)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            zhuanLanApi = retrofit.create(ZhuanLanApi.class);
        }
        return zhuanLanApi;
    }

    // Gank meizi api
    public static GankApi getGankApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(initOkHttp())
                    .baseUrl(GANHUO_API)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }
}
