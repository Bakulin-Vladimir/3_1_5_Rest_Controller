package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginContoller {

    @GetMapping()
    public String getLoginPage() {
        return "login";
    }

}
