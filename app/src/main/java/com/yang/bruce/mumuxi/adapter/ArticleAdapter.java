package com.yang.bruce.mumuxi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.yang.bruce.mumuxi.R;
import com.yang.bruce.mumuxi.bean.Article;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 14:50
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class ArticleAdapter extends RecyclerArrayAdapter<Article> {
    public ArticleAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        return new ArticleViewHolder(parent);
    }

    class ArticleViewHolder extends BaseViewHolder<Article> {
        private ImageView aticleBg;
        private CircleImageView circleImageView;
        private TextView tvLike, tvAuthor, tvComment, tvTitle;

        private static final String TAG = "ArticleViewHolder";

        public ArticleViewHolder(ViewGroup view) {
            super(view, R.layout.zhuanlanpost_item);

            aticleBg = $(R.id.iv_articlebg);
            circleImageView = $(R.id.profile_image);
            tvAuthor = $(R.id.tv_author);
            tvLike = $(R.id.tv_fans_count);
            tvComment = $(R.id.tv_comment_count);
            tvTitle = $(R.id.tv_title);
        }

        @Override
        public void setData(Article data) {
            super.setData(data);
            tvAuthor.setText(data.getSlug() + " " + "发布了文章");
            tvComment.setText(data.getCommentsCount() + " " + "评论");
            tvTitle.setText(data.getTitle());
            Log.e(TAG, data.getProfileUrl());
            // Because of zhihu url API logo is the default img
            //头像
            Glide.with(getContext())
                    .load(data.getProfileUrl())
                    .centerCrop()
                    .error(R.mipmap.zz)
                    .placeholder(R.mipmap.zz)//占位图
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(circleImageView);

            // 文章背景
            Glide.with(getContext())
                    .load(data.getTitleImage())
                    .centerCrop()
                    .error(R.drawable.error)
                    .placeholder(R.mipmap.zz)//占位图
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(aticleBg);

        }
    }
}
