package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserEntityRepository userEntityRepository;

    @Override
    public User addUser(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity = userEntityRepository.save(userEntity);
        user.setUserCreatedTime(userEntity.getUserCreatedTime());
        return user;
    }

    @Override
    public User getUser(String userId) {
        UserEntity userEntity = userEntityRepository.findByUserId(userId).get();
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public User updateUserById(String userId, User user) {
        UserEntity userEntity = userEntityRepository.findByUserId(userId).get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity = userEntityRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public Boolean deleteUserById(String userId) {
        Boolean is_deleted = (userEntityRepository.deleteUserEntityByUserId(userId) == 1);
        return is_deleted;
    }

    @Override
    public List<User> addFollower(String userId, String followerId) {
        return changeFollower(userId, followerId, "add");
    }

    @Override
    public List<User> deleteFollower(String userId, String followerId) {
        return changeFollower(userId, followerId, "remove");
    }

    private List<User> changeFollower(String userId, String followerId, String operation) {
        UserEntity userEntity = userEntityRepository.findByUserId(userId).get();
        if (operation == "add") {
            userEntity.addFollower(followerId);
        } else {
            userEntity.removeFollower(followerId);
        }
        userEntity = userEntityRepository.save(userEntity);
        User user1 = new User();
        BeanUtils.copyProperties(userEntity, user1);

        userEntity = userEntityRepository.findByUserId(followerId).get();
        if (operation == "add") {
            userEntity.addFollowedBy(userId);
        } else {
            userEntity.removeFollowBy(userId);
        }
        userEntity = userEntityRepository.save(userEntity);
        User user2 = new User();
        BeanUtils.copyProperties(userEntity, user2);

        List<User> response =new ArrayList<User>();
        response.add(user1);
        response.add(user2);
        return response;
    }
}
