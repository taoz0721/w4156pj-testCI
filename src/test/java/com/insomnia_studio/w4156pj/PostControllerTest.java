package com.insomnia_studio.w4156pj;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.service.PostService;

import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostControllerTest.class)

public class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddPost() throws Exception {
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
    }

}
