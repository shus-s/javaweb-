package com.studyroom.dao;

import com.studyroom.model.User;
import java.util.List;

public interface UserDAO {
    User login(String username, String password);
    boolean register(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(int id);
    boolean checkUsernameExists(String username);
}



