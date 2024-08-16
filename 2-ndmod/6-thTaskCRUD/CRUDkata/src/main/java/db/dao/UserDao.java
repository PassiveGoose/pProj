package db.dao;

import models.User;

import java.util.List;

public interface UserDao {

    void saveUser(String name, String lastName, int age);

    void removeUserById(int id);

    List<User> getAllUsers();

    void updateUserById(int id, String name, String lastName, int age);

    User getUserById(int id);
}
