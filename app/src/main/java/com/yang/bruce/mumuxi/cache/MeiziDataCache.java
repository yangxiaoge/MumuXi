package com.yang.bruce.mumuxi.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yang.bruce.mumuxi.bean.Girl;
import com.yang.bruce.mumuxi.bean.Item;
import com.yang.bruce.mumuxi.net.NetWorkBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class MeiziDataCache {
    private static final String TAG = "MeiziData";
    private static MeiziDataCache instance;
    // BehaviorSubject 只打印出最后一个数据
    BehaviorSubject<List<Item>> cache;
    private MeiziDataCache() {
    }
    // 单例
    public static MeiziDataCache getInstance() {
        if (instance == null) {
            instance = new MeiziDataCache();
        }
        return instance;
    }
    // 从网络加载数据
    public void loadFromNetwork(final int page) {
        Log.e(TAG,page + "");
        NetWorkBean.getGankApi()
                .getBeauties(10, page)
                .subscribeOn(Schedulers.io())
                // Observable 返回的类型 GankBeautyResult Map 转换成 List<Map>
                .map(new Func1<Girl, List<Item>>() {
                    @Override
                    public List<Item> call(Girl girl) {
                        List<Girl.GirlResult> gankBeauties = girl.girlResults;
                        List<Item> items = new ArrayList<>(gankBeauties.size());
                        for (Girl.GirlResult gankBeauty : gankBeauties) {
                            Item item = new Item();
                            item.description = gankBeauty.desc;
                            item.imageUrl = gankBeauty.url;
                            items.add(item);
                        }
                        return items;
                    }
                })
                // 在 doOnNext() 之前先处理一下 Action1<List<Item>> 里面的数据就是输入的数据
                .doOnNext(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        // 写入缓存
                        Log.e("MeiziDataCache","data write in disk cache");
                        Database.getInstance().writeItems(items);
                    }
                })
                .subscribe(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        Log.e("MeiziDataCache","data pass to subscribe");
                        cache.onNext(items);// 自动回调 cache.onNext(items);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
    // 获取数据
    public Subscription subscribeData(@NonNull Observer<List<Item>> observer, final int number) {

        if (cache == null ) {
            cache = BehaviorSubject.create();
            Observable.create(new Observable.OnSubscribe<List<Item>>() {
                @Override
                public void call(Subscriber<? super List<Item>> subscriber) {
                    List<Item> items = Database.getInstance().readItems();

                    if (items == null) {
                        Log.e("MeiziDataCache","no data in disk and load data from net");
                        loadFromNetwork(number);
                    } else {
                        Log.e("MeiziDataCache","disk has data");
                        subscriber.onNext(items);
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(cache);// 观察者与被观察着通过订阅联系起来
        } else {
            Log.e("MeiziDataCache","memory has data just read from memory");
        }
        return cache.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
    }

    public void clearMemoryCache() {
        cache = null;
    }
    // 内存和磁盘缓存
    public void clearMemoryAndDiskCache() {
        clearMemoryCache();
        // 删除磁盘缓存
        Database.getInstance().delete();
    }
}
