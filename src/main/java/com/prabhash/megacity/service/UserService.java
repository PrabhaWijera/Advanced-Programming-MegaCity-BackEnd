package com.prabhash.megacity.service;

import com.prabhash.megacity.dto.ProfileImageDTO;
import com.prabhash.megacity.entity.User;

public interface UserService {
    boolean registerUser(User user);

    User authenticateUser(String username, String password);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
//    UserDTO getUserByUsername(String username);


    boolean uploadProfileImage(String email, String imagePath);  // Upload profile image
    ProfileImageDTO getProfileImage(String email);  // Retrieve profile image details
}
