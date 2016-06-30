package com.yang.bruce.mumuxi.view.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.base.BaseActivity;
import com.yang.bruce.mumuxi.view.fragment.GirlFragment;
import com.yang.bruce.mumuxi.view.fragment.ZhiHuFragment;

import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CoordinatorLayout collapsingToolbarLayout;

    private List<Fragment> fragments;
    private String[] titles = {"专栏", "妹纸"};

    private long exitTime = 0; // 返回键 退出时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        switchFragment();

        // 判断是否有网
        isNetWorkOk(collapsingToolbarLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // initial initView
    public void initViews() {
        collapsingToolbarLayout = (CoordinatorLayout) findViewById(R.id.collapsing_toolbar);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setSupportActionBar(toolbar);

        //侧滑栏 以及 左上角图标
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.rootview);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // switch fragment
    private void switchFragment() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ZhiHuFragment();
                    case 1:
                        return new GirlFragment();
                    default:
                        return new ZhiHuFragment();
                }
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            //TabLayout titile
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return titles[0];
                    case 1:
                        return titles[1];
                    default:
                        return titles[0];
                }
            }
        });

        // tablayout与viewpager绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }


    /**
     *  返回键 (2秒内退出)
     * @param keyCode 返回键code
     * @param event keyEvent
     * @return true
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true; // 不要忘记 return true
        }

        return super.onKeyDown(keyCode, event);
    }
}
