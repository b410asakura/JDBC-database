package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.UserDao;
import org.example.model.Movie;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE users (
                                        id SERIAL PRIMARY KEY,
                                        username VARCHAR(255) NOT NULL,
                                        password VARCHAR(255) NOT NULL,
                                        email VARCHAR(255) NOT NULL) """;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("table users created");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                                        select * from users
                        """)) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void saveUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into users(user_name,password,email)  
                values(?,?,?)             
                """);) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateUser(Long id, User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                update users set user_name=?,password=?,email=? where id=?
                """)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Successfully updated user with id=" + id;
    }

    @Override
    public String deleteUser(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                delete from users where user_name=?
                """);) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Successfully deleted user with name " + name;
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean found = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                            select * from users where email=?
                           
                """);) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(Long id) {
        User user = new User();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                                            select * from users where id=?
                            """);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("User with id :" + id + " not found!");
            } else {
                user.setId(resultSet.getLong("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
