package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.repository.CommentEntityRepository;
import com.insomnia_studio.w4156pj.service.CommentService;
import com.insomnia_studio.w4156pj.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")

@AllArgsConstructor
public class CommentController {
    private CommentService commentService;
    private CommentEntityRepository commentEntityRepository;

    //create
    @PostMapping("/post/{postId}/comment/add")
    public Comment addComment(@RequestBody Comment comment, @PathVariable UUID postId){

        return commentService.addComment(comment, postId);
    }

    //get
    @GetMapping("/comment/{commentId}")
    public Comment getCommentByCommentId(@PathVariable UUID commentId) {
        return commentService.getCommentById(commentId);
    }

    //delete
    @DeleteMapping("/comment/{commentId}")
    @Transactional
    public Map<String, Boolean> deleteCommentByCommentId(@PathVariable UUID commentId) {
        Map<String, Boolean> response = new HashMap<>();
        boolean is_deleted = (commentService.deleteCommentById(commentId));
        response.put("Deleted", is_deleted);
        return response;
    }


}
