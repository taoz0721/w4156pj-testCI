package com.insomnia_studio.w4156pj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class CommentEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
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
