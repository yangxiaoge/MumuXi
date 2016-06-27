package com.yang.bruce.mumuxi.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.yang.bruce.mumuxi.bean.ZhuanLan;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 15:42
 * Version: 1.0
 * TaskId:
 * Description: ZhuanLanAdapter
 */

public class ZhuanLanAdapter extends RecyclerArrayAdapter<ZhuanLan> {


    public ZhuanLanAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhuanLanViewHolder(parent);
    }
}
