package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(Model model, Principal principal) {
        //Получим всех людей из DAO и передадим на отображение в представлении
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Set<Role> roles = user.getRoles();
        List<Role> collect = roles.stream().collect(Collectors.toList());
        Role role1 = collect.get(0);
        Role role2 = collect.get(1);
        model.addAttribute("usersList", userService.readUsers());
        model.addAttribute("useradmin", user);
        model.addAttribute("role1", role1);
        model.addAttribute("role2", role2);
        model.addAttribute("roles", roleService.readRoles());
        return "admin";
    }

    @PostMapping("/{id}")
    public String showIdUser(@PathVariable("id") long id, Model model) {
        //Получим одного человека по id из DAO и передадим на отображение в представление
        User user = userService.readUserId(id);
        Set<Role> roles = user.getRoles();
        List<Role> collect = roles.stream().collect(Collectors.toList());
        Role role1 = collect.get(0);
        Role role2 = collect.get(1);
        model.addAttribute("useradmin", user);
        model.addAttribute("role1", role1);
        model.addAttribute("role2", role2);
        return "admin-user";
    }

    @GetMapping("/new/{id}")
    public String newUser(@PathVariable("id") long id, Model model) {
        User user = userService.readUserId(id);
        Set<Role> roles = user.getRoles();
        List<Role> collect = roles.stream().collect(Collectors.toList());
        Role role1 = collect.get(0);
        Role role2 = collect.get(1);
        model.addAttribute("useradmin", user);
        model.addAttribute("role1", role1);
        model.addAttribute("role2", role2);
        model.addAttribute("usernew", new User());
        return "add-new-user";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("usernew") User user) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().endsWith("ADMIN"))) {
            user.setRoles(roleService.createRoleAdmin());
        } else {
            user.setRoles(roleService.createRoleUser());
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().endsWith("ADMIN"))) {
            user.setRoles(roleService.createRoleAdmin());
        } else {
            user.setRoles(roleService.createRoleUser());
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
