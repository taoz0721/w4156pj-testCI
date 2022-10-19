package com.insomnia_studio.w4156pj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private List<String> followers;

    @ElementCollection
    private List<String> followedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreatedTime;

    @PrePersist
    protected void onCreate() {
        userCreatedTime = new Date();
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PostEntity> posts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<CommentEntity> comments;
}
