package com.yang.bruce.mumuxi.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.base.BaseActivity;
import com.yang.bruce.mumuxi.bean.ArticleDetail;
import com.yang.bruce.mumuxi.net.NetWorkBean;
import com.yang.bruce.mumuxi.util.DeviceUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 15:12
 * Version: 1.0
 * TaskId:
 * Description: 文章 detail
 */
public class ArticleDetailActivity extends BaseActivity {
    public static final String TAG = "ArticleDetailActivity";
    private int articleSlug; //文章号 例如 slug : 20765595
    private String author, titleImage, articleTitle, articleLink;
    private ImageView mImageView;
    private WebView mWebView; // 承载html
    private LinearLayout loading;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        getDatas();
        initViews();
    }

    /**
     * Get data from ZhanlanDetailActivity
     */
    private void getDatas() {
        Intent intent = getIntent();
        author = intent.getStringExtra("author");
        articleSlug = Integer.parseInt(intent.getStringExtra("slug"));
        titleImage = intent.getStringExtra("image");
        articleTitle = intent.getStringExtra("title");
        // 拼接原文链接  https://zhuanlan.zhihu.com/p/21258353?refer=nekocode
        articleLink = NetWorkBean.ArticleDetail_Share + articleSlug + "?refer=" + author;

        getArticleDetailDatas(articleSlug);
    }

    private void getArticleDetailDatas(int articleSlug) {
        NetWorkBean.getArticleDetailApi()
                .getArticleDetail(articleSlug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleDetail>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                        loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ArticleDetail articleDetail) {
                        Log.e(TAG, "onNext --- 文章标题:" + articleDetail.getTitle());
                        progressData(articleDetail); // 加工数据
                    }
                });
    }

    /**
     * 加工数据
     * <p>
     * conten节点数据是 html的,要处理下
     * Return data of Content is HTML ,Process here and show with webView
     *
     * master.css可以不要(反正不影响)
     *
     * @param articleDetail 文章对象
     */
    private void progressData(ArticleDetail articleDetail) {
        String content = articleDetail.getContent();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";
        // 拼装成html
        String mHtml = "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + content
                + "\n</body>\n</html>";

        mWebView.loadData(mHtml, "text/html; charset=utf-8", null);
    }

    /**
     * InitViews
     */
    public void initViews() {
        mImageView = (ImageView) findViewById(R.id.backdrop);
        mWebView = (WebView) findViewById(R.id.webview);
        loading = (LinearLayout) findViewById(R.id.load_linear);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示系统actionbar自带返回键

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent)); //设置收缩后Toolbar上字体的颜色
        collapsingToolbarLayout.setTitle(articleTitle);// 设置article detail的 标题
        // 设置article detail的 标题背景图
        Glide.with(this)
                .load(titleImage)
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.mipmap.zz)//占位图
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

        // 不要也行!!!
        webViewSetting();
    }

    /**
     * webview加载 html 需要设置一些 js 等
     */
    private void webViewSetting() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_post) {
            Toast.makeText(this, "评论", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.copy_link) {
            //Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
            //startActivity(intent);

            DeviceUtils.copy2Clipboard(this, articleLink);
            Snackbar.make(mWebView, "链接复制成功",
                    Snackbar.LENGTH_LONG).show();

        } else if (id == R.id.open_browser) {
            DeviceUtils.copy2Clipboard(this, articleLink);
            //浏览器打开文章
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink));
            startActivity(intent);

        } else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
