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
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private UUID commentId;

    private UserEntity user;

    private PostEntity post;

    private ClientEntity client;

    private Integer LikesNum;

    private Integer dislikesNum;

    private String Content;

    public Comment(UserEntity user, PostEntity post, Integer likesNum, Integer dislikesNum, String content) {
        this.user = user;
        this.post = post;
        LikesNum = likesNum;
        this.dislikesNum = dislikesNum;
        Content = content;
    }


}
