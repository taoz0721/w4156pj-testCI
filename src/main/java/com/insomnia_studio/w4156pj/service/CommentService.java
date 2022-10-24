package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.Comment;

import java.util.Optional;
import java.util.UUID;

public interface CommentService {

    Optional<Comment> addComment(Comment comment);

    Optional<Comment> getCommentById(UUID commentId);

    Boolean deleteCommentById(UUID commentId);
}
