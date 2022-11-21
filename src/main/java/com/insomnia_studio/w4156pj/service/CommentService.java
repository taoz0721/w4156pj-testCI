package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.Comment;

import java.util.UUID;

public interface CommentService {

    Comment addComment(Comment comment, UUID postId) throws Exception;

    Comment getCommentById(UUID commentId, Comment comment) throws Exception;

    Comment updateCommentById(UUID commentId, Comment comment) throws Exception;

    Boolean deleteCommentById(UUID commentId, Comment comment) throws Exception;
}
