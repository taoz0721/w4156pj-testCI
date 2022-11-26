package com.insomnia_studio.w4156pj.model;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private UUID postId;

    private UUID clientId;
    private Set<String> tags;
    private UUID userId;
    private String title;
    private String content;
    private Date postCreatedTime;
    private Date postUpdatedTime;

    // Used for the test in the first iteration

    public Post(UUID postId, Set<String> tags, UUID userID, UUID clientId,String title, String content, Date postCreatedTime, Date postUpdatedTime) {
        this.postId = postId;
        this.userId = userID;
        this.clientId = clientId;
        this.tags = tags;
        this.title = title;
        this.content = content;
        this.postCreatedTime = postCreatedTime;
        this.postUpdatedTime = postUpdatedTime;
    }

    public Post(UUID postId, UUID clientId, UUID userId, String title, String content) {
        this.postId = postId;
        this.clientId = clientId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post(UUID clientId, UUID userId, String title, String content) {
        this.clientId = clientId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
