package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.CommentRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentServiceTest {
    // Class to be tested
    @Autowired
    private CommentServiceImpl commentService;

    // Dependencies
    private CommentRepository commentRepository;
    private PostEntityRepository postEntityRepository;

    @BeforeEach
    void setUp() {
        commentRepository = Mockito.mock(CommentRepository.class);
        postEntityRepository = Mockito.mock(PostEntityRepository.class);

        commentService.setCommentRepository(commentRepository);
        commentService.setPostEntityRepository(postEntityRepository);
    }

    @Test
    void testCreateCommentValidPost() {
        PostEntity post = new PostEntity();
        post.setTitle("a");
        post.setPostId(UUID.randomUUID());
        UserEntity user = new UserEntity();
        user.setUserId("123");
        Comment comment = new Comment(user, post, 0, 0, "aaaa");

        Mockito.when(postEntityRepository.existsByPostId(post.getPostId())).thenReturn(true);

        Optional<Comment> addedComment = commentService.addComment(comment);
        Assertions.assertEquals(Optional.of(comment), addedComment);
    }

    @Test
    void testCreateCommentInvalidPost() {
        PostEntity post = new PostEntity();
        post.setTitle("a");
        post.setPostId(UUID.randomUUID());
        UserEntity user = new UserEntity();
        user.setUserId("123");
        Comment comment = new Comment(user, post, 0, 0, "aaaa");

        Mockito.when(postEntityRepository.existsByPostId(post.getPostId())).thenReturn(false);

        // TO BE FIXED: Should return error message after implementing ResponseDTO
        Optional<Comment> addedComment = commentService.addComment(comment);
        Assertions.assertEquals(Optional.empty(), addedComment);
    }

    @Test
    void testGetCommentByIdValidId() {
        PostEntity post = new PostEntity();
        post.setTitle("a");
        post.setPostId(UUID.randomUUID());
        UserEntity user = new UserEntity();
        user.setUserId("123");
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(UUID.randomUUID());


        Mockito.when(commentRepository.findByCommentId(comment.getCommentId())).thenReturn(Optional.of(comment));

        Comment expectedComment = new Comment();
        BeanUtils.copyProperties(comment, expectedComment);

        Optional<Comment> foundComment = commentService.getCommentById(comment.getCommentId());
        Assertions.assertEquals(Optional.of(expectedComment), foundComment);
    }

    @Test
    void testGetCommentByIdInvalidId() {
        PostEntity post = new PostEntity();
        post.setTitle("a");
        post.setPostId(UUID.randomUUID());
        UserEntity user = new UserEntity();
        user.setUserId("123");
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(UUID.randomUUID());


        Mockito.when(commentRepository.findByCommentId(comment.getCommentId())).thenReturn(Optional.empty());

        // TO BE FIXED: Should return error message after implementing ResponseDTO
        Optional<Comment> foundComment = commentService.getCommentById(comment.getCommentId());
        Assertions.assertEquals(Optional.empty(), foundComment);
    }
}
