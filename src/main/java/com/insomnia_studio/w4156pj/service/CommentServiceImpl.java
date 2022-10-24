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
    public Optional<Comment> addComment(Comment comment) {
        // TO BE FIXED: Different response if the comment cannot be inserted
        if (postEntityRepository.existsByPostId(comment.getPost().getPostId())) {
        //if (postEntityRepository.existsByPostId(comment.getPostId())) {
            CommentEntity commentEntity = new CommentEntity();

            BeanUtils.copyProperties(comment, commentEntity);
            commentEntity = commentRepository.save(commentEntity);
            comment.setCommentId(commentEntity.getCommentId());
        }
        else {
            //return null?
            return Optional.empty();
        }

        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> getCommentById(UUID commentId) {
        Optional<CommentEntity> commentEntity = commentRepository.findByCommentId(commentId);
        if (commentEntity.isEmpty()) {
            return Optional.empty();
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity.get(), comment);

        return Optional.of(comment);
    }

    @Override
    public Boolean deleteCommentById(UUID commentId) {
        Boolean is_deleted = (commentRepository.deleteCommentEntityByCommentId(commentId) == 1);
        return is_deleted;
    }

    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void setPostEntityRepository(PostEntityRepository postEntityRepository) {
        this.postEntityRepository = postEntityRepository;
    }
}
