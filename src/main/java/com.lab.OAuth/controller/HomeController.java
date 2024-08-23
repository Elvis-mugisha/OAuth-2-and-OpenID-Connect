package com.lab.OAuth.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Return the index.html page
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("email", principal.getEmail());
        }
        return "home"; // Return the home.html page
    }

    @GetMapping("/error")
    public String error() {
        return "error"; // Return the error.html page
    }
}