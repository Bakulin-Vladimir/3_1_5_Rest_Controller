package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User findUserById(long id);

    void create(User user);

    public User findUserByEmail(String email);

    Set<User> findAllUsers();

    void delete(User user);
    void update(User user);

}
