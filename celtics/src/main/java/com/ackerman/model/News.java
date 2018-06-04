package com.ackerman.model;

import java.util.Date;

/**
 * @Author: Ackerman
 * @Description:　新闻的相关信息
 * @Date: Created in 下午5:00 18-6-4
 */
public class News {
    private int id;
    private int type;
    private int userId;
    private int likeCount;
    private int commentCount;
    private String title;
    private String link;
    private String imageLink;
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}

