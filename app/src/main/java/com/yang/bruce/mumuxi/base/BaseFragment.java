package com.yang.bruce.mumuxi.base;

import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import rx.Subscription;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 14:44
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
        , RecyclerArrayAdapter.OnLoadMoreListener {

    protected Subscription subscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 取消订阅
        unsubscribe();
    }

    // 取消订阅
    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 上拉刷新 加载更多
     */
    @Override
    public void onLoadMore() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

    }
}
