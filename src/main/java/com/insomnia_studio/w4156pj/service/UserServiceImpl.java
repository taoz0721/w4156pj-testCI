package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserEntityRepository userEntityRepository;
    private ClientEntityRepository clientEntityRepository;

    @Override
    public User addUser(User user) {
        // TO BE FIXED: Should return error message if client is not valid
        if (user.getClientId() != null && clientEntityRepository.existsByClientId(user.getClientId())) {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(user, userEntity);
            ClientEntity clientEntity = clientEntityRepository.findByClientId(user.getClientId());
            userEntity.setClient(clientEntity);
            userEntity = userEntityRepository.save(userEntity);
            user.setUserCreatedTime(userEntity.getUserCreatedTime());
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User getUser(String userId) {
        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if (userEntity != null) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            user.setClientId(userEntity.getClient().getClientId());
            return user;
        }
        else {
            return null;
        }
    }

    @Override
    public User updateUserById(String userId, User user) {
        if (user.getClientId() != null && clientEntityRepository.existsByClientId(user.getClientId())) {
            UserEntity userEntity = userEntityRepository.findByUserId(userId);
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity = userEntityRepository.save(userEntity);
            BeanUtils.copyProperties(userEntity, user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteUserById(String userId, User user) {
        if (user.getClientId() != null && clientEntityRepository.existsByClientId(user.getClientId())) {
            Boolean is_deleted = (userEntityRepository.deleteUserEntityByUserId(userId) == 1);
            return is_deleted;
        } else {
            return false;
        }
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
        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if (operation == "add") {
            userEntity.addFollower(followerId);
        } else {
            userEntity.removeFollower(followerId);
        }
        userEntity = userEntityRepository.save(userEntity);
        User user1 = new User();
        BeanUtils.copyProperties(userEntity, user1);

        userEntity = userEntityRepository.findByUserId(followerId);
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
