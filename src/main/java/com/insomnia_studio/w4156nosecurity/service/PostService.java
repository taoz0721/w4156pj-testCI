package com.insomnia_studio.w4156nosecurity.service;

import com.insomnia_studio.w4156nosecurity.model.Post;

import java.util.Optional;
import java.util.UUID;

public interface PostService {
    Post addPost(Post post) throws Exception;

    Optional<Post> getPostById(UUID postId);

    Optional<Post> updatePostById(UUID postId, Post post) throws Exception;

    Boolean deletePostById(UUID postId);

}