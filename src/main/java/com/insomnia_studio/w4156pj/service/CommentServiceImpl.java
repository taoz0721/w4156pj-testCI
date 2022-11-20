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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Comment addComment(Comment comment, UUID postId) throws ResponseStatusException{
        if (postEntityRepository.existsByPostId(postId)) {
            if (comment.getClientId() != null && clientEntityRepository.existsByClientId(comment.getClientId())) {
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
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post ID not found");
        }

        return comment;
    }

    @Override
    public Comment getCommentById(UUID commentId) throws ResponseStatusException {
        // need_TODO: add client authentication.
        // need_TODO: add invalid client ID exception after client authentication is added.
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment ID not found", e);
            //throw new Exception("Could not find postId: " + e);

        }
    }

    @Override
    public Comment updateCommentById(UUID commentId, Comment comment) throws ResponseStatusException {
        // need_TODO: revise exception after client
        if (comment.getClientId() != null && clientEntityRepository.existsByClientId(comment.getClientId())) {
            try {
                CommentEntity commentEntity = commentEntityRepository.findByCommentId(commentId);
                commentEntity.setContent(comment.getContent());
                commentEntity = commentEntityRepository.save(commentEntity);
                BeanUtils.copyProperties(commentEntity, comment);
                return comment;
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment ID not found", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid client ID");
        }

    }

    @Override
    public Boolean deleteCommentById(UUID commentId, Comment comment) throws ResponseStatusException{
        if (comment.getClientId() != null && clientEntityRepository.existsByClientId(comment.getClientId())) {
            Boolean is_deleted = (commentEntityRepository.deleteCommentEntityByCommentId(commentId) == 1);
            if (!is_deleted){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment ID not found");
            }
            else {
                return is_deleted;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
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

