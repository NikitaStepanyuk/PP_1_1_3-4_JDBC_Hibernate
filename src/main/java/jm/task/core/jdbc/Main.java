package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Иван", "Иван", (byte) 10);
        userService.saveUser("Пётр", "Петров", (byte) 20);
        userService.saveUser("Сергей", "Сергеев", (byte) 30);
        userService.saveUser("Владимир", "Владимиров", (byte) 40);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
