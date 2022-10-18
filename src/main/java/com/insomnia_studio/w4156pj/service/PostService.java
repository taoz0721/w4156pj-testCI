package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.Post;

import java.util.Optional;
import java.util.UUID;

public interface PostService {
    Post addPost(Post post) throws Exception;

    Optional<Post> getPostById(UUID postId);

    Optional<Post> updatePostById(UUID postId, Post post) throws Exception;

    Boolean deletePostById(UUID postId);

}