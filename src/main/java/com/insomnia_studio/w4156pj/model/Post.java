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
    private ClientEntity client;
    private Set<String> tags;
//    private UUID userId;
    private String title;
    private String content;
    private Date postCreatedTime;
    private Date postUpdatedTime;

    // Used for the test in the first iteration
    public Post(UUID postId, Set<String> tags, String title, String content, Date postCreatedTime, Date postUpdatedTime) {
        this.postId = postId;
        this.tags = tags;
        this.title = title;
        this.content = content;
        this.postCreatedTime = postCreatedTime;
        this.postUpdatedTime = postUpdatedTime;
    }
}
