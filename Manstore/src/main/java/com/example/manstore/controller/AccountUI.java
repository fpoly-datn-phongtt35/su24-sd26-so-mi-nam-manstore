package com.example.manstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountUI {

    @RequestMapping("/auth-logout-basic")
    public String logout() {
        return "auth/auth-logout-basic";
    }

    @RequestMapping("/reset-password")
    public String reset() {
        return "auth/auth-pass-reset-basic";
    }

    @RequestMapping("/login")
    public String signin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            if (error.equalsIgnoreCase("Unauthorized")) {
                model.addAttribute("Unauthorized", "");
            }
            if (error.equalsIgnoreCase("Expired")) {
                model.addAttribute("Expired", "");
            }
        }
        return "auth/auth-signin-basic";
    }

    @RequestMapping("/auth-signup-basic")
    public String signup() {
        return "auth/auth-signup-basic";
    }
}
