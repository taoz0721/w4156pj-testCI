package com.insomnia_studio.w4156pj.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID userId;

    private String firstName;

    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private ClientEntity client;

    @ElementCollection
    @CollectionTable(name = "user_followers")
    private Set<UUID> followers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_followedBy")
    private Set<UUID> followedBy = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreatedTime;

    @PrePersist
    protected void onCreate() {
        userCreatedTime = new Date();
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PostEntity> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    public void addPost(PostEntity postEntity) {
        posts.add(postEntity);
        postEntity.setUser(this);
    }

    public void removePost(PostEntity postEntity) {
        posts.remove(postEntity);
        postEntity.setUser(null);
    }

    public void addFollower(UUID followId) {
        followers.add(followId);
    }

    public void addFollowedBy(UUID userId) {
        followedBy.add(userId);
    }

    public void removeFollower(UUID followId) {
        followers.remove(followId);
    }

    public void removeFollowBy(UUID userId) {
        followedBy.remove(userId);
    }
}
