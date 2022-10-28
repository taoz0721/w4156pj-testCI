package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.Post;

import java.util.Optional;
import java.util.UUID;

public interface PostService {
    Post addPost(Post post) throws Exception;

    Post getPostById(UUID postId);

    Post updatePostById(UUID postId, Post post) throws Exception;

    Boolean deletePostById(UUID postId);

}