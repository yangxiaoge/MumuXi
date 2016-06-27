package com.yang.bruce.mumuxi.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.adapter.GirlAdapter;
import com.yang.bruce.mumuxi.base.BaseFragment;
import com.yang.bruce.mumuxi.bean.Item;
import com.yang.bruce.mumuxi.cache.MeiziDataCache;
import com.yang.bruce.mumuxi.view.activity.GirlActivity;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 14:47
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class GirlFragment extends BaseFragment {

    private static final String TAG = "GirlFragment";
    protected Subscription subscription;
    private EasyRecyclerView mRecyclerView;
    private GirlAdapter girlAdapter;

    private int page = 1; // 默认第一页
    private Handler handler = new Handler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhuanlan_layout, container, false);
        initViews(view);
        return view;
    }

    // initViews
    private void initViews(View view) {
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);
        // 流式布局 , 2 列
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        girlAdapter = new GirlAdapter(getActivity());
        doWithGirl(girlAdapter);

        mRecyclerView.setRefreshListener(this);
        // load data while init
        getGirlData(page);
    }

    /**
     * Here get data from 缓存 or 网络
     *
     * @param page 页码
     */
    private void getGirlData(int page) {
        unsubscribe();
        subscription = MeiziDataCache.getInstance()
                .subscribeData(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        girlAdapter.addAll(items);
                    }
                }, page);
    }

    // can intent to GirlActivity here
    private void doWithGirl(final RecyclerArrayAdapter<Item> adapter) {
        mRecyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.load_more_layout, this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity(), GirlActivity.class);
                intent.putExtra("desc", adapter.getItem(position).description);
                intent.putExtra("url", adapter.getItem(position).imageUrl);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
    }

    // Load and Refresh data
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // before refresh clean cache
                girlAdapter.clear();
                MeiziDataCache.getInstance().clearMemoryAndDiskCache();
                getGirlData(page);

            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // before load more ,clean cache
                MeiziDataCache.getInstance().clearMemoryAndDiskCache();
                page++;
                getGirlData(page);
            }
        }, 1000);
    }
}
