package com.prabhash.megacity.service;

import com.prabhash.megacity.entity.User;

import java.util.List;

public interface UserManageService {
    List<User> getAllUsers();

    List<User> getUsersByEmail(String email);
    List<User> getUsersByRole(String role);
    boolean updateUser(int userId, User updatedUser);
    boolean deleteUser(int userId);
    public User getRandomDriver();
}
