package com.yang.bruce.mumuxi.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.bean.Item;
import com.yang.bruce.mumuxi.util.DateUtils;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 19:19
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class GirlViewHolder extends BaseViewHolder<Item> {
    private ImageView imageView;
    private TextView publishDate;

    public GirlViewHolder(ViewGroup parent) {
        super(parent, R.layout.girl_item);
        imageView = $(R.id.image);
        publishDate = $(R.id.publishDate);
    }

    @Override
    public void setData(Item data) {
        super.setData(data);
        Log.e("GirlViewHolder", data.imageUrl);

        Glide.with(getContext())
                .load(data.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

        // 发布日期
        publishDate.setText(DateUtils.date2String(data.publishedAt.getTime(),"yyyy/MM/dd"));
    }
}
