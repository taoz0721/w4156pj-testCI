package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserEntityRepository userEntityRepository;
    private ClientEntityRepository clientEntityRepository;

    @Override
    public User addUser(User user) throws ResponseStatusException {
        if (user.getClientId() != null && clientEntityRepository.existsByClientId(user.getClientId())) {
            try {
                UserEntity userEntity = new UserEntity();
                BeanUtils.copyProperties(user, userEntity);
                ClientEntity clientEntity = clientEntityRepository.findByClientId(user.getClientId());
                userEntity.setClient(clientEntity);
                userEntity = userEntityRepository.save(userEntity);
                user.setUserCreatedTime(userEntity.getUserCreatedTime());
                return user;
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing User ID");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
        }
    }

    @Override
    public User getUser(String userId) throws ResponseStatusException {
        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if (userEntity != null) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            user.setClientId(userEntity.getClient().getClientId());
            return user;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
        }
    }

    @Override
    public User updateUserById(String userId, User user) throws ResponseStatusException {
        //need_TODO: add client authentication or other client distinguishing method
        if (user.getClientId() != null && clientEntityRepository.existsByClientId(user.getClientId())) {
            try {
                UserEntity userEntity = userEntityRepository.findByUserId(userId);
                userEntity.setFirstName(user.getFirstName());
                userEntity.setLastName(user.getLastName());
                userEntity = userEntityRepository.save(userEntity);
                BeanUtils.copyProperties(userEntity, user);
                return user;
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
        }
    }

    @Override
    @Transactional
    public Boolean deleteUserById(String userId, User user) throws ResponseStatusException {
        if (user.getClientId() != null && clientEntityRepository.existsByClientId(user.getClientId())) {
            Boolean is_deleted = (userEntityRepository.deleteUserEntityByUserId(userId) == 1);
            if (!is_deleted){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
            }
            else {
                return is_deleted;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
        }
    }

    @Override
    public List<User> addFollower(String userId, String followerId) throws Exception{
        return changeFollower(userId, followerId, "add");
    }

    @Override
    public List<User> deleteFollower(String userId, String followerId) throws Exception{
        return changeFollower(userId, followerId, "remove");
    }

    private List<User> changeFollower(String userId, String followerId, String operation) throws ResponseStatusException {
        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if (operation == "add") {
            try {
                userEntity.addFollower(followerId);
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
            }
        } else{
            try {
                userEntity.removeFollower(followerId);
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
            }
        }
        userEntity = userEntityRepository.save(userEntity);
        User user1 = new User();
        BeanUtils.copyProperties(userEntity, user1);

        userEntity = userEntityRepository.findByUserId(followerId);
        if (operation == "add") {
            try {
                userEntity.addFollowedBy(userId);
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
            }
        } else {
            try {
                userEntity.removeFollowBy(userId);
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user ID not found");
            }
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
