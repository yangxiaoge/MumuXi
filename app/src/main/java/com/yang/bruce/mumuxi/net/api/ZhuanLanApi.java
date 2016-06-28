package com.yang.bruce.mumuxi.net.api;
import com.yang.bruce.mumuxi.bean.ZhuanLan;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 10:30
 * Version: 1.0
 * TaskId:
 * Description: 知乎专栏 接口
 */
public interface ZhuanLanApi {
    @GET("{zhuanlanname}")
    Observable<ZhuanLan> getZhuanLan(
            @Path("zhuanlanname") String zhuanlanname
    );
}
