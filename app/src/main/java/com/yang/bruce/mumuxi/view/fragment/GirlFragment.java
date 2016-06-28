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
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.adapter.GirlAdapter;
import com.yang.bruce.mumuxi.base.BaseFragment;
import com.yang.bruce.mumuxi.bean.Item;
import com.yang.bruce.mumuxi.cache.MeiziData;
import com.yang.bruce.mumuxi.util.Dp2PxUtil;
import com.yang.bruce.mumuxi.util.NetWorkUtil;
import com.yang.bruce.mumuxi.view.activity.GirlActivity;

import java.util.ArrayList;
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

    protected List<Item> isNoItem = new ArrayList<>(); // 判断妹子数据有无?

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_layout, container, false);
        initViews(view);
        // 网络是否连接
        hasNetWork();

        return view;
    }

    // 网络是否连接
    private void hasNetWork() {
        if (!NetWorkUtil.isNetworkConnected(getActivity())) {
            SnackbarManager.show(
                    Snackbar.with(getActivity()) // context
                            .text("网络未连接￣へ￣") // text to display
                            .actionLabel("重试?") // action button label
                            .actionListener(new ActionClickListener() { // action button's ActionClickListener
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    hasNetWork();
                                }
                            }), getActivity());

        }
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

        // for itemDecoration start (装饰而已,可以不要)
        SpaceDecoration itemDecoration = new SpaceDecoration((int) Dp2PxUtil.convertDp2Pixel(8, getActivity()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        // for itemDecoration end

        // RecyclerView 刷新监听
        mRecyclerView.setRefreshListener(this);
        // load data while init , 初始化是加载数据
        getGirlData(page);
    }

    /**
     * Here get data from 缓存 or 网络
     *
     * @param page 页码
     */
    private void getGirlData(int page) {
        unsubscribe();
        subscription = MeiziData.getInstance()
                // 获取数据
                .subscribeData(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        Log.d(TAG, "OnNext");
                        isNoItem.addAll(items);
                        girlAdapter.addAll(items);
                    }
                }, page); // page是传进来的页码

    }

    // can intent to GirlActivity here(设置刷新效果,以及点击跳转大图)
    private void doWithGirl(final RecyclerArrayAdapter<Item> adapter) {
        mRecyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.load_more_layout, this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        // item点击跳转GirlActivity图片详情页
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
        hasNetWork();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * page设置成 1,也就是最新的一页,如果不赋值为1,
                 * 那么page值是上次调用 onLoadMore后累加的值( page++ 可能是5,6,7等等,取决于你上拉刷新几次)!!!
                 */
                page = 1;
                // before refresh clean cache (1、清除适配器数据 2、clearMemoryAndDiskCache 清除磁盘缓存(删除文件data.db)  )
                girlAdapter.clear();
                MeiziData.getInstance().clearMemoryAndDiskCache();

                //重新获取最新妹子数据( page = 1 )
                getGirlData(page);

                if (isNoItem.size() <= 0) {
                    Toast.makeText(getActivity(), "哎呀,暂时未能获取到妹子~~~", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        hasNetWork();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // before refresh clean cache (1、清除适配器数据 2、clearMemoryAndDiskCache 清除磁盘缓存(删除文件data.db)  )
                MeiziData.getInstance().clearMemoryAndDiskCache();

                page++;
                getGirlData(page);

                if (isNoItem.size() <= 0) {
                    Toast.makeText(getActivity(), "哎呀,暂时未能获取到妹子~~~", Toast.LENGTH_SHORT).show();
                }

            }
        }, 1000); // 设置延迟 1秒
    }
}
