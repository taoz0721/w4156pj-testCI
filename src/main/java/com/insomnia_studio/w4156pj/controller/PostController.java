package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private PostEntityRepository postEntityRepository;

    @PostMapping
    public Post addPost(@RequestBody Post post) throws Exception {
        post = postService.addPost(post);
        return post;
    }

    @GetMapping("/{postId}")
    public Post getPostByPostId(@PathVariable UUID postId, @RequestBody Post post) throws Exception {
        return postService.getPostById(postId, post);
    }

    @PutMapping("/{postId}")
    public Post updatePostByPostId(@PathVariable UUID postId,
                                            @RequestBody Post post) throws Exception {
        return postService.updatePostById(postId, post);
    }

    @DeleteMapping("/{postId}")
    @Transactional
    public Map<String, Boolean> deletePostByPostId(@PathVariable UUID postId, @RequestBody Post post) throws Exception {
        Map<String, Boolean> response = new HashMap<>();
        boolean is_deleted = (postService.deletePostById(postId, post));
        response.put("Deleted", is_deleted);
        return response;
    }
}