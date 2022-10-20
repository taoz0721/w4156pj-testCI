package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.repository.CommentRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private PostEntityRepository postEntityRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostEntityRepository postEntityRepository) {
        this.commentRepository = commentRepository;
        this.postEntityRepository = postEntityRepository;
    }



    @Override
    public Comment createComment(Comment comment) {
        // TO BE FIXED: Different response if the comment cannot be inserted
        if (postEntityRepository.existsByPostId(comment.getPostId())) {
            CommentEntity commentEntity = new CommentEntity();

            BeanUtils.copyProperties(comment, commentEntity);
            commentRepository.save(commentEntity);
        }

        return comment;
    }

    @Override
    public Optional<Comment> getCommentById(UUID commentId) {
        Optional<CommentEntity> commentEntity = commentRepository.findCommentEntitiesByCommentId(commentId);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity, comment);
        return Optional.of(comment);
    }
}
