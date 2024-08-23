package com.lab.OAuth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            // Try to get the user's full name
            String fullName = principal.getFullName(); // This often contains the user's full name
            if (fullName == null) {
                fullName = principal.getGivenName(); // Try to get the first name
                if (fullName == null) {
                    fullName = principal.getName(); // Fallback to the sub claim if no name is found
                }
            }

            String email = principal.getEmail();
            model.addAttribute("name", fullName);
            model.addAttribute("email", email);

            logger.info("Principal full name: {}", fullName);
            logger.info("Principal email: {}", email);
        } else {
            logger.warn("Principal is null");
        }
        return "home"; // Return the home.html page
    }




    @GetMapping("/error")
    public String error() {
        return "error"; // Return the error.html page
    }
}