package com.yang.bruce.mumuxi.bean;

import java.util.List;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-28 15:29
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class ArticleDetail {
    private String titleImage;
    private LinksBean links;
    private String content;
    private int slug;
    private String publishedTime;
    private String title;
    private String summary;
    private int commentsCount;
    private int likesCount;
    private List<?> reviewers;
    private List<?> topics;

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSlug() {
        return slug;
    }

    public void setSlug(int slug) {
        this.slug = slug;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<?> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<?> reviewers) {
        this.reviewers = reviewers;
    }

    public List<?> getTopics() {
        return topics;
    }

    public void setTopics(List<?> topics) {
        this.topics = topics;
    }

    public static class LinksBean {
        private String comments;

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }
}
