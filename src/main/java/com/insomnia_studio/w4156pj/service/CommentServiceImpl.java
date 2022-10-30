package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.repository.CommentEntityRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentEntityRepository commentEntityRepository;
    private PostEntityRepository postEntityRepository;
    private ClientEntityRepository clientEntityRepository;

    private UserEntityRepository userEntityRepository;

    public CommentServiceImpl(CommentEntityRepository commentEntityRepository, PostEntityRepository postEntityRepository, ClientEntityRepository clientEntityRepository, UserEntityRepository userEntityRepository) {
        this.commentEntityRepository = commentEntityRepository;
        this.postEntityRepository = postEntityRepository;
        this.clientEntityRepository = clientEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }



    @Override
    public Comment addComment(Comment comment, UUID postId) {
        if (postEntityRepository.existsByPostId(postId) &&
                comment.getClientId() != null && clientEntityRepository.existsByClientId(comment.getClientId())) {
            CommentEntity commentEntity = new CommentEntity();
            BeanUtils.copyProperties(comment, commentEntity);
            PostEntity postEntity = postEntityRepository.findByPostId(postId);
            commentEntity.setPost(postEntity);
            UserEntity userEntity = userEntityRepository.findByUserId(comment.getUserId());
            commentEntity.setUser(userEntity);
            ClientEntity clientEntity = clientEntityRepository.findByClientId(comment.getClientId());
            commentEntity.setClient(clientEntity);
            commentEntity = commentEntityRepository.save(commentEntity);
            comment.setCommentId(commentEntity.getCommentId());

        }
        else {
            return null;
        }

        return comment;
    }

    @Override
    public Comment getCommentById(UUID commentId) {
        Optional<CommentEntity> commentEntity = commentEntityRepository.findByCommentId(commentId);
        if (commentEntity.isEmpty()) {
            return null;
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity.get(), comment);

        return comment;
    }

    @Override
    public Comment updateCommentById(UUID commentId, Comment comment) {
        return null;
    }

    @Override
    public Boolean deleteCommentById(UUID commentId, Comment comment) {
        return null;
    }


    public void setCommentRepository(CommentEntityRepository commentEntityRepository) {
        this.commentEntityRepository = commentEntityRepository;
    }

    public void setPostEntityRepository(PostEntityRepository postEntityRepository) {
        this.postEntityRepository = postEntityRepository;
    }

    public void setUserEntityRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void setClientRepository(ClientEntityRepository clientEntityRepository) {
        this.clientEntityRepository = clientEntityRepository;
    }
}

