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
@RequestMapping("/authorized")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    private static long idAdmin;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String getUsers(Model model, Principal principal) {
        //Получим всех людей из DAO и передадим на отображение в представлении
        User user = userService.findUserByEmail(principal.getName());
        //Получим ID админа, чтобы можно было его редактировать
        idAdmin = user.getId();
        model.addAttribute("usersList", userService.readUsers());
        model.addAttribute("useradmin", user);
        model.addAttribute("roles", roleService.readRoles());
        return "admin";
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        //Получим всех людей из DAO и передадим на отображение в представлении
        model.addAttribute("usersList", userService.readUsers());
        model.addAttribute("useradmin", userService.readUserId(idAdmin));
        model.addAttribute("roles", roleService.readRoles());
        return "admin";
    }

    @PostMapping("admin/{id}")
    public String showIdUser(@PathVariable("id") long id, Model model) {
        //Получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("useradmin", userService.readUserId(id));
        return "admin-user";
    }

    @GetMapping("admin/new/{id}")
    public String newUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("useradmin", userService.readUserId(id));
        model.addAttribute("usernew", new User());
        return "add-new-user";
    }

    @PostMapping("/admin")
    public String saveUser(@ModelAttribute("usernew") User user) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().endsWith("ADMIN"))) {
            user.setRoles(roleService.createRoleAdmin());
        } else {
            user.setRoles(roleService.createRoleUser());
        }
        //Добавление нового пользователя в базу данных
        userService.saveUser(user);
        return "redirect:/authorized/admin";
    }

    @PatchMapping("admin/update/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().endsWith("ADMIN"))) {
            user.setRoles(roleService.createRoleAdmin());
        } else {
            user.setRoles(roleService.createRoleUser());
        }
        userService.updateUser(user);
        return "redirect:/authorized/admin";
    }

    @DeleteMapping("admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/authorized/admin";
    }

}
