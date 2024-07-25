package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        User first = new User("Ivan", "Ivanov", (byte) 19);
        User second = new User("Boris", "Borisov", (byte) 20);
        User third = new User("Karabas", "Barabas", (byte) 54);
        User fourth = new User("Van", "Darkholme", (byte) 51);

        userService.createUsersTable();

        userService.saveUser(first.getName(), first.getLastName(), first.getAge());
        userService.saveUser(second.getName(), second.getLastName(), second.getAge());
        userService.saveUser(third.getName(), third.getLastName(), third.getAge());
        userService.saveUser(fourth.getName(), fourth.getLastName(), fourth.getAge());

        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
