package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
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
            comment.setCommentCreatedTime(commentEntity.getCommentCreatedTime());
            comment.setCommentUpdatedTime(commentEntity.getCommentUpdatedTime());
            comment.setPostId(postEntity.getPostId());
        }
        else {
            return null;
        }

        return comment;
    }

    @Override
    public Comment getCommentById(UUID commentId) throws Exception {
        try{
            CommentEntity commentEntity = commentEntityRepository.findByCommentId(commentId);
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentEntity, comment);
            comment.setUserId(commentEntity.getUser().getUserId());
            comment.setClientId(commentEntity.getClient().getClientId());
            comment.setPostId(commentEntity.getPost().getPostId());
            return comment;
        }
        catch (Exception e){
            throw new Exception("Could not find postId: " + e);
        }
    }

    @Override
    public Comment updateCommentById(UUID commentId, Comment comment) throws Exception {
        try {
            if (comment.getClientId() != null && clientEntityRepository.existsByClientId(comment.getClientId())) {
                CommentEntity commentEntity = commentEntityRepository.findByCommentId(commentId);
                commentEntity.setContent(comment.getContent());
                commentEntity = commentEntityRepository.save(commentEntity);
                BeanUtils.copyProperties(commentEntity, comment);
                return comment;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Could not update comment: " + e);
        }
    }

    @Override
    public Boolean deleteCommentById(UUID commentId, Comment comment) {
        if (comment.getClientId() != null && clientEntityRepository.existsByClientId(comment.getClientId())) {
            Boolean is_deleted = (commentEntityRepository.deleteCommentEntityByCommentId(commentId) == 1);
            return is_deleted;
        } else {
            return false;
        }
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

