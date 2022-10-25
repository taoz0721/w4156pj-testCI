package com.insomnia_studio.w4156pj.service;


import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.repository.ClientRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;

import com.insomnia_studio.w4156pj.service.PostServiceImpl;
import org.hibernate.service.spi.InjectService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    @Mock
    private PostEntityRepository postrepository;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private PostServiceImpl postserviceimpl;

    @DisplayName("JUnit Test for addPost")
    @Test
    public void testAddPost() throws Exception {
        //setup
        ClientEntity client = new ClientEntity(UUID.randomUUID(), "a");
        Post post =new Post(null, client, new HashSet<>(Arrays.asList("tag1")),"Title1","Content1",null,null );
        PostEntity postEntity = new PostEntity();
        postEntity.setPostId(UUID.randomUUID());
        postEntity.setPostCreatedTime(new Date());
        postEntity.setPostUpdatedTime(new Date());

        // when
        when(postrepository.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);
        when(clientRepository.existsByClientId(Mockito.any())).thenReturn(true);
        //test
        Post addedpost = postserviceimpl.addPost(post);
        //assertion
        assertNotNull(addedpost);
        assertEquals(postEntity.getPostId(),addedpost.getPostId());
        assertEquals(postEntity.getPostCreatedTime(),addedpost.getPostCreatedTime());
        assertEquals(postEntity.getPostUpdatedTime(),addedpost.getPostUpdatedTime());
        assertEquals(post.getTags(),addedpost.getTags());
        assertEquals(post.getTitle(),addedpost.getTitle());
        assertEquals(post.getContent(),addedpost.getContent());
        System.out.println(addedpost);
    }

    @DisplayName("JUnit Test for getPostById")
    @Test
    public void testgetPostById() throws Exception {
        //given, statement
        UUID id=UUID.randomUUID();
        Set<String> tag = (new HashSet<>(Arrays.asList("tag2")));
        String title = "Title2";
        String content = "Content2";
        Date creattime = new Date();
        Date updatetime = new Date();
        PostEntity postEntity = new PostEntity(id,null,title,content,tag,null,creattime,updatetime);
        Optional<PostEntity> optionalPostEntity = Optional.of(postEntity);

        // when
        when(postrepository.findByPostId(Mockito.any(UUID.class))).thenReturn(optionalPostEntity);
        //test
        Optional<Post> foundpost = postserviceimpl.getPostById(id);

        //assertion
        assertTrue(foundpost.isPresent());
        assertEquals(optionalPostEntity.get().getPostId(),foundpost.get().getPostId());
        assertEquals(optionalPostEntity.get().getTitle(),foundpost.get().getTitle());
        assertEquals(optionalPostEntity.get().getContent(),foundpost.get().getContent());
        assertEquals(optionalPostEntity.get().getPostCreatedTime(),foundpost.get().getPostCreatedTime());
        assertEquals(optionalPostEntity.get().getPostUpdatedTime(),foundpost.get().getPostUpdatedTime());
    }

    @DisplayName("JUnit Test for updatePostById")
    @Test
    public void testUpdatePostById() throws Exception {
        //setup
        UUID postid = UUID.randomUUID();
        Post post =new Post(null,new HashSet<>(Arrays.asList("tag1")),"Title1","Content1",null,null );
        PostEntity postEntity = new PostEntity();
        postEntity.setPostId(postid);
        postEntity.setPostCreatedTime(new Date());
        postEntity.setPostUpdatedTime(new Date());
        Optional<PostEntity> optionalPostEntity = Optional.of(postEntity);
        PostEntity update = postEntity;
        update.setTags(post.getTags());
        update.setTitle(post.getTitle());
        update.setContent(post.getContent());
        // when
        when(postrepository.findByPostId(Mockito.any(UUID.class))).thenReturn(optionalPostEntity);
        when(postrepository.save(Mockito.any(PostEntity.class))).thenReturn(update);
        //test
        Optional<Post> updatedpost = postserviceimpl.updatePostById(postid,post);

        //assertion
        assertTrue(updatedpost.isPresent());
        assertEquals(update.getPostId(),updatedpost.get().getPostId());
        assertEquals(update.getTags(),updatedpost.get().getTags());
        assertEquals(update.getTitle(),updatedpost.get().getTitle());
        assertEquals(update.getPostCreatedTime(),updatedpost.get().getPostCreatedTime());
        assertEquals(update.getPostUpdatedTime(),updatedpost.get().getPostUpdatedTime());
        System.out.println(updatedpost);
    }
    
    @DisplayName("JUnit Test for deletePostById")
    @Test
    public void testDeletePostById() throws Exception {
        //setup

        UUID id=UUID.randomUUID();
        Set<String> tag = (new HashSet<>(Arrays.asList("tag2")));
        String title = "Title2";
        String content = "Content2";
        Date createtime = new Date();
        Date updatetime = new Date();
        PostEntity postEntity = new PostEntity(id,null,title,content,tag,null,createtime,updatetime);
        Post post = new Post();

        // when true
        when(postrepository.deletePostEntityByPostId(id)).thenReturn(1);

        Boolean testdeletetrue = postserviceimpl.deletePostById(id);
        assertTrue(testdeletetrue);
        // when false
        when(postrepository.deletePostEntityByPostId(id)).thenReturn(0);

        Boolean testdeletefalse = postserviceimpl.deletePostById(id);
        assertFalse(testdeletefalse);
    }
}

