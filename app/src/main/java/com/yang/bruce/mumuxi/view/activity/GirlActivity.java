package com.yang.bruce.mumuxi.view.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.base.BaseActivity;
import com.yang.bruce.mumuxi.util.ImgSaveUtil;
import com.yang.bruce.mumuxi.util.ImgShareUtil;
import com.yang.bruce.mumuxi.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
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
    private List<String> urls; //当前加载的所有的妹子url
    private int code;
    // 当前页数
    private int page;
    private Bitmap mBitmap; //妹子高清无码图
    //PhotoView是一个开源的图片查看库
    private PhotoViewAttacher attatcher;
    @Bind(R.id.very_image_viewpager)
    HackyViewPager viewPager;
    @Bind(R.id.very_image_viewpager_text)
    TextView indexText;
    @Bind(R.id.photo_save)
    ImageView saveImg;
    @Bind(R.id.photo_share)
    ImageView shareImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        beforeInitVis();
        setContentView(R.layout.girl_detail_activity_layout);
        ButterKnife.bind(this);
        getDatas();
        initViews();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void beforeInitVis() {
        // 设置过渡动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
    }

    // 获取GirlFrament传来的数据
    public void getDatas() {
        urls = new ArrayList<>();

        Intent intent = getIntent();
        desc = intent.getStringExtra("desc");
        url = intent.getStringExtra("url");
        urls = intent.getStringArrayListExtra("urls");
        code = intent.getIntExtra("code", 0);

        //Toast.makeText(this,"urls = "+urls.size()+" code = "+code,Toast.LENGTH_SHORT).show();
    }

    // initial views
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setCurrentItem(code);
        page = code;
        viewPager.setEnabled(false);
        viewPager.setOffscreenPageLimit(3); //预加载3个
        indexText.setText((code + 1) + " / " + urls.size()); //viewPager 下部图片 页码/总数

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 本方法主要监听viewpager滑动的时候的操作
             */
            @Override
            public void onPageSelected(int position) {
                indexText.setText((position + 1) + " / " + urls.size());
                page = position;
                url = urls.get(position); //当前图片的url路径
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //保存妹子图片
        saveImg.setOnClickListener(
                (View view) -> {
                    //首先动态授权
                    RxPermissions rxPermissions = new RxPermissions(GirlActivity.this);
                    rxPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(granted -> {
                                if (granted)
                                    Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            ImgSaveUtil.saveImage(GirlActivity.this, url, resource, saveImg, "保存妹子");
                                        }
                                    });
                                else
                                    Toast.makeText(GirlActivity.this, "没有读写内存的权限", Toast.LENGTH_SHORT).show();
                            });
                }
        );

        //分享妹子图片
        shareImg.setOnClickListener(
                (View view) -> {
                    //首先动态授权
                    RxPermissions rxPermissions = new RxPermissions(GirlActivity.this);
                    rxPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(granted -> {
                                if (granted)
                                    Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            ImgSaveUtil.shareImage(GirlActivity.this, resource);
                                        }
                                    });
                                else
                                    Toast.makeText(GirlActivity.this, "没有读写内存的权限", Toast.LENGTH_SHORT).show();
                            });
                }
        );
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

    /**
     * ViewPager的适配器
     */
    class ViewPagerAdapter extends PagerAdapter {
        LayoutInflater inflater;

        public ViewPagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.viewpager_very_image, container, false);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.zoom_image_view);
            final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);
            String url_image = urls.get(position); //图片的url路径

            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(false);

            // Glide
            Glide.with(GirlActivity.this)
                    .load(url_image)
                    .crossFade(700)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Toast.makeText(getApplicationContext(), "资源加载异常", Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.GONE);
                            return false;
                        }

                        //这个用于监听图片是否加载完成
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            spinner.setVisibility(View.GONE);

                            /**这里应该是加载成功后图片的高*/
                            int height = photoView.getHeight();

                            int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                            if (height > wHeight) {
                                //photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                //photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    }).into(photoView);

            container.addView(view, 0);
            return view;
        }

        @Override
        public int getCount() {
            if (urls == null || urls.size() == 0) {
                return 0;
            }
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //以下是 点击事件，点击图片关闭 Activity
    private Timer mDialogTimer = null;
    private TimerTask mDialogTask = null;
    public static Timer mIsFinishTimer = null;
    public static TimerTask mIsFinshTimerTask = null;
    private boolean touchCount = true;
    private PointF oldPoint = new PointF();
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("down-time:",
                String.valueOf(ev.getEventTime() - ev.getDownTime()));
        final PointF newPoint = new PointF(ev.getX(), ev.getY());
        float moveX = Math.abs(newPoint.x - oldPoint.x);
        float moveY = Math.abs(newPoint.y - oldPoint.y);
        if (ev.getPointerCount() > 1 || moveX > 10 || moveY > 10) {
            touchCount = false;
            if (mIsFinishTimer != null && mIsFinshTimerTask != null) {
                mIsFinishTimer.cancel();
                mIsFinishTimer = null;
                mIsFinshTimerTask.cancel();
                mIsFinshTimerTask = null;
            }
            if (mDialogTask != null && mDialogTimer != null) {
                mDialogTask.cancel();
                mDialogTask = null;
                mDialogTimer.cancel();
                mDialogTimer = null;
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mDialogTask != null && mDialogTimer != null) {
                mDialogTask.cancel();
                mDialogTask = null;
                mDialogTimer.cancel();
                mDialogTimer = null;
            }
            if (!(moveX > 10 || moveY > 10)) {
                if (mIsFinishTimer == null && touchCount) {
                    mIsFinishTimer = new Timer();
                    mIsFinshTimerTask = new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    mIsFinishTimer.schedule(mIsFinshTimerTask, 400);
                }
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchCount = true;
            mDialogTimer = new Timer();
            mDialogTask = new TimerTask() {
                @Override
                public void run() {
                }
            };
            mDialogTimer.schedule(mDialogTask, 500);
            // downX = ev.getX();
            oldPoint.set(ev.getX(), ev.getY());
        }
        return super.dispatchTouchEvent(ev);
    }
}
