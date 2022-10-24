package com.insomnia_studio.w4156pj.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;


    @Lob
    private String title;
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_tags")
    private Set<String> tags = new HashSet<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonBackReference
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
}
