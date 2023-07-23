package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    String saveUser(User user);

    void updateUser(Long id, User user);

    void deleteUser(String name);

    boolean existsByEmail(String email);
}
