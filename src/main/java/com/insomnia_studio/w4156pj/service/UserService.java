package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User getUser(String userId);

    User updateUserById(String userId, User user);

    Boolean deleteUserById(String userId);

    List<User> addFollower(String userId, String followerId);

    List<User> deleteFollower(String userId, String followerId);
}
