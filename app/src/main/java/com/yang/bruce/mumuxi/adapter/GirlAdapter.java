package com.yang.bruce.mumuxi.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.yang.bruce.mumuxi.bean.Item;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 19:16
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class GirlAdapter extends RecyclerArrayAdapter<Item> {

    public GirlAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlViewHolder(parent);
    }
}
