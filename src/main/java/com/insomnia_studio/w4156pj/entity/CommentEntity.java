package com.insomnia_studio.w4156pj.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long postId;
    private long userId;
    private long parentId;
    private long dislikeNum;
    private long likeNum;
    private String content;




}
