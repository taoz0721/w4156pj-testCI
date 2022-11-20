package com.insomnia_studio.w4156pj.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.insomnia_studio.w4156pj.model.Post;

import java.util.UUID;

public interface PostService {
    Post addPost(Post post) throws Exception;

    Post getPostById(UUID postId, Post post) throws Exception;

    Post updatePostById(UUID postId, Post post) throws Exception;

    Boolean deletePostById(UUID postId, Post post) throws Exception;

}