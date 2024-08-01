package com.chrisferdev.loginPage.controller;

import com.chrisferdev.loginPage.model.User;
import com.chrisferdev.loginPage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(){
        return "index";
    }

    @PostMapping("/index")
    public String handleForm(@RequestParam("action") String action,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam(value = "name", required = false) String name,
                             Model model){
        if ("login".equals(action)) {
            return login(email, password, model);
        } else if ("register".equals(action)) {
            return register(name, email, password, model);
        } else {
            return "redirect:/";
        }
    }

    private String login(String email, String password, Model model) {
        if(userService.authenticateUser(email, password)){
            model.addAttribute("email", email);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Email o Contraseña invalido");
            return "redirect:/";
        }
    }

    private String register(String name, String email, String password, Model model) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userService.registerUser(user);

        model.addAttribute("name", name);
        return "redirect:/";
    }
}
