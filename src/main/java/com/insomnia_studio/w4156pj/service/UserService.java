package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User addUser(User user) throws Exception;

    User getUser(UUID userId, User user) throws Exception;

    User updateUserById(UUID userId, User user) throws Exception;

    Boolean deleteUserById(UUID userId, User user) throws Exception;

    List<User> addFollower(UUID userId, UUID followerId) throws Exception;

    List<User> deleteFollower(UUID userId, UUID followerId) throws Exception;
}
