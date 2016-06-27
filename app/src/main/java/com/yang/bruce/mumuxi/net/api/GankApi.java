package com.yang.bruce.mumuxi.net.api;

import com.yang.bruce.mumuxi.bean.Girl;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<Girl> getBeauties(@Path("number") int number, @Path("page") int page);
}
