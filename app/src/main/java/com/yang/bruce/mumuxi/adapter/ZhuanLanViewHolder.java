package com.yang.bruce.mumuxi.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.bean.ZhuanLan;
import com.yang.bruce.mumuxi.net.NetWorkBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-27 15:42
 * Version: 1.0
 * TaskId:
 * Description: ZhuanLanViewHolder
 */
public class ZhuanLanViewHolder extends BaseViewHolder<ZhuanLan> {
    private CircleImageView circleImageView;
    private TextView tvName;
    private TextView tvIntro;
    private TextView tvArticleCount;
    private TextView tvFansCount;

    public ZhuanLanViewHolder(ViewGroup parent) {
        super(parent, R.layout.zhuanlan_item);
        circleImageView = $(R.id.circle_image);
        tvName = $(R.id.name);
        tvArticleCount = $(R.id.tv_article_count);
        tvFansCount = $(R.id.tv_fans_count);
        tvIntro = $(R.id.intro);
        // Bold for textView ,可以直接在布局中设置属性  android:textStyle="bold"
        /*TextPaint tp = tvIntro.getPaint();
        tp.setFakeBoldText(true);*/
    }

    @Override
    public void setData(ZhuanLan data) {
        super.setData(data);
        Glide.with(getContext())
                // https://marktony.github.io/2016/05/14/%E7%9F%A5%E4%B9%8E%E4%B8%93%E6%A0%8FAPI%E5%88%86%E6%9E%90/
                // 在获取头像地址时，将id拼接到 ZhuanLanAvatar_BASE_API 中即可获取到url，例如：也可以不传入size参数，默认获取最大(large)。
                .load(NetWorkBean.ZhuanLanAvatar_BASE_API + data.getAvatar().getId() + "_m.jpg")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(circleImageView);

        tvName.setText(data.getName());
        tvIntro.setText(data.getDescription());
        tvFansCount.setText(data.getFollowersCount() + "人关注");
        tvArticleCount.setText(data.getPostsCount() + "篇文章");

    }
}
