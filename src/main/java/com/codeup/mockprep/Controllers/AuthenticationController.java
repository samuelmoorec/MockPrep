package com.codeup.mockprep.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "anonymousUser/loginForm";
    }
}
