package com.yang.bruce.mumuxi.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by allen on 2016/6/17.
 */
public class Girl {
    public boolean error;
    // @SerializedName 定义序列化之后的名称
    public
    @SerializedName("results")
    List<GirlResult> girlResults;

    public static class GirlResult {
        public String desc; //描述
        public String url; //图片地址
        public Date publishedAt; //发布日期
    }
}
