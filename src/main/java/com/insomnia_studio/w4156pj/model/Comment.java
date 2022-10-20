package com.insomnia_studio.w4156pj.model;

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


    private Integer LikesNum;

    private Integer dislikesNum;

    private String Content;
}
