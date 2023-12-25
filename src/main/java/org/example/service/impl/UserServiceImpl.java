package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.Booking;
import org.example.model.User;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDaoImpl();

    @Override
    public void createTable() {
        userDao.createTable();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers() ;
    }

    @Override
    public String saveUser(User user) {
        userDao.saveUser(user);
        return "User is successfully saved!";
    }

    @Override
    public void updateUser(Long id, User user) {
        System.out.println(userDao.updateUser(id, user));
    }

    @Override
    public void deleteUser(String name) {
        System.out.println(userDao.deleteUser(name));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }


}
