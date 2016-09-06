package com.yang.bruce.mumuxi;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import im.fir.sdk.FIR;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 13:56
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class MumuXiApp extends Application {
    private Tracker mTracker;

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
        mTracker = getDefaultTracker();
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    /**
     * Record a screen view hit for the visible {@link } displayed
     */
    public void sendSelectedScreenName(String name) {
        // [START screen_view_hit]
        Log.i("MumuXiApp", "current fragment name: " + name);
        mTracker.setScreenName("" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    public void sendAction(String catagoryName, String actionName, String labelName) {
        //mTracker.setScreenName("ActionScreen");
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(catagoryName + "事件类别")
                .setAction(actionName + "事件操作类似按钮点击")
                .setLabel(labelName + "事件标签")
                .build());
    }
}
