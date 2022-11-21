package com.insomnia_studio.w4156pj.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Table(name = "post")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class PostEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private ClientEntity client;

    @Lob
    private String title;
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_tags")
    private Set<String> tags = new HashSet<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
//    @JsonBackReference(value="comment-post")
    private Set<CommentEntity> comments = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date postCreatedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postUpdatedTime;

    @PrePersist
    protected void onCreate() {
        postCreatedTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        postUpdatedTime = new Date();
    }

    // Used for the test in the first iteration
    public PostEntity(UUID postId, ClientEntity client, String title, String content, Set<String> tags, Set<CommentEntity> comments, Date postCreatedTime, Date postUpdatedTime) {
        this.postId = postId;
        this.client = client;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.comments = comments;
        this.postCreatedTime = postCreatedTime;
        this.postUpdatedTime = postUpdatedTime;
    }
}
