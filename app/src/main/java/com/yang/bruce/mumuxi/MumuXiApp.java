package com.yang.bruce.mumuxi;

import android.app.Application;

import im.fir.sdk.FIR;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 13:56
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class MumuXiApp extends Application {

    private static MumuXiApp instance;

    //获取 MumuXiApp静态实例对象
    public static MumuXiApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FIR.init(this);
    }
}
