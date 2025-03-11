package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.ProfileImage;
import com.prabhash.megacity.entity.User;

public interface UserDao {

    public boolean save(User user);
    public User findByEmail(String email);
    boolean checkPassword(User user, String password);
    boolean updatePassword(String email, String newPassword);
    ProfileImage getProfileImage(String email);
    boolean insertProfileImage(String email, String imagePath);
    public User getRandomDriver();
    public User getUserById(int id);


}
