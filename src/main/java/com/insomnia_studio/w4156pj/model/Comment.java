package com.insomnia_studio.w4156pj.model;

import lombok.Data;

@Data
public class Comment {
    private long id;
    private long postId;
    private long userId;
    private long parentId;
    private long dislikeNum;
    private long likeNum;
    private String content;
}
