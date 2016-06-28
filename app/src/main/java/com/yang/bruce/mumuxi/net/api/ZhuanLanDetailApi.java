package com.yang.bruce.mumuxi.net.api;

import com.yang.bruce.mumuxi.bean.Article;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 15:26
 * Version: 1.0
 * TaskId:
 * Description: 专栏Detail 接口
 */
public interface ZhuanLanDetailApi {

    @GET("{slug}/posts")
    Observable<List<Article>> getArticle(
            @Path("slug") String slug,
            @Query("limit") int limit,
            @Query("offest") int offest
    );

}
