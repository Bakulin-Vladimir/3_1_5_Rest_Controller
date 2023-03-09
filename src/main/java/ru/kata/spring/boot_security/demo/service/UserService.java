package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserService {
    void saveUser(User user);

    Set<User> readUsers();

    User readUserId(long id);

    void updateUser(User user);

    void deleteUser(long id);

    User findUserByEmail(String email);
}
