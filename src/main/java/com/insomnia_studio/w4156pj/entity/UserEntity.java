package com.insomnia_studio.w4156pj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String userId;

    private String firstName;

    private String lastName;

    @ElementCollection
    @CollectionTable(name = "user_followers")
    private Set<String> followers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_followedBy")
    private Set<String> followedBy = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreatedTime;

    @PrePersist
    protected void onCreate() {
        userCreatedTime = new Date();
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostEntity> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CommentEntity> comments = new HashSet<>();

    public void addPost(PostEntity postEntity) {
        posts.add(postEntity);
        postEntity.setUser(this);
    }

    public void removePost(PostEntity postEntity) {
        posts.remove(postEntity);
        postEntity.setUser(null);
    }

    public void addFollower(String followId) {
        followers.add(followId);
    }

    public void addFollowedBy(String userId) {
        followedBy.add(userId);
    }

    public void removeFollower(String followId) {
        followers.remove(followId);
    }

    public void removeFollowBy(String userId) {
        followedBy.remove(userId);
    }
}
