package com.insomnia_studio.w4156pj.model;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private UUID commentId;

    private UUID userId;

    private UUID postId;

    private UUID clientId;

    private Integer LikesNum;

    private Integer dislikesNum;

    private String content;

    private Date commentCreatedTime;

    private Date commentUpdatedTime;

    public Comment(UUID userId, UUID postId, Integer likesNum, Integer dislikesNum, String content) {
        this.userId = userId;
        this.postId = postId;
        LikesNum = likesNum;
        this.dislikesNum = dislikesNum;
        content = content;
    }

}
