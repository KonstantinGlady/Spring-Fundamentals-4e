package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

    @GetMapping("/hello")
    public String helloWorld(@RequestParam(defaultValue = "Welcome") String name, Model model) {
        model.addAttribute("user", name);

        return "welcome";
    }
}
