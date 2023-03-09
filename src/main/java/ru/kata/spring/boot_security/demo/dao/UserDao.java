package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserDao {
    void saveUser(User user);

    User readUserID(long id);

    Set<User> readUsers();

    void updateUser(User user);

    void deleteUser(long id);

    User findUserByEmail(String email);
}
