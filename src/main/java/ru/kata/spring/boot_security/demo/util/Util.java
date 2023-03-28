package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    private UserService userService;

    @Autowired
    public Util(UserService userService) {
        this.userService = userService;
    }


    @PostConstruct
    private void method() {
        Set<Role> list1 = new HashSet<>();
        list1.add(new Role("ROLE_USER"));
        userService.create(new User("Петр"
                , "Петров"
                , "user"
                , (byte) 35, "user@mail.ru"
                , list1));
        Set<Role> list2 = new HashSet<>();
        list2.add(new Role("ROLE_USER"));
        list2.add(new Role("ROLE_ADMIN"));
        userService.create(new User("Олег"
                , "Иванов"
                , "admin"
                , (byte) 35, "admin@mail.ru"
                , list2));
        System.err.println("!!!Пользователи были добавлены в базу данных!!!");
    }
}
