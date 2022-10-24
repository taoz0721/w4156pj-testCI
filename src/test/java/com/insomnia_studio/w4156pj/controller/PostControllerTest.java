package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.controller.PostController;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;
import static org.mockito.Mockito.when;


@WebMvcTest(PostControllerTest.class)

public class PostControllerTest {

    @Mock
    private PostService postService;


    @InjectMocks
    private PostController postController;


    @DisplayName("Test for AddPost Method")
    @Test
    public void testAddPost() throws Exception {

        UUID id = UUID.randomUUID();
        Post post = new Post(id,new HashSet<>(Arrays.asList("tag1")),"title1","content1",new Date(), new Date());
        when(postService.addPost(post)).thenReturn(post);
        Post addedpost = postController.addPost(post);
        assertEquals(post,addedpost);

    }

    @DisplayName("Test for getpostbyid Method non-null return")
    @Test
    public void testgetPostByPostIdtrue(){
        //setup
        UUID id = UUID.randomUUID();
        Post post = new Post(id,new HashSet<>(Arrays.asList("tag1")),"title1","content1",new Date(), new Date());

        //when
        when(postService.getPostById(id)).thenReturn(Optional.of(post));

        //test
        Optional<Post> foundpost = postController.getPostByPostId(id);

        //assert
        assertTrue(foundpost.isPresent());
        assertEquals(post,foundpost.get());
    }

    @DisplayName("Test for getpostbyid Method null return")
    @Test
    public void testgetPostByPostIdfalse(){
        //setup
        UUID id = UUID.randomUUID();
        Post post = new Post(id,new HashSet<>(Arrays.asList("tag1")),"title1","content1",new Date(), new Date());

        //when
        when(postService.getPostById(id)).thenReturn(Optional.empty());

        //test
        Optional<Post> foundpost = postController.getPostByPostId(id);

        //assert
        assertTrue(foundpost.isEmpty());
    }

    @DisplayName("Test for updatepostbyid Method non-null return")
    @Test
    public void testupdatePostByPostIdtrue() throws Exception {
        //setup
        UUID id = UUID.randomUUID();
        Post post = new Post(null,new HashSet<>(Arrays.asList("tag1")),"title1","content1",null, null);
        Post postreturn = post;
        postreturn.setPostId(id);
        postreturn.setPostCreatedTime(new Date());
        postreturn.setPostUpdatedTime(new Date());
        //when
        when(postService.updatePostById(id,post)).thenReturn(Optional.of(postreturn));

        //test
        Optional<Post> updatedpost = postController.updatePostByPostId(id,post);

        //assert
        assertTrue(updatedpost.isPresent());
        assertEquals(postreturn,updatedpost.get());
    }

    @DisplayName("Test for updatepostbyid Method null return")
    @Test
    public void testupdatePostByPostIdfalse() throws Exception {
        //setup
        UUID id = UUID.randomUUID();
        Post post = new Post(null,new HashSet<>(Arrays.asList("tag1")),"title1","content1",null, null);
        Post postreturn = post;
        postreturn.setPostId(id);
        postreturn.setPostCreatedTime(new Date());
        postreturn.setPostUpdatedTime(new Date());
        //when
        when(postService.updatePostById(id,post)).thenReturn(Optional.empty());
        //test
        Optional<Post> updatedpost = postController.updatePostByPostId(id,post);
        //assert
        assertTrue(updatedpost.isEmpty());
    }

    @DisplayName("Test for deletePostByPostId method")
    @Test
    public void testdeletePostByPostId() {
        UUID id = UUID.randomUUID();
        Map<String,Boolean> responsetrue = new HashMap<>();
        responsetrue.put("Deleted", Boolean.TRUE);
        Map<String,Boolean> responsefalse = new HashMap<>();
        responsefalse.put("Deleted", Boolean.FALSE);
        when(postService.deletePostById(id)).thenReturn(Boolean.TRUE);

        Map<String,Boolean> respt = postController.deletePostByPostId(id);

        when(postService.deletePostById(id)).thenReturn(Boolean.FALSE);
        Map<String,Boolean> respf = postController.deletePostByPostId(id);

        assertEquals(responsetrue,respt);
        assertEquals(responsefalse,respf);

    }
    /*
    @Test
    public void testgetPostByID() throws Exception {
        UUID id = UUID.randomUUID();
        Post post = new Post(id,new HashSet<>(Arrays.asList("tag1")),"title1","content1",new Date(), new Date());
        when(postService.addPost(Mockito.any(Post.class))).thenReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                        .content(new ObjectMapper().writeValueAsString(post)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").value(new HashSet<>(Arrays.asList("tag1"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postCreatedTime").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.postUpdatedTime").exists())
                .andExpect(status().isCreated());
    }*/
}
