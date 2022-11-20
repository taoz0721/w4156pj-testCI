package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user) throws Exception;

    User getUser(String userId) throws Exception;

    User updateUserById(String userId, User user) throws Exception;

    Boolean deleteUserById(String userId, User user) throws Exception;

    List<User> addFollower(String userId, String followerId) throws Exception;

    List<User> deleteFollower(String userId, String followerId) throws Exception;
}
