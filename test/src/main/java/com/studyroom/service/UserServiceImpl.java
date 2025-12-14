package com.studyroom.service;

import com.studyroom.dao.UserDAO;
import com.studyroom.dao.UserDAOImpl;
import com.studyroom.model.User;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public User login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return userDAO.login(username, password);
    }

    @Override
    public boolean register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        if (userDAO.checkUsernameExists(user.getUsername())) {
            return false;
        }
        return userDAO.register(user);
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() <= 0) {
            return false;
        }
        return userDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        if (id <= 0) {
            return false;
        }
        return userDAO.deleteUser(id);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userDAO.checkUsernameExists(username);
    }
}



