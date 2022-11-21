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
        PostEntity postEntity = postEntityRepository.findByPostId(postId);
        if (postEntity != null) {
            if (postEntity.getClient().getClientId().compareTo(comment.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            CommentEntity commentEntity = new CommentEntity();
            BeanUtils.copyProperties(comment, commentEntity);
            commentEntity.setPost(postEntity);
            UserEntity userEntity = userEntityRepository.findByUserId(comment.getUserId());
            if (userEntity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found");
            } else if (userEntity.getClient().getClientId().compareTo(comment.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found");
            }
            commentEntity.setUser(userEntity);
            ClientEntity clientEntity = clientEntityRepository.findByClientId(comment.getClientId());
            commentEntity.setClient(clientEntity);
            commentEntity = commentEntityRepository.save(commentEntity);
            comment.setCommentId(commentEntity.getCommentId());
            comment.setCommentCreatedTime(commentEntity.getCommentCreatedTime());
            comment.setCommentUpdatedTime(commentEntity.getCommentUpdatedTime());
            comment.setPostId(postEntity.getPostId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
        }

        return comment;
    }

    @Override
    public Comment getCommentById(UUID commentId, Comment comment) throws ResponseStatusException {
        // need_TODO: add client authentication.
        // need_TODO: add invalid client ID exception after client authentication is added.
        CommentEntity commentEntity = commentEntityRepository.findByCommentId(commentId);
        if (commentEntity != null) {
            if (commentEntity.getClient().getClientId().compareTo(comment.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            Comment responseComment = new Comment();
            BeanUtils.copyProperties(commentEntity, responseComment);
            responseComment.setUserId(commentEntity.getUser().getUserId());
            responseComment.setClientId(commentEntity.getClient().getClientId());
            responseComment.setPostId(commentEntity.getPost().getPostId());
            return responseComment;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment ID not found");
        }
    }

    @Override
    public Comment updateCommentById(UUID commentId, Comment comment) throws ResponseStatusException {
        CommentEntity commentEntity = commentEntityRepository.findByCommentId(commentId);
        if (commentEntity != null) {
            if (commentEntity.getClient().getClientId().compareTo(comment.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            commentEntity.setContent(comment.getContent());
            commentEntity = commentEntityRepository.save(commentEntity);
            BeanUtils.copyProperties(commentEntity, comment);
            comment.setPostId(commentEntity.getPost().getPostId());
            return comment;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment ID not found");
        }
    }

    @Override
    public Boolean deleteCommentById(UUID commentId, Comment comment) throws ResponseStatusException{
        CommentEntity commentEntity = commentEntityRepository.findByCommentId(commentId);
        if (commentEntity != null) {
            if (commentEntity.getClient().getClientId().compareTo(comment.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            Boolean is_deleted = (postEntityRepository.deletePostEntityByPostId(commentId) == 1);
            return is_deleted;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment ID not found");
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

