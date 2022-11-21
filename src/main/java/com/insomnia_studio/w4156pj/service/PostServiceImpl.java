package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private PostEntityRepository postEntityRepository;
    private ClientEntityRepository clientEntityRepository;

    private UserEntityRepository userEntityRepository;

    @Override
    public Post addPost(Post post) throws ResponseStatusException {
        if (post.getClientId() != null && clientEntityRepository.existsByClientId(post.getClientId())) {
            PostEntity postEntity = new PostEntity();
            BeanUtils.copyProperties(post, postEntity);
            UserEntity userEntity = userEntityRepository.findByUserId(post.getUserId());
            if (userEntity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found");
            } else if (userEntity.getClient().getClientId().compareTo(post.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            postEntity.setUser(userEntity);
            ClientEntity clientEntity = clientEntityRepository.findByClientId(post.getClientId());
            postEntity.setClient(clientEntity);
            postEntity = postEntityRepository.save(postEntity);
            post.setPostId(postEntity.getPostId());
            post.setPostCreatedTime(postEntity.getPostCreatedTime());
            post.setPostUpdatedTime(postEntity.getPostUpdatedTime());
            return post;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
        }
    }

    @Override
    public Post getPostById(UUID postId, Post post) throws ResponseStatusException {
        PostEntity postEntity = postEntityRepository.findByPostId(postId);
        if (postEntity != null) {
            if (postEntity.getClient().getClientId().compareTo(post.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            Post responsePost = new Post();
            BeanUtils.copyProperties(postEntity, responsePost);
            responsePost.setUserId(postEntity.getUser().getUserId());
            responsePost.setClientId(postEntity.getClient().getClientId());
            return responsePost;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
        }
    }

    @Override
    public Post updatePostById(UUID postId, Post post) throws ResponseStatusException {

        PostEntity postEntity = postEntityRepository.findByPostId(postId);
        if (postEntity != null) {
            if (postEntity.getClient().getClientId().compareTo(post.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            postEntity.setTitle(post.getTitle());
            postEntity.setContent(post.getContent());
            postEntity.setTags(post.getTags());
            postEntity = postEntityRepository.save(postEntity);
            BeanUtils.copyProperties(postEntity, post);
            post.setUserId(postEntity.getUser().getUserId());
            post.setClientId(postEntity.getClient().getClientId());
            return post;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
        }
    }

    @Override
    public Boolean deletePostById(UUID postId, Post post) throws ResponseStatusException{
        PostEntity postEntity = postEntityRepository.findByPostId(postId);
        if (postEntity != null) {
            if (postEntity.getClient().getClientId().compareTo(post.getClientId()) != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
            }
            Boolean is_deleted = (postEntityRepository.deletePostEntityByPostId(postId) == 1);
            return is_deleted;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
        }
    }
}
