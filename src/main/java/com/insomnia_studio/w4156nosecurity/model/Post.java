package com.insomnia_studio.w4156nosecurity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private UUID postId;
    private List<String> tags;
    private UUID userId;
    private String title;
    private String content;
    private Date postCreatedTime;
    private Date postUpdatedTime;
}
