package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.repository.ClientRepository;
import com.insomnia_studio.w4156pj.repository.CommentRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private PostEntityRepository postEntityRepository;
    private ClientRepository clientRepository;

    private UserEntityRepository userEntityRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostEntityRepository postEntityRepository, ClientRepository clientRepository, UserEntityRepository userEntityRepository) {
        this.commentRepository = commentRepository;
        this.postEntityRepository = postEntityRepository;
        this.clientRepository = clientRepository;
        this.userEntityRepository = userEntityRepository;
    }



    @Override
    public Comment addComment(Comment comment) {
        if (postEntityRepository.existsByPostId(comment.getPostId()) &&
                comment.getClientId() != null && clientRepository.existsByClientId(comment.getClientId())) {
            //if (postEntityRepository.existsByPostId(comment.getPostId())) {
            CommentEntity commentEntity = new CommentEntity();
            BeanUtils.copyProperties(comment, commentEntity);
            PostEntity post = postEntityRepository.findByPostId(comment.getPostId());
            commentEntity.setPost(post);
            UserEntity user = userEntityRepository.findByUserId(comment.getUserId());
            commentEntity.setUser(user);
            commentEntity = commentRepository.save(commentEntity);
            comment.setCommentId(commentEntity.getCommentId());
        }
        else {
            //return null?
            return null;
        }

        return comment;
    }

    @Override
    public Comment getCommentById(UUID commentId) {
        Optional<CommentEntity> commentEntity = commentRepository.findByCommentId(commentId);
        if (commentEntity.isEmpty()) {
            return null;
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity.get(), comment);

        return comment;
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

    public void setUserEntityRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}

