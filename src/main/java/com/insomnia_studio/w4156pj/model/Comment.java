package com.insomnia_studio.w4156pj.model;

import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
public class Comment {
    private UUID commentId;
    private UUID userId;
    private UUID postId;
    private int dislikeNum;
    private int likeNum;
    private String content;
}
