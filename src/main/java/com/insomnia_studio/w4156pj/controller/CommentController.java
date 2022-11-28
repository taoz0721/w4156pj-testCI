package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1")

@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    //create
    @PostMapping("/post/{postId}/comment")
    public Comment addComment(@RequestBody Comment comment,
                              @PathVariable UUID postId) throws Exception{
        return commentService.addComment(comment, postId);
    }

    //get
    @GetMapping("/comment/{commentId}")
    public Comment getCommentByCommentId(@PathVariable UUID commentId,
                                         @RequestBody Comment comment) throws Exception {
        return commentService.getCommentById(commentId, comment);
    }

    @PutMapping("/comment/{commentId}")
    public Comment updateCommentByCommentId(@PathVariable UUID commentId,
                                            @RequestBody Comment comment) throws Exception {
        return commentService.updateCommentById(commentId, comment);
    }

    @PostMapping("/comment/{commentId}/addLike")
    public Comment addLikeByCommentId(@PathVariable UUID commentId,
                                      @RequestBody Comment comment) {
        return commentService.addLikeById(commentId, comment);
    }

    @PostMapping("/comment/{commentId}/addDislike")
    public Comment addDislikeByCommentId(@PathVariable UUID commentId,
                                         @RequestBody Comment comment) {
        return commentService.addDislikeById(commentId, comment);
    }

    //delete
    @DeleteMapping("/comment/{commentId}")
    @Transactional
    public Map<String, Boolean> deleteCommentByCommentId(@PathVariable UUID commentId,
                                                         @RequestBody Comment comment) throws Exception {
        Map<String, Boolean> response = new HashMap<>();
        boolean is_deleted = (commentService.deleteCommentById(commentId, comment));
        response.put("Deleted", is_deleted);
        return response;
    }


}
