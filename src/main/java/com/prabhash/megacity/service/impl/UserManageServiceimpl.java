package com.prabhash.megacity.service.impl;


import com.prabhash.megacity.dao.UserManageDAO;
import com.prabhash.megacity.dao.impl.UserManageDAOimpl;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserManageService;

import java.util.List;
import java.util.Random;

public class UserManageServiceimpl implements UserManageService {

    private UserManageDAO userManagDAO = new UserManageDAOimpl();

    @Override
    public List<User> getAllUsers() {
        return userManagDAO.getAllUsers();
    }

    @Override
    public List<User> getUsersByEmail(String email) {
        return userManagDAO.getUsersByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userManagDAO.getUsersByRole(role);
    }

    @Override
    public boolean updateUser(int userId, User updatedUser) {
        return userManagDAO.updateUser(userId, updatedUser);
    }

    @Override
    public boolean deleteUser(int userId) {
        return userManagDAO.deleteUser(userId);
    }

    // Method to fetch a random driver
    public User getRandomDriver() {
        // Get all drivers using the existing getUsersByRole method
        List<User> drivers = userManagDAO.getUsersByRole("driver");

        if (drivers == null || drivers.isEmpty()) {
            return null;  // No drivers available
        }

        // Select a random driver from the list
        Random random = new Random();
        int randomIndex = random.nextInt(drivers.size());  // Get a random index
        return drivers.get(randomIndex);  // Return the randomly selected driver
    }

    @Override
    public String getUserById(int userId) {
        return userManagDAO.getUserEmailById(userId);
    }
}
