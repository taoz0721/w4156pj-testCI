package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.repository.CommentEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post/{postId}/comment/")
@AllArgsConstructor
public class CommentController {
    private CommentEntityRepository commentEntityRepository;

}
