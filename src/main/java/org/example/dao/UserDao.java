package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void saveUser(User user);

    String updateUser(Long id, User user);

    String deleteUser(String name);

    boolean existsByEmail(String email);
}
