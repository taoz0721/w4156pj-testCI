package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User user) {
        return userService.updateUserById(userId, user);
    }

    @DeleteMapping("/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable String userId, @RequestBody User user) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted: ", userService.deleteUserById(userId, user));
        return response;
    }

    @PutMapping("/{userId}/addFollower/{followerId}")
    public List<User> addFollower(@PathVariable String userId, @PathVariable String followerId) {
        return userService.addFollower(userId, followerId);
    }

    @PutMapping("/{userId}/deleteFollower/{followerId}")
    public List<User> deleteFollower(@PathVariable String userId, @PathVariable String followerId) {
        return userService.deleteFollower(userId, followerId);
    }

}
