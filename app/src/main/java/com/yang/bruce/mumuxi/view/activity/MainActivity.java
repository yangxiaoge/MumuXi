package com.yang.bruce.mumuxi.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.base.BaseActivity;
import com.yang.bruce.mumuxi.util.NetWorkUtil;
import com.yang.bruce.mumuxi.view.fragment.GirlFragment;
import com.yang.bruce.mumuxi.view.fragment.ZhiHuFragment;

import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private String[] titles = {"专栏", "妹纸"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        switchFragment();
        isNetWorkOk();
    }

    // Judge network is ok
    private void isNetWorkOk() {
        if (!NetWorkUtil.isNetworkConnected(getApplicationContext()) && !NetWorkUtil.isWifiConnected(getApplication())) {
            SnackbarManager.show(
                    Snackbar.with(getApplicationContext()) // context
                            .text("网络未连接￣へ￣") // text to display
                            .actionLabel("重试?") // action button label
                            .actionListener(new ActionClickListener() { // action button's ActionClickListener
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    isNetWorkOk();
                                }
                            }), this);
        }
    }

    // initial initView
    public void initViews() {
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setSupportActionBar(toolbar);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
