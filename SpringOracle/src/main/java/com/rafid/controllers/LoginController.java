package com.rafid.controllers;


import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginCheck(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password) {
        List<Users> result = userRepository.findByEmailAndPassword(email,password);
        if (result.isEmpty()){
            return "redirect:/login";
        }
        else {
            session.setAttribute("username", result.get(0).getUserName());
            return "redirect:/home";
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/login";
    }

}
