package com.yang.bruce.mumuxi.bean;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 14:42
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class Article {
    private String title;
    private String titleImage;
    private String summary;
    private String content;
    private String profileUrl;
    private int commentsCount;
    private int likeCount;
    private String slug;
    private String articleSlug;

    public Article(String title, String titleImage, String summary, String content, String profileUrl
            , int commentsCount, int likeCount, String slug, String articleSlug) {
        this.title = title;
        this.titleImage = titleImage;
        this.summary = summary;
        this.content = content;
        this.profileUrl = profileUrl;
        this.commentsCount = commentsCount;
        this.likeCount = likeCount;
        this.slug = slug;
        this.articleSlug = articleSlug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getArticleSlug() {
        return articleSlug;
    }

    public void setArticleSlug(String articleSlug) {
        this.articleSlug = articleSlug;
    }
}
