package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void create(User user);
    User findUserById(long id);
    void update(User user);
    void delete(long id);
    Set<User> findAllUsers();
    public User findUserByEmail(String email);
}
