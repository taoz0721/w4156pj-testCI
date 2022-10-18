package com.insomnia_studio.w4156nosecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @JoinColumn(name = "user_id")
    private UserEntity user;



    @Lob
    private String title;
    private String content;

    @ElementCollection
    private List<String> tags;

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
