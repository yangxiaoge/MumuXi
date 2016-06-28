package com.yang.bruce.mumuxi.net.api;

import com.yang.bruce.mumuxi.bean.Girl;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 11:00
 * Version: 1.0
 * TaskId:
 * Description: Gank 妹子福利 接口
 */
public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<Girl> getBeauties(@Path("number") int number, @Path("page") int page);
}
