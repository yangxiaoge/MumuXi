package com.yang.bruce.mumuxi.net.api;
import com.yang.bruce.mumuxi.bean.ZhuanLan;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ZhuanLanApi {
    @GET("{zhuanlanname}")
    Observable<ZhuanLan> getZhuanLan(
            @Path("zhuanlanname") String zhuanlanname
    );
}
