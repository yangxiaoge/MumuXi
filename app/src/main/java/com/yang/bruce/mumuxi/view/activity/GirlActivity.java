package com.yang.bruce.mumuxi.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.base.BaseActivity;
import com.yang.bruce.mumuxi.util.ImgSaveUtil;
import com.yang.bruce.mumuxi.util.ImgShareUtil;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 19:25
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class GirlActivity extends BaseActivity {
    public static final String TAG = "GirlActivity";
    private Toolbar toolbar;
    private ImageView mImageView;
    private String desc;
    private String url;
    private Bitmap mBitmap; //妹子高清无码图
    //PhotoView是一个开源的图片查看库
    private PhotoViewAttacher attatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitVis();
        setContentView(R.layout.girl_detail_activity_layout);
        getDatas();
        initViews();
    }

    public void beforeInitVis() {
        // 设置过渡动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
    }

    // 获取GirlFrament传来的数据
    public void getDatas() {

        Intent intent = getIntent();
        desc = intent.getStringExtra("desc");
        url = intent.getStringExtra("url");

    }

    // initial views
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 增加系统自带的返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 设置 自己的返回键图片(这里用的矢量图)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.vector_arrow_white_24dp);
        getSupportActionBar().setTitle(desc);

        mImageView = (ImageView) findViewById(R.id.image_meizhi);
        attatcher = new PhotoViewAttacher(mImageView);

        // Glide
        Glide.with(this)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() { // 这样写有什么好处？
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mImageView.setImageBitmap(resource);
                        attatcher.update();
                        mBitmap = resource;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.girl_detail_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 保存图片
            case R.id.action_save:
                ImgSaveUtil.saveImage(this, url, mBitmap, mImageView, "保存妹子");
                break;
            // 分享图片
            case R.id.action_share:
                ImgShareUtil.shareImage(this, ImgSaveUtil.saveImage(this, url, mBitmap, mImageView, "分享妹子"));
                break;
            // toolbar返回键
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
