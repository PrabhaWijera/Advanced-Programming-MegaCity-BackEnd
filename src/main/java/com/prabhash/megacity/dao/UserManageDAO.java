package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.User;

import java.sql.ResultSet;
import java.util.List;

public interface UserManageDAO {
    List<User> getAllUsers();
    List<User> getUsersByEmail(String email);
    List<User> getUsersByRole(String role);
    boolean updateUser(int userId, User updatedUser);
    boolean deleteUser(int userId);
    User mapResultSetToUser(ResultSet resultSet);
    String getUserEmailById(int userId);


}
