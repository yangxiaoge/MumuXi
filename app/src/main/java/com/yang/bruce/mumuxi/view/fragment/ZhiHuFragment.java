package com.yang.bruce.mumuxi.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.adapter.ZhuanLanAdapter;
import com.yang.bruce.mumuxi.base.BaseFragment;
import com.yang.bruce.mumuxi.bean.ZhuanLan;
import com.yang.bruce.mumuxi.constant.TopicData;
import com.yang.bruce.mumuxi.net.NetWorkBean;
import com.yang.bruce.mumuxi.util.NetWorkUtil;
import com.yang.bruce.mumuxi.view.activity.ZhuanLanDetailActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 14:43
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class ZhiHuFragment extends BaseFragment {
    private static final String TAG = "ZhihuFragment";
    private EasyRecyclerView mRecyclerView;
    private LinearLayout noNetWorkLayout;
    private ZhuanLanAdapter zhuanLanAdapter;
    private Handler handler = new Handler();
    // 话题
    private String[] ids;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认话题
        ids = TopicData.default_ids;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhuanlan_layout, container, false);
        initViews(view);

        // 网络是否连接
        if (!NetWorkUtil.isNetworkConnected(getActivity()) && !NetWorkUtil.isWifiConnected(getActivity())) {
            noNetWorkLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }

    // initViews
    private void initViews(View view) {
        //FloatingActionButton
        final FloatingActionButton fb = (FloatingActionButton) view.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 为 fb 设置动画
                Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.animator_rotation);
                animator.setTarget(fb);

                // Repeats the animation for a specified number of cycles. The
                // rate of change follows a sinusoidal pattern. 指定动画变化周期
                animator.setInterpolator(new CycleInterpolator(2));
                animator.start();

                // 切换话题
                ids = TopicData.life_talks_ids;
                // 刷新数据
                onRefresh();
            }
        });

        noNetWorkLayout = (LinearLayout) view.findViewById(R.id.no_network);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 默认分割线
        mRecyclerView.setLayoutManager(linearLayoutManager);
        zhuanLanAdapter = new ZhuanLanAdapter(getActivity());
        doWithZhuanLan(zhuanLanAdapter);
        mRecyclerView.setRefreshListener(this);
        onRefresh();

    }

    // 初始化,刷新,加载更多progress , 以及 点击item 跳转 detailactivity
    private void doWithZhuanLan(final RecyclerArrayAdapter<ZhuanLan> adapter) {
        // 加载中...
        mRecyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.load_more_layout, this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ZhuanLanDetailActivity.class);
                intent.putExtra("slug", adapter.getItem(position).getSlug());
                intent.putExtra("title", adapter.getItem(position).getName());
                intent.putExtra("profile_url", adapter.getItem(position).getCreator().getProfileUrl());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
    }

    //  获取 zhuanlan 作者 list
    private void getZhuanLanData(String zhuanLanName) {
        NetWorkBean.getZhuanLanApi()
                .getZhuanLan(zhuanLanName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhuanLan>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage() + "\t" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ZhuanLan zhuanLan) {
                        Log.d(TAG, "OnNext");
                        zhuanLanAdapter.add(zhuanLan);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 清除adapter
                zhuanLanAdapter.clear();
                for (int i = 0; i < ids.length; i++) {
                    // 获取 zhuanlan 作者 list
                    getZhuanLanData(ids[i]);
                }
            }
        }, 1000);
    }

    // 加载更多
    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ids = TopicData.photography_ids;
                //zhuanLanAdapter.clear(); 不需要清除
                for (int i = 0; i < ids.length; i++) {
                    getZhuanLanData(ids[i]);
                }
            }
        }, 1000);
    }

}
