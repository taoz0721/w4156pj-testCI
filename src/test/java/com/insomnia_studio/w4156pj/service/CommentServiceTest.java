package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.ClientRepository;
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
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        commentRepository = Mockito.mock(CommentRepository.class);
        postEntityRepository = Mockito.mock(PostEntityRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);

        commentService.setCommentRepository(commentRepository);
        commentService.setPostEntityRepository(postEntityRepository);
        commentService.setClientRepository(clientRepository);
    }

    @Test
    void testCreateCommentValidPost() {
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
       //user.setUserId("123");
        //ClientEntity client = new ClientEntity(UUID.randomUUID(), "c");
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Comment comment = new Comment(UUID.randomUUID(), userId.toString(), postId, clientId, 0, 0, "comment1");
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(comment, commentEntity);

        Mockito.when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        Mockito.when(postEntityRepository.existsByPostId(postId)).thenReturn(true);
        Mockito.when(clientRepository.existsByClientId(Mockito.any())).thenReturn(true);

        Comment addedComment = commentService.addComment(comment, postId);
        Assertions.assertEquals(comment, addedComment);
    }

    @Test
    void testCreateCommentInvalidPost() {
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
        //user.setUserId("123");
        //ClientEntity client = new ClientEntity(UUID.randomUUID(), "c");
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Comment comment = new Comment(UUID.randomUUID(), userId.toString(), postId, clientId, 0, 0, "comment2");
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(comment, commentEntity);

        Mockito.when(postEntityRepository.existsByPostId(postId)).thenReturn(false);
        Mockito.when(clientRepository.existsByClientId(Mockito.any())).thenReturn(true);

        // TO BE FIXED: Should return error message after implementing ResponseDTO
        Comment addedComment = commentService.addComment(comment, postId);
        Assertions.assertEquals(null, addedComment);
    }

    @Test
    void testCreateCommentValidClient() {
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
        //user.setUserId("123");
        //ClientEntity client = new ClientEntity(UUID.randomUUID(), "c");
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Comment comment = new Comment(UUID.randomUUID(), userId.toString(), postId, clientId, 0, 0, "comment3");
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(comment, commentEntity);

        Mockito.when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        Mockito.when(postEntityRepository.existsByPostId(Mockito.any())).thenReturn(true);
        Mockito.when(clientRepository.existsByClientId(clientId)).thenReturn(true);

        Comment addedComment = commentService.addComment(comment, postId);
        Assertions.assertEquals(comment, addedComment);
    }

    @Test
    void testCreateCommentInvalidClient() {
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
        //user.setUserId("123");
        //ClientEntity client = new ClientEntity(UUID.randomUUID(), "c");
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Comment comment = new Comment(UUID.randomUUID(), userId.toString(), postId, clientId, 0, 0, "comment4");
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(comment, commentEntity);

        Mockito.when(postEntityRepository.existsByPostId(Mockito.any())).thenReturn(true);
        Mockito.when(clientRepository.existsByClientId(Mockito.any())).thenReturn(false);

        // TO BE FIXED: Should return error message after implementing ResponseDTO
        Comment addedComment = commentService.addComment(comment, postId);
        Assertions.assertEquals(null, addedComment);
    }

    @Test
    void testGetCommentByIdValidId() {
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
        //user.setUserId("123");
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(UUID.randomUUID());


        Mockito.when(commentRepository.findByCommentId(comment.getCommentId())).thenReturn(Optional.of(comment));

        Comment expectedComment = new Comment();
        BeanUtils.copyProperties(comment, expectedComment);

        Comment foundComment = commentService.getCommentById(comment.getCommentId());
        Assertions.assertEquals(expectedComment, foundComment);
    }

    @Test
    void testGetCommentByIdInvalidId() {
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
        //user.setUserId("123");
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(UUID.randomUUID());


        Mockito.when(commentRepository.findByCommentId(comment.getCommentId())).thenReturn(Optional.empty());

        // TO BE FIXED: Should return error message after implementing ResponseDTO
        Comment foundComment = commentService.getCommentById(comment.getCommentId());
        Assertions.assertEquals(null, foundComment);
    }
}
