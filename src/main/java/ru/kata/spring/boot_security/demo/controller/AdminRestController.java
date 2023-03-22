package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Set<User>> showAllUsers(){
        Set<User> allUsers = userService.findAllUsers();
        return allUsers != null && !allUsers.isEmpty() ?
                new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> showUser(@PathVariable("id") long id){
        User userById = userService.findUserById(id);
        return userById != null
                ? new ResponseEntity<>(userById, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> showCreateUser(@RequestBody User user) {
        if(user.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN"))){
            user.setRoles(roleService.createRoleAdmin());
        }else{
            user.setRoles(roleService.createRoleUser());
        }
        userService.create(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> showUpdateUser(@RequestBody User user) {
        if(user.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN"))){
            user.setRoles(roleService.createRoleAdmin());
        }else{
            user.setRoles(roleService.createRoleUser());
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> showDeleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> showAllRoles() {
        return new ResponseEntity<>(roleService.readRoles(), HttpStatus.OK);
    }
}
