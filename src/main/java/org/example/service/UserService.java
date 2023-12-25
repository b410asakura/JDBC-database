package org.example.service;

import org.example.model.Booking;
import org.example.model.User;

import java.util.List;

public interface UserService {
    void createTable();
    List<User> getAllUsers();

    String saveUser(User user);

    void updateUser(Long id, User user);

    void deleteUser(String name);

    boolean existsByEmail(String email);

    User getById(Long id);


}
