package db.service;

import db.dao.UserDao;
import db.dao.UserDaoImpl;
import models.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public void saveUser(String name, String lastName, int age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(int id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void updateUserById(int id, String name, String lastName, int age) {
        userDao.updateUserById(id, name, lastName, age);
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
}