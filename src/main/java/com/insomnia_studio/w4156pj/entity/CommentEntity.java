package com.insomnia_studio.w4156pj.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class CommentEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="clientId")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CommentEntity> childComments = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentCreatedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentUpdatedTime;

    @PrePersist
    protected void onCreate() {
        commentCreatedTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        commentUpdatedTime = new Date();
    }

    private Integer LikesNum;

    private Integer dislikesNum;

    @Lob
    private String content;
}
