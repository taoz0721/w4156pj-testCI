package com.insomnia_studio.w4156pj.model;

import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

public class Comment {
    private UUID commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private String userId;

    @ManyToOne(fetch = FetchType.EAGER)
    private PostEntity post;

    private UUID postId;

    private String parentId;

    private Integer LikesNum;

    private Integer dislikesNum;

    @Lob
    private String Content;
}
