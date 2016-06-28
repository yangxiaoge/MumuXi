package com.yang.bruce.mumuxi.net.api;

import com.yang.bruce.mumuxi.bean.ArticleDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 11:00
 * Version: 1.0
 * TaskId:
 * Description: 专栏Detail下的文章几口
 */
public interface ArticleDetailApi {
    @GET("{articleSlug}")
    Observable<ArticleDetail> getArticleDetail(
            @Path("articleSlug") int articleSlug
    );
}
