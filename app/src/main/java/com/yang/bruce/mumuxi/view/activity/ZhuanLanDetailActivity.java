package com.yang.bruce.mumuxi.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.adapter.ArticleAdapter;
import com.yang.bruce.mumuxi.base.BaseActivity;
import com.yang.bruce.mumuxi.bean.Article;
import com.yang.bruce.mumuxi.net.NetWorkBean;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 16:57
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class ZhuanLanDetailActivity extends BaseActivity {
    private static final String TAG = "ZhuanLanDetailActivity";
    private String slug, title, name, articeSlug, titleImage, profileUrl;
    private List<Article> articleList;

    private EasyRecyclerView mRecyclerView;
    private Toolbar toolbar;
    private ArticleAdapter articleAdapter;
    private Handler handler = new Handler();

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 布局加载之前 设置动画
        beforeInitVis();
        setContentView(R.layout.zhuanlan_detail_layout);
        getDatas();
        initViews();
    }

    // 设置过渡动画
    public void beforeInitVis() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
    }

    public void getDatas() {
        Intent intent = getIntent();
        slug = intent.getStringExtra("slug"); //专题作者
        title = intent.getStringExtra("title");
        profileUrl = intent.getStringExtra("profile_url");
    }

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 增加系统自带的返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 设置 自己的返回键图片(这里用的矢量图)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.vector_arrow_white_24dp);
        getSupportActionBar().setTitle(slug);

        articleList = new ArrayList<>();
        mRecyclerView = (EasyRecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        articleAdapter = new ArticleAdapter(getApplicationContext());
        doWithArticle(articleAdapter);

        mRecyclerView.setRefreshListener(this);
        onRefresh();
    }

    // here can intent to ArticleDetailActivity  (设置刷新效果,以及跳转ArticleDetailActivity)
    private void doWithArticle(final RecyclerArrayAdapter<Article> adapter) {
        mRecyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.load_more_layout, this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ZhuanLanDetailActivity.this, ArticleDetailActivity.class);
                intent.putExtra("slug", adapter.getItem(position).getArticleSlug());
                intent.putExtra("image", adapter.getItem(position).getTitleImage());
                intent.putExtra("title", adapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
    }

    /**
     * Get Article List with the zhuanlan_detial
     *
     * @param slug   作者
     * @param limit  数据条数
     * @param offest 页码
     */
    private void getArticleData(String slug, int limit, int offest) {
        NetWorkBean.getZhuanLanDetailApi()
                .getArticle(slug, limit, offest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Article>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        Log.e(TAG, "onNext");
                        //处理数据
                        progressData(articles);
                        articleAdapter.addAll(articleList);
                    }
                });
    }

    /**
     * // process data so that can be posted to ArticleAdapter
     *
     * @param articles articles
     */
    private List<Article> progressData(List<Article> articles) {
        for (Article article : articles) {
            String title = article.getTitle();  //标题
            String content = article.getContent(); //内容
            titleImage = article.getTitleImage(); //标题图片
            String summary = article.getSummary(); // 概要
            int commentsCount = article.getCommentsCount(); //评论数
            int likesCount = article.getLikeCount(); //喜欢
            articeSlug = article.getArticleSlug(); //文章作者

            // 重新组装 Article对象
            Article articleItem = new Article(title, titleImage, summary, content, profileUrl
                    , commentsCount, likesCount, slug, articeSlug);
            articleList.add(articleItem);
        }
        return articleList;
    }

    @Override
    public void onRefresh() {
        articleAdapter.clear();
        getArticleData(slug, 5, 0);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getArticleData(slug, page * 5, 0);
                page++;
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.zhuanlan_detail_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 分享专栏
            case R.id.action_share:
                Toast.makeText(this, "coming soon~~", Toast.LENGTH_SHORT).show();
                break;
            // toolbar返回键
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
